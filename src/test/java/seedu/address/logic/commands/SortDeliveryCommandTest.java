package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DELIVERY_SORTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.APPLE;
import static seedu.address.testutil.TypicalDeliveries.BREAD;
import static seedu.address.testutil.TypicalDeliveries.CAN;
import static seedu.address.testutil.TypicalDeliveries.DURIAN;
import static seedu.address.testutil.TypicalDeliveries.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.SortOrder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.DeliverySortComparator;
import seedu.address.model.delivery.DeliverySortCostComparator;
import seedu.address.model.delivery.DeliverySortDateTimeComparator;
import seedu.address.model.delivery.DeliverySortStatusComparator;

/**
 * Contains integration tests (interaction with the Model) for {@code SortDeliveryCommand}.
 */
public class SortDeliveryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_ascendingCost_sortedByAscendingCost() {
        String expectedMessage = String.format(MESSAGE_DELIVERY_SORTED_OVERVIEW, 4, "cost", "ascending");
        DeliverySortCostComparator comparator = new DeliverySortCostComparator(new SortOrder("a"));
        SortDeliveryCommand command = new SortDeliveryCommand(comparator);
        expectedModel.updateSortedDeliveryList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BREAD, CAN, DURIAN, APPLE), model.getSortedDeliveryList());
    }

    @Test
    public void execute_descendingCost_sortedByDescendingCost() {
        String expectedMessage = String.format(MESSAGE_DELIVERY_SORTED_OVERVIEW, 4, "cost", "descending");
        DeliverySortCostComparator comparator = new DeliverySortCostComparator(new SortOrder("d"));
        SortDeliveryCommand command = new SortDeliveryCommand(comparator);
        expectedModel.updateSortedDeliveryList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE, CAN, DURIAN, BREAD), model.getSortedDeliveryList());
    }

    @Test
    public void execute_ascendingDateTime_sortedByAscendingDateTime() {
        String expectedMessage = String.format(MESSAGE_DELIVERY_SORTED_OVERVIEW, 4, "date time", "ascending");
        DeliverySortDateTimeComparator comparator = new DeliverySortDateTimeComparator(new SortOrder("a"));
        SortDeliveryCommand command = new SortDeliveryCommand(comparator);
        expectedModel.updateSortedDeliveryList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE, BREAD, CAN, DURIAN), model.getSortedDeliveryList());
    }

    @Test
    public void execute_descendingDateTime_sortedByDescendingDateTime() {
        String expectedMessage = String.format(MESSAGE_DELIVERY_SORTED_OVERVIEW, 4, "date time", "descending");
        DeliverySortDateTimeComparator comparator = new DeliverySortDateTimeComparator(new SortOrder("d"));
        SortDeliveryCommand command = new SortDeliveryCommand(comparator);
        expectedModel.updateSortedDeliveryList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CAN, DURIAN, BREAD, APPLE), model.getSortedDeliveryList());
    }

    @Test
    public void execute_ascendingStatus_sortedByAscendingStatus() {
        String expectedMessage = String.format(MESSAGE_DELIVERY_SORTED_OVERVIEW, 4,
                "status", "ascending");
        DeliverySortStatusComparator comparator = new DeliverySortStatusComparator(new SortOrder("a"));
        SortDeliveryCommand command = new SortDeliveryCommand(comparator);
        expectedModel.updateSortedDeliveryList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DURIAN, APPLE, BREAD, CAN), model.getSortedDeliveryList());
    }

    @Test
    public void execute_descendingStatus_sortedByDescendingStatus() {
        String expectedMessage = String.format(MESSAGE_DELIVERY_SORTED_OVERVIEW, 4,
                "status", "descending");
        DeliverySortStatusComparator comparator = new DeliverySortStatusComparator(new SortOrder("d"));
        SortDeliveryCommand command = new SortDeliveryCommand(comparator);
        expectedModel.updateSortedDeliveryList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE, BREAD, CAN, DURIAN), model.getSortedDeliveryList());
    }

    @Test
    public void equals() {
        DeliverySortComparator firstComparator = new DeliverySortCostComparator(new SortOrder("a"));
        DeliverySortComparator secondComparator = new DeliverySortCostComparator(new SortOrder("d"));

        SortDeliveryCommand sortFirstDeliveryCommand = new SortDeliveryCommand(firstComparator);
        SortDeliveryCommand sortSecondDeliveryCommand = new SortDeliveryCommand(secondComparator);

        // same object -> returns true
        assertTrue(sortFirstDeliveryCommand.equals(sortFirstDeliveryCommand));

        // same values -> returns true
        SortDeliveryCommand sortFirstDeliveryCommandCopy = new SortDeliveryCommand(firstComparator);
        assertTrue(sortFirstDeliveryCommand.equals(sortFirstDeliveryCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstDeliveryCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstDeliveryCommand.equals(null));

        // different command -> returns false
        assertFalse(sortFirstDeliveryCommand.equals(sortSecondDeliveryCommand));
    }

    @Test
    public void toStringMethod() {
        DeliverySortCostComparator comparator = new DeliverySortCostComparator(new SortOrder("a"));
        SortDeliveryCommand sortDeliveryCommand = new SortDeliveryCommand(comparator);
        String expected = SortDeliveryCommand.class.getCanonicalName() + "{comparator=" + comparator + "}";
        assertEquals(expected, sortDeliveryCommand.toString());
    }
}
