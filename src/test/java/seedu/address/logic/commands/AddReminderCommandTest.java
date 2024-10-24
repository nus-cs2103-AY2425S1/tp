package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Reminder;
import seedu.address.testutil.PersonBuilder;

public class AddReminderCommandTest {
    private static final String VALID_DATE = "01-01-2024";
    private static final String VALID_DESCRIPTION = "valid description";
    private static final Person VALID_PERSON = new PersonBuilder().build();
    private static final Reminder VALID_REMINDER = new Reminder(VALID_DATE, VALID_DESCRIPTION, VALID_PERSON.getName());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddReminderCommand(null,
                VALID_DATE, VALID_DESCRIPTION));
        assertThrows(NullPointerException.class, () -> new AddReminderCommand(INDEX_FIRST_PERSON,
                null, VALID_DESCRIPTION));
        assertThrows(NullPointerException.class, () -> new AddReminderCommand(INDEX_FIRST_PERSON,
                VALID_DATE, null));
    }

    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        AddReminderCommandTest.ModelStubAcceptingReminderAdded modelStub =
                new AddReminderCommandTest.ModelStubAcceptingReminderAdded();

        CommandResult commandResult = new AddReminderCommand(INDEX_FIRST_PERSON, VALID_DATE,
                VALID_DESCRIPTION).execute(modelStub);

        assertEquals(String.format(AddReminderCommand.MESSAGE_SUCCESS, VALID_PERSON.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(VALID_REMINDER), modelStub.remindersAdded);
    }

    @Test
    public void equals() {
        AddReminderCommand addReminderCommand = new AddReminderCommand(INDEX_FIRST_PERSON,
                VALID_DATE, VALID_DESCRIPTION);

        // same object -> returns true
        assertTrue(addReminderCommand.equals(addReminderCommand));

        // same values -> returns true
        AddReminderCommand addReminderCommandCopy = new AddReminderCommand(INDEX_FIRST_PERSON,
                VALID_DATE, VALID_DESCRIPTION);
        assertTrue(addReminderCommand.equals(addReminderCommandCopy));

        // different types -> returns false
        assertFalse(addReminderCommand.equals(1));

        // null -> returns false
        assertFalse(addReminderCommand.equals(null));

        // different person -> returns false
        assertFalse(addReminderCommand.equals(new AddReminderCommand(INDEX_FIRST_PERSON,
                "02-02-2024", VALID_DESCRIPTION)));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReminder(Reminder reminder, Person person) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithReminder extends AddReminderCommandTest.ModelStub {
        private final Person person;
        private final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        ModelStubWithReminder(Person person, Reminder reminder) {
            requireNonNull(person);
            requireNonNull(reminder);
            this.person = person;
            this.remindersAdded.add(reminder);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        public void addReminder(Reminder reminder, Person person) {
            requireNonNull(reminder);
            remindersAdded.add(reminder);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableList(Arrays.asList(person));
        }
    }

    /**
     * A Model stub that always accepts the Reminder being added.
     */
    private class ModelStubAcceptingReminderAdded extends AddReminderCommandTest.ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();
        private Person person = new PersonBuilder().build();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void addReminder(Reminder reminder, Person person) {
            requireNonNull(reminder);
            remindersAdded.add(reminder);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            personsAdded.add(person);
            return javafx.collections.FXCollections.observableList(personsAdded);
        }
    }
}
