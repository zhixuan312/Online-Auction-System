/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless.jpa;

import entity.CreditTransaction;
import javax.ejb.Local;

/**
 *
 * @author XUAN
 */
@Local
public interface CreditTransactionJpaSessionBeanLocal {
    
    public Long createCreditTransaction (CreditTransaction creditTransaction);
}
