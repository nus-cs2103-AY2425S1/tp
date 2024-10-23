package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.DeliveryDatePredicate;
import seedu.address.model.delivery.DeliveryProductPredicate;
import seedu.address.model.delivery.DeliveryStatusPredicate;
import seedu.address.model.delivery.DeliverySupplierPredicate;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.product.Product;
import seedu.address.testutil.TypicalDeliveries;

/**
 * Contains integration tests (interaction with the Model) for {@code FindDeliveryCommand}.
 */
public class FindDeliveryCommandTest {

    private Model model = new ModelManager(TypicalDeliveries.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalDeliveries.getTypicalAddressBook(), new UserPrefs());

    /**
     * Test case for finding deliveries by delivery date.
     */
    @Test
    public void execute_dateContainsKeyword_oneDeliveryFound() {
        // Only "03-03-2025 10:30" matches the delivery date for CAN and DURIAN
        Optional<SupplierIndex> supplierIndex = Optional.empty();
        DeliveryDatePredicate predicate = new DeliveryDatePredicate(new DateTime("03-03-2025 10:30"));
        FindDeliveryCommand command = new FindDeliveryCommand(predicate, supplierIndex);
        expectedModel.updateFilteredDeliveryList(predicate);

        assertCommandSuccess(command, model, String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 2), expectedModel);
    }

    /**
     * Test case for finding deliveries by product name.
     */
    @Test
    public void execute_productContainsKeyword_multipleDeliveriesFound() {
        // "bread" should match the product in BREAD and ALICE
        Optional<SupplierIndex> supplierIndex = Optional.empty();
        DeliveryProductPredicate predicate = new DeliveryProductPredicate(new Product("bread"));
        FindDeliveryCommand command = new FindDeliveryCommand(predicate, supplierIndex);
        expectedModel.updateFilteredDeliveryList(predicate);

        assertCommandSuccess(command, model, String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 1), expectedModel);
    }

    /**
     * Test case for finding deliveries by status.
     */
    @Test
    public void execute_statusContainsKeyword_multipleDeliveriesFound() {
        // "PENDING" should match APPLE, BREAD, and CAN (3 deliveries)
        Optional<SupplierIndex> supplierIndex = Optional.empty();
        DeliveryStatusPredicate predicate = new DeliveryStatusPredicate(Status.PENDING);
        FindDeliveryCommand command = new FindDeliveryCommand(predicate, supplierIndex);
        expectedModel.updateFilteredDeliveryList(predicate);

        assertCommandSuccess(command, model, String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 3), expectedModel);
    }

    /**
     * Test case for finding deliveries by supplier index.
     */
    @Test
    public void execute_supplierIndexContainsKeyword_oneDeliveryFound() {
        Optional<SupplierIndex> supplierIndex = Optional.empty();
        DeliverySupplierPredicate predicate = new DeliverySupplierPredicate(BENSON);
        FindDeliveryCommand command = new FindDeliveryCommand(predicate, supplierIndex);
        expectedModel.updateFilteredDeliveryList(predicate);

        assertCommandSuccess(command, model, String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 1), expectedModel);
    }

    /**
     * Test case for finding deliveries by supplier index when the index is invalid.
     */
    @Test
    public void execute_invalidSupplierIndex_noDeliveriesFound() {
        Optional<SupplierIndex> supplierIndex = Optional.of(new SupplierIndex("9"));
        DeliverySupplierPredicate predicate = new DeliverySupplierPredicate(BENSON);
        FindDeliveryCommand command = new FindDeliveryCommand(predicate, supplierIndex);

        CommandResult result = command.execute(model);
        assertEquals(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, result.getFeedbackToUser());
    }

    /**
     * Test case where no deliveries match the keyword.
     */
    @Test
    public void execute_noMatch_noDeliveriesFound() {
        // No deliveries match a delivery time of "01-01-2020 00:00"
        Optional<SupplierIndex> supplierIndex = Optional.empty();
        DeliveryDatePredicate predicate = new DeliveryDatePredicate(new DateTime("01-01-2020 00:00"));
        FindDeliveryCommand command = new FindDeliveryCommand(predicate, supplierIndex);
        expectedModel.updateFilteredDeliveryList(predicate);

        assertCommandSuccess(command, model, String.format(MESSAGE_DELIVERIES_LISTED_OVERVIEW, 0), expectedModel);
    }

    @Test
    public void toStringMethod() {
        DeliveryDatePredicate predicate = new DeliveryDatePredicate(new DateTime("23-08-2024 20:21"));
        FindDeliveryCommand findDeliveryCommand = new FindDeliveryCommand(predicate, null);
        String expected = FindDeliveryCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findDeliveryCommand.toString());
    }
}
