#!/bin/sh

arg=$1

if [ "$#" -ne 1 ] || ([ $arg != "kafka-dbz" ] && [ $arg != "flink-cdc" ]); then
    echo "usage: ./execute_demo_steps.sh kafka-dbz|flink-cdc"
    exit 1
fi

echo "INFO: running containers:"
docker ps --format "table {{.Names}}"

read -p "INFO: configuring the microservice read path step by step"

echo "STEP 1: change proxy config to route owner reads to microservice"

./proxy_config.sh read $arg

if [ $arg = 'kafka-dbz' ]; then
    read -p "STEP 2: register MySQL source connector for owners + pets tables"
    http POST http://localhost:8083/connectors/ < register-mysql-source-owners-pets.json

    read -p "INFO: inspect owners data in kafka"

    docker run --tty --rm \
        --network flinkforward21_default \
        debezium/tooling:1.1 \
        kafkacat -b kafka:9092 -C -t mysql1.petclinic.owners -o beginning -q | jq .

    read -p "INFO: inspect pets data in kafka"

    docker run --tty --rm \
        --network flinkforward21_default \
        debezium/tooling:1.1 \
        kafkacat -b kafka:9092 -C -t mysql1.petclinic.pets -o beginning -q | jq .
fi

read -p "INFO: inspect flink joined data in kafka"

docker run --tty --rm \
    --network flinkforward21_default \
    debezium/tooling:1.1 \
    kafkacat -b kafka:9092 -C -t flink_owner_with_pets -o beginning -q | jq .

read -p "STEP 3: register MongoDB sink connector for joined owners with pets topic"

http POST http://localhost:8083/connectors/ < register-mongodb-sink-owners-pets.json

echo "INFO: configuring the microservice write path step by step"

read -p "STEP 4: change proxy config to route owner writes to microservice"

./proxy_config.sh read_write $arg

read -p "STEP 5: update MySQL source connector to ignore owner table"

http PUT http://localhost:8083/connectors/petclinic-owners-pets-mysql-src-001/config < update-mysql-source-owners-pets.json

read -p "STEP 6: configure MongoDB source connector for owners with pets collection"
http POST http://localhost:8083/connectors/ < register-mongodb-source-owners.json

echo  "STEP 7: configure MySQL JDBC sink connector for owners table"
http POST http://localhost:8083/connectors/ < register-jdbc-mysql-sink-owners.json

echo "Done! THX for watching :)"
