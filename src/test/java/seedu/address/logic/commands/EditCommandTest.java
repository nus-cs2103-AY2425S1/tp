package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalNrics.NRIC_FIRST_PERSON;
import static seedu.address.testutil.TypicalNrics.NRIC_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(NRIC_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // Find the person in the filtered list with the specified NRIC
        Person personToEdit = model.getFilteredPersonList().stream()
                .filter(p -> p.getNric().equals(NRIC_FIRST_PERSON))
                .findFirst()
                .orElse(null);

        // Ensure the person exists in the filtered list
        assertNotNull(personToEdit);

        // Update the person in the expected model
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // Assuming NRIC is available as a unique identifier for the person we want to edit
        Person lastPerson = model.getFilteredPersonList().get(model.getFilteredPersonList().size() - 1);
        Nric targetNric = lastPerson.getNric();

        // Build the edited person with updated fields
        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        // Create the EditPersonDescriptor with the fields to edit
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();

        // Create the edit command using the NRIC instead of an index
        EditCommand editCommand = new EditCommand(targetNric, descriptor);

        // Define the expected success message
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        // Set up the expected model with the edited person
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        // Execute the command and check if it succeeds
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        // Retrieve the first person in the list and their NRIC
        Person firstPerson = model.getFilteredPersonList().get(0);
        Nric targetNric = firstPerson.getNric();

        // Create an edit command using the NRIC and an empty descriptor (no fields specified for editing)
        EditCommand editCommand = new EditCommand(targetNric, new EditPersonDescriptor());

        // Since no fields are edited, the edited person should be the same as the original
        Person editedPerson = firstPerson;

        // Define the expected success message
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        // Set up the expected model, which remains unchanged
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // Execute the command and check if it succeeds
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        // Show the person at the first index in the filtered list
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // Retrieve the person in the filtered list and get their NRIC
        Person personInFilteredList = model.getFilteredPersonList().get(0);
        Nric targetNric = personInFilteredList.getNric();

        // Create an edited version of the person with the updated name
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();

        // Create the edit command using the NRIC and the name descriptor
        EditCommand editCommand = new EditCommand(targetNric,
            new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        // Define the expected success message
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        // Set up the expected model with the edited person
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personInFilteredList, editedPerson);

        // Execute the command and check if it succeeds
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        // Retrieve the first and second persons in the list by their NRICs
        Person firstPerson = model.getFilteredPersonList().get(0);
        Nric secondPersonNric = model.getFilteredPersonList().get(1).getNric();

        // Create an EditPersonDescriptor using the details of the first person (to simulate duplication)
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();

        // Create the edit command with the NRIC of the second person, attempting to duplicate the first person
        EditCommand editCommand = new EditCommand(secondPersonNric, descriptor);

        // Assert that the command fails with a duplicate person message
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        // Show the person at the first index in the filtered list
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // Retrieve the NRICs of the first person in the filtered list and the second person in the address book
        Person personInFilteredList = model.getFilteredPersonList().get(0);
        Nric targetNric = personInFilteredList.getNric();

        Person personInList = model.getAddressBook().getPersonList().get(1);

        // Create an EditCommand to edit the first person to have the same details as the second person (duplicate)
        EditCommand editCommand = new EditCommand(targetNric,
                new EditPersonDescriptorBuilder(personInList).build());

        // Assert that the command fails with a duplicate person message
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonNricUnfilteredList_failure() {
        // Create a fake NRIC that doesn't exist in the address book
        Nric invalidNric = new Nric("A1234567A"); // Ensure this NRIC is not in the test data
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(invalidNric, descriptor);

        // Assert that the command fails with an invalid person message
        assertCommandFailure(editCommand, model, Messages.MESSAGE_NO_PERSON_FOUND);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonNricFilteredList_failure() {
        // Show only the first person in the filtered list
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // Get the NRIC of a person who is not in the filtered list but is in the full address book
        Person outOfFilteredListPerson = model.getAddressBook().getPersonList().get(1);
        Nric outOfFilteredListNric = outOfFilteredListPerson.getNric();

        // Ensure that outOfFilteredListPerson is actually outside of the filtered list's bounds
        assertTrue(model.getFilteredPersonList().stream().noneMatch(p -> p.getNric().equals(outOfFilteredListNric)));

        // Create an EditCommand using the NRIC of the person outside the filtered list
        EditCommand editCommand = new EditCommand(outOfFilteredListNric,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        // Assert that the command fails with an invalid person message
        assertCommandFailure(editCommand, model, Messages.MESSAGE_NO_PERSON_FOUND);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(NRIC_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(NRIC_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(NRIC_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(NRIC_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Nric nric = NRIC_FIRST_PERSON;
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(NRIC_FIRST_PERSON, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{nric=" + nric + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
