/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import ejb.session.stateless.jpa.CreditPackageJpaSessionBeanRemote;
import entity.CreditPackage;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author XUAN
 */
@Stateless
@Local(CreditPackageJpaSessionBeanLocal.class)
@Remote(CreditPackageJpaSessionBeanRemote.class)
public class CreditPackageJpaSessionBean implements CreditPackageJpaSessionBeanRemote,CreditPackageJpaSessionBeanLocal{
    
    private CreditPackage creditPackage;
    
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Long createCreditPackage (CreditPackage creditPackage) {
        em.persist(creditPackage);
        em.flush();
        
        return creditPackage.getcreditPackageId();
    }
    
    
    @Override
    public CreditPackage getCreditPackage(){
        return creditPackage;
    }
    
    @Override
    public void setCreditPackage(CreditPackage creditPackage) {
        this.creditPackage = creditPackage;
    }
    
    @Override
    public void updateCreditPackage (CreditPackage creditPackage) {
        em.merge(creditPackage);
    }
    
    @Override
    public List<CreditPackage> retrieveAllCreditPackage() {
        try{
            String jpql = "SELECT cp FROM CreditPackage cp";
            Query query = em.createQuery(jpql);
            List<CreditPackage> list =  query.getResultList();
            return list;
        } catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void deleteCreditPackage(Long id) {
        CreditPackage creditPackage = em.find(CreditPackage.class, id);
        em.remove(creditPackage);
    }
    
    @Override
    public CreditPackage retrieveCreditPackage(Long id){
        String jpql = "SELECT cp FROM CreditPackage cp WHERE cp.creditPackageId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, id);
        return (CreditPackage)query.getSingleResult();
    }
}
