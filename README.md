# Strangler Fig Pattern Demo

## Build applications

Before being able to spin up the docker-compose based demo environment please make sure to successfully **build all 4 projects - the petclinic monolith based on Spring, the owner microservice powered by Quarkus, the custom Flink functions as well as the Flink table joiner application:**

```
./build-applications.sh
```

## Run with Docker

Spin up the demo environment by means of Docker compose.

* If you want to run the demo using Kafka Connect with Debezium and source the Flink Table API application from Kafka topics run it with

```
docker compose -f docker-compose-table-api-kafka-dbz.yaml up
```

* Otherwise, the demo can also Flink CDC based on Debezium to directly source the Flink SQL application from the database. To do so, run it with:

```
docker compose -f docker-compose-sql-flink-cdc.yaml up
```

## Execute Strangler Fig Pattern Demo

There is a simple, yet convenient script which allows to run through the demo step-by-step. Once the docker compose stack is successfully up just run the demo in either of the two provided modes:

* Debezium with Kafka Connect and Flink Table API application:

```
./execute_demo_steps.sh kafka-dbz
```

* Flink CDC based on Debezium with Flink SQL application:

```
./execute_demo_steps.sh flink-cdc
```
