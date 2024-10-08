package seedu.address.model.rentalinformation;

import seedu.address.model.person.Address;

public class RentalInformation {
    public Address address;
    public RentalDate rentalStartDate;
    public RentalDate rentalEndDate;
    public RentDueDate rentDueDate;
    public MonthlyRent monthlyRent;
    public Deposit deposit;
    public CustomerList customerList;

    public RentalInformation(String address, String rentalStartDate, String rentalEndDate, String rentDueDate,
                             String monthlyRent, String deposit, String customerList) {
        this.address = new Address(address);
        this.rentalStartDate = new RentalDate(rentalStartDate);
        this.rentalEndDate = new RentalDate(rentalEndDate);
        this.rentDueDate = new RentDueDate(rentDueDate);
        this.monthlyRent = new MonthlyRent(monthlyRent);
        this.deposit = new Deposit(deposit);
        this.customerList = new CustomerList(customerList);
    }
}
