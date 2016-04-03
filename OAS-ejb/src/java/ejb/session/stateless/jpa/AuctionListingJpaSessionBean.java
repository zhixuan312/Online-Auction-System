/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import entity.AuctionListing;
import entity.Bid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
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
@Local(AuctionListingJpaSessionBeanLocal.class)
@Remote(AuctionListingJpaSessionBeanRemote.class)
public class AuctionListingJpaSessionBean implements AuctionListingJpaSessionBeanRemote, AuctionListingJpaSessionBeanLocal {
    
    @EJB
            AuctionListingJpaSessionBeanLocal AuctionListingJpaSessionBeanLocal;
    private AuctionListing auctionListing;
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Long createAuctionListing (AuctionListing auctionListing) {
        em.persist(auctionListing);
        em.flush();
        
        return auctionListing.getAuctionListingId();
    }
    
    @Override
    public AuctionListing getAuctionListing(){
        return auctionListing;
    }
    
    @Override
    public void setAuctionListing(AuctionListing auctionListing) {
        this.auctionListing = auctionListing;
    }
    
    @Override
    public void updateAuctionListing (AuctionListing auctionListing) {
        em.merge(auctionListing);
    }
    
    @Override
    public List<AuctionListing> retrieveAllAuctionListing() {
        try{
            String jpql = "SELECT al FROM AuctionListing al";
            Query query = em.createQuery(jpql);
            List<AuctionListing> list =  query.getResultList();
            return list;
        } catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<AuctionListing> retrieveAllAvailableAuctionListing() {
        try{
            String jpql = "SELECT al FROM AuctionListing al";
            Query query = em.createQuery(jpql);
            List<AuctionListing> list =  query.getResultList();
            Date today = new Date();
            List<AuctionListing> answer = new ArrayList<>();
            for(int i = 0; i < list.size();i++){
                if (list.get(i).getStartDate() != null && list.get(i).getEndDate() != null){
                    if(list.get(i).getStartDate().compareTo(today) == -1 && list.get(i).getEndDate().compareTo(today) == 1 && !list.get(i).isAuctionClosed()){
                        answer.add(list.get(i));
                    }
                }
            }
            return answer;
        } catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void deleteAuctionListing(Long id) {
        AuctionListing auctionListing = em.find(AuctionListing.class, id);
        em.remove(auctionListing);
    }
    
    @Override
    public AuctionListing retrieveAuctionListing(Long id){
        String jpql = "SELECT al FROM AuctionListing al WHERE al.auctionListingId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, id);
        try{
            return (AuctionListing)query.getSingleResult();
        } catch (Exception e ){
            return null;
        }
    }
    
    @Override
    public Boolean closeAuctionListing (Long id){
        AuctionListing auctionListing = em.find(AuctionListing.class, id);
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date today = c.getTime();
        if (today.after(auctionListing.getEndDate())) {
            auctionListing.setAuctionClosed(true);
            em.merge(auctionListing);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Boolean closeAuctionListingAboveReservePrice (Long id){
        AuctionListing auctionListing = em.find(AuctionListing.class, id);
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date today = c.getTime();
        BigDecimal highest = currentHighestBid(id);
        if (highest != null) {
            if (today.after(auctionListing.getEndDate()) && highest.compareTo(auctionListing.getReservePrice()) == 1) {
                auctionListing.setAuctionClosed(true);
                em.merge(auctionListing);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public Boolean closeAuctionListingBelowReservePrice (Long auctionListingid){
        AuctionListing auctionListing = em.find(AuctionListing.class, auctionListingid);
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date today = c.getTime();
        BigDecimal highest = currentHighestBid (auctionListingid);
        if (highest != null) {
            if (today.after(auctionListing.getEndDate()) && highest.compareTo(auctionListing.getReservePrice()) == -1) {
                auctionListing.setAuctionClosed(true);
                em.merge(auctionListing);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public BigDecimal currentHighestBid (Long id) {
        String jpql = "SELECT b FROM Bid b WHERE b.auctionListing.auctionListingId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, id);
        List <Bid> bids = query.getResultList();
        if (!bids.isEmpty()) {
            BigDecimal highest = bids.get(0).getBidAmount();
            for (int i = 0; i <bids.size(); i ++) {
                if (bids.get(i).getBidAmount().compareTo(highest) ==1 ){
                    highest = bids.get(i).getBidAmount();
                }
            }
            return highest;
        } else {
            return null;
        }
    }
    
    @Override
    public Bid winningBid (Long auctionListingId) {
        String jpql = "SELECT b FROM Bid b WHERE b.auctionListing.auctionListingId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, auctionListingId);
        try {
            List <Bid> bids = query.getResultList();
            BigDecimal highest = bids.get(0).getBidAmount();
            Bid highestBid = bids.get(0);
            for (int i = 0; i <bids.size(); i ++) {
                if (bids.get(i).getBidAmount().compareTo(highest) ==1 ){
                    highest = bids.get(i).getBidAmount();
                    highestBid = bids.get(i);
                }
            }
            return highestBid;
        } catch (Exception e) {
            return null;
        }
    }
    
}
