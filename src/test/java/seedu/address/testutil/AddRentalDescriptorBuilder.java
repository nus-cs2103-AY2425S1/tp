package seedu.address.testutil;

import seedu.address.logic.commands.AddRentalCommand.AddRentalDescriptor;
import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * A utility class to help with building AddRentalDescriptor objects.
 */
public class AddRentalDescriptorBuilder {
    private AddRentalDescriptor descriptor;

    public AddRentalDescriptorBuilder() {
        descriptor = new AddRentalDescriptor();
    }

    public AddRentalDescriptorBuilder(AddRentalDescriptor descriptor) {
        this.descriptor = new AddRentalDescriptor(descriptor);
    }

    /**
     * Returns an {@code AddRentalDescriptor} with fields containing {@code rentalInformation}'s details
     */
    public AddRentalDescriptorBuilder(RentalInformation rentalInformation) {
        descriptor = new AddRentalDescriptor();
        descriptor.setAddress(rentalInformation.getAddress());
        descriptor.setRentalStartDate(rentalInformation.getRentalStartDate());
        descriptor.setRentalEndDate(rentalInformation.getRentalEndDate());
        descriptor.setRentDueDate(rentalInformation.getRentDueDate());
        descriptor.setMonthlyRent(rentalInformation.getMonthlyRent());
        descriptor.setDeposit(rentalInformation.getDeposit());
        descriptor.setCustomerList(rentalInformation.getCustomerList());
    }

    /**
     * Sets the {@code Address} of the {@code AddRentalDescriptorBuilder} that we are building.
     */
    public AddRentalDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the (end) {@code RentalDate} of the {@code AddRentalDescriptorBuilder} that we are building.
     */
    public AddRentalDescriptorBuilder withRentalStartDate(String rentalStartDate) {
        descriptor.setRentalStartDate(new RentalDate(rentalStartDate));
        return this;
    }

    /**
     * Sets the (start) {@code RentalDate} of the {@code AddRentalDescriptorBuilder} that we are building.
     */
    public AddRentalDescriptorBuilder withRentalEndDate(String rentalEndDate) {
        descriptor.setRentalEndDate(new RentalDate(rentalEndDate));
        return this;
    }

    /**
     * Sets the {@code RentDueDate} of the {@code AddRentalDescriptorBuilder} that we are building.
     */
    public AddRentalDescriptorBuilder withRentDueDate(String rentDueDate) {
        descriptor.setRentDueDate(new RentDueDate(rentDueDate));
        return this;
    }

    /**
     * Sets the {@code MonthlyRent} of the {@code AddRentalDescriptorBuilder} that we are building.
     */
    public AddRentalDescriptorBuilder withMonthlyRent(String monthlyRent) {
        descriptor.setMonthlyRent(new MonthlyRent(monthlyRent));
        return this;
    }

    /**
     * Sets the {@code Deposit} of the {@code AddRentalDescriptorBuilder} that we are building.
     */
    public AddRentalDescriptorBuilder withDeposit(String deposit) {
        descriptor.setDeposit(new Deposit(deposit));
        return this;
    }

    /**
     * Sets the {@code CustomerList} of the {@code AddRentalDescriptorBuilder} that we are building.
     */
    public AddRentalDescriptorBuilder withCustomerList(String customerList) {
        descriptor.setCustomerList(new CustomerList(customerList));
        return this;
    }

    public AddRentalDescriptor build() {
        return descriptor;
    }
}
