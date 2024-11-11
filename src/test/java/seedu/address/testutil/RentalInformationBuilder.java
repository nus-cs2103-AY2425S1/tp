package seedu.address.testutil;

import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * A utility class to help with building RentalInformation objects.
 */
public class RentalInformationBuilder {
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_RENTAL_START_DATE = "01/01/2024";
    public static final String DEFAULT_RENTAL_END_DATE = "01/02/2024";
    public static final String DEFAULT_RENT_DUE_DATE = "15";
    public static final String DEFAULT_MONTHLY_RENT = "2500";
    public static final String DEFAULT_DEPOSIT = "7500";
    public static final String DEFAULT_CUSTOMER_LIST = "Steven Tan;David Ng";

    private Address address;
    private RentalDate rentalStartDate;
    private RentalDate rentalEndDate;
    private RentDueDate rentDueDate;
    private MonthlyRent monthlyRent;
    private Deposit deposit;
    private CustomerList customerList;

    /**
     * Creates a {@code RentalInformationBuilder} with the default details.
     */
    public RentalInformationBuilder() {
        address = new Address(DEFAULT_ADDRESS);
        rentalStartDate = new RentalDate(DEFAULT_RENTAL_START_DATE);
        rentalEndDate = new RentalDate(DEFAULT_RENTAL_END_DATE);
        rentDueDate = new RentDueDate(DEFAULT_RENT_DUE_DATE);
        monthlyRent = new MonthlyRent(DEFAULT_MONTHLY_RENT);
        deposit = new Deposit(DEFAULT_DEPOSIT);
        customerList = new CustomerList(DEFAULT_CUSTOMER_LIST);
    }

    /**
     * Initializes the RentalInformationBuilder with the data of {@code rentalInformationToCopy}.
     */
    public RentalInformationBuilder(RentalInformation rentalInformationToCopy) {
        address = rentalInformationToCopy.getAddress();
        rentalStartDate = rentalInformationToCopy.getRentalStartDate();
        rentalEndDate = rentalInformationToCopy.getRentalEndDate();
        rentDueDate = rentalInformationToCopy.getRentDueDate();
        monthlyRent = rentalInformationToCopy.getMonthlyRent();
        deposit = rentalInformationToCopy.getDeposit();
        customerList = rentalInformationToCopy.getCustomerList();
    }

    /**
     * Sets the {@code Address} of the {@code RentalInformation} that we are building.
     */
    public RentalInformationBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the (start) {@code RentalDate} of the {@code RentalInformation} that we are building.
     */
    public RentalInformationBuilder withRentalStartDate(String rentalStartDate) {
        this.rentalStartDate = new RentalDate(rentalStartDate);
        return this;
    }

    /**
     * Sets the (end) {@code RentalDate} of the {@code RentalInformation} that we are building.
     */
    public RentalInformationBuilder withRentalEndDate(String rentalEndDate) {
        this.rentalEndDate = new RentalDate(rentalEndDate);
        return this;
    }

    /**
     * Sets the {@code RentDueDate} of the {@code RentalInformation} that we are building.
     */
    public RentalInformationBuilder withRentDueDate(String rentDueDate) {
        this.rentDueDate = new RentDueDate(rentDueDate);
        return this;
    }

    /**
     * Sets the {@code MonthlyRent} of the {@code RentalInformation} that we are building.
     */
    public RentalInformationBuilder withMonthlyRent(String monthlyRent) {
        this.monthlyRent = new MonthlyRent(monthlyRent);
        return this;
    }

    /**
     * Sets the {@code Deposit} of the {@code RentalInformation} that we are building.
     */
    public RentalInformationBuilder withDeposit(String deposit) {
        this.deposit = new Deposit(deposit);
        return this;
    }

    /**
     * Sets the {@code CustomerList} of the {@code RentalInformation} that we are building.
     */
    public RentalInformationBuilder withCustomerList(String customerList) {
        this.customerList = new CustomerList(customerList);
        return this;
    }

    /**
     * Returns a new {@code RentalInformation} with current attributes.
     */
    public RentalInformation build() {
        return new RentalInformation(address, rentalStartDate, rentalEndDate, rentDueDate, monthlyRent, deposit,
                customerList);
    }
}
