package seedu.researchroster.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.researchroster.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.researchroster.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.researchroster.testutil.Assert.assertThrows;
import static seedu.researchroster.testutil.TypicalPersons.ALICE;
import static seedu.researchroster.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.researchroster.model.person.Person;
import seedu.researchroster.model.person.exceptions.DuplicatePersonException;
import seedu.researchroster.testutil.PersonBuilder;

public class ResearchRosterTest {

    private final ResearchRoster researchRoster = new ResearchRoster();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), researchRoster.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> researchRoster.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        ResearchRoster newData = getTypicalAddressBook();
        researchRoster.resetData(newData);
        assertEquals(newData, researchRoster);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ResearchRosterStub newData = new ResearchRosterStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> researchRoster.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> researchRoster.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(researchRoster.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        researchRoster.addPerson(ALICE);
        assertTrue(researchRoster.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        researchRoster.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(researchRoster.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> researchRoster.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ResearchRoster.class.getCanonicalName() + "{persons=" + researchRoster.getPersonList() + "}";
        assertEquals(expected, researchRoster.toString());
    }

    /**
     * A stub ReadOnlyResearchRoster whose persons list can violate interface constraints.
     */
    private static class ResearchRosterStub implements ReadOnlyResearchRoster {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        ResearchRosterStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
