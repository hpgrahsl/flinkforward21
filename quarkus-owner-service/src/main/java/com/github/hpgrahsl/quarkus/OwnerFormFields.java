package com.github.hpgrahsl.quarkus;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;

public class OwnerFormFields {

  @FormParam("first_name") @PartType(MediaType.TEXT_PLAIN)
  public String first_name;

  @FormParam("last_name") @PartType(MediaType.TEXT_PLAIN)
  public String last_name;

  @FormParam("city") @PartType(MediaType.TEXT_PLAIN)
  public String city;

  @FormParam("address") @PartType(MediaType.TEXT_PLAIN)
  public String address;

  @FormParam("telephone") @PartType(MediaType.TEXT_PLAIN)
  public String telephone;


  public OwnerWithPets updateEntity(OwnerWithPets entity) {
    entity.owner.first_name = first_name;
    entity.owner.last_name = last_name;
    entity.owner.city = city;
    entity.owner.address = address;
    entity.owner.telephone = telephone;
    return entity;
  }



}
