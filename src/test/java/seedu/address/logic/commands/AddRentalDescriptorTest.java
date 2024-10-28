package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADD_RENTAL_DESCRIPTOR_ONE;
import static seedu.address.logic.commands.CommandTestUtil.ADD_RENTAL_DESCRIPTOR_TWO;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_RENTAL_DESCRIPTOR_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_LIST_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPOSIT_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTHLY_RENT_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_END_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_START_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENT_DUE_DATE_TWO;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddRentalCommand.AddRentalDescriptor;
import seedu.address.testutil.AddRentalDescriptorBuilder;

public class AddRentalDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        AddRentalDescriptor descriptorWithSameValues = new AddRentalDescriptor(ADD_RENTAL_DESCRIPTOR_ONE);
        assertTrue(ADD_RENTAL_DESCRIPTOR_ONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(ADD_RENTAL_DESCRIPTOR_ONE.equals(ADD_RENTAL_DESCRIPTOR_ONE));

        // null -> returns false
        assertFalse(ADD_RENTAL_DESCRIPTOR_ONE.equals(null));

        // different types -> returns false
        assertFalse(ADD_RENTAL_DESCRIPTOR_ONE.equals(5));

        // different values -> returns false
        assertFalse(ADD_RENTAL_DESCRIPTOR_ONE.equals(ADD_RENTAL_DESCRIPTOR_TWO));

        // different address -> returns false
        AddRentalDescriptor descriptor1 = new AddRentalDescriptorBuilder(ADD_RENTAL_DESCRIPTOR_ONE)
                .withAddress(VALID_ADDRESS_TWO).build();
        assertFalse(ADD_RENTAL_DESCRIPTOR_ONE.equals(descriptor1));

        // different rental start date -> returns false
        descriptor1 = new AddRentalDescriptorBuilder(ADD_RENTAL_DESCRIPTOR_ONE)
                .withRentalStartDate(VALID_RENTAL_START_DATE_TWO)
                .build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(descriptor1));

        // different rental end date -> returns false
        descriptor1 = new AddRentalDescriptorBuilder(ADD_RENTAL_DESCRIPTOR_ONE)
                .withRentalEndDate(VALID_RENTAL_END_DATE_TWO).build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(descriptor1));

        // different rent due date -> returns false
        descriptor1 = new AddRentalDescriptorBuilder(ADD_RENTAL_DESCRIPTOR_ONE).withRentDueDate(VALID_RENT_DUE_DATE_TWO)
                .build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(descriptor1));

        // different monthly rent -> returns false
        descriptor1 = new AddRentalDescriptorBuilder(ADD_RENTAL_DESCRIPTOR_ONE).withMonthlyRent(VALID_MONTHLY_RENT_TWO)
                .build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(descriptor1));

        // different deposit -> returns false
        descriptor1 = new AddRentalDescriptorBuilder(ADD_RENTAL_DESCRIPTOR_ONE).withDeposit(VALID_DEPOSIT_TWO).build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(descriptor1));

        // different customer list -> returns false
        descriptor1 = new AddRentalDescriptorBuilder(ADD_RENTAL_DESCRIPTOR_ONE)
                .withCustomerList(VALID_CUSTOMER_LIST_TWO).build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(descriptor1));
    }

    @Test
    public void toStringMethod() {
        AddRentalDescriptor addRentalDescriptor = new AddRentalDescriptor();
        String expected = AddRentalDescriptor.class.getCanonicalName() + "{address="
                + addRentalDescriptor.getAddress().orElse(null) + ", rental start date="
                + addRentalDescriptor.getRentalStartDate().orElse(null) + ", rental end date="
                + addRentalDescriptor.getRentalEndDate().orElse(null) + ", rent due date="
                + addRentalDescriptor.getRentDueDate().orElse(null) + ", monthly rent="
                + addRentalDescriptor.getMonthlyRent().orElse(null) + ", deposit="
                + addRentalDescriptor.getDeposit().orElse(null) + ", customer list="
                + addRentalDescriptor.getCustomerList().orElse(null) + "}";
        assertEquals(expected, addRentalDescriptor.toString());
    }
}
