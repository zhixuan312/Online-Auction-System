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
public class CreditPackage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long creditPackageId;
    private String name;
    private BigDecimal creditValue;
    private BigDecimal price;
    @OneToMany(mappedBy = "creditPackage")
    private List<CreditTransaction> creditTransaction;

    public CreditPackage() {
        creditTransaction = new ArrayList<>();
    }

    public CreditPackage(Long creditPackageId, String name, BigDecimal creditValue, BigDecimal price) {
        this.creditPackageId = creditPackageId;
        this.name = name;
        this.creditValue = creditValue;
        this.price = price;
    }

    public Long getcreditPackageId() {
        return creditPackageId;
    }

    public void setcreditPackageId(Long id) {
        this.creditPackageId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(BigDecimal creditValue) {
        this.creditValue = creditValue;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        hash += (creditPackageId != null ? creditPackageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the creditPackageId fields are not set
        if (!(object instanceof CreditPackage)) {
            return false;
        }
        CreditPackage other = (CreditPackage) object;
        if ((this.creditPackageId == null && other.creditPackageId != null) || (this.creditPackageId != null && !this.creditPackageId.equals(other.creditPackageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CreditPackage[ id=" + creditPackageId + " ]";
    }
    
}
