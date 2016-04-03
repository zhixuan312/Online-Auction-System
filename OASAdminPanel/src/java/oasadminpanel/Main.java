/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package oasadminpanel;

import ejb.session.stateless.jpa.AuctionListingJpaSessionBeanRemote;
import ejb.session.stateless.jpa.BidJpaSessionBeanRemote;
import ejb.session.stateless.jpa.CreditPackageJpaSessionBeanRemote;
import ejb.session.stateless.jpa.CustomerJpaSessionBeanRemote;
import ejb.session.stateless.jpa.WinnerJpaSessionBeanRemote;
import javax.ejb.EJB;

public class Main {
    
    @EJB
    private static CreditPackageJpaSessionBeanRemote creditPackageJpaSessionBeanRemote;
    @EJB
    private static CustomerJpaSessionBeanRemote customerJpaSessionBeanRemote;
    @EJB
    private static AuctionListingJpaSessionBeanRemote auctionListingJpaSessionBeanRemote;
    @EJB
    private static WinnerJpaSessionBeanRemote winnerJpaSessionBeanRemote;
    @EJB
    private static BidJpaSessionBeanRemote bidJpaSessionBeanRemote;
    
    public Main() {
    }
    
    public static void main(String[] args) {
        
        if(creditPackageJpaSessionBeanRemote != null && customerJpaSessionBeanRemote != null && auctionListingJpaSessionBeanRemote != null && winnerJpaSessionBeanRemote != null && bidJpaSessionBeanRemote != null){
            ControllerJpa controllerJpa = new ControllerJpa(creditPackageJpaSessionBeanRemote, customerJpaSessionBeanRemote,auctionListingJpaSessionBeanRemote,winnerJpaSessionBeanRemote,bidJpaSessionBeanRemote);
            controllerJpa.runSystem();
        } else {
            System.err.println("FATAL EXCEPTION: Unable to inject enterprise session beans! System will be terminated!");
        }
    }
    
}
