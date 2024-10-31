package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.UniqueSupplierList;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.SupplierBuilder;
import seedu.address.testutil.TypicalDeliveriesWithoutSender;
import seedu.address.testutil.TypicalSuppliers;

public class AddDeliveryCommandTest {

    @Test
    public void constructor_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddDeliveryCommand(null));
    }

    @Test
    public void execute_deliveryAcceptedByModel_addSuccessful() throws Exception {
        Supplier validSupplier = new SupplierBuilder().build();
        ModelStubAcceptingDeliveryAdded modelStub = new ModelStubAcceptingDeliveryAdded(validSupplier);
        Delivery deliveryWithNullSender = new DeliveryBuilder().buildWithNullSender();
        CommandResult commandResult = new AddDeliveryCommand(deliveryWithNullSender).execute(modelStub);
        assertEquals(String.format(AddDeliveryCommand.MESSAGE_SUCCESS, Messages.format(deliveryWithNullSender)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(deliveryWithNullSender), modelStub.deliveriesAdded);
    }

    @Test
    public void execute_duplicateDelivery_throwsCommandException() {
        Delivery validDelivery = new DeliveryBuilder().buildWithNullSender();
        validDelivery.setDeliverySender(TypicalSuppliers.ALICE);
        AddDeliveryCommand addDeliveryCommand = new AddDeliveryCommand(validDelivery);
        ModelStub modelStub = new ModelStubWithDelivery(validDelivery);
        assertThrows(CommandException.class, AddDeliveryCommand.MESSAGE_DUPLICATE_DELIVERY, () ->
                addDeliveryCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Delivery deliveryApple = TypicalDeliveriesWithoutSender.APPLE;
        Delivery deliveryBread = TypicalDeliveriesWithoutSender.BREAD;
        AddDeliveryCommand addAppleDeliveryCommand = new AddDeliveryCommand(deliveryApple);
        AddDeliveryCommand addBreadDeliveryCommand = new AddDeliveryCommand(deliveryBread);

        // same object -> returns true
        assertTrue(addAppleDeliveryCommand.equals(addAppleDeliveryCommand));

        // same values -> returns true
        AddDeliveryCommand addAppleDeliveryCommandCopy = new AddDeliveryCommand(deliveryApple);
        assertTrue(addAppleDeliveryCommand.equals(addAppleDeliveryCommandCopy));

        // different types -> returns false
        assertFalse(addAppleDeliveryCommand.equals(1));

        // null -> returns false
        assertFalse(addAppleDeliveryCommand.equals(null));

        // different delivery -> returns false
        assertFalse(addAppleDeliveryCommand.equals(addBreadDeliveryCommand));
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
        public void addSupplier(Supplier supplier) {
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
        public boolean hasSupplier(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSupplier(Supplier target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSupplier(Supplier target, Supplier editedSupplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Supplier> getFilteredSupplierList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDelivery(Delivery delivery) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDelivery(Delivery target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDelivery(Delivery delivery) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Delivery> getFilteredDeliveryList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredDeliveryList(Predicate<Delivery> delivery) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setDelivery(Delivery target, Delivery updatedDelivery) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Delivery> getSortedDeliveryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedDeliveryList(Comparator<Delivery> delivery) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Delivery> getModifiedDeliveryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Supplier> getModifiedSupplierList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Supplier> getSortedSupplierList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateSortedSupplierList(Comparator<Supplier> comparator) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single delivery.
     */
    private class ModelStubWithDelivery extends ModelStub {
        private final Delivery delivery;
        private final Supplier supplier = TypicalSuppliers.ALICE;
        private final UniqueSupplierList uniqueSupplierList = new UniqueSupplierList();
        ModelStubWithDelivery(Delivery delivery) {
            requireNonNull(delivery);
            this.delivery = delivery;
            uniqueSupplierList.add(supplier);
        }

        @Override
        public ObservableList<Supplier> getFilteredSupplierList() {
            return uniqueSupplierList.asUnmodifiableObservableList();
        }

        @Override
        public boolean hasDelivery(Delivery delivery) {
            requireNonNull(delivery);
            return this.delivery.isSameDelivery(delivery);
        }
    }

    /**
     * A Model stub that always accept the delivery being added.
     */
    private class ModelStubAcceptingDeliveryAdded extends ModelStub {
        final ArrayList<Delivery> deliveriesAdded = new ArrayList<>();
        private final Supplier supplier;
        private final UniqueSupplierList uniqueSupplierList = new UniqueSupplierList();

        ModelStubAcceptingDeliveryAdded(Supplier supplier) {
            requireNonNull(supplier);
            this.supplier = supplier;
            uniqueSupplierList.add(supplier);
        }

        @Override
        public ObservableList<Supplier> getFilteredSupplierList() {
            return uniqueSupplierList.asUnmodifiableObservableList();
        }

        @Override
        public boolean hasDelivery(Delivery delivery) {
            requireNonNull(delivery);
            return deliveriesAdded.stream().anyMatch(delivery::isSameDelivery);
        }

        @Override
        public void addDelivery(Delivery delivery) {
            requireNonNull(delivery);
            deliveriesAdded.add(delivery);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
