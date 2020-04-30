package be.pxl.student.dao;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class LabelDao {
    private EntityManager entityManager;

    public LabelDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Label> findAllLabels(){
        TypedQuery<Label> findAllLabels = entityManager.createNamedQuery("findAllLabels", Label.class);
        try{
            List<Label> labels = findAllLabels.getResultList();
            return labels;
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    public Label findLabelByName(String name){
        TypedQuery<Label> findLabelByName = entityManager.createNamedQuery("findLabelByName", Label.class);
        findLabelByName.setParameter("name", name);
        try{
            return findLabelByName.getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    public Label updateLabel(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        label = entityManager.merge(label);
        transaction.commit();
        return label;
    }

    public Label createLabel(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(label);
        transaction.commit();
        return label;
    }

}
