/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author XUAN
 */
@Entity
public class Winner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long winnerId;
    private String remark;
    @OneToOne(optional = false)
    private Bid bid;
    @OneToOne(optional = false)
    private AuctionListing auctionListing;
    @ManyToOne
    private Address address; 
    
    public Winner(){
        
    }

    public Winner(Long winnerId, String remark, Bid bid, AuctionListing auctionListing) {
        this.winnerId = winnerId;
        this.remark = remark;
        this.bid = bid;
        this.auctionListing = auctionListing;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public AuctionListing getAuctionListing() {
        return auctionListing;
    }

    public void setAuctionListing(AuctionListing auctionListing) {
        this.auctionListing = auctionListing;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (winnerId != null ? winnerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the winnerId fields are not set
        if (!(object instanceof Winner)) {
            return false;
        }
        Winner other = (Winner) object;
        if ((this.winnerId == null && other.winnerId != null) || (this.winnerId != null && !this.winnerId.equals(other.winnerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Winner[ id=" + winnerId + " ]";
    }
    
}
