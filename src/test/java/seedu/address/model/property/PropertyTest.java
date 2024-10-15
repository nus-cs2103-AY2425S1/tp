package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_BEDOK;
import static seedu.address.testutil.TypicalProperty.ADMIRALTY;
import static seedu.address.testutil.TypicalProperty.BEDOK;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PropertyBuilder;

public class PropertyTest {

    @Test
    public void isSameProperty() {
        // same object -> returns true
        assertTrue(ADMIRALTY.isSameProperty(ADMIRALTY));

        // null -> returns false
        assertFalse(ADMIRALTY.isSameProperty(null));

        // same name, all other attributes different -> returns false
        Property editedAdmiralty = new PropertyBuilder(ADMIRALTY).withPostalCode(VALID_POSTALCODE_BEDOK)
                .withUnit(VALID_UNIT_BEDOK).build();
        assertFalse(ADMIRALTY.isSameProperty(editedAdmiralty));

        // different postal code -> returns false
        editedAdmiralty = new PropertyBuilder(ADMIRALTY).withPostalCode(VALID_POSTALCODE_BEDOK).build();
        assertFalse(ADMIRALTY.isSameProperty(editedAdmiralty));

        // different unit -> returns false
        editedAdmiralty = new PropertyBuilder(ADMIRALTY).withUnit(VALID_UNIT_BEDOK).build();
        assertFalse(ADMIRALTY.isSameProperty(editedAdmiralty));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Property admiraltyCopy = new PropertyBuilder(ADMIRALTY).build();
        assertTrue(ADMIRALTY.equals(admiraltyCopy));

        // same object -> returns true
        assertTrue(ADMIRALTY.equals(ADMIRALTY));

        // null -> returns false
        assertFalse(ADMIRALTY.equals(null));

        // different type -> returns false
        assertFalse(ADMIRALTY.equals(5));

        // different person -> returns false
        assertFalse(ADMIRALTY.equals(BEDOK));

        // different postal code -> returns false
        Property editedAdmiralty = new PropertyBuilder(ADMIRALTY).withPostalCode(VALID_POSTALCODE_BEDOK).build();
        assertFalse(ADMIRALTY.equals(editedAdmiralty));

        // different unit -> returns false
        editedAdmiralty = new PropertyBuilder(ADMIRALTY).withUnit(VALID_UNIT_BEDOK).build();
        assertFalse(ADMIRALTY.equals(editedAdmiralty));
    }

    @Test
    public void toStringMethod() {
        String expected = Property.class.getCanonicalName() + "{postalCode=" + ADMIRALTY.getPostalCode()
                + ", unit=" + ADMIRALTY.getUnit() + "}";
        assertEquals(expected, ADMIRALTY.toString());
    }
}
