package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIER_REJECT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAgentAssist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class AgentAssistTest {

    private final AgentAssist agentAssist = new AgentAssist();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), agentAssist.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> agentAssist.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAgentAssist_replacesData() {
        AgentAssist newData = getTypicalAgentAssist();
        agentAssist.resetData(newData);
        assertEquals(newData, agentAssist);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTier(VALID_TIER_REJECT)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AgentAssistStub newData = new AgentAssistStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> agentAssist.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> agentAssist.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAgentAssist_returnsFalse() {
        assertFalse(agentAssist.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAgentAssist_returnsTrue() {
        agentAssist.addPerson(ALICE);
        assertTrue(agentAssist.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAgentAssist_returnsTrue() {
        agentAssist.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTier(VALID_TIER_REJECT)
                .build();
        assertTrue(agentAssist.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> agentAssist.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AgentAssist.class.getCanonicalName() + "{persons=" + agentAssist.getPersonList() + "}";
        assertEquals(expected, agentAssist.toString());
    }

    /**
     * A stub ReadOnlyAgentAssist whose persons list can violate interface constraints.
     */
    private static class AgentAssistStub implements ReadOnlyAgentAssist {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AgentAssistStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
