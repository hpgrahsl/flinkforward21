package com.github.hpgrahsl.quarkus;

import io.quarkus.mongodb.panache.MongoEntity;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection="flink_owner_with_pets")
public class OwnerWithPets {

  @BsonId
  public int _id;
  public Owner owner;
  public List<Pet> pets;

}
