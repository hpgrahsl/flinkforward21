package com.github.hpgrahsl.flink.table.join.udf;

import com.github.hpgrahsl.flink.table.join.model.Owner;
import com.github.hpgrahsl.flink.table.join.model.OwnerWithPets;
import com.github.hpgrahsl.flink.table.join.model.Pet;
import org.apache.flink.table.functions.TableAggregateFunction;
import org.apache.flink.util.Collector;

public class OwnerWithPetsTableAggregator extends
    TableAggregateFunction<OwnerWithPets, OwnerWithPets> {

  @Override
  public OwnerWithPets createAccumulator() {
    return new OwnerWithPets();
  }

  public void accumulate(OwnerWithPets acc, Owner owner, Pet pet) {
    acc.addPet(pet, owner);
  }

  public void retract(OwnerWithPets acc, Owner owner, Pet pet) {
    acc.removePet(pet);
  }

  public void emitValue(OwnerWithPets acc, Collector<OwnerWithPets> out) {
    out.collect(acc);
  }

}
