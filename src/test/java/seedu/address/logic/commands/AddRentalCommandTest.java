package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalRentalInformation.RENTAL_ONE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RentalInformationBuilder;

public class AddRentalCommandTest {
    @Test
    public void constructor_nullRentalInformation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddRentalCommand(INDEX_FIRST_PERSON, null));
        assertThrows(NullPointerException.class, () -> new AddRentalCommand(null, new RentalInformation("BLK",
                "01/01/2024", "01/02/2024", "10", "150", "150", "Steven")));
        assertThrows(NullPointerException.class, () -> new AddRentalCommand(null, null));
    }

    @Test
    public void execute_rentalInformationAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRentalInformationAdded modelStub = new ModelStubAcceptingRentalInformationAdded();
        RentalInformation validRentalInformation = new RentalInformationBuilder().build();
        Client validClient = new PersonBuilder().withRentalInformation(validRentalInformation).build();

        CommandResult commandResult = new AddRentalCommand(INDEX_FIRST_PERSON,
                validRentalInformation).execute(modelStub);

        assertEquals(String.format(AddRentalCommand.MESSAGE_SUCCESS,
                        Messages.formatRentalInformation(validRentalInformation)), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validClient), modelStub.filteredList);
    }

    @Test
    public void execute_duplicateRentalInformation_throwsCommandException() {
        ModelStubAcceptingRentalInformationAdded modelStub = new ModelStubAcceptingRentalInformationAdded();
        RentalInformation validRentalInformation = new RentalInformationBuilder().build();
        Client validClient = new PersonBuilder().build();
        Client validClientWithRentalInformation = new PersonBuilder().withRentalInformation(validRentalInformation)
                .build();
        AddRentalCommand addRentalCommand = new AddRentalCommand(INDEX_FIRST_PERSON, validRentalInformation);

        modelStub.setPerson(validClient, validClientWithRentalInformation);

        assertThrows(CommandException.class, AddRentalCommand.MESSAGE_DUPLICATE_RENTAL_INFORMATION, () ->
                addRentalCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        RentalInformation firstRentalInformation = new RentalInformationBuilder().withAddress("Ang Mo Kio").build();
        RentalInformation secondRentalInformation = new RentalInformationBuilder().withAddress("Bishan").build();
        AddRentalCommand firstAddRentalCommand = new AddRentalCommand(INDEX_FIRST_PERSON, firstRentalInformation);
        AddRentalCommand secondAddRentalCommand = new AddRentalCommand(INDEX_FIRST_PERSON, secondRentalInformation);

        // same object -> returns true
        assertTrue(firstAddRentalCommand.equals(firstAddRentalCommand));

        // same values -> returns true
        AddRentalCommand firstAddRentalCommandCopy = new AddRentalCommand(INDEX_FIRST_PERSON, firstRentalInformation);
        assertTrue(firstAddRentalCommand.equals(firstAddRentalCommandCopy));

        // different types -> returns false
        assertFalse(firstAddRentalCommand.equals(1));

        // null -> returns false
        assertFalse(firstAddRentalCommand.equals(null));

        // different client -> returns false
        assertFalse(firstAddRentalCommand.equals(secondAddRentalCommand));
    }

    @Test
    public void toStringMethod() {
        AddRentalCommand addRentalCommand = new AddRentalCommand(INDEX_FIRST_PERSON, RENTAL_ONE);
        String expected = AddRentalCommand.class.getCanonicalName() + "{toAdd=" + RENTAL_ONE + "}";
        assertEquals(expected, addRentalCommand.toString());
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
        public void addPerson(Client client) {
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
        public boolean hasPerson(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Client target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Client target, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRentalInformation(Client client, RentalInformation rentalInformation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getSortedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedPersonList(Comparator<Client> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<RentalInformation> getVisibleRentalInformationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateVisibleRentalInformationList(List<RentalInformation> rentalInformationList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObjectProperty<Client> getLastViewedClientAsObjectProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Client getLastViewedClient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLastViewedClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public String getPreviousCommand() {
            return null;
        }

        @Override
        public String getNextCommand() {
            return null;
        }
    }

    /**
     * A Model stub that accept the rental information being added.
     */
    private class ModelStubAcceptingRentalInformationAdded extends ModelStub {
        final ArrayList<Client> originalList = new ArrayList<>(List.of(new PersonBuilder().build()));
        final FilteredList<Client> filteredList = new FilteredList<>(FXCollections.observableList(originalList));

        @Override
        public boolean hasPerson(Client client) {
            requireNonNull(client);
            return filteredList.stream().anyMatch(client::isSamePerson);
        }

        @Override
        public void addPerson(Client client) {
            requireNonNull(client);
            filteredList.add(client);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public boolean hasRentalInformation(Client client, RentalInformation rentalInformation) {
            requireNonNull(client);
            requireNonNull(rentalInformation);

            return filteredList.get(filteredList.indexOf(client)).getRentalInformation().contains(rentalInformation);
        }

        @Override
        public ObservableList<Client> getFilteredPersonList() {
            return filteredList;
        }

        @Override
        public void setPerson(Client target, Client editedClient) {
            requireNonNull(target);
            requireNonNull(editedClient);

            originalList.set(originalList.indexOf(target), editedClient);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Client> predicate) {
            filteredList.setPredicate(predicate);
        }

        @Override
        public void updateVisibleRentalInformationList(List<RentalInformation> rentalInformationList) {
            // do nothing as UI related
        }

        @Override
        public Client getLastViewedClient() {
            // object returned is not used
            return null;
        }

        public ObjectProperty<Client> getLastViewedClientAsObjectProperty() {
            // object returned is not used
            return null;
        }

        @Override
        public void setLastViewedClient(Client client) {
            // do nothing as UI related
        }
    }

}
