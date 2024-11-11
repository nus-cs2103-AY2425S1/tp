package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_TYPE_B;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.reminder.ReminderCommandTestUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;
import seedu.address.testutil.PersonBuilder;

public class ClientHubTest {

    private final ClientHub clientHub = new ClientHub();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clientHub.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clientHub.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyClientHub_replacesData() {
        ClientHub newData = getTypicalClientHub();
        clientHub.resetData(newData);
        assertEquals(newData, clientHub);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE)
                .withAddress(VALID_ADDRESS_BOB).withClientTypes(VALID_CLIENT_TYPE_B)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Reminder> newReminders = Collections.emptyList();
        ClientHubStub newData = new ClientHubStub(newPersons, newReminders);

        assertThrows(DuplicatePersonException.class, () -> clientHub.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clientHub.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInClientHub_returnsFalse() {
        assertFalse(clientHub.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInClientHub_returnsTrue() {
        clientHub.addPerson(ALICE);
        assertTrue(clientHub.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInClientHub_returnsTrue() {
        clientHub.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withClientTypes(VALID_CLIENT_TYPE_B)
                .build();
        assertTrue(clientHub.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> clientHub.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ClientHub.class.getCanonicalName() + "{persons="
                + clientHub.getPersonList() + ", reminders=" + clientHub.getReminderList() + "}";
        assertEquals(expected, clientHub.toString());
    }

    /**
     * A stub ReadOnlyClientHub whose persons list can violate interface constraints.
     */
    private static class ClientHubStub implements ReadOnlyClientHub {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Reminder> reminders = FXCollections.observableArrayList();

        ClientHubStub(Collection<Person> persons, Collection<Reminder> reminders) {
            this.persons.setAll(persons);
            this.reminders.setAll(reminders);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Reminder> getReminderList() {
            return reminders;
        }
    }

    @Test
    public void equals() {
        ClientHub clientHub = new ClientHub();
        PersonBuilder personBuilder = new PersonBuilder();
        personBuilder.withName("Simon");
        ClientHub clientHub1 = getTypicalClientHub();
        clientHub1.addPerson(personBuilder.build());
        assertTrue(clientHub.equals(clientHub));
        assertFalse(clientHub.equals(null));
        assertFalse(clientHub.equals(clientHub1));
    }

    @Test
    public void equalHashCode() {
        //Both are default clienthubs
        ClientHub clientHub = new ClientHub();
        PersonBuilder personBuilder = new PersonBuilder();
        personBuilder.withName("Simon");
        ClientHub clientHub1 = getTypicalClientHub();
        clientHub1.addPerson(personBuilder.build());
        assertNotEquals(clientHub.hashCode(), clientHub1.hashCode());
        assertEquals(clientHub.hashCode(), clientHub.hashCode());
    }

    @Test
    public void getReminderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> clientHub.getReminderList().remove(0));
    }

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clientHub.hasReminder(null));
    }

    @Test
    public void hasReminder_reminderNotInClientHub_returnsFalse() {
        assertFalse(clientHub.hasReminder(new Reminder("Test",
                LocalDateTime.of(2010, 10, 10, 16, 10),
                new ReminderDescription("lunch"))));
    }

    @Test
    public void hasReminder_reminderInClientHub_returnsTrue() {
        Reminder reminder = ReminderCommandTestUtil.DEFAULT_REMINDER;
        clientHub.addReminder(reminder);
        assertTrue(clientHub.hasReminder(reminder));
    }

}
