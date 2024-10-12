package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_ADMIRALTY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperty.ADMIRALTY;
import static seedu.address.testutil.TypicalProperty.BEDOK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
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
    public void contains_propertyWithSameIdentityFieldsInList_returnsTrue() {
        uniquePropertyList.add(ADMIRALTY);
        Property editedAlice = new PropertyBuilder(ADMIRALTY).withPostalCode(VALID_POSTALCODE_BEDOK).build();
        assertFalse(uniquePropertyList.contains(editedAlice));
    }

    @Test
    public void add_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.add(null));
    }

    @Test
    public void setProperty_nullTargetProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperty(null, ADMIRALTY));
    }

    @Test
    public void setProperty_nullEditedProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperty(ADMIRALTY, null));
    }

    @Test
    public void setProperty_targetPropertyNotInList_throwsPersonNotFoundException() {
        assertThrows(PropertyNotFoundException.class, () -> uniquePropertyList.setProperty(ADMIRALTY, ADMIRALTY));
    }

    @Test
    public void setProperty_editedPropertyIsSameProperty_success() {
        uniquePropertyList.add(ADMIRALTY);
        uniquePropertyList.setProperty(ADMIRALTY, ADMIRALTY);
        UniquePropertiesList expectedUniquePropertiesList = new UniquePropertiesList();
        expectedUniquePropertiesList.add(ADMIRALTY);
        System.out.println(uniquePropertyList.equals(expectedUniquePropertiesList));
        assertEquals(expectedUniquePropertiesList, uniquePropertyList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePropertyList.add(ADMIRALTY);
        Property editedAdmiralty = new PropertyBuilder(ADMIRALTY).withPostalCode(VALID_POSTALCODE_ADMIRALTY)
                .withUnit(VALID_UNIT_ADMIRALTY)
                .build();
        uniquePropertyList.setProperty(ADMIRALTY, editedAdmiralty);
        UniquePropertiesList expectedUniquePropertiesList = new UniquePropertiesList();
        expectedUniquePropertiesList.add(editedAdmiralty);
        assertEquals(expectedUniquePropertiesList, uniquePropertyList);
    }

    @Test
    public void setProperty_editedPropertyHasDifferentIdentity_success() {
        uniquePropertyList.add(ADMIRALTY);
        uniquePropertyList.setProperty(ADMIRALTY, BEDOK);
        UniquePropertiesList expectedUniquePropertiesList = new UniquePropertiesList();
        expectedUniquePropertiesList.add(BEDOK);
        assertEquals(expectedUniquePropertiesList, uniquePropertyList);
    }

    @Test
    public void setProperty_editedPropertyHasNonUniqueIdentity_throwsDuplicatePropertyException() {
        uniquePropertyList.add(ADMIRALTY);
        uniquePropertyList.add(BEDOK);
        assertThrows(DuplicatePropertyException.class, () -> uniquePropertyList.setProperty(ADMIRALTY, BEDOK));
    }

    //      COMMENTED CODE IS FOR PERSON IMPLEMENTING DELETE FUNCTIONAILITY

    //    @Test
    //    public void remove_nullProperty_throwsNullPointerException() {
    //        assertThrows(NullPointerException.class, () -> uniquePropertyList.remove(null));
    //    }

    //    @Test
    //    public void remove_propertyDoesNotExist_throwsPropertyNotFoundException() {
    //        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    //    }

    //    @Test
    //    public void remove_existingProperty_removesProperty() {
    //        uniquePersonList.add(ALICE);
    //        uniquePersonList.remove(ALICE);
    //        UniquePersonList expectedUniquePersonList = new UniquePersonList();
    //        assertEquals(expectedUniquePersonList, uniquePersonList);
    //    }

    @Test
    public void setProperty_nullUniquePropertyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperties((UniquePropertiesList) null));
    }

    @Test
    public void setProperty_uniquePropertyList_replacesOwnListWithProvidedUniquePropertyList() {
        uniquePropertyList.add(ADMIRALTY);
        UniquePropertiesList expectedUniquePropertiesList = new UniquePropertiesList();
        expectedUniquePropertiesList.add(BEDOK);
        uniquePropertyList.setProperties(expectedUniquePropertiesList);
        assertEquals(expectedUniquePropertiesList, uniquePropertyList);
    }

    @Test
    public void setProperty_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.setProperties((List<Property>) null));
    }

    @Test
    public void setProperty_list_replacesOwnListWithProvidedList() {
        uniquePropertyList.add(ADMIRALTY);
        List<Property> propertyList = Collections.singletonList(BEDOK);
        uniquePropertyList.setProperties(propertyList);
        UniquePropertiesList expectedUniquePropertiesList = new UniquePropertiesList();
        expectedUniquePropertiesList.add(BEDOK);
        assertEquals(expectedUniquePropertiesList, uniquePropertyList);
    }

    @Test
    public void setProperty_listWithDuplicateProperty_throwsDuplicatePropertyException() {
        List<Property> listWithDuplicateProperties = Arrays.asList(ADMIRALTY, ADMIRALTY);
        assertThrows(DuplicatePersonException.class, () -> uniquePropertyList
                .setProperties(listWithDuplicateProperties));
    }


    @Test
    public void add_duplicateProperty_throwsDuplicatePropertyException() {
        uniquePropertyList.add(ADMIRALTY);
        assertThrows(DuplicatePersonException.class, () -> uniquePropertyList.add(ADMIRALTY));
    }


    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniquePropertyList.asUnmodifiableObservableList().remove(0));
    }

}
