package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_RENTAL_DESCRIPTOR_ONE;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_RENTAL_DESCRIPTOR_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_LIST_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPOSIT_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTHLY_RENT_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_END_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_START_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENT_DUE_DATE_TWO;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditRentalCommand.EditRentalDescriptor;
import seedu.address.testutil.EditRentalDescriptorBuilder;

public class EditRentalDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditRentalDescriptor descriptorWithSameValues = new EditRentalDescriptor(EDIT_RENTAL_DESCRIPTOR_ONE);
        assertTrue(EDIT_RENTAL_DESCRIPTOR_ONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(EDIT_RENTAL_DESCRIPTOR_ONE.equals(EDIT_RENTAL_DESCRIPTOR_ONE));

        // null -> returns false
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(null));

        // different types -> returns false
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(5));

        // different values -> returns false
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(EDIT_RENTAL_DESCRIPTOR_TWO));

        // different address -> returns false
        EditRentalDescriptor editedOne = new EditRentalDescriptorBuilder(EDIT_RENTAL_DESCRIPTOR_ONE)
                .withAddress(VALID_ADDRESS_TWO).build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(editedOne));

        // different rental start date -> returns false
        editedOne = new EditRentalDescriptorBuilder(EDIT_RENTAL_DESCRIPTOR_ONE)
                .withRentalStartDate(VALID_RENTAL_START_DATE_TWO).build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(editedOne));

        // different rental end date -> returns false
        editedOne = new EditRentalDescriptorBuilder(EDIT_RENTAL_DESCRIPTOR_ONE)
                .withRentalEndDate(VALID_RENTAL_END_DATE_TWO).build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(editedOne));

        // different rent due date -> returns false
        editedOne = new EditRentalDescriptorBuilder(EDIT_RENTAL_DESCRIPTOR_ONE)
                .withRentDueDate(VALID_RENT_DUE_DATE_TWO).build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(editedOne));

        // different monthly rent -> returns false
        editedOne = new EditRentalDescriptorBuilder(EDIT_RENTAL_DESCRIPTOR_ONE)
                .withMonthlyRent(VALID_MONTHLY_RENT_TWO).build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(editedOne));

        // different deposit -> returns false
        editedOne = new EditRentalDescriptorBuilder(EDIT_RENTAL_DESCRIPTOR_ONE).withDeposit(VALID_DEPOSIT_TWO)
                .build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(editedOne));

        // different customer list -> returns false
        editedOne = new EditRentalDescriptorBuilder(EDIT_RENTAL_DESCRIPTOR_ONE)
                .withCustomerList(VALID_CUSTOMER_LIST_TWO).build();
        assertFalse(EDIT_RENTAL_DESCRIPTOR_ONE.equals(editedOne));
    }

    @Test
    public void toStringMethod() {
        EditRentalDescriptor editRentalDescriptor = new EditRentalDescriptor();
        String expected = EditRentalDescriptor.class.getCanonicalName() + "{address="
                + editRentalDescriptor.getAddress().orElse(null) + ", rental start date="
                + editRentalDescriptor.getRentalStartDate().orElse(null) + ", rental end date="
                + editRentalDescriptor.getRentalEndDate().orElse(null) + ", rent due date="
                + editRentalDescriptor.getRentDueDate().orElse(null) + ", monthly rent="
                + editRentalDescriptor.getMonthlyRent().orElse(null) + ", deposit="
                + editRentalDescriptor.getDeposit().orElse(null) + ", customer list="
                + editRentalDescriptor.getCustomerList().orElse(null) + "}";
        assertEquals(expected, editRentalDescriptor.toString());
    }
}
