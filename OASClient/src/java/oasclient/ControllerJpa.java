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
import entity.Address;
import entity.AuctionListing;
import entity.Bid;
import entity.CreditPackage;
import entity.CreditTransaction;
import entity.Customer;
import entity.Winner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import util.helper.BigDecimalHelper;

public class ControllerJpa {
    CustomerJpaSessionBeanRemote customerJpaSessionBeanRemote;
    CreditPackageJpaSessionBeanRemote creditPackageJpaSessionBeanRemote;
    CreditTransactionJpaSessionBeanRemote creditTransactionJpaSessionRemote;
    AddressJpaSessionBeanRemote addressJpaSessionBeanRemote;
    AuctionListingJpaSessionBeanRemote auctionListingJpaSessionBeanRemote;
    BidJpaSessionBeanRemote bidJpaSessionBeanRemote;
    WinnerJpaSessionBeanRemote winnerJpaSessionBeanRemote;
    
    public ControllerJpa(){
    }
    
    public ControllerJpa(CreditPackageJpaSessionBeanRemote creditPackageJpaSessionBeanRemote,CustomerJpaSessionBeanRemote customerJpaSessionBeanRemote,CreditTransactionJpaSessionBeanRemote creditTransactionJpaSessionRemote,AddressJpaSessionBeanRemote addressJpaSessionBeanRemote,AuctionListingJpaSessionBeanRemote auctionListingJpaSessionBeanRemote, BidJpaSessionBeanRemote bidJpaSessionBeanRemote,WinnerJpaSessionBeanRemote winnerJpaSessionBeanRemote){
        this.customerJpaSessionBeanRemote = customerJpaSessionBeanRemote;
        this.creditPackageJpaSessionBeanRemote = creditPackageJpaSessionBeanRemote;
        this.creditTransactionJpaSessionRemote = creditTransactionJpaSessionRemote;
        this.addressJpaSessionBeanRemote = addressJpaSessionBeanRemote;
        this.auctionListingJpaSessionBeanRemote = auctionListingJpaSessionBeanRemote;
        this.bidJpaSessionBeanRemote = bidJpaSessionBeanRemote;
        this.winnerJpaSessionBeanRemote = winnerJpaSessionBeanRemote;
    }
    
    public void runSystem()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response;
        
