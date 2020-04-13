package be.pxl.student.dao;

import be.pxl.student.data.Account;
import be.pxl.student.data.Payment;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountDao {
    private EntityManager entityManager;

    public AccountDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Payment> findByName(String name){
          TypedQuery<Account> findByName = entityManager.createNamedQuery("findByName", Account.class);
          findByName.setParameter("name", name);

          return findByName.getSingleResult().getPayments();
    }


}
