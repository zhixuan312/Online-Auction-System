/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import entity.Customer;
import ejb.session.stateless.jpa.CustomerJpaSessionBeanRemote;
import entity.Address;
import entity.Bid;
import entity.CreditTransaction;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
@Local(CustomerJpaSessionBeanLocal.class)
@Remote(CustomerJpaSessionBeanRemote.class)
public class CustomerJpaSessionBean implements CustomerJpaSessionBeanRemote,CustomerJpaSessionBeanLocal{
    
    @EJB
            CustomerJpaSessionBeanLocal customerJpaSessionBeanLocal;
    private Customer customer;
    private List<Address> address;
    private List<Bid> bid;
    private List<CreditTransaction> creditTransaction;
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Long createCustomer (Customer customer) {
        boolean sameEmail = false;
        List<Customer> list = new ArrayList<>();
        try{
            String jpql = "SELECT c FROM Customer c";
            Query query = em.createQuery(jpql);
            list = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).getEmail().equals(customer.getEmail())){
                sameEmail = true;
            }
        }
        if (!sameEmail) {
            em.persist(customer);
            em.flush();
            
            return customer.getCustomerId();
        } else {
            return new Long("-1");
        }
    }
    
    @Override
    public Boolean login(String email, String password)
    {
        customer = customerJpaSessionBeanLocal.retrieveCustomerByEmail(email);
        
        if(customer != null)
        {
            if(customer.getPassword().equals(password))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public void logout()
    {
        customer = null;
        address = new ArrayList<>();
        bid = new ArrayList<>();
        creditTransaction = new ArrayList<>();
    }
    
    @Override
    public Customer getCustomer(){
        return customer;
    }
    
    @Override
    public void setCustomer(Customer customerNew) {
        customer = customerNew;
    }
    
    @Override
    public void updateCustomer (Customer customer) {
        em.merge(customer);
        em.flush();
    }
    
    
    
    @Override
    public Customer retrieveCustomerByEmail(String email) {
        try{
            String jpql = "SELECT c FROM Customer c WHERE c.email = ?1";
            Query query = em.createQuery(jpql);
            query.setParameter(1, email);
            return (Customer)query.getSingleResult();
        } catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
