package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StarredStatus;

/**
 * Contains integration tests (interaction with the Model) and unit tests for StarCommand.
 */
public class StarCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws Exception {
        Person personToStar = model.getFilteredPersonList().get(Index.fromZeroBased(0).getZeroBased());
        StarCommand starCommand = new StarCommand(Index.fromZeroBased(0));

        CommandResult commandResult = starCommand.execute(model);

        assertEquals(String.format(StarCommand.MESSAGE_STAR_PERSON_SUCCESS, personToStar.getName()),
                commandResult.getFeedbackToUser());

        Person starredPerson = model.getFilteredPersonList().get(Index.fromZeroBased(0).getZeroBased());
        assertEquals(new StarredStatus("true"), starredPerson.getStarredStatus());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        StarCommand starCommand = new StarCommand(invalidIndex);

        assertCommandFailure(starCommand, model, StarCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_validNameFilteredList_success() throws CommandException {
        Person personToStar = model.getFilteredPersonList().get(0);
        Name targetName = personToStar.getName();
        StarCommand starCommand = new StarCommand(targetName);

        CommandResult commandResult = starCommand.execute(model);

        assertEquals(String.format(StarCommand.MESSAGE_STAR_PERSON_SUCCESS, personToStar.getName()),
                commandResult.getFeedbackToUser());

        Person starredPerson = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().equals(targetName))
                .findFirst().orElse(null);

        assertEquals(new StarredStatus("true"), starredPerson.getStarredStatus());
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Name invalidName = new Name("Nonexistent Name");
        StarCommand starCommand = new StarCommand(invalidName);

        assertCommandFailure(starCommand, model, String.format(StarCommand.MESSAGE_PERSON_NOT_FOUND, invalidName));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        StarCommand starCommand = new StarCommand(outOfBoundIndex);
        assertCommandFailure(starCommand, model, StarCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_negativeIndex_throwsIndexOutOfBoundsException() {
        int negativeIndexValue = -1;
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Index negativeIndex = Index.fromOneBased(negativeIndexValue);
            new StarCommand(negativeIndex);
        });
    }

    @Test
    public void equals() {
        StarCommand starNameCommand = new StarCommand(new Name("Alice"));
        StarCommand starNameCommandCopy = new StarCommand(new Name("Alice"));
        StarCommand starIndexCommand = new StarCommand(Index.fromOneBased(1));

        // same object -> returns true
        assertTrue(starNameCommand.equals(starNameCommand));

        // same values -> returns true
        assertTrue(starNameCommand.equals(starNameCommandCopy));

        // different types -> returns false
        assertFalse(starNameCommand.equals(1));

        // null -> returns false
        assertFalse(starNameCommand.equals(null));

        // different names -> returns false
        assertFalse(starNameCommand.equals(starIndexCommand));

        // different index -> returns false
        assertFalse(starIndexCommand.equals(starNameCommand));
    }

    @Test
    public void toStringMethod() {
        // Testing with name
        Name targetName = new Name("John Doe");
        StarCommand starCommandByName = new StarCommand(targetName);
        String expectedNameString = String.format("StarCommand[targetName=%s]", targetName);
        assertEquals(expectedNameString, starCommandByName.toString());

        // Testing with index
        Index targetIndex = Index.fromOneBased(1);
        StarCommand starCommandByIndex = new StarCommand(targetIndex);
        String expectedIndexString = String.format("StarCommand[targetIndex=%d]", targetIndex.getOneBased());
        assertEquals(expectedIndexString, starCommandByIndex.toString());
    }

    @Test
    public void execute_personAlreadyStarred_throwsCommandException() {
        Person personToStar = model.getFilteredPersonList().get(0);
        Person alreadyStarredPerson = new Person(
                personToStar.getName(),
                personToStar.getPhone(),
                personToStar.getEmail(),
                personToStar.getAddress(),
                personToStar.getAge(),
                personToStar.getSex(),
                personToStar.getAppointment(),
                personToStar.getTags(),
                personToStar.getNote(),
                new StarredStatus("true"));
        model.setPerson(personToStar, alreadyStarredPerson);

        StarCommand starCommand = new StarCommand(alreadyStarredPerson.getName());

        assertCommandFailure(starCommand, model,
                String.format(StarCommand.MESSAGE_ALREADY_STARRED, alreadyStarredPerson.getName()));
    }
}
