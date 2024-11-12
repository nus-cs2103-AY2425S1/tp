package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model;

    public static AddressBook getTypicalAddressBookForVieww() {
        Wedding aliceWedding = new WeddingBuilder()
                .withName("Alice Adam Wedding")
                .withVenue("Marina Bay Sands")
                .withDate("2024-12-12")
                .build();

        Wedding georgeWedding = new WeddingBuilder()
                .withName("George Jane Wedding")
                .withVenue("Sentosa")
                .withDate("2025-01-01")
                .build();

        Person alice = new PersonBuilder()
                .withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withEmail("alice@example.com")
                .withPhone("94351253")
                .withRole("florist")
                .withOwnWedding(aliceWedding)
                .build();

        Person benson = new PersonBuilder()
                .withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com")
                .withPhone("98756432")
                .withRole("caterer")
                .addWeddingJob(georgeWedding)
                .build();

        Person carl = new PersonBuilder()
                .withName("Carl Kurz")
                .withPhone("95352563")
                .withEmail("heinz@example.com")
                .withAddress("wall street")
                .withRole("florist")
                .addWeddingJob(georgeWedding)
                .build();

        Person carlJr = new PersonBuilder()
                .withName("Carl Kurz Jr")
                .withPhone("95352564")
                .withEmail("heinzzz@example.com")
                .withAddress("wall street")
                .withRole("florist")
                .addWeddingJob(georgeWedding)
                .build();

        Person george = new PersonBuilder()
                .withName("George Best")
                .withPhone("94824422")
                .withEmail("anna@example.com")
                .withAddress("4th street")
                .withOwnWedding(georgeWedding)
                .build();

        ArrayList<Person> persons = new ArrayList<>(Arrays.asList(alice, benson, carl, george, carlJr));
        ArrayList<Wedding> weddings = new ArrayList<>(Arrays.asList(aliceWedding, georgeWedding));

        AddressBook addressBook = new AddressBook();
        for (Person person : persons) {
            addressBook.addPerson(person);
        }

        for (Wedding wedding : weddings) {
            addressBook.addWedding(wedding);
        }

        return addressBook;
    }

    @BeforeEach
    public void setUpEach() {
        model = new ModelManager(getTypicalAddressBookForVieww(), new UserPrefs());
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, null, descriptor);

        // Expected behaviour: Only name, address, phone, email gets edited.
        // Role, ownWedding and weddingJobs stay the same.
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person personAtFirstIndex = model.getFilteredPersonList().get(0);
        Person expectedPerson = new PersonBuilder(editedPerson)
                .withRole(personAtFirstIndex.getRole().map(role -> role.roleName).orElse(null))
                .withOwnWedding(personAtFirstIndex.getOwnWedding())
                .withWeddingJobs(personAtFirstIndex.getWeddingJobs()).build();
        expectedModel.setPerson(model.getFilteredPersonList().get(0), expectedPerson);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(expectedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);

        editedPerson = new PersonBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        editCommand = new EditCommand(INDEX_SECOND_PERSON, null, descriptor);

        // Expected behaviour: Only name, address, phone, email gets edited.
        // Role, ownWedding and weddingJobs stay the same.
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person personAtSecondIndex = model.getFilteredPersonList().get(1);
        expectedPerson = new PersonBuilder(editedPerson)
                .withRole(personAtSecondIndex.getRole().map(role -> role.roleName).orElse(null))
                .withOwnWedding(personAtSecondIndex.getOwnWedding())
                .withWeddingJobs(personAtSecondIndex.getWeddingJobs()).build();
        expectedModel.setPerson(model.getFilteredPersonList().get(1), expectedPerson);
        expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(expectedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, null, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editedPhoneNumberNotUnique_throwsCommandException() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        String newPhone = ALICE.getPhone().toString();

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(newPhone)
                .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(newPhone).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, null, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_DUPLICATE_PHONE, Messages.format(editedPerson));

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_editedEmailNotUnique_throwsCommandException() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        String newEmail = ALICE.getEmail().toString();

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withEmail(newEmail)
                .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withEmail(newEmail).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, null, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_DUPLICATE_EMAIL, Messages.format(editedPerson));

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_throwsCommandsException() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, null, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_NO_CHANGE, Messages.format(editedPerson));

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, null,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, null, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in the address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, null,
                new EditPersonDescriptorBuilder(personInList).build());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandFailure(editCommand, expectedModel, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, null, descriptor);

        assertCommandFailure(editCommand, model, String.format(
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, outOfBoundIndex.getOneBased(),
                model.getFilteredPersonList().size()));
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex, null,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, String.format(
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, outOfBoundIndex.getOneBased(),
                model.getFilteredPersonList().size()));
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, null, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, null, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, null, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, null, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, null, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
