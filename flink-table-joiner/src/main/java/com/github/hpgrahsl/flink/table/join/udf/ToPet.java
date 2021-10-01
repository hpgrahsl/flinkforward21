package com.github.hpgrahsl.flink.table.join.udf;

import com.github.hpgrahsl.flink.table.join.model.Pet;
import org.apache.flink.table.functions.ScalarFunction;

public class ToPet extends ScalarFunction {

  public Pet eval(Integer id, String name, String birth_date, Integer type_id, Integer owner_id) {
    return new Pet(id, name, birth_date, type_id, owner_id);
  }

}
