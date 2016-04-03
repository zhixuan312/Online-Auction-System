/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import ejb.session.stateless.jpa.CreditPackageJpaSessionBeanLocal;
import ejb.session.stateless.jpa.CreditTransactionJpaSessionBeanLocal;
import ejb.session.stateless.jpa.CustomerJpaSessionBeanLocal;
import entity.CreditPackage;
import entity.CreditTransaction;
import entity.Customer;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author XUAN
 */
@Named(value = "purchaseCreditPackageManagedBean")
@RequestScoped
public class PurchaseCreditPackageManagedBean {
    
    @EJB
    private CreditPackageJpaSessionBeanLocal creditPackageJpaSessionBeanLocal;
    @EJB
    private CustomerJpaSessionBeanLocal customerJpaSessionBeanLocal;
    @EJB
    private CreditTransactionJpaSessionBeanLocal creditTransactionJpaSessionBeanLocal;
    
    List<CreditPackage> creditPackages;
    Long creditPackageId;
    private Customer customer;
    private CreditTransaction creditTransaction;
    private int quantity;
    private BigDecimal totalAmount;
    
    public PurchaseCreditPackageManagedBean() {
        creditPackages = new ArrayList<>();
        creditPackageId = new Long("0");
        customer = new Customer();
        quantity = 0;
        totalAmount = new BigDecimal ("0");
        creditTransaction = new CreditTransaction();
    }
    
    public void createNewTransaction(ActionEvent event){
        creditTransaction = new CreditTransaction();
    }
    
    @PostConstruct
    public void init()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
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
        
        creditPackages = creditPackageJpaSessionBeanLocal.retrieveAllCreditPackage();
        customer = customerJpaSessionBeanLocal.getCustomer();
    }
    
    public void purchaseCreditPackage(){
        if (customer != null){
            Customer currentCustomer = customerJpaSessionBeanLocal.getCustomer();
            creditTransaction.setCutmoer(currentCustomer);
            if (!creditPackages.isEmpty()){
                CreditPackage creditPackage = creditPackageJpaSessionBeanLocal.retrieveCreditPackage(creditPackageId);
                creditTransaction.setCreditPackage(creditPackage);
                creditTransaction.setQuantity(quantity);
                BigDecimal price = creditPackage.getPrice();
                creditTransaction.setPrice(price);
                totalAmount = BigDecimal.valueOf((double)price.intValueExact() * quantity);
                creditTransaction.setTotalAmount(totalAmount);
                Date date = new Date();
                creditTransaction.setTransactionDateTime(date);
                customer.setCurrentCreditBalance(BigDecimal.valueOf((double)customer.getCurrentCreditBalance().intValueExact() + (double)totalAmount.intValueExact()));
                customerJpaSessionBeanLocal.updateCustomer(customer);
                Long creditTransactionId = creditTransactionJpaSessionBeanLocal.createCreditTransaction(creditTransaction);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New transaction " + creditTransactionId + " created successfully, and your current credit balance is " + customerJpaSessionBeanLocal.getCustomer().getCurrentCreditBalance(), "New transaction " + creditTransactionId + " created successfully, and your current credit balance is " + customerJpaSessionBeanLocal.getCustomer().getCurrentCreditBalance()));
            }
        }
    }
    
    public void refreshCustomerProfile (ActionEvent event)
    {
        customer = customerJpaSessionBeanLocal.getCustomer();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer Profile refreshed successfully", "Customer Profile refreshed successfully"));
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public List<CreditPackage> getCreditPackages() {
        return creditPackages;
    }
    
    public void setCreditPackages(List<CreditPackage> creditPackages) {
        this.creditPackages = creditPackages;
    }
    
    public Long getCreditPackageId() {
        return creditPackageId;
    }
    
    public void setCreditPackageId(Long creditPackageId) {
        this.creditPackageId = creditPackageId;
    }
    
    public CreditTransaction getCreditTransaction() {
        return creditTransaction;
    }
    
    public void setCreditTransaction(CreditTransaction creditTransaction) {
        this.creditTransaction = creditTransaction;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
}
