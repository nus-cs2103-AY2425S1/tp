package seedu.sellsavvy.logic.commands.customercommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.commons.util.StringUtil.normalise;
import static seedu.sellsavvy.logic.Messages.MESSAGE_SIMILAR_NAME_WARNING;
import static seedu.sellsavvy.logic.commands.customercommands.AddCustomerCommand.MESSAGE_SIMILAR_TAGS_WARNING;
import static seedu.sellsavvy.logic.commands.customercommands.AddCustomerCommand.MESSAGE_SUCCESS;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalCustomers.ALICE;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.sellsavvy.commons.core.GuiSettings;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.AddressBook;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.ReadOnlyAddressBook;
import seedu.sellsavvy.model.ReadOnlyUserPrefs;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.testutil.CustomerBuilder;

public class AddCustomerCommandTest {

    @Test
    public void constructor_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCustomerCommand(null));
    }

    @Test
    public void execute_customerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCustomerAdded modelStub = new ModelStubAcceptingCustomerAdded();
        Customer validCustomer = new CustomerBuilder().build();

        CommandResult commandResult = new AddCustomerCommand(validCustomer).execute(modelStub);

        assertEquals(String.format(MESSAGE_SUCCESS, Messages.format(validCustomer)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCustomer), modelStub.customersAdded);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        Customer validCustomer = new CustomerBuilder().build();
        AddCustomerCommand addCustomerCommand = new AddCustomerCommand(validCustomer);
        ModelStub modelStub = new ModelStubWithCustomer(validCustomer);

        assertThrows(CommandException.class,
                AddCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER, () -> addCustomerCommand.execute(modelStub));
    }

    @Test
    public void execute_similarTags_givesWarning() throws Exception {
        ModelStubAcceptingCustomerAdded modelStub = new ModelStubAcceptingCustomerAdded();
        Customer validCustomer = new CustomerBuilder().withTags("friends", "Friends").build();

        CommandResult commandResult = new AddCustomerCommand(validCustomer).execute(modelStub);

        assertEquals(MESSAGE_SIMILAR_TAGS_WARNING
                        + String.format(MESSAGE_SUCCESS, Messages.format(validCustomer)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCustomer), modelStub.customersAdded);
    }

    @Test
    public void execute_similarCustomer_givesWarning() {
        // creates a similar customer that is not identical
        Customer validCustomer = new CustomerBuilder().build();
        String normalisedName = normalise(validCustomer.getName().fullName);
        Customer similarCustomer = new CustomerBuilder().withName(normalisedName).build();
        assertNotEquals(validCustomer, similarCustomer);

        // an actual model was used because of the need for actual interaction of ModelManger
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addCustomer(validCustomer);
        Model expectedModel = model.createCopy();
        expectedModel.addCustomer(similarCustomer);

        AddCustomerCommand addCustomerCommand = new AddCustomerCommand(similarCustomer);

        String expectedMessage = MESSAGE_SIMILAR_NAME_WARNING
                + String.format(MESSAGE_SUCCESS, Messages.format(similarCustomer));

        assertCommandSuccess(addCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Customer alice = new CustomerBuilder().withName("Alice").build();
        Customer bob = new CustomerBuilder().withName("Bob").build();
        AddCustomerCommand addAliceCommand = new AddCustomerCommand(alice);
        AddCustomerCommand addBobCommand = new AddCustomerCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCustomerCommand addAliceCommandCopy = new AddCustomerCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different customer -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCustomerCommand addCustomerCommand = new AddCustomerCommand(ALICE);
        String expected = AddCustomerCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCustomerCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSimilarCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCustomer(Customer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCustomer(Customer target, Customer editedCustomer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Model createCopy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCustomerList(Predicate<Customer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyObjectProperty<Customer> getSelectedCustomerProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSelectedCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isSelectedCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Customer getSelectedCustomer() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FilteredList<Order> getFilteredOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public OrderList getSelectedOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOrder(Order target, Order editedOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Customer findEquivalentCustomer(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single customer.
     */
    private class ModelStubWithCustomer extends ModelStub {
        private final Customer customer;

        ModelStubWithCustomer(Customer customer) {
            requireNonNull(customer);
            this.customer = customer;
        }

        @Override
        public boolean hasCustomer(Customer customer) {
            requireNonNull(customer);
            return this.customer.isSameCustomer(customer);
        }

        @Override
        public boolean hasSimilarCustomer(Customer customer) {
            return this.customer.isSimilarTo(customer);
        }
    }

    /**
     * A Model stub that always accept the customer being added.
     */
    private class ModelStubAcceptingCustomerAdded extends ModelStub {
        final ArrayList<Customer> customersAdded = new ArrayList<>();

        @Override
        public boolean hasCustomer(Customer customer) {
            requireNonNull(customer);
            return customersAdded.stream().anyMatch(customer::isSameCustomer);
        }

        @Override
        public boolean hasSimilarCustomer(Customer customer) {
            requireNonNull(customer);
            return customersAdded.stream().anyMatch(customer::isSimilarTo);
        }

        @Override
        public void addCustomer(Customer customer) {
            requireNonNull(customer);
            customersAdded.add(customer);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
