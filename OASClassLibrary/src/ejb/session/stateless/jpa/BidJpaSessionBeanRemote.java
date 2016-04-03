/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import entity.Bid;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author XUAN
 */
@Remote
public interface BidJpaSessionBeanRemote {
    
    public Long createBid (Bid bid, Long id);
    
    public Bid getBid();
    
    public void setBid(Bid bid);
    
    public void updateBid (Bid bid);
    
    public List<Bid> retrieveAllBid(Long id);
    
    public Bid retrieveBid(Long id);
    
    public List<Bid> retrieveAllBidByCustomer(Long customerid);
    
}
