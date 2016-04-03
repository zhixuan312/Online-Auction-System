/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless.jpa;

import entity.CreditPackage;
import java.util.List;

/**
 *
 * @author XUAN
 */
public interface CreditPackageJpaSessionBeanRemote {
    
    public Long createCreditPackage (CreditPackage creditPackage);
    
    public CreditPackage getCreditPackage();
    
    public void setCreditPackage(CreditPackage creditPackage);
    
    public void updateCreditPackage (CreditPackage creditPackage);
    
    public List<CreditPackage> retrieveAllCreditPackage();
    
    public void deleteCreditPackage(Long id);
    
    public CreditPackage retrieveCreditPackage(Long id);
}
