package be.pxl.student.service;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.LabelDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.EntityManagerUtil;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;
import javax.validation.constraints.Null;
import java.util.List;

public class LabelService {
    private LabelDao labelDao;

    public LabelService() {
        labelDao = new LabelDao(EntityManagerUtil.createEntityManager());
    }

    public List<Label> findAllLabels() throws NullPointerException{
        List<Label> labels = labelDao.findAllLabels();
        if(labels == null){
            throw new NullPointerException();
        }
        return labels;
    }

    public Label findLabelByName(String name) throws NullPointerException{
        Label label = labelDao.findLabelByName(name);
        if(label == null){
            throw new NullPointerException();
        }
        return label;
    }

    public void addLabel(String name) throws Exception {
        if (labelDao.findLabelByName(name) != null){
            throw new Exception("Label already exists: " + name);
        }
        Label label = new Label();
        label.setName(name);
        labelDao.createLabel(label);
    }
}
