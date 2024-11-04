package tutorease.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalStudents.ALICE;
import static tutorease.address.testutil.TypicalStudents.AMY;
import static tutorease.address.testutil.TypicalStudents.BOB;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tutorease.address.model.person.Person;
import tutorease.address.model.person.exceptions.DuplicatePersonException;
import tutorease.address.testutil.StudentBuilder;

public class TutorEaseTest {

    private final TutorEase addressBook = new TutorEase();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTutorEase_replacesData() {
        TutorEase newData = getTypicalTutorEase();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        TutorEaseStub newData = new TutorEaseStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInTutorEase_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInTutorEase_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInTutorEase_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasSamePhone_personWithSamePhoneInAddressBook_returnsTrue() {
        addressBook.addPerson(AMY);
        Person personWithSamePhone = new StudentBuilder().withPhone(VALID_PHONE_AMY).build();
        assertTrue(addressBook.hasSamePhone(personWithSamePhone));
    }

    @Test
    public void hasSamePhone_personWithDifferentPhoneInAddressBook_returnsFalse() {
        addressBook.addPerson(BOB);
        Person personWithDifferentPhone = new StudentBuilder().withPhone(VALID_PHONE_AMY).build();
        assertFalse(addressBook.hasSamePhone(personWithDifferentPhone));
    }

    @Test
    public void hasSameEmail_personWithSameEmailInAddressBook_returnsTrue() {
        addressBook.addPerson(AMY);
        Person personWithSameEmail = new StudentBuilder().withEmail(VALID_EMAIL_AMY).build();
        assertTrue(addressBook.hasSameEmail(personWithSameEmail));
    }

    @Test
    public void hasSameEmail_personWithDifferentEmailInAddressBook_returnsFalse() {
        addressBook.addPerson(ALICE);
        Person personWithDifferentEmail = new StudentBuilder().withEmail(VALID_EMAIL_AMY).build();
        assertFalse(addressBook.hasSameEmail(personWithDifferentEmail));
    }


    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = TutorEase.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyTutorEase whose persons list can violate interface constraints.
     */
    private static class TutorEaseStub implements ReadOnlyTutorEase {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        TutorEaseStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public Person getPerson(String name) {
            return null;
        }
    }

}
