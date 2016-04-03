/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author XUAN
 */
@Entity
public class CreditTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long creditTransactionId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDateTime;
    @ManyToOne(optional = false)
    private CreditPackage creditPackage;
    @ManyToOne(optional = false)
    private Customer customer;
    
    public CreditTransaction(){
        
    }

    public CreditTransaction(Long creditTransactionId, Integer quantity, BigDecimal price, BigDecimal totalAmount, Date transactionDateTime, CreditPackage creditPackage, Customer cusomter) {
        this.creditTransactionId = creditTransactionId;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;
        this.transactionDateTime = transactionDateTime;
        this.creditPackage = creditPackage;
        this.customer = cusomter;
    }

    public Long getCreditTransactionId() {
        return creditTransactionId;
    }

    public void setCreditTransactionId(Long creditTransactionId) {
        this.creditTransactionId = creditTransactionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public CreditPackage getCreditPackage() {
        return creditPackage;
    }

    public void setCreditPackage(CreditPackage creditPackage) {
        this.creditPackage = creditPackage;
    }

    public Customer getCutomer() {
        return customer;
    }

    public void setCutmoer(Customer cusomter) {
        this.customer = cusomter;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditTransactionId != null ? creditTransactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the creditTransactionId fields are not set
        if (!(object instanceof CreditTransaction)) {
            return false;
        }
        CreditTransaction other = (CreditTransaction) object;
        if ((this.creditTransactionId == null && other.creditTransactionId != null) || (this.creditTransactionId != null && !this.creditTransactionId.equals(other.creditTransactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CreditTransaction[ id=" + creditTransactionId + " ]";
    }
    
}
