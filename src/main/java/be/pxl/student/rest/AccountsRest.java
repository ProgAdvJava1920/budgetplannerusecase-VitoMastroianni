package be.pxl.student.rest;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.entity.Account;
import be.pxl.student.dao.EntityManagerUtil;
import be.pxl.student.entity.Payment;
import be.pxl.student.rest.resource.PaymentCreateResource;
import be.pxl.student.rest.resource.PaymentResource;
import be.pxl.student.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.security.auth.login.AccountNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.stream.Collectors;

@Path("accounts")
public class AccountsRest {

    @Inject
    private AccountService accountService;

    @GET
    @Path("{name}")
    @Produces("application/json")
    public Response getPaymentsByName(@PathParam("name") String name) {
        try {
            List<Payment> payments = accountService.findPaymentsByAccountName(name);
            return Response.ok(mapPayments(payments)).build();
        } catch (AccountNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{name}")
    public Response addPayment(@PathParam("name") String name, PaymentCreateResource paymentCreateResource) {
        try {
            accountService.addPayment(name,paymentCreateResource.getCounterAccount(),paymentCreateResource.getAmount(),paymentCreateResource.getDate(), paymentCreateResource.getDetail());
            return Response.created(UriBuilder.fromPath("/accounts/" + name).build()).build();
        } catch(AccountNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    private List<PaymentResource> mapPayments(List<Payment> payments) {
        return payments.stream().map(this::mapPayment).collect(Collectors.toList());
    }

    private PaymentResource mapPayment(Payment payment) {
        PaymentResource result = new PaymentResource();
        result.setId(payment.getId());
        result.setAmount(payment.getAmount());
        result.setCounterAccount(payment.getCounterAccount().getIBAN());
        result.setCurrency(payment.getCurrency());
        result.setDetail(payment.getDetail());
        return result;
    }
}
