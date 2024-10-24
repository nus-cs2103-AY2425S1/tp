package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.MATH;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.exceptions.DuplicateTutorialException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TutorialBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTutorialList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons, Collections.singletonList(MATH));

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateTutorials_throwsDuplicateTutorialException() {
        // Two tutorials with the same subject fields
        Tutorial editedMath = new TutorialBuilder(MATH).build();
        List<Tutorial> newTutorials = Arrays.asList(MATH, editedMath);
        AddressBookStub newData = new AddressBookStub(Collections.singletonList(ALICE), newTutorials);

        assertThrows(DuplicateTutorialException.class, () -> addressBook.resetData(newData));
    }


    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasTutorial_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasTutorial(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }
    @Test
    public void hasTutorial_tutorialNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasTutorial(MATH));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }
    @Test
    public void hasTutorial_tutorialInAddressBook_returnsTrue() {
        addressBook.addTutorial(MATH);
        assertTrue(addressBook.hasTutorial(MATH));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }
    @Test
    public void hasTutorial_tutorialWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addTutorial(MATH);
        Tutorial editedMath = new TutorialBuilder(MATH).build();
        assertTrue(addressBook.hasTutorial(editedMath));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }
    @Test
    public void getTutorialList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTutorialList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{persons=" + addressBook.getPersonList()
                + ", tutorials=" + addressBook.getTutorialList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void equals() {
        AddressBook addressBook2 = new AddressBook();

        // same values -> returns true
        assertTrue(addressBook.equals(addressBook2));

        // same object -> returns true
        assertTrue(addressBook.equals(addressBook));

        // null -> returns false
        assertFalse(addressBook.equals(null));

        // different type -> returns false
        assertFalse(addressBook.equals(5.0f));

        //different values -> returns false
        addressBook.addPerson(ALICE);
        addressBook.addTutorial(MATH);
        assertFalse(addressBook.equals(addressBook2));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list and tutorials list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tutorial> tutorials = FXCollections.observableArrayList();

        private final ObservableList<Participation> participations = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Tutorial> tutorials) {
            this.persons.setAll(persons);
            this.tutorials.setAll(tutorials);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
        @Override
        public ObservableList<Tutorial> getTutorialList() {
            return tutorials;
        }

        @Override
        public ObservableList<Participation> getParticipationList() {
            return participations;
        }
    }

}
