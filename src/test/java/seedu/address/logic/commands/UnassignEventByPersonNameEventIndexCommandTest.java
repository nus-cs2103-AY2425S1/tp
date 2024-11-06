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
import seedu.address.model.person.Name;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnassignEventByPersonNameEventIndexCommand}.
 */
public class UnassignEventByPersonNameEventIndexCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        model.addPerson(ALICE);
        model.addEvent(MEETING);
        model.assignEventToPerson(ALICE, MEETING);
    }

    @Test
    public void execute_validPersonAndEvent_success() {
        UnassignEventByPersonNameEventIndexCommand command = new UnassignEventByPersonNameEventIndexCommand(
                ALICE.getName(), Index.fromOneBased(1));

        String expectedMessage = String.format(UnassignEventByPersonNameEventIndexCommand.MESSAGE_SUCCESS,
                MEETING.getEventName(), ALICE.getName());

        Model expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        expectedModel.addEvent(MEETING);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Name invalidName = new Name("Invalid Name");
        UnassignEventByPersonNameEventIndexCommand command = new UnassignEventByPersonNameEventIndexCommand(
                invalidName, Index.fromOneBased(1));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
    }

    @Test
    public void execute_eventNotFound_throwsCommandException() {
        UnassignEventByPersonNameEventIndexCommand command = new UnassignEventByPersonNameEventIndexCommand(
                ALICE.getName(), Index.fromOneBased(2)); // Assuming event index 2 does not exist

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personNotAssignedToEvent_throwsCommandException() {
        model.addPerson(BOB);

        UnassignEventByPersonNameEventIndexCommand command = new UnassignEventByPersonNameEventIndexCommand(
                BOB.getName(), Index.fromOneBased(1));

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_PERSON_NOT_ASSIGNED_TO_EVENT,
                BOB.getName(), MEETING.getEventName()));
    }

    @Test
    public void equals() {
        UnassignEventByPersonNameEventIndexCommand command1 = new UnassignEventByPersonNameEventIndexCommand(
                ALICE.getName(), Index.fromOneBased(1));
        UnassignEventByPersonNameEventIndexCommand command2 = new UnassignEventByPersonNameEventIndexCommand(
                ALICE.getName(), Index.fromOneBased(1));
        UnassignEventByPersonNameEventIndexCommand command3 = new UnassignEventByPersonNameEventIndexCommand(
                BOB.getName(), Index.fromOneBased(1));
        UnassignEventByPersonNameEventIndexCommand command4 = new UnassignEventByPersonNameEventIndexCommand(
                ALICE.getName(), Index.fromOneBased(2));

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
        Index eventIndex = Index.fromOneBased(1);
        UnassignEventByPersonNameEventIndexCommand unassignEventCommand =
                new UnassignEventByPersonNameEventIndexCommand(personName, eventIndex);
        String expected = UnassignEventByPersonNameEventIndexCommand.class.getCanonicalName() + "{targetPersonName="
                + personName + ", targetEventIndex=" + eventIndex + "}";
        assertEquals(expected, unassignEventCommand.toString());
    }
}
