package com.cy.service;

import com.cy.dao.MailReciverDao;
import com.cy.entity.mail.Mailreciver;
import com.cy.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class MailService {

    @Autowired
    private MailReciverDao mailReciverDao;

    public List<Mailreciver> findMailReciverByReadAndDelAndReciverId(boolean b, boolean b1, User user) {
        return mailReciverDao.findByReadAndDelAndReciverId(b,b1,user);
    }
}
