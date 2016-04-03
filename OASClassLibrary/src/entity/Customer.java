/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author XUAN
 */
@Entity
public class Customer implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal currentCreditBalance;
    @OneToMany(mappedBy = "customer")
    private List<Address> address;
    @OneToMany(mappedBy = "customer")
    private List<Bid> bid;
    @OneToMany(mappedBy = "customer")
    private List<CreditTransaction> creditTransaction;

    public Customer (){
        address = new ArrayList<>();
        bid = new ArrayList<>();
        creditTransaction = new ArrayList<>();
    }
    
    public Customer(Long customerId, String firstName, String lastName, String email, String password, BigDecimal currentCreditBalance) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.currentCreditBalance = currentCreditBalance;
    }
    
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getCurrentCreditBalance() {
        return currentCreditBalance;
    }

    public void setCurrentCreditBalance(BigDecimal currentCreditBalance) {
        this.currentCreditBalance = currentCreditBalance;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Bid> getBid() {
        return bid;
    }

    public void setBid(List<Bid> bid) {
        this.bid = bid;
    }

    public List<CreditTransaction> getCreditTransaction() {
        return creditTransaction;
    }

    public void setCreditTransaction(List<CreditTransaction> creditTransaction) {
        this.creditTransaction = creditTransaction;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Entity.Customer[ customerId=" + customerId + " ]";
    }
    
}
