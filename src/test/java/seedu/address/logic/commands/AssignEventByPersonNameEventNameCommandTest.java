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

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AssignEventByPersonNameEventNameCommand}.
 */
public class AssignEventByPersonNameEventNameCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addPerson(ALICE);
        model.addEvent(MEETING);
    }

    @Test
    public void execute_validPersonAndEvent_success() {
        AssignEventByPersonNameEventNameCommand command = new AssignEventByPersonNameEventNameCommand(
                ALICE.getName(), MEETING.getEventName());

        String expectedMessage = String.format(AssignEventByPersonNameEventNameCommand.MESSAGE_SUCCESS,
                MEETING.getEventName(), ALICE.getName());

        Model expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        expectedModel.addEvent(MEETING);
        expectedModel.assignEventToPerson(ALICE, MEETING);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Name invalidName = new Name("Invalid Name");
        AssignEventByPersonNameEventNameCommand command = new AssignEventByPersonNameEventNameCommand(
                invalidName, MEETING.getEventName());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        EventName invalidEventName = new EventName("Invalid Event");
        AssignEventByPersonNameEventNameCommand command = new AssignEventByPersonNameEventNameCommand(
                ALICE.getName(), invalidEventName);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_NAME);
    }

    @Test
    public void execute_personAlreadyAssignedToEvent_throwsCommandException() {
        model.assignEventToPerson(ALICE, MEETING);
        AssignEventByPersonNameEventNameCommand command = new AssignEventByPersonNameEventNameCommand(
                ALICE.getName(), MEETING.getEventName());

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_PERSON_ALREADY_ASSIGNED_TO_EVENT,
                ALICE.getName(), MEETING.getEventName()));
    }

    @Test
    public void equals() {
        model.addPerson(BOB);
        AssignEventByPersonNameEventNameCommand command1 = new AssignEventByPersonNameEventNameCommand(
                ALICE.getName(), MEETING.getEventName());
        AssignEventByPersonNameEventNameCommand command2 = new AssignEventByPersonNameEventNameCommand(
                ALICE.getName(), MEETING.getEventName());
        AssignEventByPersonNameEventNameCommand command3 = new AssignEventByPersonNameEventNameCommand(
                BOB.getName(), MEETING.getEventName());
        AssignEventByPersonNameEventNameCommand command4 = new AssignEventByPersonNameEventNameCommand(
                ALICE.getName(), WORKSHOP.getEventName());

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
        Name personName = new Name("Alice");
        EventName eventName = new EventName("Meeting");
        AssignEventByPersonNameEventNameCommand assignEventCommand =
                new AssignEventByPersonNameEventNameCommand(personName, eventName);
        String expected = AssignEventByPersonNameEventNameCommand.class.getCanonicalName() + "{targetPersonName="
                + personName + ", targetEventName=" + eventName + "}";
        assertEquals(expected, assignEventCommand.toString());
    }
}
