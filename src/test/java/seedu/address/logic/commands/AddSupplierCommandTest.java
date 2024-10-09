package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.PersonBuilder;

public class AddSupplierCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSupplierCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Supplier validSupplier = new PersonBuilder().build();

        CommandResult commandResult = new AddSupplierCommand(validSupplier).execute(modelStub);

        assertEquals(String.format(AddSupplierCommand.MESSAGE_SUCCESS, Messages.format(validSupplier)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSupplier), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Supplier validSupplier = new PersonBuilder().build();
        AddSupplierCommand addSupplierCommand = new AddSupplierCommand(validSupplier);
        ModelStub modelStub = new ModelStubWithPerson(validSupplier);

        assertThrows(CommandException.class, AddSupplierCommand.MESSAGE_DUPLICATE_PERSON, () ->
                addSupplierCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Supplier alice = new PersonBuilder().withName("Alice").build();
        Supplier bob = new PersonBuilder().withName("Bob").build();
        AddSupplierCommand addAliceCommand = new AddSupplierCommand(alice);
        AddSupplierCommand addBobCommand = new AddSupplierCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddSupplierCommand addAliceCommandCopy = new AddSupplierCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different supplier -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddSupplierCommand addSupplierCommand = new AddSupplierCommand(ALICE);
        String expected = AddSupplierCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addSupplierCommand.toString());
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
        public boolean hasProduct(Product product) {
            return true;
        }

        @Override
        public void addProduct(Product product){}

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
        public void addPerson(Supplier supplier) {
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
        public boolean hasPerson(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Supplier target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Supplier target, Supplier editedSupplier) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Supplier> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Supplier> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single supplier.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Supplier supplier;

        ModelStubWithPerson(Supplier supplier) {
            requireNonNull(supplier);
            this.supplier = supplier;
        }

        @Override
        public boolean hasPerson(Supplier supplier) {
            requireNonNull(supplier);
            return this.supplier.isSamePerson(supplier);
        }
    }

    /**
     * A Model stub that always accept the supplier being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Supplier> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Supplier supplier) {
            requireNonNull(supplier);
            return personsAdded.stream().anyMatch(supplier::isSamePerson);
        }

        @Override
        public void addPerson(Supplier supplier) {
            requireNonNull(supplier);
            personsAdded.add(supplier);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
