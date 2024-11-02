package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASKING_PRICE_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_TYPE_BRENDA;
import static seedu.address.testutil.property.TypicalProperties.ALICE;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.property.Property;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.testutil.property.PropertyBuilder;

public class PropertyListTest {

    private final PropertyList propertyList = new PropertyList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), propertyList.getPropertyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> propertyList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPropertyList_replacesData() {
        PropertyList newData = getTypicalPropertyList();
        propertyList.resetData(newData);
        assertEquals(newData, propertyList);
    }

    @Test
    public void resetData_withDuplicateProperties_throwsDuplicatePropertyException() {
        // Two properties with the same identity fields.
        Property editedAlice = new PropertyBuilder(ALICE).withAskingPrice(VALID_ASKING_PRICE_ALAN).build();
        List<Property> newProperties = Arrays.asList(ALICE, editedAlice);
        PropertyListStub newData = new PropertyListStub(newProperties);

        assertThrows(DuplicatePropertyException.class, () -> propertyList.resetData(newData));
    }

    @Test
    public void hasProperty_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> propertyList.hasProperty(null));
    }

    @Test
    public void hasProperty_propertyNotInPropertyList_returnsFalse() {
        assertFalse(propertyList.hasProperty(ALICE));
    }

    @Test
    public void hasProperty_propertyInPropertyList_returnsTrue() {
        propertyList.addProperty(ALICE);
        assertTrue(propertyList.hasProperty(ALICE));
    }

    @Test
    public void hasProperty_propertyWithSameIdentityFieldsInPropertyList_returnsTrue() {
        propertyList.addProperty(ALICE);
        Property editedAlice = new PropertyBuilder(ALICE).withAskingPrice(VALID_ASKING_PRICE_ALAN)
                .withPropertyType(VALID_PROPERTY_TYPE_BRENDA).build();
        assertTrue(propertyList.hasProperty(editedAlice));
    }

    @Test
    public void getPropertyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> propertyList.getPropertyList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = PropertyList.class.getCanonicalName() + "{properties=" + propertyList.getPropertyList() + "}";
        assertEquals(expected, propertyList.toString());
    }

    /**
     * A stub ReadOnlyPropertyList whose property list can violate interface constraints.
     */
    private static class PropertyListStub implements ReadOnlyPropertyList {
        private final ObservableList<Property> propertyList = FXCollections.observableArrayList();

        PropertyListStub(Collection<Property> propertyList) {
            this.propertyList.setAll(propertyList);
        }

        @Override
        public ObservableList<Property> getPropertyList() {
            return propertyList;
        }
    }
}
