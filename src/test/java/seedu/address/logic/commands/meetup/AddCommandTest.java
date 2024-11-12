package seedu.address.logic.commands.meetup;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.meetup.TypicalMeetUps.FIRST_MEETUP;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.MeetUpList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.meetup.MeetUp;
import seedu.address.testutil.meetup.MeetUpBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullMeetUp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_meetUpAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetUpAdded modelStub = new ModelStubAcceptingMeetUpAdded();
        MeetUp validMeetUp = new MeetUpBuilder().build();

        CommandResult commandResult = new AddCommand(validMeetUp).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validMeetUp)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeetUp), modelStub.meetUpsAdded);
    }

    @Test
    public void execute_duplicateMeetUp_throwsCommandException() {
        MeetUp validMeetUp = new MeetUpBuilder().build();
        AddCommand addCommand = new AddCommand(validMeetUp);

        ModelStub modelStub = new ModelStubWithMeetUp(validMeetUp);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_MEETUP, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidMeetUpToFrom_throwsCommandException() {
        ModelStub modelStub = new ModelStubAcceptingMeetUpAdded();
        MeetUp invalidMeetUp = new MeetUpBuilder().withTo(MeetUpBuilder.DEFAULT_FROM).build();
        AddCommand addCommand = new AddCommand(invalidMeetUp);

        assertThrows(CommandException.class,
                String.format(AddCommand.MESSAGE_INVALID_TO_FROM, invalidMeetUp.getTo(), invalidMeetUp.getFrom()), ()
                        -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        MeetUp meetUpA = new MeetUpBuilder().withSubject("meetUpA").build();
        MeetUp meetUpB = new MeetUpBuilder().withSubject("meetUpB").build();
        AddCommand addMeetUpACommand = new AddCommand(meetUpA);
        AddCommand addMeetUpBCommand = new AddCommand(meetUpB);

        // same object -> returns true
        assertTrue(addMeetUpACommand.equals(addMeetUpACommand));

        // same values -> returns true
        AddCommand addMeetUpACommandCopy = new AddCommand(meetUpA);
        assertTrue(addMeetUpACommand.equals(addMeetUpACommandCopy));

        // different types -> returns false
        assertFalse(addMeetUpACommand.equals(1));

        // null -> returns false
        assertFalse(addMeetUpACommand.equals(null));

        // different buyer -> returns false
        assertFalse(addMeetUpACommand.equals(addMeetUpBCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(FIRST_MEETUP);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + FIRST_MEETUP + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A Model stub that contains a single buyer.
     */
    private class ModelStubWithMeetUp extends ModelStub {
        private final MeetUp meetUp;

        ModelStubWithMeetUp(MeetUp meetUp) {
            requireNonNull(meetUp);
            this.meetUp = meetUp;
        }

        @Override
        public boolean hasMeetUp(MeetUp meetUp) {
            requireNonNull(meetUp);
            return this.meetUp.isSameMeetUp(meetUp);
        }
    }

    /**
     * A Model stub that always accept the meetup being added.
     */
    private class ModelStubAcceptingMeetUpAdded extends ModelStub {
        final ArrayList<MeetUp> meetUpsAdded = new ArrayList<>();

        @Override
        public boolean hasMeetUp(MeetUp meetUp) {
            requireNonNull(meetUp);
            return meetUpsAdded.stream().anyMatch(meetUp::isSameMeetUp);
        }

        @Override
        public void addMeetUp(MeetUp meetUp) {
            requireNonNull(meetUp);
            meetUpsAdded.add(meetUp);
        }

        @Override
        public ReadOnlyMeetUpList getMeetUpList() {
            return new MeetUpList();
        }
    }

}
