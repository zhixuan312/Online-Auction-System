/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import ejb.session.stateless.jpa.AddressJpaSessionBeanLocal;
import ejb.session.stateless.jpa.CustomerJpaSessionBeanLocal;
import entity.Address;
import entity.Customer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author XUAN
 */
@Named(value = "addressManagedBean")
@RequestScoped
public class AddressManagedBean {
    
    @EJB
    private AddressJpaSessionBeanLocal addressJpaSessionBeanLocal;
    @EJB
    private CustomerJpaSessionBeanLocal customerJpaSessionBeanLocal;
    
    private Customer customer;
    private List<Address> addresses;
    private List<Address> filteredAddresses;
    private List<Address> selectedAddresses;
    private Address newAddress;
    private Address selectedAddress;
    
    public AddressManagedBean() {
        customer = new Customer();
        addresses = new ArrayList<>();
        filteredAddresses = new ArrayList<>();
        selectedAddresses = new ArrayList<>();
        newAddress = new Address();
        selectedAddress = new Address();
    }
    
    @PostConstruct
    public void init(){
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
        customer = customerJpaSessionBeanLocal.getCustomer();
        addresses = addressJpaSessionBeanLocal.retrieveAllAddress(customer.getCustomerId());
    }
    
    public void createNewAddress(ActionEvent event){
        newAddress = new Address();
    }
    
    public void saveNewAddress(ActionEvent event){
        newAddress.setCustomer(customer);
        Long newAddressId = addressJpaSessionBeanLocal.createAddress(newAddress);
//        newAddress.setAddressId(newAddressId);
        addresses.add(newAddress);
        
        newAddress = new Address();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New address " + newAddressId + " created successfully", "New address " + newAddressId + " created successfully"));
    }
    
    public void updateAddress(ActionEvent event){
        selectedAddress.setCustomer(customer);
        addressJpaSessionBeanLocal.updateAddress(selectedAddress);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Address " + selectedAddress.getAddressId() + " updated successfully", "Book " + selectedAddress.getAddressId() + " updated successfully"));
    }
    
    public void deleteAddress(ActionEvent event){
        Address addressToDelete = (Address)event.getComponent().getAttributes().get("addressToDelete");
        
        addressJpaSessionBeanLocal.deleteAddress(addressToDelete.getAddressId());
        addresses.remove(addressToDelete);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Book " + addressToDelete.getAddressId() + " deleted successfully", "Book " + addressToDelete.getAddressId() + " deleted successfully"));
    }
    
    public void deleteAddresses(ActionEvent event){
        if(!selectedAddresses.isEmpty()){
            String ids = "";
            
            addressJpaSessionBeanLocal.deleteAddresses(selectedAddresses);
            addresses.removeAll(selectedAddresses);
            
            for(Address address:selectedAddresses){
                if(ids.length() > 0){
                    ids += ", ";
                }
                
                ids += address.getAddressId();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Addresses " + ids + " deleted successfully", "Addresses " + ids + " deleted successfully"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No address was selected for deletion", "No address was selected for deletion"));
        }
    }
    
    public void refreshAddresses(ActionEvent event){
        addresses = addressJpaSessionBeanLocal.retrieveAllAddress(customer.getCustomerId());
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Addresses refreshed successfully", "Addresses refreshed successfully"));
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Address> getFilteredAddresses() {
        return filteredAddresses;
    }

    public void setFilteredAddresses(List<Address> filteredAddresses) {
        this.filteredAddresses = filteredAddresses;
    }

    public List<Address> getSelectedAddresses() {
        return selectedAddresses;
    }

    public void setSelectedAddresses(List<Address> selectedAddresses) {
        this.selectedAddresses = selectedAddresses;
    }

    public Address getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(Address newAddress) {
        this.newAddress = newAddress;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

}
