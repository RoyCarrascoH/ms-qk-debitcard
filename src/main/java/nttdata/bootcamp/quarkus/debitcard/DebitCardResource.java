package nttdata.bootcamp.quarkus.debitcard;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nttdata.bootcamp.quarkus.debitcard.application.DebitCardService;
import nttdata.bootcamp.quarkus.debitcard.dto.DebitCardResponse;
import nttdata.bootcamp.quarkus.debitcard.dto.ResponseBase;
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
    public DebitCardResponse getDebitCards() {
        DebitCardResponse debitCardsResponse = new DebitCardResponse();
        List<DebitCard> debitCards = service.listAll();
        if (debitCards == null) {
            debitCardsResponse.setCodigoRespuesta(2);
            debitCardsResponse.setMensajeRespuesta("Respuesta nula");
            debitCardsResponse.setDebitCards(null);
        } else if (debitCards.size() == 0) {
            debitCardsResponse.setCodigoRespuesta(1);
            debitCardsResponse.setMensajeRespuesta("No existen tarjetas de debido");
            debitCardsResponse.setDebitCards(debitCards);
        } else {
            debitCardsResponse.setCodigoRespuesta(0);
            debitCardsResponse.setMensajeRespuesta("Respuesta Exitosa");
            debitCardsResponse.setDebitCards(debitCards);
        }
        return debitCardsResponse;
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
        service.save(DebitCard);
        return Response.ok(DebitCard).status(200).build();
    }

    @PUT
    @Path("{idDebitCard}")
    @Transactional
    public DebitCard updateDebitCard(@PathParam("idDebitCard") Long idDebitCard, DebitCard DebitCard) {
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
    public ResponseBase delete(@PathParam("idDebitCard") Long idDebitCard) {
        ResponseBase response = new ResponseBase();
        DebitCard entity = service.findById(idDebitCard);
        if (entity == null) {
            response.setCodigoRespuesta(1);
            response.setMensajeRespuesta("Id de debitCard no existe");
            throw new WebApplicationException("DebiCard with id of " + idDebitCard + " does not exist.", 404);
        } else {
            response.setCodigoRespuesta(0);
            response.setMensajeRespuesta("Eliminacion exitosa de debitCard id = " + idDebitCard);
            service.delete(entity.getIdDebitCard());
        }

        return response;
    }

}
