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
import entity.AuctionListing;
import entity.Bid;
import entity.CreditPackage;
import entity.Winner;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import util.helper.BigDecimalHelper;
/**
 *
 * @author XUAN
 */
public class ControllerJpa {
    CreditPackageJpaSessionBeanRemote creditPackageJpaSessionBeanRemote;
    CustomerJpaSessionBeanRemote customerJpaSessionBeanRemote;
    AuctionListingJpaSessionBeanRemote auctionListingJpaSessionBeanRemote;
    WinnerJpaSessionBeanRemote winnerJpaSessionBeanRemote;
    BidJpaSessionBeanRemote bidJpaSessionBeanRemote;
    
    public ControllerJpa(){
    }
    
    public ControllerJpa(CreditPackageJpaSessionBeanRemote creditPackageJpaSessionBeanRemote, CustomerJpaSessionBeanRemote customerJpaSessionBeanRemote,AuctionListingJpaSessionBeanRemote auctionListingJpaSessionBeanRemote,WinnerJpaSessionBeanRemote winnerJpaSessionBeanRemote,BidJpaSessionBeanRemote bidJpaSessionBeanRemote){
        this.creditPackageJpaSessionBeanRemote = creditPackageJpaSessionBeanRemote;
        this.customerJpaSessionBeanRemote = customerJpaSessionBeanRemote;
        this.auctionListingJpaSessionBeanRemote = auctionListingJpaSessionBeanRemote;
        this.winnerJpaSessionBeanRemote = winnerJpaSessionBeanRemote;
        this.bidJpaSessionBeanRemote = bidJpaSessionBeanRemote;
    }
    
    public void runSystem()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response;
        