        while(true)
        {
            System.out.println("*** Welcome to Online Auction System ***\n");
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Logout");
            System.out.println("4: View Customer Profile");
            System.out.println("5: Update Customer Profile");
            System.out.println("6: Purchase Credit Package");
            System.out.println("7: Create Address");
            System.out.println("8: Update Address");
            System.out.println("9: Retrieve All Addesses");
            System.out.println("10: Delete Addesses");
            System.out.println("11: Browse All Auction Listing");
            System.out.println("12: Place New Bid");
            System.out.println("13: Refresh Auction Listing Bids");
            System.out.println("14: Browse Won Auction Listing");
            System.out.println("15: Select Delivery Address For Won AuctionListing");
            System.out.println("16: Exit");
            response = 0;
            
            while(response < 1 || response > 16)
            {
                System.out.print("> ");
                
                response = scanner.nextInt();
                
                if(response.equals(1)) {
                    createCustomer();
                } else if (response == 2) {
                    login();
                } else if (response == 3) {
                    logout();
                } else if (response == 4) {
                    viewCustomerProfile();
                } else if (response == 5) {
                    updateCustomerProfile();
                } else if (response == 6) {
                    createCreditTransaction();
                } else if (response == 7) {
                    createAddress ();
                } else if (response == 8) {
                    updateAddress ();
                } else if (response == 9) {
                    retrieveAllAddress();
                } else if (response == 10) {
                    deleteAddress();
                } else if (response == 11) {
                    retrieveAllAuctionListing();
                } else if (response == 12) {
                    placeNewBid ();
                } else if (response == 13) {
                    refreshAuctionListingBids();
                } else if (response == 14) {
                    browseWonAuctionListing();
                } else if (response == 15) {
                    selectDeliveryAddressForWonAuctionListing();
                } else if (response == 16) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 16)
            {
                break;
            }
        }
    }
    
    private void createCustomer(){
        Customer customer = new Customer();
        Scanner scanner = new Scanner(System.in);
        
        
        System.out.println("*** Online Auction System > Create Customer ***\n");
        System.out.print("First Name: ");
        customer.setFirstName(scanner.nextLine());
        System.out.print("Last Name: ");
        customer.setLastName(scanner.nextLine());
        System.out.print("Email: ");
        customer.setEmail(scanner.nextLine());
        System.out.print("Password: ");
        customer.setPassword(scanner.nextLine());
        
        customer.setCurrentCreditBalance(BigDecimalHelper.createBigDecimal("0"));
        
        Long newCustomerId = customerJpaSessionBeanRemote.createCustomer(customer);
        
        if(newCustomerId != null)
        {
            System.out.println("New customer " + newCustomerId + " created successfully!\n\n");
        }
        else
        {
            System.out.println("An error has occurred while creating the new customer!\n\n");
        }
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
                
                if(customerJpaSessionBeanRemote.login(email, password))
                {
                    System.out.println("Login successful, welcome back " + customerJpaSessionBeanRemote.getCustomer().getFirstName() + " " + customerJpaSessionBeanRemote.getCustomer().getLastName() + "!\n");
                    break;
                }
                else
                {
                    System.out.println("Invalid login credential, please try again, or resigter with new account. ");
                    System.out.print("Register new account? 1 for Yes, 2 for No ");
                    int answer = scanner.nextInt();
                    if (answer == 1) {
                        createCustomer();
                        break;
                    }
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
    
    private void viewCustomerProfile(){
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            System.out.println("*** Online Auction System > View Customer Profile ***\n");
            
            System.out.println("Customer ID = " + customerJpaSessionBeanRemote.getCustomer().getCustomerId());
            System.out.println("First Name = " + customerJpaSessionBeanRemote.getCustomer().getFirstName());
            System.out.println("Last Name = " + customerJpaSessionBeanRemote.getCustomer().getLastName());
            System.out.println("Email = " + customerJpaSessionBeanRemote.getCustomer().getEmail());
            System.out.println("Password = " + customerJpaSessionBeanRemote.getCustomer().getPassword());
            System.out.println("Credit Balance = " + BigDecimalHelper.formatCurrency(customerJpaSessionBeanRemote.getCustomer().getCurrentCreditBalance()));
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void updateCustomerProfile(){
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            Customer customer = customerJpaSessionBeanRemote.getCustomer();
            System.out.println("*** Online Auction System > Update Customer ***\n");
            System.out.print("First Name: ");
            customer.setFirstName(scanner.nextLine());
            System.out.print("Last Name: ");
            customer.setLastName(scanner.nextLine());
            System.out.print("Email: ");
            customer.setEmail(scanner.nextLine());
            System.out.print("Password: ");
            customer.setPassword(scanner.nextLine());
            
            customerJpaSessionBeanRemote.updateCustomer(customer);
            customerJpaSessionBeanRemote.setCustomer(customer);
            
            System.out.println("*** Update Sucessful ***\n");
            
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void createCreditTransaction(){
        Scanner scanner = new Scanner(System.in);
        CreditTransaction creditTransaction = new CreditTransaction();
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            Customer customer = customerJpaSessionBeanRemote.getCustomer();
            creditTransaction.setCutmoer(customer);
            CreditPackage creditPackage = new CreditPackage();
            
            System.out.println("*** Online Auction System > Purchase Credit Package ***\n");
            List<CreditPackage> creditPackages = creditPackageJpaSessionBeanRemote.retrieveAllCreditPackage();
            System.out.println("List of All Credit Package:\n");
            for(int i = 0; i < creditPackages.size();i++)
            {
                System.out.println(creditPackages.get(i).getcreditPackageId() + "\t" + creditPackages.get(i).getName() + "\t" + BigDecimalHelper.formatCurrency(creditPackages.get(i).getCreditValue()) + "\t" + BigDecimalHelper.formatCurrency(creditPackages.get(i).getPrice()));
            }
            
            System.out.println("-- End of Listing --\n\n");
            if (!creditPackages.isEmpty()) {
                System.out.print("Please Enter Purchasing Package ID: ");
                creditPackage = creditPackageJpaSessionBeanRemote.retrieveCreditPackage(scanner.nextLong());
                creditTransaction.setCreditPackage(creditPackage);
                System.out.print("Please Enter Quantity: ");
                int quantity = scanner.nextInt();
                creditTransaction.setQuantity(quantity);
                scanner.nextLine();
                BigDecimal price = creditPackage.getPrice();
                creditTransaction.setPrice(price);
                System.out.print("Price : " + price + "\n");
                BigDecimal totalAmount = BigDecimal.valueOf((double)price.intValueExact() * quantity);
                creditTransaction.setTotalAmount(totalAmount);
                System.out.print("Total Amount: " + BigDecimalHelper.formatCurrency(totalAmount) + "\n");
                Date date = new Date();
                creditTransaction.setTransactionDateTime(date);
                customer.setCurrentCreditBalance(BigDecimal.valueOf((double)customer.getCurrentCreditBalance().intValueExact() + (double)totalAmount.intValueExact()));
                customerJpaSessionBeanRemote.updateCustomer(customer);
                Long creditTransactionId = creditTransactionJpaSessionRemote.createCreditTransaction(creditTransaction);
                customerJpaSessionBeanRemote.setCustomer(customer);
                if(creditTransactionId != null)
                {
                    System.out.println("New Transaction " + creditTransactionId + " has been made\n");
                }
                else
                {
                    System.out.println("An error has occurred while making the transaction!\n\n");
                }
            } else {
                System.out.println("There is no package...");
            }
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void createAddress (){
        Scanner scanner = new Scanner(System.in);
        Address address = new Address();
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            System.out.println("*** Online Auction System > Create Address ***\n");
            System.out.print("Line1: ");
            address.setLine1(scanner.nextLine());
            System.out.print("Line2: ");
            address.setLine2(scanner.nextLine());
            System.out.print("Line3: ");
            address.setLine3(scanner.nextLine());
            System.out.print("State: ");
            address.setState(scanner.nextLine());
            System.out.print("City: ");
            address.setCity(scanner.nextLine());
            System.out.print("PostalCode: ");
            address.setPostalCode(scanner.nextLine());
            System.out.print("Country: ");
            address.setCountry(scanner.nextLine());
            Customer customer = customerJpaSessionBeanRemote.getCustomer();
            address.setCustomer(customer);
            
            Long newAddressId = addressJpaSessionBeanRemote.createAddress(address);
            
            if(newAddressId != null)
            {
                System.out.println("New Address " + newAddressId + " created successfully!\n\n");
            }
            else
            {
                System.out.println("An error has occurred while creating the new Address!\n\n");
            }
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void updateAddress () {
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            Address address = new Address();
            System.out.println("*** Online Auction System > Update Address ***\n");
            retrieveAllAddress();
            System.out.print("Please Enter the Address ID that you want to update: ");
            Long addressId = scanner.nextLong();
            List <Address> addressList = addressJpaSessionBeanRemote.retrieveAllAddress(customerJpaSessionBeanRemote.getCustomer().getCustomerId());
            if (addressList.isEmpty()) {
                System.out.println("You do not have any address yet.");
            } else {
                Boolean isBelonged = false;
                for (int i = 0; i < addressList.size(); i ++) {
                    if (addressList.get(i).getAddressId().equals(addressId) ) {
                        isBelonged = true;
                    }
                }
                if (isBelonged) {
                    address.setAddressId(addressId);
                    scanner.nextLine();
                    System.out.print("Line1: ");
                    address.setLine1(scanner.nextLine());
                    System.out.print("Line2: ");
                    address.setLine2(scanner.nextLine());
                    System.out.print("Line3: ");
                    address.setLine3(scanner.nextLine());
                    System.out.print("State: ");
                    address.setState(scanner.nextLine());
                    System.out.print("City: ");
                    address.setCity(scanner.nextLine());
                    System.out.print("PostalCode: ");
                    address.setPostalCode(scanner.nextLine());
                    System.out.print("Country: ");
                    address.setCountry(scanner.nextLine());
                    Customer customer = customerJpaSessionBeanRemote.getCustomer();
                    address.setCustomer(customer);
                    
                    
                    addressJpaSessionBeanRemote.updateAddress(address);
                    
                    System.out.println("*** Update Sucessful ***\n");
                } else {
                    System.out.println("The address dose not belong to you.");
                }
            }
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void retrieveAllAddress(){
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            List<Address> address = addressJpaSessionBeanRemote.retrieveAllAddress(customerJpaSessionBeanRemote.getCustomer().getCustomerId());
            
            System.out.println("*** Online Auction System > Retrieve All Addresses ***\n");
            
            for(int i = 0; i < address.size();i++)
            {
                System.out.println(address.get(i).getAddressId() + "\t" + address.get(i).getLine1() + "\t" + address.get(i).getLine2() + "\t" + address.get(i).getLine3() + "\t" + address.get(i).getCity() + "\t" + address.get(i).getPostalCode() + "\t" + address.get(i).getCountry());
            }
            
            System.out.println("-- End of Listing --\n\n");
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void deleteAddress(){
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            System.out.println("*** Online Auction System > Delete Address ***\n");
            System.out.print("Please Enter the Address ID that you want to delete: ");
            Long addressId = scanner.nextLong();
            Address address = addressJpaSessionBeanRemote.retrieveAddress(addressId);
            if (address != null) {
                if (!addressJpaSessionBeanRemote.isAssociatedWithWinningBid(addressId)){
                    addressJpaSessionBeanRemote.deleteAddress(addressId);
                    System.out.println("*** Delete Sucessful ***\n");
                } else {
                    System.out.println("*** Delete Failed ***\n");
                }
            } else {
                System.out.println("*** Delete Failed, Please enter a valid address ID ***\n");
            }
        } else {
            System.out.println("Please login...");
            
        }
    }
    
    private void viewAuctionListingDetails(Long id){
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            System.out.println("*** Online Auction System >  View Auction Listing Details ***\n");
            
            AuctionListing auctionListing = auctionListingJpaSessionBeanRemote.retrieveAuctionListing(id);
            if (auctionListing != null) {
                System.out.println("ID: " + auctionListing.getAuctionListingId() + "\t" + "Title: " +  auctionListing.getTitle() + "\t" + "Description: " + auctionListing.getDescription() + "\t" + "StartingBid: " + BigDecimalHelper.formatCurrency(auctionListing.getStartingBid()) + "\t" + "ReservePrice: " + BigDecimalHelper.formatCurrency(auctionListing.getReservePrice()) + "\t" + "StartDate: " + auctionListing.getStartDate() + "\t" + "EndDate: " + auctionListing.getEndDate());
            } else {
                System.out.println("Please enter a valid Auction Listing Id");
            }
        }else {
            System.out.println("Please login...");
            
        }
    }
    
    private Boolean retriveAuctionListingDetails(Long id){
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            System.out.println("*** Online Auction System >  View Auction Listing Details ***\n");
            
            AuctionListing auctionListing = auctionListingJpaSessionBeanRemote.retrieveAuctionListing(id);
            if (auctionListing != null) {
                System.out.println("ID: " + auctionListing.getAuctionListingId() + "\t" + "Title: " +  auctionListing.getTitle() + "\t" + "Description: " + auctionListing.getDescription() + "\t" + "StartingBid: " + BigDecimalHelper.formatCurrency(auctionListing.getStartingBid()) + "\t" + "ReservePrice: " + BigDecimalHelper.formatCurrency(auctionListing.getReservePrice()) + "\t" + "StartDate: " + auctionListing.getStartDate() + "\t" + "EndDate: " + auctionListing.getEndDate());
                return true;
            } else {
                System.out.println("Please enter a valid Auction Listing Id");
                return false;
            }
        }else {
            System.out.println("Please login...");
        }
        return false;
    }
    
    private void retrieveAllAuctionListing(){
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            List<AuctionListing> auctionListing = auctionListingJpaSessionBeanRemote.retrieveAllAuctionListing();
            
            System.out.println("*** Online Auction System > Browse All Auction Listing ***\n");
            
            for(int i = 0; i < auctionListing.size();i++)
            {
                Date today = todayDate ();
                if (auctionListing.get(i).getStartDate() != null && auctionListing.get(i).getEndDate() != null){
                    if(auctionListing.get(i).getStartDate().before(today) && auctionListing.get(i).getEndDate().after(today)){
                        System.out.println("ID: " + auctionListing.get(i).getAuctionListingId() + "\t" + "Title: " +  auctionListing.get(i).getTitle() + "\t" + "Description: " + auctionListing.get(i).getDescription() + "\t" + "StartingBid: " + BigDecimalHelper.formatCurrency(auctionListing.get(i).getStartingBid()) + "\t" + "ReservePrice: " + BigDecimalHelper.formatCurrency(auctionListing.get(i).getReservePrice()) + "\t" + "StartDate: " + auctionListing.get(i).getStartDate() + "\t" + "EndDate: " + auctionListing.get(i).getEndDate());
                    }
                }
            }
            
            System.out.println("-- End of Listing --\n\n");
            
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void placeNewBid (){
        
        Bid bid = new Bid();
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            System.out.println("*** Online Auction System > Place New Bid ***\n");
            
            System.out.print("Please Enter the Auction Listing ID that you want to Bid: ");
            Long auctionListingId = scanner.nextLong();
            if(retriveAuctionListingDetails(auctionListingId)){
                BigDecimal highestBid = null;
                highestBid = auctionListingJpaSessionBeanRemote.currentHighestBid(auctionListingId);
                if (highestBid != null) {
                    System.out.println("Current highest bid is: " + BigDecimalHelper.formatCurrency(highestBid));
                } else {
                    System.out.println("There is no bid yet.");
                }
                scanner.nextLine();
                Customer customer = customerJpaSessionBeanRemote.getCustomer();
                bid.setCustomer(customer);
                
                Calendar c = new GregorianCalendar();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                Date today = c.getTime();
                bid.setBidDate(today);
                
                System.out.print("Please enter your bid amount: ");
                BigDecimal newBid = BigDecimalHelper.createBigDecimal(scanner.nextLine());
                
                if (customerJpaSessionBeanRemote.getCustomer().getCurrentCreditBalance().compareTo(newBid) != -1){
                    bid.setBidAmount(newBid);
                    BigDecimal newBalance = BigDecimal.valueOf((double)customerJpaSessionBeanRemote.getCustomer().getCurrentCreditBalance().intValueExact() - (double)newBid.intValueExact());
                    
                    customer.setCurrentCreditBalance(newBalance);
                    customerJpaSessionBeanRemote.updateCustomer(customer);
                    customerJpaSessionBeanRemote.setCustomer(customer);
                    Long newBidId = bidJpaSessionBeanRemote.createBid(bid,auctionListingId);
                    
                    if(newBidId != null)
                    {
                        System.out.println("New bid " + newBidId + " has been palced successfully!\n\n");
                    }
                    else
                    {
                        System.out.println("An error has occurred while creating the new Bid!\n\n");
                    }
                } else {
                    System.out.println("You do not have enough Credit balance.");
                } 
            }
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void refreshAuctionListingBids(){
        
        Scanner scanner = new Scanner(System.in);
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            System.out.println("*** Online Auction System > Refresh Auction Listing Bids ***\n");
            System.out.print("Please Enter the Auction Listing ID that you want to view: ");
            Long auctionListingId = scanner.nextLong();
            viewAuctionListingDetails(auctionListingId);
            BigDecimal highestBid = null;
            highestBid = auctionListingJpaSessionBeanRemote.currentHighestBid(auctionListingId);
            if (highestBid != null) {
                System.out.println("Current Highest Bid is: " + BigDecimalHelper.formatCurrency(highestBid));
            } else {
                System.out.println("There is no Bid yet.");
            }
        } else {
            System.out.println("Please login...");
        }
    }
    
    private void browseWonAuctionListing (){
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            System.out.println("*** Online Auction System > Browse Won Auction Listing ***\n");
            List<Bid> bids = bidJpaSessionBeanRemote.retrieveAllBidByCustomer(customerJpaSessionBeanRemote.getCustomer().getCustomerId());
            List<Bid> winningBids = new ArrayList<Bid>();
            if (bids != null){
                for (int i = 0; i < bids.size(); i ++) {
                    if (winnerJpaSessionBeanRemote.isWinningBid(bids.get(i).getBidId())){
                        winningBids.add(bids.get(i));
                    }
                }
            } else {
                System.out.println("There is no bid..");
            }
            if (!winningBids.isEmpty()) {
                for (int i = 0; i < winningBids.size(); i ++) {
                    AuctionListing auctionListing = winningBids.get(i).getAuctionListing();
                    System.out.println(auctionListing.getAuctionListingId() + "\t" + auctionListing.getTitle() + "\t" + auctionListing.getDescription() + "\t" + BigDecimalHelper.formatCurrency(auctionListing.getStartingBid()) + "\t" + BigDecimalHelper.formatCurrency(auctionListing.getReservePrice()) + "\t" + auctionListing.getStartDate() + "\t" + auctionListing.getEndDate());
                }
            } else {
                System.out.println("There is no winning bid...");
            }
        } else {
            System.out.println("Please login...");
            
        }
    }
    
    private void selectDeliveryAddressForWonAuctionListing(){
        
        Scanner scanner = new Scanner(System.in);
        
        Winner winner = new Winner();
        
        if(customerJpaSessionBeanRemote.getCustomer() != null){
            
            List<Bid> bids = bidJpaSessionBeanRemote.retrieveAllBidByCustomer(customerJpaSessionBeanRemote.getCustomer().getCustomerId());
            List<Bid> winningBids = new ArrayList<Bid>();
            if (bids != null){
                for (int i = 0; i < bids.size(); i ++) {
                    if (winnerJpaSessionBeanRemote.isWinningBid(bids.get(i).getBidId())){
                        winningBids.add(bids.get(i));
                    }
                }
            } else {
                System.out.println("There is no bid by you..");
            }
            if (!winningBids.isEmpty()) {
                System.out.println("*** Online Auction System > Select Delivery Address For Won Auction Listing ***\n");
                System.out.print("Please Enter the Auction Listing ID that you won: ");
                Long auctionListingId = scanner.nextLong();
                scanner.nextLine();
                winner = winnerJpaSessionBeanRemote.retrieveWinnerByAuctionListingId(auctionListingId);
                retrieveAllAddress();
                System.out.print("Please Enter the Address ID that you want to deliver: ");
                Long addressId = scanner.nextLong();
                scanner.nextLine();
                winner.setAddress(addressJpaSessionBeanRemote.retrieveAddress(addressId));
                
                winnerJpaSessionBeanRemote.updateWinner(winner);
                System.out.print("Update Sucessful");
            } else {
                System.out.println("There is no winning bid...");
            }
        } else {
            System.out.println("Please login...");
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
}


