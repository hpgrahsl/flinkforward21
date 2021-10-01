package com.github.hpgrahsl.flink.udf;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.types.Row;

public class ToPet extends ScalarFunction {

  @DataTypeHint("ROW<`id` INT, `name` STRING, `birth_date` STRING, `type_id` INT, `owner_id` INT>")
  public Row eval(Integer id, String name, String birth_date, Integer type_id, Integer owner_id) {
    return Row.of(id, name, birth_date, type_id, owner_id);
  }

}