        while(true)
        {
            System.out.println("*** Welcome to Online Auction System ***\n");
            System.out.println("1: Admin Login");
            System.out.println("2: Logout");
            System.out.println("3: Create CreditPackage");
            System.out.println("4: Update CreditPackage");
            System.out.println("5: View All CreditPackage");
            System.out.println("6: Delete CreditPackage");
            System.out.println("7: Create Auction Listing");
            System.out.println("8: Update Auction Listing");
            System.out.println("9: View Auction Listing Details");
            System.out.println("10: View All AuctionListing");
            System.out.println("11: Delete AuctionListing");
            System.out.println("12: Close Auction Listing");
            System.out.println("13: Exit");
            response = 0;
            
            while(response < 1 || response > 13)
            {
                System.out.print("> ");
                
                response = scanner.nextInt();
                
                if(response.equals(1)) {
                    login();
                } else if (response == 2) {
                    logout();
                } else if (response == 3) {
                    createCreditPackage();
                } else if (response == 4) {
                    updateCreditPackage();
                } else if (response == 5) {
                    retrieveAllCreditPackage();
                } else if (response == 6) {
                    deleteCreditPackage();
                } else if (response == 7) {
                    createAuctionListing();
                } else if (response == 8) {
                    updateAuctionListing();
                } else if (response == 9) {
                    viewAuctionListingDetails();
                } else if (response == 10) {
                    retrieveAllAuctionListing();
                } else if (response == 11) {
                    deleteAuctionListing();
                } else if (response == 12) {
                    closeAuctionListing ();
                } else if (response == 13) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 13)
            {
                break;
            }
        }
    }
    
    private void createCreditPackage(){
        CreditPackage creditPackage = new CreditPackage();
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            System.out.println("*** Online Auction System > Create CreditPackage ***\n");
            System.out.print("Name: ");
            creditPackage.setName(scanner.nextLine());
            System.out.print("Credit and Price: ");
            BigDecimal price = scanner.nextBigDecimal();
            creditPackage.setCreditValue(price);
            creditPackage.setPrice(price);
            
            Long newCreditPackageId = creditPackageJpaSessionBeanRemote.createCreditPackage(creditPackage);
            
            if(newCreditPackageId != null)
            {
                System.out.println("New CreditPackage " + newCreditPackageId + " created successfully!\n\n");
            }
            else
            {
                System.out.println("An error has occurred while creating the new CreditPackage!\n\n");
            }
        } else {
            System.out.println("Please login with admin account");
        }
    }
    
    private void updateCreditPackage(){
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            System.out.println("*** Online Auction System > Update CreditPackage ***\n");
            CreditPackage creditPackage = new CreditPackage();
            retrieveAllCreditPackage();
            System.out.print("Please Enter the CreditPackage ID that you want to update: ");
            creditPackage.setcreditPackageId(scanner.nextLong());
            scanner.nextLine();
            System.out.print("New Name: ");
            creditPackage.setName(scanner.nextLine());
            System.out.print("New Credit and Price: ");
            BigDecimal price = scanner.nextBigDecimal();
            creditPackage.setCreditValue(price);
            creditPackage.setPrice(price);
            
            creditPackageJpaSessionBeanRemote.updateCreditPackage(creditPackage);
            
            System.out.println("*** Update Sucessful ***\n");
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void retrieveAllCreditPackage(){
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            List<CreditPackage> creditPackage = creditPackageJpaSessionBeanRemote.retrieveAllCreditPackage();
            
            System.out.println("*** Online Auction System > Retrieve All Credit Packages ***\n");
            
            for(int i = 0; i < creditPackage.size();i++)
            {
                System.out.println(creditPackage.get(i).getcreditPackageId() + "\t" + creditPackage.get(i).getName() + "\t" + BigDecimalHelper.formatCurrency(creditPackage.get(i).getCreditValue()) + "\t" + BigDecimalHelper.formatCurrency(creditPackage.get(i).getPrice()));
            }
            
            System.out.println("-- End of Listing --\n\n");
            
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void deleteCreditPackage(){
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            System.out.println("*** Online Auction System > Delete CreditPackage ***\n");
            System.out.print("Please Enter the CreditPackage ID that you want to delete: ");
            creditPackageJpaSessionBeanRemote.deleteCreditPackage(scanner.nextLong());
            System.out.println("*** Delete Sucessful ***\n");
        } else {
            System.out.println("Please login...");
            
        }
    }
    
    private void createAuctionListing() {
        
        AuctionListing auctionListing = new AuctionListing();
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            System.out.println("*** Online Auction System > Create AuctionListing ***\n");
            System.out.print("Title: ");
            auctionListing.setTitle(scanner.nextLine());
            System.out.print("Description: ");
            auctionListing.setDescription(scanner.nextLine());
            System.out.print("Starting Bid: ");
            auctionListing.setStartingBid(scanner.nextBigDecimal());
            scanner.nextLine();
            System.out.print("Reserve Price: ");
            auctionListing.setReservePrice(scanner.nextBigDecimal());
            scanner.nextLine();
            String dateFormat = "dd/MM/yyyy";
            
            Date today = todayDate ();
            Date startDate = null;
            Date endDate = null;
            
            while (true) {
                System.out.println("Date Format: dd/MM/yyyy ");
                System.out.print("Start Date: ");
                try {
                    startDate = new SimpleDateFormat(dateFormat).parse(scanner.nextLine());
                } catch(Exception e) {}
                if(startDate != null && today.before(startDate)){
                    auctionListing.setStartDate(startDate);
                    break;
                } else {
                    System.out.println("Please enter a valid future date");
                }
            }
            while (true) {
                System.out.print("End Date: ");
                try {
                    endDate = new SimpleDateFormat(dateFormat).parse(scanner.nextLine());
                } catch(Exception e) {}
                if (endDate != null && startDate.before(endDate)) {
                    auctionListing.setEndDate(endDate);
                    break;
                } else {
                    System.out.println("Please enter a valid future date");
                }
            }
            auctionListing.setAuctionClosed(false);
            
            Long newAuctionListingId = auctionListingJpaSessionBeanRemote.createAuctionListing(auctionListing);
            
            if(newAuctionListingId != null)
            {
                System.out.println("New Auction Listing " + newAuctionListingId + " created successfully!\n\n");
            }
            else
            {
                System.out.println("An error has occurred while creating the new Auction Listing!\n\n");
            }
        } else {
            System.out.println("Please login with admin account");
        }
    }
    
    private Date todayDate (){
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date today = c.getTime();
        return today;
    }
    
    private void updateAuctionListing(){
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            System.out.println("*** Online Auction System > Update Auction Listing ***\n");
            AuctionListing auctionListing = new AuctionListing();
            retrieveAllAuctionListing();
            System.out.print("Please Enter the Auction Listing ID that you want to update: ");
            Long auctionListingId = scanner.nextLong();
            auctionListing.setAuctionListingId(auctionListingId);
            scanner.nextLine();
            Date auctionListingDate = auctionListingJpaSessionBeanRemote.retrieveAuctionListing(auctionListingId).getStartDate();
            if (todayDate().after(auctionListingDate)){
                System.out.print("Title: ");
                auctionListing.setTitle(scanner.nextLine());
                System.out.print("Description: ");
                auctionListing.setDescription(scanner.nextLine());
                System.out.print("Starting Bid: ");
                auctionListing.setStartingBid(scanner.nextBigDecimal());
                scanner.nextLine();
                System.out.print("Reserve Price: ");
                auctionListing.setReservePrice(scanner.nextBigDecimal());
                scanner.nextLine();
                String dateFormat = "dd/MM/yyyy";
                
                Calendar c = new GregorianCalendar();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                Date today = c.getTime();
                Date startDate = null;
                Date endDate = null;
                
                while (true) {
                    System.out.println("Date Format: dd/MM/yyyy ");
                    System.out.print("Start Date: ");
                    try {
                        startDate = new SimpleDateFormat(dateFormat).parse(scanner.nextLine());
                    } catch(Exception e) {}
                    if(startDate != null && today.before(startDate)){
                        auctionListing.setStartDate(startDate);
                        break;
                    } else {
                        System.out.println("Please enter a valid future date");
                    }
                }
                while (true) {
                    System.out.print("End Date: ");
                    try {
                        endDate = new SimpleDateFormat(dateFormat).parse(scanner.nextLine());
                    } catch(Exception e) {}
                    if (endDate != null && startDate.before(endDate)) {
                        auctionListing.setEndDate(endDate);
                        break;
                    } else {
                        System.out.println("Please enter a valid future date");
                    }
                }
                auctionListing.setAuctionClosed(false);
                
                auctionListingJpaSessionBeanRemote.updateAuctionListing(auctionListing);
                
                System.out.println("*** Update Sucessful ***\n");
            } else{
                System.out.println("Update failed since it has already started...");
            }
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void viewAuctionListingDetails(){
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            System.out.println("*** Online Auction System >  View Auction Listing Details ***\n");
            System.out.println("Please Enter the Auction Listing ID that you want to view: ");
            AuctionListing auctionListing = auctionListingJpaSessionBeanRemote.retrieveAuctionListing(scanner.nextLong());
            System.out.println("ID: " + auctionListing.getAuctionListingId() + "\t" + "Title: " +  auctionListing.getTitle() + "\t" + "Description: " + auctionListing.getDescription() + "\t" + "StartingBid: " + BigDecimalHelper.formatCurrency(auctionListing.getStartingBid()) + "\t" + "ReservePrice: " + BigDecimalHelper.formatCurrency(auctionListing.getReservePrice()) + "\t" + "StartDate: " + auctionListing.getStartDate() + "\t" + "EndDate: " + auctionListing.getEndDate() + "\t" + "IsClosed: " + auctionListing.isAuctionClosed());
        } else {
            System.out.println("Please login...");
            
        }
    }
    
    private void retrieveAllAuctionListing(){
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            List<AuctionListing> auctionListing = auctionListingJpaSessionBeanRemote.retrieveAllAuctionListing();
            
            System.out.println("*** Online Auction System > Retrieve All Auction Listing ***\n");
            for(int i = 0; i < auctionListing.size();i++)
            {
                System.out.println("ID: " + auctionListing.get(i).getAuctionListingId() + "\t" +"Title: " + auctionListing.get(i).getTitle() + "\t" + "Description: " + auctionListing.get(i).getDescription() + "\t" + "StartingBid: " + BigDecimalHelper.formatCurrency(auctionListing.get(i).getStartingBid()) + "\t" + "ReservePrice: " + BigDecimalHelper.formatCurrency(auctionListing.get(i).getReservePrice()) + "\t" + "StartDate: " + auctionListing.get(i).getStartDate() + "\t" + "EndDate: " + auctionListing.get(i).getEndDate()+ "\t" + "IsClosed: " + auctionListing.get(i).isAuctionClosed());
            }
            
            System.out.println("-- End of Listing --\n\n");
            
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void deleteAuctionListing(){
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            System.out.println("*** Online Auction System > Delete Auction Listing ***\n");
            System.out.print("Please Enter the Auction Listing ID that you want to delete: ");
            Long auctionListingId = scanner.nextLong();
            Date auctionListingStartDate = auctionListingJpaSessionBeanRemote.retrieveAuctionListing(auctionListingId).getStartDate();
            Date auctionListingEndDate = auctionListingJpaSessionBeanRemote.retrieveAuctionListing(auctionListingId).getEndDate();
            Boolean isClosed = auctionListingJpaSessionBeanRemote.retrieveAuctionListing(auctionListingId).isAuctionClosed();
            if (todayDate().before(auctionListingStartDate)) {
                auctionListingJpaSessionBeanRemote.deleteAuctionListing(auctionListingId);
                System.out.println("*** Delete Sucessful ***\n");
            } else if (todayDate().after(auctionListingEndDate) && isClosed){
                auctionListingJpaSessionBeanRemote.deleteAuctionListing(auctionListingId);
                System.out.println("*** Delete Sucessful ***\n");
            }else {
                System.out.println("*** Delete Failed ***\n");
            }
        } else {
            System.out.println("Please login...");
            
        }
    }
    
    private void closeAuctionListing (){
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin")){
            System.out.println("*** Online Auction System > Close Auction Listing ***\n");
            System.out.print("Please Enter the Auction Listing ID that you want to close: ");
            Long auctionListingid = scanner.nextLong();
            if (!auctionListingJpaSessionBeanRemote.retrieveAuctionListing(auctionListingid).isAuctionClosed()){
                if(auctionListingJpaSessionBeanRemote.closeAuctionListing(auctionListingid) || auctionListingJpaSessionBeanRemote.closeAuctionListingBelowReservePrice(auctionListingid)){
                    System.out.println("Successfully closed");
                }
            } else {
                System.out.println("Failed to close or the aucting listing has already been closed");
            }
            
            if (auctionListingJpaSessionBeanRemote.closeAuctionListingAboveReservePrice(auctionListingid)) {
                System.out.println("There is a winner!");
                Long bidId = auctionListingJpaSessionBeanRemote.winningBid(auctionListingid).getBidId();
                createWinner(bidId,auctionListingid);
            } else if (auctionListingJpaSessionBeanRemote.closeAuctionListingBelowReservePrice(auctionListingid)){
                System.out.println("Highest bid is lower than reserve price, do you want to make the highest bid the winner?");
                System.out.print("1 for Yes, 2 for No, Please enter your choice:");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {
                    Long bidId = auctionListingJpaSessionBeanRemote.winningBid(auctionListingid).getBidId();
                    createWinner(bidId,auctionListingid);
                } else {
                    System.out.println("There will be no winner!");
                }
            }
            
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void createWinner (Long bidId, Long auctionListingId){
        Scanner scanner = new Scanner (System.in);
        Winner winner = new Winner();
        System.out.print("Remark: ");
        winner.setRemark(scanner.nextLine());
        AuctionListing auctionListing = auctionListingJpaSessionBeanRemote.retrieveAuctionListing(auctionListingId);
        winner.setAuctionListing(auctionListing);
        Bid bid = bidJpaSessionBeanRemote.retrieveBid(bidId);
        winner.setBid(bid);
        
        winnerJpaSessionBeanRemote.createWinner(winner);
        
    }
    
    private void login () {
        
        Scanner scanner = new Scanner(System.in);
        String email = null;
        String password = null;
        
        System.out.println("*** Online Auction System > Customer Login ***\n");
        while(true){
            if(customerJpaSessionBeanRemote.getCustomer() == null){
                System.out.print("Email: ");
                email = scanner.nextLine();
                System.out.print("Password: ");
                password = scanner.nextLine();
                
                if(customerJpaSessionBeanRemote.login(email, password) && customerJpaSessionBeanRemote.getCustomer().getEmail().equals("admin"))
                {
                    System.out.println("Login successful, welcome back " + customerJpaSessionBeanRemote.getCustomer().getFirstName() + " " + customerJpaSessionBeanRemote.getCustomer().getLastName() + "!\n");
                    break;
                }
                else
                {
                    System.out.println("Invalid login credential, please try again!\n");
                    customerJpaSessionBeanRemote.setCustomer(null);
                }
                
            } else {
                System.out.println("You are already login!\n");
                break;
            }
        }
    }
    
    private void logout(){
        
        customerJpaSessionBeanRemote.logout();
        
        System.out.println("You have logout successfully!\n");
    }
    
}
