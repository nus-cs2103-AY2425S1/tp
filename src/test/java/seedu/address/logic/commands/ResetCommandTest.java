package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_ALL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getMarkedAddressBook;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

public class ResetCommandTest {
    private static final String TUTORIAL_TO_TEST = "1";
    private Model markedModel = new ModelManager(getMarkedAddressBook(TUTORIAL_TO_TEST), new UserPrefs());

    @Test
    public void constructor_invalidIndex_throwsParseException() {
        assertThrows(ParseException.class, () ->
                new ResetCommand(ParserUtil.parseIndex("-1"),
                        new Tutorial(TUTORIAL_TO_TEST)).execute(markedModel));
    }

    /**
     * Reset a person's attendance which is already not taken place.
     */
    @Test
    public void execute_tutorialAlreadyReset_failure() {
        ResetCommand resetCommand = new ResetCommand(INDEX_FIRST_PERSON, new Tutorial(TUTORIAL_TO_TEST));
        try {
            resetCommand.execute(markedModel);

            // Second execution should fail
            assertCommandFailure(resetCommand, markedModel, String.format(Messages.MESSAGE_RESET_UNNECESSARY,
                    Messages.format(markedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())),
                    new Tutorial("1").tutorial));
        } catch (CommandException e) {
            // Should not get here
            throw new RuntimeException();
        }
    }

    @Test
    public void execute_success() {
        Tutorial tutorialToBeAdded = new Tutorial(TUTORIAL_TO_TEST);

        Person personToEdit = markedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ResetCommand resetCommand = new ResetCommand(INDEX_FIRST_PERSON, tutorialToBeAdded);

        try {
            resetCommand.execute(markedModel);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }

        Person editedPerson = markedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Check person is edited
        Map<Tutorial, AttendanceStatus> newTutorials = new HashMap<>(personToEdit.getTutorials());
        newTutorials.put(tutorialToBeAdded, AttendanceStatus.NOT_TAKEN_PLACE);
        Person expectedEditedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getStudentId(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getTags(),
                newTutorials
        );
        assertEquals(expectedEditedPerson, editedPerson);

        // Check model is updated with new person attribute
        Model expectedModel = new ModelManager(new AddressBook(markedModel.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(Messages.MESSAGE_RESET_SUCCESS, Messages.format(editedPerson),
                tutorialToBeAdded.tutorial);
        Model typicalModel = new ModelManager(getMarkedAddressBook(TUTORIAL_TO_TEST), new UserPrefs());
        assertCommandSuccess(resetCommand, typicalModel, expectedMessage, expectedModel);
    }

    /**
     * Reset all persons in the list.
     */
    @Test
    public void execute_shouldResetAll_success() {
        Tutorial tutorialToBeAdded = new Tutorial(TUTORIAL_TO_TEST);

        ResetCommand resetCommand = new ResetCommand(INDEX_ALL, tutorialToBeAdded);

        try {
            resetCommand.execute(markedModel);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }

        // Ensure all are edited and marked as reset
        for (Person person : markedModel.getFilteredPersonList()) {
            Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(person.getTutorials());
            newTutorials.put(tutorialToBeAdded, AttendanceStatus.NOT_TAKEN_PLACE);
            Person expectedEditedPerson = new Person(
                    person.getName(),
                    person.getStudentId(),
                    person.getPhone(),
                    person.getEmail(),
                    person.getTags(),
                    newTutorials
            );

            assertEquals(expectedEditedPerson, person);
        }

        // Check model is updated with new person attribute
        Model expectedModel = new ModelManager(new AddressBook(markedModel.getAddressBook()), new UserPrefs());
        String expectedMessage = String.join("\n",
                markedModel.getFilteredPersonList().stream()
                        .map(personToEdit -> String.format(Messages.MESSAGE_RESET_SUCCESS,
                                Messages.format(personToEdit), tutorialToBeAdded.tutorial)).toArray(String[]::new));
        Model typicalModel = new ModelManager(getMarkedAddressBook(TUTORIAL_TO_TEST), new UserPrefs());
        assertCommandSuccess(resetCommand, typicalModel, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Tutorial tutorial = new Tutorial(TUTORIAL_TO_TEST);
        ResetCommand resetCommand = new ResetCommand(INDEX_FIRST_PERSON, tutorial);

        // same object -> returns true
        assertTrue(resetCommand.equals(resetCommand));

        // null -> returns false
        assertFalse(resetCommand.equals(null));

        // different types -> returns false
        assertFalse(resetCommand.equals(1));

        // same values -> return true
        Tutorial duplicateTutorial = new Tutorial(TUTORIAL_TO_TEST);
        ResetCommand duplicateResetCommand = new ResetCommand(INDEX_FIRST_PERSON, duplicateTutorial);
        assertTrue(resetCommand.equals(duplicateResetCommand));

        // different index -> return false
        ResetCommand otherIndexResetCommand = new ResetCommand(INDEX_SECOND_PERSON, tutorial);
        assertFalse(resetCommand.equals(otherIndexResetCommand));

        // different tutorial -> return false
        Tutorial otherTutorial = new Tutorial("2");
        ResetCommand otherTutResetCommand = new ResetCommand(INDEX_FIRST_PERSON, otherTutorial);
        assertFalse(resetCommand.equals(otherTutResetCommand));
    }
}
