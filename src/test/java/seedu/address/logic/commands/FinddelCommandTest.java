package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.APPLES;
import static seedu.address.testutil.TypicalDeliveries.BANANAS;
import static seedu.address.testutil.TypicalDeliveries.ORANGES;
import static seedu.address.testutil.TypicalDeliveries.PEARS;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.delivery.DeliveryList;
import seedu.address.model.delivery.ItemNameContainsKeywordPredicate;
import seedu.address.testutil.DeliveryListBuilder;


public class FinddelCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void equals() {
        ItemNameContainsKeywordPredicate firstPredicate =
                new ItemNameContainsKeywordPredicate(Collections.singletonList("first"));
        ItemNameContainsKeywordPredicate secondPredicate =
                new ItemNameContainsKeywordPredicate(Collections.singletonList("second"));

        FinddelCommand findFirstCommand = new FinddelCommand(firstPredicate);
        FinddelCommand findSecondCommand = new FinddelCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FinddelCommand findFirstCommandCopy = new FinddelCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noDeliveryFound() {
        AddressBookParser.setInspect(true);
        DeliveryList deliveryList = new DeliveryListBuilder().build();
        model.setFilteredDeliveryList(deliveryList);
        expectedModel.setFilteredDeliveryList(deliveryList);
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 0);
        ItemNameContainsKeywordPredicate predicate = preparePredicate(" ");
        FinddelCommand command = new FinddelCommand(predicate);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDeliveryList());
    }

    @Test
    public void execute_multipleKeywords_multipleDeliveriesFound() {
        AddressBookParser.setInspect(true);
        DeliveryList deliveryList = new DeliveryListBuilder().build();
        model.setFilteredDeliveryList(deliveryList);
        expectedModel.setFilteredDeliveryList(deliveryList);
        String expectedMessage = String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 4);
        ItemNameContainsKeywordPredicate predicate = preparePredicate("apples oranges bananas pears");
        FinddelCommand command = new FinddelCommand(predicate);
        expectedModel.updateFilteredDeliveryList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLES, ORANGES, BANANAS, PEARS), model.getFilteredDeliveryList());
    }

    @Test
    public void toStringMethod() {
        ItemNameContainsKeywordPredicate predicate = new ItemNameContainsKeywordPredicate(Arrays.asList("keyword"));
        FinddelCommand findCommand = new FinddelCommand(predicate);
        String expected = FinddelCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ItemNameContainsKeywordPredicate}.
     */
    private ItemNameContainsKeywordPredicate preparePredicate(String userInput) {
        return new ItemNameContainsKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
