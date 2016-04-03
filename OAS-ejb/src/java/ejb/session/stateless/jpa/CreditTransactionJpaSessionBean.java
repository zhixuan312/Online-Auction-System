/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless.jpa;

import ejb.session.stateless.jpa.CreditTransactionJpaSessionBeanRemote;
import entity.CreditTransaction;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author XUAN
 */
@Stateless
@Local(CreditTransactionJpaSessionBeanLocal.class)
@Remote(CreditTransactionJpaSessionBeanRemote.class)
public class CreditTransactionJpaSessionBean implements CreditTransactionJpaSessionBeanRemote, CreditTransactionJpaSessionBeanLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Long createCreditTransaction (CreditTransaction creditTransaction) {
        em.persist(creditTransaction);
        em.flush();
        
        return creditTransaction.getCreditTransactionId();
    }
}
