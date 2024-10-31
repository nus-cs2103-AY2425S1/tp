package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditListingCommand.EditListingDescriptor;
import seedu.address.model.listing.Region;
import seedu.address.testutil.EditListingDescriptorBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.TAMPINES;
import static seedu.address.testutil.TypicalPersons.BENSON;

public class EditListingDescriptorTest {
    @Test
    public void equals() {
        EditListingDescriptor descriptorPasirRis = new EditListingDescriptorBuilder(PASIR_RIS).build();
        EditListingDescriptor descriptorTampines = new EditListingDescriptorBuilder(TAMPINES).build();

        // same values -> returns true
        EditListingDescriptor copyPasirRis = new EditListingDescriptorBuilder(PASIR_RIS).build();
        assertTrue(descriptorPasirRis.equals(copyPasirRis));

        // same object -> returns true
        assertTrue(descriptorPasirRis.equals(descriptorPasirRis));

        // null -> returns false
        assertFalse(descriptorPasirRis.equals(null));

        // different types -> returns false
        assertFalse(descriptorPasirRis.equals(5));

        // different values -> returns false
        assertFalse(descriptorPasirRis.equals(descriptorTampines));

        // different name -> returns false
        EditListingDescriptor editedPasirRis = new EditListingDescriptorBuilder(PASIR_RIS)
                .withName(TAMPINES.getName()).build();
        assertFalse(descriptorPasirRis.equals(editedPasirRis));

        // different price -> returns false
        editedPasirRis = new EditListingDescriptorBuilder(PASIR_RIS)
                .withPrice(TAMPINES.getPrice()).build();
        assertFalse(descriptorPasirRis.equals(editedPasirRis));

        // different area -> returns false
        editedPasirRis = new EditListingDescriptorBuilder(PASIR_RIS)
                .withArea(TAMPINES.getArea()).build();
        assertFalse(descriptorPasirRis.equals(editedPasirRis));

        // different address -> returns false
        editedPasirRis = new EditListingDescriptorBuilder(PASIR_RIS)
                .withAddress(TAMPINES.getAddress()).build();
        assertFalse(descriptorPasirRis.equals(editedPasirRis));

        // different region -> returns false
        editedPasirRis = new EditListingDescriptorBuilder(PASIR_RIS)
                .withRegion(Region.CENTRAL).build();
        assertFalse(descriptorPasirRis.equals(editedPasirRis));

        // different seller -> returns false
        editedPasirRis = new EditListingDescriptorBuilder(PASIR_RIS)
                .withSeller(BENSON).build();
        assertFalse(descriptorPasirRis.equals(editedPasirRis));
    }

    @Test
    public void toStringMethod() {
        EditListingDescriptor editListingDescriptor = new EditListingDescriptor();
        String expected = EditListingDescriptor.class.getCanonicalName() + "{name="
                + editListingDescriptor.getName().orElse(null) + ", price="
                + editListingDescriptor.getPrice().orElse(null) + ", area="
                + editListingDescriptor.getArea().orElse(null) + ", address="
                + editListingDescriptor.getAddress().orElse(null) + ", region="
                + editListingDescriptor.getRegion().orElse(null) + ", seller="
                + editListingDescriptor.getSellerName().orElse(null) + "}";

        assertEquals(expected, editListingDescriptor.toString());
    }
}
