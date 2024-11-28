package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.testutil.TypicalPersons;

public class UniqueSellingPropertyListTest {

    private UniqueSellingPropertyList uniqueSellingPropertyList = new UniqueSellingPropertyList();
    private ObservableList<Property> propertiesToAdd = TypicalPersons.ALICE.getListOfSellingProperties();
    @Test
    public void execute_addUniqueSellingProperties_success() {
        uniqueSellingPropertyList.addUniqueSellingProperties(propertiesToAdd);

        assertEquals(propertiesToAdd.size(), uniqueSellingPropertyList.getUniqueSellingPropertiesCount());
    }

    @Test
    public void addUniqueSellingProperties_duplicateProperties_noDuplicatesAdded() {
        uniqueSellingPropertyList.addUniqueSellingProperties(propertiesToAdd);
        uniqueSellingPropertyList.addUniqueSellingProperties(propertiesToAdd);

        // Verify that there are no duplicate properties
        assertEquals(propertiesToAdd.size(), uniqueSellingPropertyList.getUniqueSellingPropertiesCount());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(uniqueSellingPropertyList.equals(uniqueSellingPropertyList));
    }

    @Test
    public void equals_differentObjectsWithSameProperties_returnsTrue() {
        UniqueSellingPropertyList anotherList = new UniqueSellingPropertyList();
        anotherList.addUniqueSellingProperties(propertiesToAdd);

        uniqueSellingPropertyList.addUniqueSellingProperties(propertiesToAdd);
        assertTrue(uniqueSellingPropertyList.equals(anotherList));
    }

    @Test
    public void equals_differentObjectsWithDifferentProperties_returnsFalse() {
        UniqueBuyingPropertyList anotherList = new UniqueBuyingPropertyList();
        ObservableList<Property> differentProperties = TypicalPersons.BOB.getListOfBuyingProperties();
        anotherList.addUniqueBuyingProperties(differentProperties);

        uniqueSellingPropertyList.addUniqueSellingProperties(propertiesToAdd);
        assertFalse(uniqueSellingPropertyList.equals(anotherList));
    }

    @Test
    public void hashCode_sameObjectsWithSameProperties_sameHashCode() {
        UniqueBuyingPropertyList anotherList = new UniqueBuyingPropertyList();
        anotherList.addUniqueBuyingProperties(propertiesToAdd);

        uniqueSellingPropertyList.addUniqueSellingProperties(propertiesToAdd);

        assertEquals(uniqueSellingPropertyList.hashCode(), anotherList.hashCode());
    }
}
