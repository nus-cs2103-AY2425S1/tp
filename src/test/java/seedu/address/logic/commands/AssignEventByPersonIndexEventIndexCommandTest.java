package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AssignEventByPersonIndexEventIndexCommand}.
 */
public class AssignEventByPersonIndexEventIndexCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addPerson(ALICE);
        model.addEvent(MEETING);
    }

    @Test
    public void execute_validPersonAndEvent_success() {
        AssignEventByPersonIndexEventIndexCommand command = new AssignEventByPersonIndexEventIndexCommand(
                Index.fromOneBased(1), Index.fromOneBased(1));

        String expectedMessage = String.format(AssignEventByPersonIndexEventIndexCommand.MESSAGE_SUCCESS,
                MEETING.getEventName(), ALICE.getName());

        Model expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        expectedModel.addEvent(MEETING);
        expectedModel.assignEventToPerson(ALICE, MEETING);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Index invalidPersonIndex = Index.fromOneBased(100); // Assuming index 100 does not exist
        AssignEventByPersonIndexEventIndexCommand command = new AssignEventByPersonIndexEventIndexCommand(
                invalidPersonIndex, Index.fromOneBased(1));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        Index invalidEventIndex = Index.fromOneBased(100); // Assuming index 100 does not exist
        AssignEventByPersonIndexEventIndexCommand command = new AssignEventByPersonIndexEventIndexCommand(
                Index.fromOneBased(1), invalidEventIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personAlreadyAssignedToEvent_throwsCommandException() {
        model.assignEventToPerson(ALICE, MEETING);
        AssignEventByPersonIndexEventIndexCommand command = new AssignEventByPersonIndexEventIndexCommand(
                Index.fromOneBased(1), Index.fromOneBased(1));

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_PERSON_ALREADY_ASSIGNED_TO_EVENT,
                ALICE.getName(), MEETING.getEventName()));
    }

    @Test
    public void equals() {
        model.addPerson(BOB);
        AssignEventByPersonIndexEventIndexCommand command1 = new AssignEventByPersonIndexEventIndexCommand(
                Index.fromOneBased(1), Index.fromOneBased(1));
        AssignEventByPersonIndexEventIndexCommand command2 = new AssignEventByPersonIndexEventIndexCommand(
                Index.fromOneBased(1), Index.fromOneBased(1));
        AssignEventByPersonIndexEventIndexCommand command3 = new AssignEventByPersonIndexEventIndexCommand(
                Index.fromOneBased(2), Index.fromOneBased(1));
        AssignEventByPersonIndexEventIndexCommand command4 = new AssignEventByPersonIndexEventIndexCommand(
                Index.fromOneBased(1), Index.fromOneBased(2));

        // same object -> returns true
        assertEquals(command1, command1);

        // same values -> returns true
        assertEquals(command1, command2);

        // different person -> returns false
        assertEquals(false, command1.equals(command3));

        // different event -> returns false
        assertEquals(false, command1.equals(command4));

        // different type -> returns false
        assertEquals(false, command1.equals(1));

        // null -> returns false
        assertEquals(false, command1.equals(null));
    }

    @Test
    public void toStringMethod() {
        Index personIndex = Index.fromOneBased(1);
        Index eventIndex = Index.fromOneBased(1);
        AssignEventByPersonIndexEventIndexCommand assignEventCommand =
                new AssignEventByPersonIndexEventIndexCommand(personIndex, eventIndex);
        String expected = AssignEventByPersonIndexEventIndexCommand.class.getCanonicalName() + "{targetPersonIndex="
                + personIndex + ", targetEventIndex=" + eventIndex + "}";
        assertEquals(expected, assignEventCommand.toString());
    }
}
