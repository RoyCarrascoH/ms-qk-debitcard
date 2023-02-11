package nttdata.bootcamp.quarkus.debitcard;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nttdata.bootcamp.quarkus.debitcard.application.DebitCardService;
import nttdata.bootcamp.quarkus.debitcard.entity.DebitCard;
import nttdata.bootcamp.quarkus.debitcard.util.Utilitarios;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/api/debit-card")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DebitCardResource {
    private static final Logger LOGGER = Logger.getLogger(DebitCardResource.class.getName());

    @Inject
    private DebitCardService service;

    @GET
    public List<DebitCard> getDebitCards() {
        return service.listAll();
    }

    @GET
    @Path("{idDebitCard}")
    public DebitCard viewDebitCardDetails(@PathParam("idDebitCard") Long idDebitCard) {
        DebitCard entity = service.findById(idDebitCard);
        if (entity == null) {
            throw new WebApplicationException("DebitCard with id of " + idDebitCard + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response createDebitCard(DebitCard DebitCard) {
        if (DebitCard.getDebitCardNumber() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        service.save(DebitCard);
        return Response.ok(DebitCard).status(200).build();
    }

    @PUT
    @Path("{idDebitCard}")
    @Transactional
    public DebitCard updateDebitCard(@PathParam("idDebitCard") Long idDebitCard, DebitCard DebitCard) {
        if (DebitCard.getDebitCardNumber() == null) {
            throw new WebApplicationException("DebitCard number account was not set on request.", 422);
        }
        DebitCard entity = service.findById(idDebitCard);
        if (entity == null) {
            throw new WebApplicationException("DebitCard with id of " + idDebitCard + " does not exist.", 404);
        }
        entity = Utilitarios.saveDebitCard(entity, DebitCard);
        service.update(idDebitCard, entity);
        return entity;
    }

    @DELETE
    @Path("{idDebitCard}")
    @Transactional
    public Response delete(@PathParam("idDebitCard") Long idDebitCard) {
        DebitCard entity = service.findById(idDebitCard);
        if (entity == null) {
            throw new WebApplicationException("DebitCard with id of " + idDebitCard + " does not exist.", 404);
        }
        service.delete(entity.getIdDebitCard());
        return Response.status(200).build();
    }

}
