package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TUTOR;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalEduContacts;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class EduContactsTest {

    private final EduContacts eduContacts = new EduContacts();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eduContacts.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduContacts.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEduContacts_replacesData() {
        EduContacts newData = getTypicalEduContacts();
        eduContacts.resetData(newData);
        assertEquals(newData, eduContacts);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTag(VALID_TAG_TUTOR)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        EduContactsStub newData = new EduContactsStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> eduContacts.resetData(newData));
    }

    @Test
    public void addPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduContacts.addPerson(null));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduContacts.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInEduContacts_returnsFalse() {
        assertFalse(eduContacts.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInEduContacts_returnsTrue() {
        eduContacts.addPerson(ALICE);
        assertTrue(eduContacts.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInEduContacts_returnsTrue() {
        eduContacts.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTag(VALID_TAG_TUTOR)
                .build();
        assertTrue(eduContacts.hasPerson(editedAlice));
    }

    @Test
    public void addModule_personNotInEduContacts_throwsIllegalArgumentException() {
        Module module = new Module("CS2101");
        assertThrows(IllegalArgumentException.class, () -> eduContacts.addModule(ALICE, module));
    }

    @Test
    public void removeModule_personNotInEduContacts_throwsIllegalArgumentException() {
        Module module = new Module("CS2101");
        assertThrows(IllegalArgumentException.class, () -> eduContacts.removeModule(ALICE, module));
    }

    @Test
    public void removeModule_moduleNotInPerson_throwsIllegalArgumentException() {
        eduContacts.addPerson(ALICE);
        Module module = new Module("CS2101");
        assertThrows(IllegalArgumentException.class, () -> eduContacts.removeModule(ALICE, module));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eduContacts.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = EduContacts.class.getCanonicalName() + "{persons=" + eduContacts.getPersonList() + "}";
        assertEquals(expected, eduContacts.toString());
    }

    /**
     * A stub ReadOnlyEduContacts whose persons list can violate interface constraints.
     */
    private static class EduContactsStub implements ReadOnlyEduContacts {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        EduContactsStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
