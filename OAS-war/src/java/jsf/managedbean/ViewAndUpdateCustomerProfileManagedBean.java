/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import ejb.session.stateless.jpa.CustomerJpaSessionBeanLocal;
import entity.Customer;
import java.io.IOException;
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
@Named(value = "viewAndUpdateCustomerProfileManagedBean")
@RequestScoped
public class ViewAndUpdateCustomerProfileManagedBean {
    
    @EJB
    private CustomerJpaSessionBeanLocal customerJpaSessionBeanLocal;
    
    private Customer customer;
    
    public ViewAndUpdateCustomerProfileManagedBean() {
        customer = new Customer();
    }
    
    @PostConstruct
    public void init(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        try{
            if(ec.getSessionMap().get("login") == null){
                ec.redirect("login.xhtml?faces-redirect=true");
            } else {
                if(ec.getSessionMap().get("login").equals(false)) {
                    ec.redirect("login.xhtml?faces-redirect=true");
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        customer = customerJpaSessionBeanLocal.getCustomer();
    }
    
    public void updateCustomer(){
        if (customer != null){
            customerJpaSessionBeanLocal.updateCustomer(customer);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Successful","Update Successful"));
        }
    }
    
    public void cancelUpdate(ActionEvent event){
        try{
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("viewAndUpdateCustomerProfile.xhtml?faces-redirect=true");
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
