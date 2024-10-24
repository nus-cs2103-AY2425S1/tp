package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BREAD;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveries;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.DeliveryIsUpcomingPredicate;
import seedu.address.model.delivery.Status;
import seedu.address.testutil.TypicalDeliveries;

public class UpcomingCommandTest {
    private Model model = new ModelManager(TypicalDeliveries.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalDeliveries.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullDeliveryIsUpcomingPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpcomingCommand(null));
    }

    @Test
    public void execute_earlyCompletionDate_noDeliveriesFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 0);
        DeliveryIsUpcomingPredicate predicate = preparePredicate("31-01-2010 23:59");
        UpcomingCommand command = new UpcomingCommand(predicate);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDeliveryList());
    }

    //need check with JZ why this fails
    @Test
    public void execute_lateCompletionDate_multipleDeliveriesFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 3);
        DeliveryIsUpcomingPredicate predicate = preparePredicate("31-12-2030 23:59");
        UpcomingCommand command = new UpcomingCommand(predicate);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredDeliveryList(), model.getFilteredDeliveryList());
    }

    @Test
    public void equals() {
        DateTime firstDateTime = new DateTime(VALID_DATE_APPLE);
        DateTime secondDateTimeBread = new DateTime(VALID_DATE_BREAD);
        Status statusApple = Status.PENDING;
        Status statusBread = Status.CANCELLED;
        DeliveryIsUpcomingPredicate firstPredicate = new DeliveryIsUpcomingPredicate(firstDateTime, statusApple);
        DeliveryIsUpcomingPredicate secondPredicate = new DeliveryIsUpcomingPredicate(secondDateTimeBread, statusBread);

        UpcomingCommand upcomingFirstDate = new UpcomingCommand(firstPredicate);
        UpcomingCommand upcomingSecondDate = new UpcomingCommand(secondPredicate);

        // same object -> returns true
        assertTrue(upcomingFirstDate.equals(upcomingFirstDate));

        // same values -> returns true
        UpcomingCommand upcomingFirstDateCopy = new UpcomingCommand(new DeliveryIsUpcomingPredicate(
                firstDateTime, statusApple));
        assertTrue(upcomingFirstDate.equals(upcomingFirstDateCopy));

        // different types -> returns false
        assertFalse(upcomingFirstDate.equals("abc"));

        // null -> returns false
        assertFalse(upcomingFirstDate.equals(null));

        // different command -> returns false
        assertFalse(upcomingFirstDate.equals(upcomingSecondDate));
    }

    @Test
    public void toStringMethod() {
        DateTime dateTime = new DateTime(VALID_DATE_APPLE);
        Status statusApple = Status.PENDING;
        DeliveryIsUpcomingPredicate predicate = new DeliveryIsUpcomingPredicate(dateTime, statusApple);
        UpcomingCommand upcomingCommand = new UpcomingCommand(predicate);
        String expected = UpcomingCommand.class.getCanonicalName() + "{completionDateTime=" + predicate + "}";
        assertEquals(expected, upcomingCommand.toString());
    }
    /**
     * Parses {@code userInput} into a {@code DeliveryIsUpcomingPredicate}.
     */
    private DeliveryIsUpcomingPredicate preparePredicate(String userInput) {
        return new DeliveryIsUpcomingPredicate(new DateTime(userInput), Status.PENDING);
    }
}
