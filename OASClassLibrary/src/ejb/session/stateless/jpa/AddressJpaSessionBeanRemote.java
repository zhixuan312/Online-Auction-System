/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import entity.Address;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author XUAN
 */
@Remote
public interface AddressJpaSessionBeanRemote {
    
    public Long createAddress (Address address);
    
    public Address getAddress();
    
    public void setAddress(Address address);
    
    public void updateAddress (Address address);
    
    public List<Address> retrieveAllAddress(Long id);
    
    public void deleteAddress(Long id);
    
    public Address retrieveAddress(Long id);
    
    public Boolean isAssociatedWithWinningBid (Long id);
}
