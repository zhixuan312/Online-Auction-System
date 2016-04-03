/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import ejb.session.stateless.jpa.CustomerJpaSessionBeanLocal;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author XUAN
 */
@Named(value = "loginManagedBean")
@RequestScoped

public class LoginManagedBean
{
    private String email;
    private String password;
    
    @EJB
    CustomerJpaSessionBeanLocal customerJpaSessionBeanLocal;
    
    public LoginManagedBean() {
    }
    
    @PostConstruct
    public void init()
    {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
    
    public void login(ActionEvent event)
    {
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            

            
            if(customerJpaSessionBeanLocal.login(email, password))
            {
                ec.getSessionMap().put("login", true);
                ec.redirect("http://localhost:8080/OAS-war/index.xhtml?faces-redirect=true");
                customerJpaSessionBeanLocal.setCustomer(customerJpaSessionBeanLocal.retrieveCustomerByEmail(email));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential", "Invalid login credential"));
            }
            
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void cancelLogin(ActionEvent event)
    {
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("index.xhtml?faces-redirect=true");
            
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    
    public void logout(ActionEvent event)
    {
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ((HttpSession)ec.getSession(true)).invalidate();
            ec.redirect("index.xhtml?faces-redirect=true");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
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
}
