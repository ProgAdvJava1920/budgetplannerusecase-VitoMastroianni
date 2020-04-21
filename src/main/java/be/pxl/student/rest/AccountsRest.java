package be.pxl.student.rest;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.data.Account;
import be.pxl.student.data.Payment;
import be.pxl.student.dao.EntityManagerUtil;
import be.pxl.student.rest.resource.PaymentCreateResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.security.auth.login.AccountNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("accounts")
public class AccountsRest {

    private static final Logger LOGGER = LogManager.getLogger(AccountsRest.class);
    private AccountDao accountDao;
    private EntityManager em;
    @GET
    @Path("{name}")
    @Produces("application/json")
    public String getPaymentsByName(@PathParam("name") String name) {
        em = EntityManagerUtil.createEntityManager();
        accountDao = new AccountDao(em);
        LOGGER.info("QueryParam: " + name);
        Account account = accountDao.findPaymentsByName(name);
        LOGGER.info("Aantal payments: " + account.getPayments().size() + " for " + name);
        em.close();
        return account.getPayments().toString();
    }

    @POST
    @Path("{name}")
    public Response addPayment(@PathParam("name") String name, PaymentCreateResource paymentCreateResource) {
        try {
            em = EntityManagerUtil.createEntityManager();
            accountDao = new AccountDao(em);
            accountDao.addPayment(name,paymentCreateResource.getCounterAccount(), paymentCreateResource.getAmount(),paymentCreateResource.getDetail());
            return Response.created(UriBuilder.fromPath("/accounts/" + name).build()).build();
        } catch(AccountNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }
}
