/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import ejb.session.stateless.jpa.AuctionListingJpaSessionBeanLocal;
import ejb.session.stateless.jpa.BidJpaSessionBeanLocal;
import ejb.session.stateless.jpa.CustomerJpaSessionBeanLocal;
import entity.AuctionListing;
import entity.Bid;
import entity.Customer;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author XUAN
 */
@Named(value = "viewAuctionListingAndPlaceNewBidManagedBean")
@SessionScoped
public class ViewAuctionListingAndPlaceNewBidManagedBean implements Serializable {
    
    @EJB
    private CustomerJpaSessionBeanLocal customerJpaSessionBeanLocal;
    @EJB
    private AuctionListingJpaSessionBeanLocal auctionListingJpaSessionBeanLocal;
    @EJB
    private BidJpaSessionBeanLocal bidJpaSessionBeanLocal;
    
    private Customer customer;
    private Long alid;
    private Bid bid;
    BigDecimal newBid;
    private List<AuctionListing> auctionListings;
    private BigDecimal highestBid;
    
    public ViewAuctionListingAndPlaceNewBidManagedBean() {
        customer = new Customer();
        alid = new Long("0");
        auctionListings = new ArrayList<>();
        bid = new Bid();
        newBid = new BigDecimal("0");
        highestBid = new BigDecimal("0");
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
        auctionListings = auctionListingJpaSessionBeanLocal.retrieveAllAvailableAuctionListing();
        customer = customerJpaSessionBeanLocal.getCustomer();
    }
    
    public void retrieveHighestBid (){
        highestBid = auctionListingJpaSessionBeanLocal.currentHighestBid(alid);
        System.out.println("alid = " + alid);
        if (highestBid == null) {
            highestBid = new BigDecimal("0");
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  "Current highest bid is " + highestBid, "Current highest bid is " + highestBid));
    }
    
    public void createNewBid (){
        customer = customerJpaSessionBeanLocal.getCustomer();
        bid.setCustomer(customer);
        Date date = new Date();
        bid.setBidDate(date);
        
        if (customer !=null && customer.getCurrentCreditBalance().compareTo(newBid) != -1){
            bid.setBidAmount(newBid);
            BigDecimal newBalance = BigDecimal.valueOf((double)customer.getCurrentCreditBalance().intValueExact() - (double)newBid.intValueExact());
            
            customer.setCurrentCreditBalance(newBalance);
            customerJpaSessionBeanLocal.updateCustomer(customer);
            customerJpaSessionBeanLocal.setCustomer(customer);
            
            Long newBidId = bidJpaSessionBeanLocal.createBid(bid,alid);
            
            if(newBidId != null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New bid " + newBidId + " has been palced successfully!", "New bid " + newBidId + " has been palced successfully!"));
                
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "An error has occurred while creating the new Bid!", "An error has occurred while creating the new Bid!"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You do not have enough Credit balance.", "You do not have enough Credit balance."));
        }
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Long getAlid() {
        return alid;
    }
    
    public void setAlid(Long alid) {
        this.alid = alid;
    }
    
    
    
    public List<AuctionListing> getAuctionListings() {
        return auctionListings;
    }
    
    public void setAuctionListings(List<AuctionListing> auctionListings) {
        this.auctionListings = auctionListings;
    }
    
    public Bid getBid() {
        return bid;
    }
    
    public void setBid(Bid bid) {
        this.bid = bid;
    }
    
    public BigDecimal getNewBid() {
        return newBid;
    }
    
    public void setNewBid(BigDecimal newBid) {
        this.newBid = newBid;
    }
    
    public BigDecimal getHighestBid() {
        return highestBid;
    }
    
    public void setHighestBid(BigDecimal highestBid) {
        this.highestBid = highestBid;
    }
    
    
}
