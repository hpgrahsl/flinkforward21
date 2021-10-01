-- register custom user-defined functions
CREATE FUNCTION ff21_to_owner AS 'com.github.hpgrahsl.flink.udf.ToOwner' LANGUAGE JAVA;
CREATE FUNCTION ff21_to_pet AS 'com.github.hpgrahsl.flink.udf.ToPet' LANGUAGE JAVA;
CREATE FUNCTION ff21_agg_owner_pets AS 'com.github.hpgrahsl.flink.udf.OwnerWithPetsAggregator' LANGUAGE JAVA;

-- create kafka source connector table definitions for topics in DBZ json format
CREATE TABLE dbz_mysql_owners (id INT, first_name STRING, last_name STRING, address STRING, city STRING, telephone STRING) WITH ('connector' = 'kafka', 'topic' = 'mysql1.petclinic.owners', 'properties.bootstrap.servers' = 'kafka:9092', 'properties.group.id' = 'somegrp1234', 'scan.startup.mode' = 'earliest-offset', 'format' = 'debezium-json');
CREATE TABLE dbz_mysql_pets (id INT, name STRING, birth_date STRING, type_id INT, owner_id INT) WITH ('connector' = 'kafka', 'topic' = 'mysql1.petclinic.pets', 'properties.bootstrap.servers' = 'kafka:9092', 'properties.group.id' = 'somegrp1234', 'scan.startup.mode' = 'earliest-offset', 'format' = 'debezium-json');
-- create kafka upsert sink connector table definition
CREATE TABLE flink_owner_with_pets (owner_id INT, owner ROW<id INT, first_name STRING, last_name STRING, address STRING, city STRING, telephone STRING>, pets ARRAY<ROW<id INT, name STRING, birth_date STRING, type_id INT, owner_id INT>>, PRIMARY KEY (owner_id) NOT ENFORCED) WITH ('connector' = 'upsert-kafka', 'topic' = 'flink_owner_with_pets', 'properties.bootstrap.servers' = 'kafka:9092', 'key.format' = 'json', 'value.format' = 'json', 'value.fields-include' = 'EXCEPT_KEY');

-- join parent child relationship (owner <-> pets) using custom agg functions
INSERT INTO flink_owner_with_pets 
SELECT owp.owner.id as key,owp.owner,owp.pets FROM (
    SELECT ff21_agg_owner_pets(owner,pet) as owp FROM (
        SELECT 
            ff21_to_owner(o.id, first_name, last_name, address, city, telephone) as owner,
            ff21_to_pet(p.id,name,birth_date,type_id,owner_id) as pet
        FROM dbz_mysql_owners o
        LEFT JOIN dbz_mysql_pets p ON p.owner_id = o.id
    ) as t1
    GROUP BY pet.owner_id
) as t2
;
