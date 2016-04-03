package jsf.managedbean;

import ejb.session.stateless.jpa.CustomerJpaSessionBeanLocal;
import entity.Customer;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.helper.BigDecimalHelper;



@Named(value = "createCustomerManagedBean")
@RequestScoped

public class CreateCustomerManagedBean
{
    @EJB
    private CustomerJpaSessionBeanLocal customerJpaSessionBeanLocal;
    
    private Customer customer;
    
    public CreateCustomerManagedBean()
    {
        customer = new Customer();
    }
    
    public void createCustomer(ActionEvent event)
    {
        customer.setCurrentCreditBalance(BigDecimalHelper.createBigDecimal("0"));
        Long newCustomerId = customerJpaSessionBeanLocal.createCustomer(customer);
        customer = new Customer();
        if (!newCustomerId.equals(new Long("-1"))){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New customer " + newCustomerId + " created successfully!", "New customer " + newCustomerId + " created successfully!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email is been registered", "Email is been registered"));
            
        }
    }
    
    
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public void cancelRegister(ActionEvent event){
        try{
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("createCustomer.xhtml?faces-redirect=true");
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}