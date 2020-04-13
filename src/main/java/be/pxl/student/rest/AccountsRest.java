package be.pxl.student.rest;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.data.Payment;
import be.pxl.student.dao.EntityManagerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("accounts")
public class AccountsRest {

    private static final Logger LOGGER = LogManager.getLogger(AccountsRest.class);

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> getPaymentsByName(@PathParam("name") String name) {
        EntityManager em = EntityManagerUtil.createEntityManager();
        AccountDao accountDao = new AccountDao(em);
        LOGGER.info("QueryParam: " + name);
        List<Payment> payments = accountDao.findByName(name);
        LOGGER.info("Aantal payments: " + payments.size() + " for " + name);
        em.close();
        return payments;
    }
}
