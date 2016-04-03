/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import ejb.session.stateless.jpa.BidJpaSessionBeanRemote;
import entity.AuctionListing;
import entity.Bid;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author XUAN
 */
@Stateless
@Local(BidJpaSessionBeanLocal.class)
@Remote(BidJpaSessionBeanRemote.class)
public class BidJpaSessionBean implements BidJpaSessionBeanRemote, BidJpaSessionBeanLocal {
    
    @EJB
            BidJpaSessionBeanLocal BidJpaSessionBeanLocal;
    private Bid bid;
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Long createBid (Bid bid, Long id) {
        AuctionListing auctionListing = em.find(AuctionListing.class, id);
        bid.setAuctionListing(auctionListing);
        em.persist(bid);
        em.flush();
        
        return bid.getBidId();
    }
    
    @Override
    public Bid getBid(){
        return bid;
    }
    
    @Override
    public void setBid(Bid bid) {
        this.bid = bid;
    }
    
    @Override
    public void updateBid (Bid bid) {
        em.merge(bid);
    }
    
    @Override
    public Bid retrieveBid(Long id){
        String jpql = "SELECT b FROM Bid b WHERE b.bidId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, id);
        return (Bid)query.getSingleResult();
    }
    
    @Override
    public List<Bid> retrieveAllBid(Long id){
        String jpql = "SELECT b FROM Bid b WHERE b.auctionListing.auctionListingId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, id);
        return query.getResultList();
    }
    
    @Override
    public List<Bid> retrieveAllBidByCustomer(Long customerid){
        try{
            String jpql = "SELECT b FROM Bid b WHERE b.customer.customerId = ?1";
            Query query = em.createQuery(jpql);
            query.setParameter(1, customerid);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
