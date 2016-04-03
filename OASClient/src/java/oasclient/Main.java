/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package oasclient;

import ejb.session.stateless.jpa.AddressJpaSessionBeanRemote;
import ejb.session.stateless.jpa.AuctionListingJpaSessionBeanRemote;
import ejb.session.stateless.jpa.BidJpaSessionBeanRemote;
import ejb.session.stateless.jpa.CreditPackageJpaSessionBeanRemote;
import ejb.session.stateless.jpa.CreditTransactionJpaSessionBeanRemote;
import ejb.session.stateless.jpa.CustomerJpaSessionBeanRemote;
import ejb.session.stateless.jpa.WinnerJpaSessionBeanRemote;
import javax.ejb.EJB;

public class Main {
    
    @EJB
    private static CustomerJpaSessionBeanRemote customerJpaSessionBeanRemote;
    @EJB
    private static CreditPackageJpaSessionBeanRemote creditPackageJpaSessionBeanRemote;
    @EJB
    private static CreditTransactionJpaSessionBeanRemote creditTransactionJpaSessionRemote;
    @EJB
    private static AddressJpaSessionBeanRemote addressJpaSessionRemote;
    @EJB
    private static AuctionListingJpaSessionBeanRemote auctionListingSessionBeanRemote;
    @EJB
    private static BidJpaSessionBeanRemote bidJpaSessionBeanRemote;
    @EJB
    private static WinnerJpaSessionBeanRemote winnerJpaSessionBeanRemote;
    
    public Main() {
    }
    
    public static void main(String[] args) {
        
        if(creditPackageJpaSessionBeanRemote != null && customerJpaSessionBeanRemote != null && creditTransactionJpaSessionRemote!= null && addressJpaSessionRemote != null && auctionListingSessionBeanRemote != null && bidJpaSessionBeanRemote != null && winnerJpaSessionBeanRemote != null){
            ControllerJpa controllerJpa = new ControllerJpa(creditPackageJpaSessionBeanRemote, customerJpaSessionBeanRemote,creditTransactionJpaSessionRemote,addressJpaSessionRemote,auctionListingSessionBeanRemote,bidJpaSessionBeanRemote,winnerJpaSessionBeanRemote);
            controllerJpa.runSystem();
        } else {
            System.err.println("FATAL EXCEPTION: Unable to inject enterprise session beans! System will be terminated!");
        }
    }
    
}
