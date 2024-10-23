package seedu.address.testutil;

import seedu.address.logic.commands.EditRentalCommand.EditRentalDescriptor;
import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * A utility class to help with building EditRentalDescriptor objects.
 */
public class EditRentalDescriptorBuilder {
    private EditRentalDescriptor descriptor;

    public EditRentalDescriptorBuilder() {
        descriptor = new EditRentalDescriptor();
    }

    public EditRentalDescriptorBuilder(EditRentalDescriptor descriptor) {
        this.descriptor = new EditRentalDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRentalDescriptor} with fields containing {@code rentalInformation}'s details
     */
    public EditRentalDescriptorBuilder(RentalInformation rentalInformation) {
        descriptor = new EditRentalDescriptor();
        descriptor.setAddress(rentalInformation.getAddress());
        descriptor.setRentalStartDate(rentalInformation.getRentalStartDate());
        descriptor.setRentalEndDate(rentalInformation.getRentalEndDate());
        descriptor.setRentDueDate(rentalInformation.getRentDueDate());
        descriptor.setMonthlyRent(rentalInformation.getMonthlyRent());
        descriptor.setDeposit(rentalInformation.getDeposit());
        descriptor.setCustomerList(rentalInformation.getCustomerList());
    }

    /**
     * Sets the {@code Address} of the {@code EditRentalDescriptorBuilder} that we are building.
     */
    public EditRentalDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the (end) {@code RentalDate} of the {@code EditRentalDescriptorBuilder} that we are building.
     */
    public EditRentalDescriptorBuilder withRentalStartDate(String rentalStartDate) {
        descriptor.setRentalStartDate(new RentalDate(rentalStartDate));
        return this;
    }

    /**
     * Sets the (start) {@code RentalDate} of the {@code EditRentalDescriptorBuilder} that we are building.
     */
    public EditRentalDescriptorBuilder withRentalEndDate(String rentalEndDate) {
        descriptor.setRentalEndDate(new RentalDate(rentalEndDate));
        return this;
    }

    /**
     * Sets the {@code RentDueDate} of the {@code EditRentalDescriptorBuilder} that we are building.
     */
    public EditRentalDescriptorBuilder withRentDueDate(String rentDueDate) {
        descriptor.setRentDueDate(new RentDueDate(rentDueDate));
        return this;
    }

    /**
     * Sets the {@code MonthlyRent} of the {@code EditRentalDescriptorBuilder} that we are building.
     */
    public EditRentalDescriptorBuilder withMonthlyRent(String monthlyRent) {
        descriptor.setMonthlyRent(new MonthlyRent(monthlyRent));
        return this;
    }

    /**
     * Sets the {@code Deposit} of the {@code EditRentalDescriptorBuilder} that we are building.
     */
    public EditRentalDescriptorBuilder withDeposit(String deposit) {
        descriptor.setDeposit(new Deposit(deposit));
        return this;
    }

    /**
     * Sets the {@code CustomerList} of the {@code EditRentalDescriptorBuilder} that we are building.
     */
    public EditRentalDescriptorBuilder withCustomerList(String customerList) {
        descriptor.setCustomerList(new CustomerList(customerList));
        return this;
    }

    public EditRentalDescriptor build() {
        return descriptor;
    }
}
