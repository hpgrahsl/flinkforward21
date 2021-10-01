package com.github.hpgrahsl.flink.udf;

import com.github.hpgrahsl.flink.udf.OwnerWithPetsAggregator.OwnerPetsAcc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.data.ArrayData;
import org.apache.flink.table.data.GenericArrayData;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.functions.AggregateFunction;

@FunctionHint(
    input = {
        @DataTypeHint(value="ROW<`id` INT, `first_name` STRING, `last_name` STRING, `address` STRING, `city` STRING, `telephone` STRING>", bridgedTo = RowData.class),
        @DataTypeHint(value="ROW<`id` INT, `name` STRING, `birth_date` STRING, `type_id` INT, `owner_id` INT>", bridgedTo = RowData.class)
    },
    output =
      @DataTypeHint(
        value = "ROW<owner ROW<`id` INT, `first_name` STRING, `last_name` STRING, `address` STRING, `city` STRING, `telephone` STRING>, pets ARRAY<ROW<`id` INT, `name` STRING, `birth_date` STRING, `type_id` INT, `owner_id` INT>>>",
        bridgedTo = RowData.class))
public class OwnerWithPetsAggregator extends
    AggregateFunction<RowData, OwnerPetsAcc> {

  public static class OwnerPetsAcc {
    @DataTypeHint(value="ROW<`id` INT, `first_name` STRING, `last_name` STRING, `address` STRING, `city` STRING, `telephone` STRING>",bridgedTo = RowData.class)
    public RowData owner;
    @DataTypeHint(value="ARRAY<ROW<`id` INT, `name` STRING, `birth_date` STRING, `type_id` INT, `owner_id` INT>>", bridgedTo = ArrayData.class)
    public List<RowData> pets = new ArrayList<>();

    public OwnerPetsAcc accPet(RowData pet, RowData owner) {
      this.owner = owner;
      pets.add(pet);
      return this;
    }

    public void retPet(RowData pet, RowData owner) {
      Iterator<RowData> it = pets.iterator();
      while (it.hasNext()) {
        RowData rd = it.next();
        if (rd.getInt(0) == pet.getInt(0)) {
          it.remove();
          break;
        }
      }
    }
  }

  @Override
  public OwnerPetsAcc createAccumulator() {
    return new OwnerPetsAcc();
  }

  @Override
  public RowData getValue(OwnerPetsAcc acc) {
    RowData[] rd = new RowData[acc.pets.size()];
    for(int i=0;i<rd.length;i++) {
      rd[i] = acc.pets.get(i);
    }
    return GenericRowData.of(acc.owner, new GenericArrayData(rd));
  }

  public void accumulate(OwnerPetsAcc acc, RowData owner, RowData pet) {
    acc.accPet(pet, owner);
  }

  public void retract(OwnerPetsAcc acc, RowData owner, RowData pet) {
    acc.retPet(pet, owner);
  }

}
