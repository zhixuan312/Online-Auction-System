/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author XUAN
 */
@Entity
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;
    private String line1;
    private String line2;
    private String line3;
    private String state;
    private String city;
    private String postalCode;
    private String country;
    @ManyToOne(optional = false)
    private Customer customer;
    @OneToMany(mappedBy = "address")
    private List<Winner> winner;
    
    public Address (){
        winner = new ArrayList<>();
    }
    
    public Address(Long addressId, String line1, String line2, String line3, String state, String city, String postalCode, String country, Customer customer) {
        this.addressId = addressId;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.customer = customer;
    }
    
    public Long getAddressId() {
        return addressId;
    }
    
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    
    public String getLine1() {
        return line1;
    }
    
    public void setLine1(String line1) {
        this.line1 = line1;
    }
    
    public String getLine2() {
        return line2;
    }
    
    public void setLine2(String line2) {
        this.line2 = line2;
    }
    
    public String getLine3() {
        return line3;
    }
    
    public void setLine3(String line3) {
        this.line3 = line3;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Winner> getWinner() {
        return winner;
    }

    public void setWinner(List<Winner> winner) {
        this.winner = winner;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressId != null ? addressId.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the addressId fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.addressId == null && other.addressId != null) || (this.addressId != null && !this.addressId.equals(other.addressId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Entity.Address[ id=" + addressId + " ]";
    }
    
}
