package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BREAD;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TestUtil.createPredicateList;
import static seedu.address.testutil.TestUtil.prepareAfterPredicate;
import static seedu.address.testutil.TestUtil.prepareBeforePredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryIsUpcomingAfterPredicate;
import seedu.address.model.delivery.DeliveryIsUpcomingBeforePredicate;
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
    public void execute_deliveryAfterCompletionDate_noDeliveriesFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 0);
        DeliveryIsUpcomingAfterPredicate predicate = prepareAfterPredicate("31-01-2030 23:59", Status.PENDING);
        List<Predicate<Delivery>> predicates = createPredicateList(predicate);
        UpcomingCommand command = new UpcomingCommand(predicates);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDeliveryList());
    }

    @Test
    public void execute_deliveryBeforeStartDate_noDeliveriesFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 0);
        DeliveryIsUpcomingAfterPredicate predicate = prepareAfterPredicate("31-12-2030 23:59", Status.PENDING);
        List<Predicate<Delivery>> predicates = new ArrayList<>();
        predicates.add(predicate);
        UpcomingCommand command = new UpcomingCommand(predicates);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredDeliveryList(), model.getFilteredDeliveryList());
    }

    @Test
    public void execute_deliveryBeforeCompletionDate_multipleDeliveriesFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 3);
        DeliveryIsUpcomingBeforePredicate predicate = prepareBeforePredicate("31-12-2030 23:59", Status.PENDING);
        List<Predicate<Delivery>> predicates = createPredicateList(predicate);
        UpcomingCommand command = new UpcomingCommand(predicates);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredDeliveryList(), model.getFilteredDeliveryList());
    }

    @Test
    public void execute_deliveryAfterStartDate_multipleDeliveriesFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 3);
        DeliveryIsUpcomingAfterPredicate predicate = prepareAfterPredicate("31-12-2001 23:59", Status.PENDING);
        List<Predicate<Delivery>> predicates = createPredicateList(predicate);
        UpcomingCommand command = new UpcomingCommand(predicates);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredDeliveryList(), model.getFilteredDeliveryList());
    }

    @Test
    public void execute_betweenStartAndEndDate_multipleDeliveriesFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 3);
        DeliveryIsUpcomingBeforePredicate predicateBefore = prepareBeforePredicate("04-12-2025 23:59", Status.PENDING);
        DeliveryIsUpcomingAfterPredicate predicateAfter = prepareAfterPredicate("12-10-2020 07:30", Status.PENDING);
        List<Predicate<Delivery>> predicates = new ArrayList<>();
        predicates.add(predicateAfter);
        predicates.add(predicateBefore);
        UpcomingCommand command = new UpcomingCommand(predicates);
        expectedModel.updateFilteredDeliveryList(predicateBefore.and(predicateAfter));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredDeliveryList(), model.getFilteredDeliveryList());
    }

    @Test
    public void execute_betweenStartAndEndDate_noDeliveriesFound() {
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 0);
        DeliveryIsUpcomingBeforePredicate predicateBefore = prepareBeforePredicate("11-12-2030 23:59", Status.PENDING);
        DeliveryIsUpcomingAfterPredicate predicateAfter = prepareAfterPredicate("31-12-2030 23:59", Status.PENDING);

        List<Predicate<Delivery>> predicates = new ArrayList<>();
        predicates.add(predicateAfter);
        predicates.add(predicateBefore);
        UpcomingCommand command = new UpcomingCommand(predicates);
        expectedModel.updateFilteredDeliveryList(predicateBefore.and(predicateAfter));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredDeliveryList(), model.getFilteredDeliveryList());
    }

    @Test
    public void equals() {
        DeliveryIsUpcomingBeforePredicate firstPredicate = prepareBeforePredicate(VALID_DATE_APPLE, Status.PENDING);
        DeliveryIsUpcomingBeforePredicate secondPredicate = prepareBeforePredicate(VALID_DATE_BREAD,
                Status.CANCELLED);
        List<Predicate<Delivery>> predicatesForApple = createPredicateList(firstPredicate);
        List<Predicate<Delivery>> predicatesForBread = createPredicateList(secondPredicate);
        UpcomingCommand upcomingFirstDate = new UpcomingCommand(predicatesForApple);
        UpcomingCommand upcomingSecondDate = new UpcomingCommand(predicatesForBread);

        // same object -> returns true
        assertTrue(upcomingFirstDate.equals(upcomingFirstDate));

        List<Predicate<Delivery>> predicates3 = createPredicateList(firstPredicate);

        // same values -> returns true
        UpcomingCommand upcomingFirstDateCopy = new UpcomingCommand(predicates3);
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
        DeliveryIsUpcomingBeforePredicate predicate = prepareBeforePredicate(VALID_DATE_APPLE, Status.PENDING);
        List<Predicate<Delivery>> predicates = createPredicateList(predicate);
        UpcomingCommand upcomingCommand = new UpcomingCommand(predicates);
        String expected = UpcomingCommand.class.getCanonicalName() + "{predicates=" + predicates + "}";
        assertEquals(expected, upcomingCommand.toString());
    }
}
