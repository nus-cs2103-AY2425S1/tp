package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

public class UnmarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () ->
                new UnmarkCommand(ParserUtil.parseIndex("-1"),
                        new Tutorial("2")).execute(model));
    }

    /**
     * Unmark a person using index outside the displayed list.
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnmarkCommand unmarkCommand = new UnmarkCommand(outOfBoundIndex,
                new Tutorial("1"));

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Unmark a person using tutorial that is already unmarked.
     */
    @Test
    public void execute_invalidTutorialNumber_failure() {
        UnmarkCommand unmarkCommand = new UnmarkCommand(INDEX_FIRST_PERSON,
                new Tutorial("1"));
        assertCommandFailure(unmarkCommand, model,
                String.format(UnmarkCommand.MESSAGE_UNMARK_UNNECESSARY, 1,
                        Messages.format(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))));
    }

    @Test
    public void execute_success() {
        // Setup
        Tutorial tutorialNumber = new Tutorial("1");
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        try {
            new MarkCommand(INDEX_FIRST_PERSON, tutorialNumber).execute(model);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }

        // Check model is reverted back to original
        UnmarkCommand unmarkCommand = new UnmarkCommand(INDEX_FIRST_PERSON, tutorialNumber);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_SUCCESS,
                tutorialNumber.tutorial, Messages.format(personToEdit));
        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Tutorial tutorial = new Tutorial("1");
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, tutorial);

        // is itself
        assertTrue(markCommand.equals(markCommand));

        // is null
        assertFalse(markCommand.equals(null));

        // duplicate MarkCommand
        Tutorial duplicateTutorial = new Tutorial("1");
        MarkCommand duplicateMarkCommand = new MarkCommand(INDEX_FIRST_PERSON, duplicateTutorial);

        assertTrue(markCommand
                .equals(duplicateMarkCommand));
    }
}
