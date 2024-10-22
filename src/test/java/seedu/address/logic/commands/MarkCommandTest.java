package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_ALL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import seedu.address.model.person.AttendanceStatus;
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

    /**
     * Mark a person that has already been marked.
     */
    @Test
    public void execute_tutorialAlreadyMarked_failure() {
        // Execute mark twice
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, new Tutorial("1"));
        try {
            markCommand.execute(model);
            markCommand.execute(model);
        } catch (CommandException e) {
            assertCommandFailure(markCommand, model,
                    String.format(Messages.MESSAGE_MARK_UNNECESSARY,
                            Messages.format(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())),
                            new Tutorial("1").tutorial));
        }
    }

    /**
     * Mark all persons in the list.
     */
    @Test
    public void execute_shouldMarkAll_success() {
        Tutorial tutorialToBeAdded = new Tutorial("1");

        MarkCommand markCommand = new MarkCommand(INDEX_ALL, tutorialToBeAdded);

        try {
            markCommand.execute(model);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }

        List<Person> editedPersonList = new ArrayList<>();
        // Check all persons are edited
        for (Person person : model.getFilteredPersonList()) {
            Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(person.getTutorials());
            newTutorials.put(tutorialToBeAdded, AttendanceStatus.PRESENT);
            Person expectedEditedPerson = new Person(
                    person.getName(),
                    person.getStudentId(),
                    person.getPhone(),
                    person.getEmail(),
                    person.getTags(),
                    newTutorials
            );
            editedPersonList.add(expectedEditedPerson);
            assertEquals(expectedEditedPerson, person);
        }

        // Check model is updated with new person attribute
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.join("\n",
                model.getFilteredPersonList().stream()
                        .map(personToEdit -> String.format(Messages.MESSAGE_MARK_SUCCESS,
                                Messages.format(personToEdit), tutorialToBeAdded.tutorial)).toArray(String[]::new));
        Model typicalModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(markCommand, typicalModel, expectedMessage, expectedModel);
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
        Map<Tutorial, AttendanceStatus> newTutorials = new HashMap<>(personToEdit.getTutorials());
        newTutorials.put(tutorialToBeAdded, AttendanceStatus.PRESENT);
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
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(Messages.MESSAGE_MARK_SUCCESS, Messages.format(editedPerson),
                tutorialToBeAdded.tutorial);
        Model typicalModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(markCommand, typicalModel, expectedMessage, expectedModel);
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
