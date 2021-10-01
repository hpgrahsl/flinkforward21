package com.github.hpgrahsl.flink.table.join;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.call;

import com.github.hpgrahsl.flink.table.join.udf.OwnerWithPetsTableAggregator;
import com.github.hpgrahsl.flink.table.join.udf.ToOwner;
import com.github.hpgrahsl.flink.table.join.udf.ToPet;
import com.github.hpgrahsl.flink.table.join.util.ConfigLoader;
import java.util.Properties;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

public class OwnersPetsJoiner {

  public static void main(String[] args) {

    Properties config = ConfigLoader.loadProperties();

    EnvironmentSettings envSettings = EnvironmentSettings
        .newInstance()
        .inStreamingMode()
        .build();

    TableEnvironment tableEnv = TableEnvironment.create(envSettings);
    tableEnv.executeSql(config.getProperty("ddl.create.owner.table"));
    tableEnv.executeSql(config.getProperty("ddl.create.pet.table"));
    Table dbz_mysql_owners = tableEnv.from("dbz_mysql_owners")
        .select(call(ToOwner.class,
            $("id"),$("first_name"),
            $("last_name"),$("address"),
            $("city"),$("telephone")).as("owner")
        );

    Table dbz_mysql_pets = tableEnv.from("dbz_mysql_pets")
        .select(call(ToPet.class,
            $("id"),$("name"),
            $("birth_date"),$("type_id"),
            $("owner_id")).as("pet")
        );

    Table pet_and_owner =
      //dbz_mysql_pets.join(dbz_mysql_owners) 
      dbz_mysql_owners.leftOuterJoin(dbz_mysql_pets)
        .where($("owner").get("id").isEqual($("pet").get("owner_id")));

    Table owners_with_pets =
        //pet_and_owner.groupBy($("pet").get("owner_id").as("key"))
        pet_and_owner.groupBy($("owner").get("id").as("key"))
        .flatAggregate(call(OwnerWithPetsTableAggregator.class,$("owner"),$("pet")).as("owner","pets"))
        .select($("key"),$("owner"),$("pets"));

    tableEnv.executeSql(config.getProperty("ddl.create.owner-with-pets.table"));
    owners_with_pets.executeInsert("flink_owner_with_pets");

  }

}
