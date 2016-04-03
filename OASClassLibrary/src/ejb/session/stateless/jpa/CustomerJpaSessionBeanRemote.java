/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless.jpa;

import entity.Customer;

/**
 *
 * @author XUAN
 */
public interface CustomerJpaSessionBeanRemote {
    
    public Long createCustomer (Customer customer);
    
    public Boolean login(String email, String password);
    
    public void logout();
     
    public Customer retrieveCustomerByEmail(String email);
    
    public void updateCustomer (Customer customer);
    
    public Customer getCustomer();
    
    public void setCustomer(Customer customerNew);
}
