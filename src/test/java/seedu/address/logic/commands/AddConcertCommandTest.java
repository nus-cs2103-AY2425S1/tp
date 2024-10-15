package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.concert.Concert;
import seedu.address.testutil.ConcertBuilder;
import seedu.address.testutil.ModelUtil;
import seedu.address.testutil.ModelUtil.ModelStubAcceptingConcertAdded;
import seedu.address.testutil.ModelUtil.ModelStubWithConcert;

public class AddConcertCommandTest {
    private final ModelUtil modelUtil = new ModelUtil();

    @Test
    public void constructor_nullConcert_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddConcertCommand(null));
    }

    @Test
    public void execute_concertAcceptedByModel_throwsCommandException() throws Exception {
        ModelStubAcceptingConcertAdded modelStub = modelUtil.ofAcceptingConcertAdded();
        Concert validConcert = new ConcertBuilder().build();

        CommandResult commandResult = new AddConcertCommand(validConcert).execute(modelStub);

        assertEquals(String.format(AddConcertCommand.MESSAGE_SUCCESS, Messages.format(
                validConcert)), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validConcert), modelStub.getConcerts());
    }

    @Test
    public void execute_duplicateConcert_throwsCommandException() {
        Concert validConcert = new ConcertBuilder().build();
        AddConcertCommand addConcertCommand = new AddConcertCommand(validConcert);
        ModelStubWithConcert modelStub = modelUtil.ofStubWithConcert(validConcert);
        // @formatter: off
        assertThrows(CommandException.class, AddConcertCommand.MESSAGE_DUPLICATE_CONCERT, ()
            -> addConcertCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Concert coachella = new ConcertBuilder().withName("Coachella").build();
        Concert glastonbury = new ConcertBuilder().withName("Glastonbury").build();
        AddConcertCommand addCoachCommand = new AddConcertCommand(coachella);
        AddConcertCommand addGlastCommand = new AddConcertCommand(glastonbury);

        // same object return true
        assertTrue(addCoachCommand.equals(addCoachCommand));

        // same values return true
        AddConcertCommand copy = new AddConcertCommand(coachella);
        assertTrue(addCoachCommand.equals(copy));

        // different types return false
        assertFalse(addCoachCommand.equals(1));

        // null return false
        assertFalse(addCoachCommand.equals(null));

        // different concert return false
        assertFalse(addCoachCommand.equals(addGlastCommand));
    }

    @Test
    public void toStringMethod() {
        AddConcertCommand addConcertCommand = new AddConcertCommand(COACHELLA);
        String expected = AddConcertCommand.class.getCanonicalName() + "{toAdd=" + COACHELLA + "}";
        assertEquals(addConcertCommand.toString(), expected);
    }
}
