package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.WORKSHOP;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.EventName;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AssignEventByPersonNameEventNameCommand}.
 */
public class AssignEventByPersonIndexEventNameCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addPerson(ALICE);
        model.addEvent(MEETING);
    }

    @Test
    public void execute_validPersonAndEvent_success() {
        AssignEventByPersonIndexEventNameCommand command = new AssignEventByPersonIndexEventNameCommand(
                Index.fromOneBased(1), MEETING.getEventName());

        String expectedMessage = String.format(AssignEventByPersonIndexEventNameCommand.MESSAGE_SUCCESS,
                MEETING.getEventName(), ALICE.getName());

        Model expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        expectedModel.addEvent(MEETING);
        expectedModel.assignEventToPerson(ALICE, MEETING);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(100); // Assuming index 100 does not exist
        AssignEventByPersonIndexEventNameCommand command = new AssignEventByPersonIndexEventNameCommand(
                invalidIndex, MEETING.getEventName());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        EventName invalidEventName = new EventName("Invalid Event");
        AssignEventByPersonIndexEventNameCommand command = new AssignEventByPersonIndexEventNameCommand(
                Index.fromOneBased(1), invalidEventName);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_NAME);
    }

    @Test
    public void execute_personAlreadyAssignedToEvent_throwsCommandException() {
        model.assignEventToPerson(ALICE, MEETING);
        AssignEventByPersonIndexEventNameCommand command = new AssignEventByPersonIndexEventNameCommand(
                Index.fromOneBased(1), MEETING.getEventName());

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_PERSON_ALREADY_ASSIGNED_TO_EVENT,
                ALICE.getName(), MEETING.getEventName()));
    }

    @Test
    public void equals() {
        model.addPerson(BOB);
        AssignEventByPersonIndexEventNameCommand command1 = new AssignEventByPersonIndexEventNameCommand(
                Index.fromOneBased(1), MEETING.getEventName());
        AssignEventByPersonIndexEventNameCommand command2 = new AssignEventByPersonIndexEventNameCommand(
                Index.fromOneBased(1), MEETING.getEventName());
        AssignEventByPersonIndexEventNameCommand command3 = new AssignEventByPersonIndexEventNameCommand(
                Index.fromOneBased(2), MEETING.getEventName());
        AssignEventByPersonIndexEventNameCommand command4 = new AssignEventByPersonIndexEventNameCommand(
                Index.fromOneBased(1), WORKSHOP.getEventName());

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
        EventName eventName = new EventName("Meeting");
        AssignEventByPersonIndexEventNameCommand assignEventCommand =
                new AssignEventByPersonIndexEventNameCommand(personIndex, eventName);
        String expected = AssignEventByPersonIndexEventNameCommand.class.getCanonicalName() + "{targetPersonIndex="
                + personIndex + ", targetEventName=" + eventName + "}";
        assertEquals(expected, assignEventCommand.toString());
    }
}
