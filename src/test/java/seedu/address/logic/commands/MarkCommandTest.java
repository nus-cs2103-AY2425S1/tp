package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

public class MarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () ->
                new MarkCommand(ParserUtil.parseIndex("-1"),
                        new Tutorial("2")).execute(model));
    }

    /**
     * Mark a person using index outside the displayed list.
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkCommand markCommand = new MarkCommand(outOfBoundIndex,
                new Tutorial("1"));

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_success() {
        Tutorial tutorialToBeAdded = new Tutorial("1");

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, tutorialToBeAdded);

        try {
            markCommand.execute(model);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Check person is edited
        Set<Tutorial> newTutorials = new HashSet<>(personToEdit.getTutorials());
        newTutorials.add(tutorialToBeAdded);
        Person expectedEditedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                newTutorials
        );
        assertEquals(expectedEditedPerson, editedPerson);

        // Check model is updated with new person attribute
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(MarkCommand.MESSAGE_ADD_MARK_SUCCESS, Messages.format(editedPerson));
        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals_duplicateTutorial_success() {
        Tutorial firstTutorial = new Tutorial("1");
        Tutorial duplicateTutorial = new Tutorial("1");

        assertTrue(new MarkCommand(INDEX_FIRST_PERSON, firstTutorial)
                .equals(new MarkCommand(INDEX_FIRST_PERSON, duplicateTutorial)));
    }
}
