/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import entity.AuctionListing;
import entity.Bid;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author XUAN
 */
@Local
public interface AuctionListingJpaSessionBeanLocal {
    
    public Long createAuctionListing (AuctionListing auctionListing);
    
    public AuctionListing getAuctionListing();
    
    public void setAuctionListing(AuctionListing auctionListing);
    
    public void updateAuctionListing (AuctionListing auctionListing);
    
    public List<AuctionListing> retrieveAllAuctionListing();
    
    public List<AuctionListing> retrieveAllAvailableAuctionListing();
    
    public void deleteAuctionListing(Long id);
    
    public AuctionListing retrieveAuctionListing(Long id);
    
    public BigDecimal currentHighestBid (Long id);
    
    public Boolean closeAuctionListing (Long id);
    
    public Boolean closeAuctionListingBelowReservePrice (Long id);
    
    public Boolean closeAuctionListingAboveReservePrice (Long id);
    
    public Bid winningBid (Long auctionListingId);
    
}
