package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Inventory;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.util.Remark;

public class AddSupplyOrderCommandTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        ArrayList<Integer> validIdList = new ArrayList<>(Arrays.asList(1));
        assertThrows(NullPointerException.class, () -> new AddSupplyOrderCommand(
                null, new Phone("12345678"), validIdList, new Remark("")));
    }

    @Test
    public void constructor_nullPhone_throwsNullPointerException() {
        ArrayList<Integer> validIdList = new ArrayList<>(Arrays.asList(1));
        assertThrows(NullPointerException.class, () -> new AddSupplyOrderCommand(
                new Name("Valid Name"), null, validIdList, new Remark("")));
    }

    @Test
    public void constructor_nullIdList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSupplyOrderCommand(
                new Name("Valid Name"), new Phone("12345678"), null, new Remark("")));
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        ArrayList<Integer> idList = new ArrayList<>(Arrays.asList(1));
        AddSupplyOrderCommand addOrderCommand = new AddSupplyOrderCommand(
                new Name("Valid Name"), new Phone("12345678"), idList, new Remark(""));

        CommandResult commandResult = addOrderCommand.execute(modelStub);

        assertEquals(String.format(AddSupplyOrderCommand.MESSAGE_ADD_CUSTOMER_ORDER_SUCCESS,
                modelStub.ordersAdded.get(0).viewOrder()), commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.ordersAdded.size());
    }

    @Test
    public void execute_invalidIngredientId_throwsCommandException() {
        ArrayList<Integer> invalidIdList = new ArrayList<>(Arrays.asList(999));
        AddSupplyOrderCommand addOrderCommand = new AddSupplyOrderCommand(
                new Name("Valid Name"), new Phone("12345678"), invalidIdList, new Remark(""));
        ModelStub modelStub = new ModelStubWithEmptyCatalogue();

        assertThrows(CommandException.class, () -> addOrderCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        ArrayList<Integer> idList1 = new ArrayList<>(Arrays.asList(1, 2));
        ArrayList<Integer> idList2 = new ArrayList<>(Arrays.asList(3, 4));
        AddSupplyOrderCommand addOrder1Command = new AddSupplyOrderCommand(
                new Name("Alice"), new Phone("12345678"), idList1, new Remark(""));
        AddSupplyOrderCommand addOrder2Command = new AddSupplyOrderCommand(
                new Name("Bob"), new Phone("87654321"), idList2, new Remark(""));

        // same object -> returns true
        assertTrue(addOrder1Command.equals(addOrder1Command));

        // same values -> returns true
        AddSupplyOrderCommand addOrder1CommandCopy = new AddSupplyOrderCommand(
                new Name("Alice"), new Phone("12345678"), idList1, new Remark(""));
        assertTrue(addOrder1Command.equals(addOrder1CommandCopy));

        // different types -> returns false
        assertFalse(addOrder1Command.equals(1));

        // null -> returns false
        assertFalse(addOrder1Command.equals(null));

        // different order -> returns false
        assertFalse(addOrder1Command.equals(addOrder2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {
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
        public void addPerson(Person person) {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPastry(Pastry pastry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public PastryCatalogue getPastryCatalogue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIngredient(Ingredient ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCustomerOrder(CustomerOrder order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSupplyOrder(SupplyOrder order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public IngredientCatalogue getIngredientCatalogue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CustomerOrderList getCustomerOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SupplyOrderList getSupplyOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Inventory getInventory() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub with an empty ingredient catalogue.
     */
    private class ModelStubWithEmptyCatalogue extends ModelStub {
        @Override
        public IngredientCatalogue getIngredientCatalogue() {
            return IngredientCatalogue.getInstance();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }
    }

    /**
     * A Model stub that always accepts the supply order being added.
     */
    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final ArrayList<SupplyOrder> ordersAdded = new ArrayList<>();

        @Override
        public void addSupplyOrder(SupplyOrder order) {
            requireNonNull(order);
            ordersAdded.add(order);
        }

        @Override
        public void addPerson(Person person) {
            // Do nothing
        }

        @Override
        public IngredientCatalogue getIngredientCatalogue() {
            IngredientCatalogue catalogue = IngredientCatalogue.getInstance();
            catalogue.addIngredient(new Ingredient(1, "Test Ingredient", 10.0));
            return catalogue;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }
    }
}