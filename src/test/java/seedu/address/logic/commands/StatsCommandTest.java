package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalGuests.AVA;
import static seedu.address.testutil.TypicalGuests.BRIAN;
import static seedu.address.testutil.TypicalGuests.CAM;
import static seedu.address.testutil.TypicalVendors.ELLA;
import static seedu.address.testutil.TypicalVendors.GAVIN;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class StatsCommandTest {
    private Model model = new ModelManager();

    @AfterEach
    public void resetModel() {
        model = new ModelManager();
    }

    @Test
    public void execute_noGuestsOrVendors_success() {
        StatsCommand statsCommand = new StatsCommand();
        CommandResult result = statsCommand.execute(model);

        String expectedMessage = StatsCommand.MESSAGE_STATS_SUCCESS
                + "Number of guests: 0 (0 pending, 0 coming, 0 not coming)\n"
                + "Number of vendors: 0";

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_singleGuestPending_success() {
        model.addPerson(BRIAN);

        StatsCommand statsCommand = new StatsCommand();
        CommandResult result = statsCommand.execute(model);

        String expectedMessage = StatsCommand.MESSAGE_STATS_SUCCESS
                + "Number of guests: 1 (1 pending, 0 coming, 0 not coming)\n"
                + "Number of vendors: 0";

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleGuestsMixedStatus_success() {
        model.addPerson(AVA);
        model.addPerson(BRIAN);
        model.addPerson(CAM);

        StatsCommand statsCommand = new StatsCommand();
        CommandResult result = statsCommand.execute(model);

        String expectedMessage = StatsCommand.MESSAGE_STATS_SUCCESS
                + "Number of guests: 3 (1 pending, 1 coming, 1 not coming)\n"
                + "Number of vendors: 0";

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_singleVendor_success() {
        model.addPerson(ELLA);

        StatsCommand statsCommand = new StatsCommand();
        CommandResult result = statsCommand.execute(model);

        String expectedMessage = StatsCommand.MESSAGE_STATS_SUCCESS
                + "Number of guests: 0 (0 pending, 0 coming, 0 not coming)\n"
                + "Number of vendors: 1";

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleVendors_success() {
        model.addPerson(ELLA);
        model.addPerson(GAVIN);

        StatsCommand statsCommand = new StatsCommand();
        CommandResult result = statsCommand.execute(model);

        String expectedMessage = StatsCommand.MESSAGE_STATS_SUCCESS
                + "Number of guests: 0 (0 pending, 0 coming, 0 not coming)\n"
                + "Number of vendors: 2";

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }
}
