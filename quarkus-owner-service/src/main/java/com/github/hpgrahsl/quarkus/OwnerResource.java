package com.github.hpgrahsl.quarkus;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.resteasy.reactive.MultipartForm;

@Path("owners")
public class OwnerResource {

  @Inject
  OwnerRepository repository;

  @Inject
  Template ownersList;

  @Inject
  Template owner;

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance getOwners(@QueryParam("lastName") @DefaultValue("") String lastName) {
    return lastName.isEmpty()
        ? ownersList.data("owners", repository.listAll())
        :  ownersList.data("owners", repository.list("owner.last_name LIKE ?1",lastName));
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance getOwnerById(@PathParam("id") Integer id) {
    return owner.data("o", repository.findByIdOptional(id).orElseThrow(NotFoundException::new));
  }

  @POST
  @Path("{id}")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response updateOwner(@PathParam("id") Integer id,@MultipartForm OwnerFormFields ownerFormFields) {
    OwnerWithPets ownerWithPets = repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    ownerWithPets = ownerFormFields.updateEntity(ownerWithPets);
    repository.update(ownerWithPets);
    return Response.status(Status.MOVED_PERMANENTLY)
        .location(URI.create("/owners")).build();
  }

}
