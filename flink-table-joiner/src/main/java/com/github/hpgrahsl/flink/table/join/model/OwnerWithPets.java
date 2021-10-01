package com.github.hpgrahsl.flink.table.join.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.flink.table.annotation.DataTypeHint;

public class OwnerWithPets {

  //@DataTypeHint("ROW<`id` INT, `first_name` STRING, `last_name` STRING, `address` STRING, `city` STRING, `telephone` STRING>")
  public Owner owner;
  //@DataTypeHint("ARRAY<ROW<`id` INT, `name` STRING, `birth_date` STRING, `type_id` INT, `owner_id` INT>>")
  public List<Pet> pets = new ArrayList<>();

  public OwnerWithPets() {
  }

  public OwnerWithPets(Owner owner,
      List<Pet> pets) {
    this.owner = owner;
    this.pets = pets;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public List<Pet> getPets() {
    return pets;
  }

  public void setPets(List<Pet> pets) {
    this.pets = pets;
  }

  public OwnerWithPets addPet(Pet pet, Owner owner) {

    this.owner = owner;
    pets.add(pet);

    return this;
  }

  public OwnerWithPets removePet(Pet pet) {

    Iterator<Pet> it = pets.iterator();
    while (it.hasNext()) {
      Pet p = it.next();
      if (p.id.equals(pet.id)) {
        it.remove();
        break;
      }
    }

    return this;
  }

  @Override
  public String toString() {
    return "OwnerWithPets{" +
        "owner=" + owner +
        ", pets=" + pets +
        '}';
  }

}
