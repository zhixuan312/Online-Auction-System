/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import entity.Winner;
import javax.ejb.Remote;

/**
 *
 * @author XUAN
 */
@Remote
public interface WinnerJpaSessionBeanRemote {
    
    public void createWinner (Winner winner);
    
    public void updateWinner (Winner winner);
    
    public Winner getWinner();
    
    public void setWinner(Winner winner);
    
    public Boolean isWinningBid(Long bidId);
    
    public Winner retrieveWinnerByAuctionListingId(Long auctionListingId);
    
}
