/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import ejb.session.stateless.jpa.AddressJpaSessionBeanRemote;
import entity.Address;
import entity.Winner;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author XUAN
 */
@Stateless
@Local(AddressJpaSessionBeanLocal.class)
@Remote(AddressJpaSessionBeanRemote.class)
public class AddressJpaSessionBean implements AddressJpaSessionBeanRemote, AddressJpaSessionBeanLocal {
    
    private Address address;
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Long createAddress (Address address) {
        em.persist(address);
        em.flush();
        
        return address.getAddressId();
    }
    
    @Override
    public Address getAddress(){
        return address;
    }
    
    @Override
    public void setAddress(Address address) {
        this.address = address;
    }
    
    @Override
    public void updateAddress (Address address) {
        em.merge(address);
    }
    
    @Override
    public List<Address> retrieveAllAddress(Long id) {
        try{
            String jpql = "SELECT a FROM Address a WHERE a.customer.customerId = ?1";
            Query query = em.createQuery(jpql);
            query.setParameter(1, id);
            List<Address> list =  query.getResultList();
            return list;
        } catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void deleteAddress(Long id) {
        Address address = em.find(Address.class, id);
        em.remove(address);
    }
    
    @Override
    public void deleteAddresses(List<Address> addresses){
        for(Address address:addresses)
        {
            deleteAddress(address.getAddressId());
        }
    }
    
    @Override
    public Address retrieveAddress(Long id){
        String jpql = "SELECT a FROM Address a WHERE a.addressId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, id);
        try {
            Address address = (Address)query.getSingleResult();
            return address;
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public Boolean isAssociatedWithWinningBid (Long id){
        
        String jpql = "SELECT w FROM Winner w WHERE w.address.addressId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, id);
        try {
            Winner winner = (Winner)query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
        
    }
}
