package seedu.address.model.person;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.testutil.TypicalPersons;

import static org.junit.jupiter.api.Assertions.*;

public class UniqueBuyingPropertyListTest {

    UniqueBuyingPropertyList uniqueBuyingPropertyList = new UniqueBuyingPropertyList();
    ObservableList<Property> propertiesToAdd = TypicalPersons.ALICE.getListOfBuyingProperties();
    @Test
    public void execute_addUniqueBuyingProperties_success() {
        uniqueBuyingPropertyList.addUniqueBuyingProperties(propertiesToAdd);

        assertEquals(propertiesToAdd.size(), uniqueBuyingPropertyList.getUniqueBuyingPropertiesCount());
    }

    @Test
    public void addUniqueBuyingProperties_duplicateProperties_noDuplicatesAdded() {
        uniqueBuyingPropertyList.addUniqueBuyingProperties(propertiesToAdd);
        uniqueBuyingPropertyList.addUniqueBuyingProperties(propertiesToAdd);

        // Verify that there are no duplicate properties
        assertEquals(propertiesToAdd.size(), uniqueBuyingPropertyList.getUniqueBuyingPropertiesCount());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(uniqueBuyingPropertyList.equals(uniqueBuyingPropertyList));
    }

    @Test
    public void equals_differentObjectsWithSameProperties_returnsTrue() {
        UniqueBuyingPropertyList anotherList = new UniqueBuyingPropertyList();
        anotherList.addUniqueBuyingProperties(propertiesToAdd);

        uniqueBuyingPropertyList.addUniqueBuyingProperties(propertiesToAdd);
        assertTrue(uniqueBuyingPropertyList.equals(anotherList));
    }

    @Test
    public void equals_differentObjectsWithDifferentProperties_returnsFalse() {
        UniqueBuyingPropertyList anotherList = new UniqueBuyingPropertyList();
        ObservableList<Property> differentProperties = TypicalPersons.BOB.getListOfBuyingProperties();
        anotherList.addUniqueBuyingProperties(differentProperties);

        uniqueBuyingPropertyList.addUniqueBuyingProperties(propertiesToAdd);
        assertFalse(uniqueBuyingPropertyList.equals(anotherList));
    }

    @Test
    public void hashCode_sameObjectsWithSameProperties_sameHashCode() {
        UniqueBuyingPropertyList anotherList = new UniqueBuyingPropertyList();
        anotherList.addUniqueBuyingProperties(propertiesToAdd);

        uniqueBuyingPropertyList.addUniqueBuyingProperties(propertiesToAdd);

        assertEquals(uniqueBuyingPropertyList.hashCode(), anotherList.hashCode());
    }

    @Test
    public void hashCode_differentObjectsWithDifferentProperties_differentHashCode() {
        UniqueBuyingPropertyList anotherList = new UniqueBuyingPropertyList();
        ObservableList<Property> differentProperties = TypicalPersons.BOB.getListOfBuyingProperties();
        anotherList.addUniqueBuyingProperties(differentProperties);

        uniqueBuyingPropertyList.addUniqueBuyingProperties(propertiesToAdd);

        assertNotEquals(uniqueBuyingPropertyList.hashCode(), anotherList.hashCode());
    }
}
