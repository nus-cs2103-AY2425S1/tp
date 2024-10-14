package seedu.address.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInternshipApplications.AIRBNB;
import static seedu.address.testutil.TypicalInternshipApplications.BYTEDANCE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.internshipapplication.exceptions.DuplicatePersonException;
import seedu.address.model.internshipapplication.exceptions.PersonNotFoundException;
import seedu.address.testutil.InternshipApplicationBuilder;
import seedu.address.testutil.PersonBuilder;

public class UniqueListTest {

    private final UniqueList<InternshipApplication> uniqueList = new UniqueList<>();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueList.contains(AIRBNB));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueList.add(AIRBNB);
        assertTrue(uniqueList.contains(AIRBNB));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueList.add(AIRBNB);
        InternshipApplication editedAirbnb = new InternshipApplicationBuilder(AIRBNB).withRole(VALID_ROLE_BOFA)
                .build();
        assertTrue(uniqueList.contains(editedAirbnb));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueList.add(AIRBNB);
        assertThrows(DuplicatePersonException.class, () -> uniqueList.add(AIRBNB));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.setItem(null, AIRBNB));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.setItem(AIRBNB, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueList.setItem(AIRBNB, AIRBNB));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueList.add(AIRBNB);
        uniqueList.setItem(AIRBNB, AIRBNB);
        UniqueList<InternshipApplication> expectedUniqueList = new UniqueList<InternshipApplication>();
        expectedUniqueList.add(AIRBNB);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueList.add(AIRBNB);
        InternshipApplication editedAirbnb = new InternshipApplicationBuilder(AIRBNB).withRole(VALID_ROLE_BOFA)
                .build();
        uniqueList.setItem(AIRBNB, editedAirbnb);
        UniqueList<InternshipApplication> expectedUniqueList = new UniqueList<InternshipApplication>();
        expectedUniqueList.add(editedAirbnb);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueList.add(AIRBNB);
        uniqueList.setItem(AIRBNB, BYTEDANCE);
        UniqueList<InternshipApplication> expectedUniqueList = new UniqueList<InternshipApplication>();
        expectedUniqueList.add(BYTEDANCE);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueList.add(AIRBNB);
        uniqueList.add(BYTEDANCE);
        assertThrows(DuplicatePersonException.class, () -> uniqueList.setItem(AIRBNB, BYTEDANCE));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueList.remove(AIRBNB));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueList.add(AIRBNB);
        uniqueList.remove(AIRBNB);
        UniqueList<InternshipApplication> expectedUniqueList = new UniqueList<InternshipApplication>();
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setPersons_nullUniqueList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.setItems((List<InternshipApplication>) null));
    }

    @Test
    public void setPersons_uniqueList_replacesOwnListWithProvidedUniqueList() {
        uniqueList.add(AIRBNB);
        UniqueList<InternshipApplication> expectedUniqueList = new UniqueList<InternshipApplication>();
        expectedUniqueList.add(BYTEDANCE);
        uniqueList.setItems(expectedUniqueList);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.setItems((List<InternshipApplication>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueList.add(AIRBNB);
        List<InternshipApplication> personList = Collections.singletonList(BYTEDANCE);
        uniqueList.setItems(personList);
        UniqueList<InternshipApplication> expectedUniqueList = new UniqueList<InternshipApplication>();
        expectedUniqueList.add(BYTEDANCE);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<InternshipApplication> listWithDuplicatePersons = Arrays.asList(AIRBNB, AIRBNB);
        assertThrows(DuplicatePersonException.class, () -> uniqueList.setItems(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueList.asUnmodifiableObservableList().toString(), uniqueList.toString());
    }
}
