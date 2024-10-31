package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class VersionedAddressBookTest {
    private VersionedAddressBook versionedAddressBook;

    @BeforeEach
    public void setUp() {
        versionedAddressBook = new VersionedAddressBook(new AddressBook());
    }

    @Test
    public void undo_initialState_throwsCommandException() {
        assertThrows(CommandException.class, () -> versionedAddressBook.undo());
    }

    @Test
    public void redo_initialState_throwsCommandException() {
        assertThrows(CommandException.class, () -> versionedAddressBook.redo());
    }

    @Test
    public void getCurrentPredicate_initialState_returnsPredicateShowAllPersons() {
        Predicate<? super Person> currentPredicate = versionedAddressBook.getCurrentPredicate();

        assertEquals(currentPredicate, PREDICATE_SHOW_ALL_PERSONS);
    }

    @Test
    public void undo_withPreviousStates_success() throws CommandException {
        // Add a person and commit
        AddressBook updatedAddressBook = new AddressBook();
        updatedAddressBook.addPerson(TypicalPersons.TEACHER_ALICE);
        versionedAddressBook.commit(updatedAddressBook, PREDICATE_SHOW_ALL_PERSONS);

        // Undo once and assert that the state is reverted to previous original state
        ReadOnlyAddressBook expectedAddressBook = new AddressBook();
        ReadOnlyAddressBook previousState = versionedAddressBook.undo();
        assertEquals(previousState, expectedAddressBook);
    }

    @Test
    public void redo_withNextStates_success() throws CommandException {
        // Add a person, commit, and then undo
        AddressBook updatedAddressBook = new AddressBook();
        updatedAddressBook.addPerson(TypicalPersons.TEACHER_ALICE);
        versionedAddressBook.commit(updatedAddressBook, PREDICATE_SHOW_ALL_PERSONS);
        versionedAddressBook.undo();

        // Redo and assert that the state is restored to the updated state
        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addPerson(TypicalPersons.TEACHER_ALICE);
        ReadOnlyAddressBook redoneState = versionedAddressBook.redo();
        assertEquals(redoneState, expectedAddressBook);
    }

    @Test
    public void getCurrentPredicate_afterCommits_returnsNewPredicate() {
        Predicate<Person> oldPredicate = person -> person.getAddress().value.contains("123 NUS Rd");
        Predicate<Person> newPredicate = person -> person.getName().fullName.contains("John Doe");

        versionedAddressBook.commit(new AddressBook(), oldPredicate);
        versionedAddressBook.commit(new AddressBook(), newPredicate);

        assertEquals(newPredicate, versionedAddressBook.getCurrentPredicate());
    }

    @Test
    public void undo_revertsPredicateToPrevious() throws CommandException {
        Predicate<Person> oldPredicate = person -> person.getAddress().value.contains("123 NUS Rd");
        Predicate<Person> newPredicate = person -> person.getName().fullName.contains("John Doe");

        versionedAddressBook.commit(new AddressBook(), oldPredicate);
        versionedAddressBook.commit(new AddressBook(), newPredicate);

        versionedAddressBook.undo();

        assertEquals(oldPredicate, versionedAddressBook.getCurrentPredicate());
    }
}
