package com.github.hpgrahsl.quarkus;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OwnerRepository implements
    PanacheMongoRepositoryBase<OwnerWithPets,Integer> {

}
