package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESIREDROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESIREDROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DesiredRole;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DesiredRoleCommand.
 */
public class DesiredRoleCommandTest {
    private static final String DESIRED_ROLE_STUB = "Software Engineer";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_deleteDesiredRoleUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
            .withDesiredRole("")
            .withSkills(firstPerson.getSkills().value)
            .withExperience(firstPerson.getExperience().value)
            .withStatus(firstPerson.getStatus().value)
            .withNote(firstPerson.getNote().value)
            .build();

        DesiredRoleCommand desiredRoleCommand = new DesiredRoleCommand(INDEX_FIRST_PERSON, new DesiredRole(""));

        String expectedMessage = String.format(DesiredRoleCommand.MESSAGE_DELETE_DESIRED_ROLE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(desiredRoleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DesiredRoleCommand desiredRoleCommand = new DesiredRoleCommand(outOfBoundIndex,
            new DesiredRole(VALID_DESIREDROLE_BOB));

        // Adjust the expected message to include the period at the end
        assertCommandFailure(desiredRoleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        DesiredRoleCommand desiredRoleCommand = new DesiredRoleCommand(outOfBoundIndex,
            new DesiredRole(VALID_DESIREDROLE_BOB));

        // Adjust the expected message to include the period at the end
        assertCommandFailure(desiredRoleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DesiredRoleCommand standardCommand = new DesiredRoleCommand(INDEX_FIRST_PERSON,
            new DesiredRole(VALID_DESIREDROLE_AMY));

        // same values -> returns true
        DesiredRoleCommand commandWithSameValues = new DesiredRoleCommand(INDEX_FIRST_PERSON,
            new DesiredRole(VALID_DESIREDROLE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DesiredRoleCommand(INDEX_SECOND_PERSON,
            new DesiredRole(VALID_DESIREDROLE_AMY))));

        // different desired role -> returns false
        assertFalse(standardCommand.equals(new DesiredRoleCommand(INDEX_FIRST_PERSON,
            new DesiredRole(VALID_DESIREDROLE_BOB))));
    }
}
