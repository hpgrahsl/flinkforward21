package com.github.hpgrahsl.flink.udf;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.types.Row;

public class ToOwner extends ScalarFunction {

  @DataTypeHint("ROW<`id` INT, `first_name` STRING, `last_name` STRING, `address` STRING, `city` STRING, `telephone` STRING>")
  public Row eval(Integer id, String first_name, String last_name, String address, String city,
      String telephone) {
    return Row.of(id, first_name, last_name, address, city, telephone);
  }

}
