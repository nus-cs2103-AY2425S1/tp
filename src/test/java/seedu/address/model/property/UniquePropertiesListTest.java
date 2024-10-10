package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BEDOK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperty.ADMIRALTY;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PropertyBuilder;

public class UniquePropertiesListTest {
    private final UniquePropertiesList uniquePropertyList = new UniquePropertiesList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.contains(null));
    }

    @Test
    public void contains_propertyNotInList_returnsFalse() {
        assertFalse(uniquePropertyList.contains(ADMIRALTY));
    }

    @Test
    public void contains_propertyInList_returnsTrue() {
        uniquePropertyList.add(ADMIRALTY);
        assertTrue(uniquePropertyList.contains(ADMIRALTY));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePropertyList.add(ADMIRALTY);
        Property editedAlice = new PropertyBuilder(ADMIRALTY).withPostalCode(VALID_POSTALCODE_BEDOK).build();
        assertFalse(uniquePropertyList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePropertyList.add(ADMIRALTY);
        assertThrows(DuplicatePersonException.class, () -> uniquePropertyList.add(ADMIRALTY));
    }


    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniquePropertyList.asUnmodifiableObservableList().remove(0));
    }

}
