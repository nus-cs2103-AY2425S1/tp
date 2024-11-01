package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@code PropertyList}.
 */
public class PropertyListTest {

    private PropertyList propertyList;
    private PropertyList emptyPropertyList;
    private Property sampleProperty;

    @BeforeEach
    public void setUp() {
        sampleProperty = Property.of(
                "123 Main St",
                "Orchard",
                "Condo",
                120.5,
                3,
                2,
                500000.00
        );
        propertyList = new PropertyList(); // Initialize empty list for each test
        emptyPropertyList = new PropertyList();
    }

    @Test
    public void addProperty_nullProperty_throwsNullPointerException() {
        // Adding a null property should throw NullPointerException
        assertThrows(NullPointerException.class, () -> propertyList.addProperty(null));
    }

    @Test
    public void addProperty_validProperty_success() {
        propertyList.addProperty(sampleProperty);
        assertEquals(1, propertyList.getProperties().size());
        assertEquals(sampleProperty, propertyList.getProperty(0));
    }

    @Test
    public void getProperty_outOfBounds_throwsIndexOutOfBoundsException() {
        // Retrieving a property from an empty list should throw IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> propertyList.getProperty(0));
    }

    @Test
    public void removeProperty_validIndex_success() {
        propertyList.addProperty(sampleProperty);
        propertyList.removeProperty(0);
        assertEquals(0, propertyList.getProperties().size());
    }

    @Test
    public void removeProperty_outOfBounds_throwsIndexOutOfBoundsException() {
        // Attempting to remove from an empty list should throw IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> propertyList.removeProperty(0));
    }

    @Test
    public void addProperty_immutableAdd_returnsNewPropertyList() {
        PropertyList newPropertyList = PropertyList.addProperty(propertyList, sampleProperty);

        // Original list should remain unchanged
        assertEquals(0, propertyList.getProperties().size());

        // New list should contain the added property
        assertEquals(1, newPropertyList.getProperties().size());
        assertEquals(sampleProperty, newPropertyList.getProperty(0));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        // Same object reference should return true
        assertEquals(propertyList, propertyList);
    }

    @Test
    public void equals_differentObjectSameContent_returnsTrue() {
        PropertyList anotherList = new PropertyList();
        assertEquals(propertyList, anotherList);
    }

    @Test
    public void equals_differentObjectDifferentContent_returnsFalse() {
        PropertyList anotherList = new PropertyList();
        anotherList.addProperty(sampleProperty);

        assertNotEquals(propertyList, anotherList);
    }

    @Test
    public void equals_null_returnsFalse() {
        // Comparing with null should return false
        assertNotEquals(propertyList, null);
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // Comparing with a different class should return false
        assertNotEquals(propertyList, new Object());
    }

    @Test
    public void toString_emptyList_returnsCorrectString() {
        // Verify the toString output for an empty list
        String expectedString = "Property List:\n";
        assertEquals(expectedString, propertyList.toString());
    }

    @Test
    public void emptyPropertyList_returnTrue() {
        assertTrue(emptyPropertyList.isEmpty());
    }

    @Test
    public void toString_nonEmptyList_returnsCorrectString() {
        propertyList.addProperty(sampleProperty);

        // Verify the toString output for a list with one property
        String expectedString = "Property List:\n" + sampleProperty.toString() + "\n";
        assertEquals(expectedString, propertyList.toString());
    }
}
