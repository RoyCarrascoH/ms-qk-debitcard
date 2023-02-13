package nttdata.bootcamp.quarkus.debitcard.proxy;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import nttdata.bootcamp.quarkus.debitcard.entity.BankAccount;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/api/bank-account")
public interface BankAccountApi {

    @PUT
    @Path("/updateMainAccount/{idBankAccount}/mainAccount/{mainAccount}")
    @Transactional
    public BankAccount updateMainAccount(@PathParam("idBankAccount") Long idBankAccount, @PathParam("mainAccount") String mainAccount);

}
