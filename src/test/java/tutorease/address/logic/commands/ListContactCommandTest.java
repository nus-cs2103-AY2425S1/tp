package tutorease.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorease.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorease.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tutorease.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tutorease.address.testutil.TypicalLessons.getTypicalLessons;
import static tutorease.address.testutil.TypicalStudents.getTypicalTutorEase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.TutorEase;
import tutorease.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListContactCommand.
 */
public class ListContactCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorEase(), new UserPrefs(), getTypicalLessons());
        expectedModel = new ModelManager(model.getTutorEase(), new UserPrefs(), getTypicalLessons());
    }

    @Test
    public void execute_noContactsFound_showsNoContactsMessage() {
        // Simulate when there are no contacts at all
        model = new ModelManager(new TutorEase(), new UserPrefs(), getTypicalLessons());

        CommandResult result = new ListContactCommand().execute(model);

        // Check that the command returns the expected message
        assertEquals(ListContactCommand.MESSAGE_NO_CONTACTS_FOUND, result.getFeedbackToUser());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        // Simulate filtering the list by showing a person at the first index
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
