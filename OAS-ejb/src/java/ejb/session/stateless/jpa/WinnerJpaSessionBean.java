/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import ejb.session.stateless.jpa.WinnerJpaSessionBeanRemote;
import entity.Address;
import entity.Winner;
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
@Local(WinnerJpaSessionBeanLocal.class)
@Remote(WinnerJpaSessionBeanRemote.class)
public class WinnerJpaSessionBean implements WinnerJpaSessionBeanRemote, WinnerJpaSessionBeanLocal {
    
    private Winner winner;
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void createWinner (Winner winner) {
        em.persist(winner);
        em.flush();
        
    }
    @Override
    public void updateWinner (Winner winner) {
        em.merge(winner);
    }
    
    @Override
    public Winner getWinner(){
        return winner;
    }
    
    @Override
    public void setWinner(Winner winner) {
        this.winner = winner;
    }
    
    @Override
    public Boolean isWinningBid(Long bidId){
        String jpql = "SELECT w FROM Winner w WHERE w.bid.bidId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, bidId);
        try{
            Winner winner = (Winner)query.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Winner retrieveWinnerByAuctionListingId(Long auctionListingId) {
        String jpql = "SELECT w FROM Winner w WHERE w.auctionListing.auctionListingId = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, auctionListingId);
        return(Winner)query.getSingleResult();
    }
}
