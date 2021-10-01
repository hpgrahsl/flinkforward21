package com.github.hpgrahsl.flink.table.join.udf;

import com.github.hpgrahsl.flink.table.join.model.Owner;
import org.apache.flink.table.functions.ScalarFunction;

public class ToOwner extends ScalarFunction {

  public Owner eval(Integer id, String first_name, String last_name, String address, String city,
      String telephone) {
    return new Owner(id, first_name, last_name, address, city, telephone);
  }

}
