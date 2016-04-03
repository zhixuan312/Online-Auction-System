/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import ejb.session.stateless.jpa.AddressJpaSessionBeanLocal;
import ejb.session.stateless.jpa.BidJpaSessionBeanLocal;
import ejb.session.stateless.jpa.CustomerJpaSessionBeanLocal;
import ejb.session.stateless.jpa.WinnerJpaSessionBeanLocal;
import entity.AuctionListing;
import entity.Bid;
import entity.Customer;
import entity.Winner;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author XUAN
 */
@Named(value = "wonAuctionManagedBean")
@SessionScoped
public class WonAuctionManagedBean implements Serializable {
    
    @EJB
    private CustomerJpaSessionBeanLocal customerJpaSessionBeanLocal;
    @EJB
    private WinnerJpaSessionBeanLocal winnerJpaSessionBeanLocal;
    @EJB
    private BidJpaSessionBeanLocal bidJpaSessionBeanLocal;
    @EJB
    private AddressJpaSessionBeanLocal addressJpaSessionBeanLocal;
    
    private Customer customer;
    private List<AuctionListing> wonAuctions;
    private Winner winner;
    private Long wonAuctionId;
    private Long addressId;
    
    public WonAuctionManagedBean() {
        customer = new Customer();
        wonAuctions = new ArrayList<>();
        winner = new Winner();
        wonAuctionId = new Long ("0");
        addressId = new Long ("0");
    }
    
    @PostConstruct
    public void init(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        System.out.println("wewewewea");
        try
        {
            if(ec.getSessionMap().get("login") == null)
            {
                ec.redirect("login.xhtml?faces-redirect=true");
            }
            else
            {
                if(ec.getSessionMap().get("login").equals(false))
                {
                    ec.redirect("login.xhtml?faces-redirect=true");
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        browseWonAuctionListing ();
        customer = customerJpaSessionBeanLocal.getCustomer();
    }
    
    public void browseWonAuctionListing (){
        List<Bid> bids = bidJpaSessionBeanLocal.retrieveAllBidByCustomer(customerJpaSessionBeanLocal.getCustomer().getCustomerId());
        if (bids != null){
            for (int i = 0; i < bids.size(); i ++) {
                if (winnerJpaSessionBeanLocal.isWinningBid(bids.get(i).getBidId())){
                    wonAuctions.add(bids.get(i).getAuctionListing());
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  "There is no bid", "There is no bid"));
        }
    }
    
    public void selectDeliveryAddressForWonAuctionListing (){
        winner = winnerJpaSessionBeanLocal.retrieveWinnerByAuctionListingId(wonAuctionId);
        winner.setAddress(addressJpaSessionBeanLocal.retrieveAddress(addressId));
        winnerJpaSessionBeanLocal.updateWinner(winner);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  "Update Success", "Update Success"));
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public List<AuctionListing> getWonAuctions() {
        return wonAuctions;
    }
    
    public void setWonAuctions(List<AuctionListing> wonAuctions) {
        this.wonAuctions = wonAuctions;
    }
    
    public Winner getWinner() {
        return winner;
    }
    
    public void setWinner(Winner winner) {
        this.winner = winner;
    }
    
    public Long getWonAuctionId() {
        return wonAuctionId;
    }
    
    public void setWonAuctionId(Long wonAuctionId) {
        this.wonAuctionId = wonAuctionId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    
}
