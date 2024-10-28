package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.storage.CommandHistoryStorage;

public class NextCommandHistoryCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CommandHistoryTest");
    private static final Path testFilePath =
            TEST_DATA_FOLDER.resolve("commandHistoryTest.txt");;
    private CommandHistoryStorage commandHistoryStorage = new CommandHistoryStorage();

    @Test
    public void execute_nextCommandInHistory_success() throws CommandException, IOException {
        CommandHistoryStorage commandHistoryStorage = new CommandHistoryStorage();
        commandHistoryStorage.setCommandHistoryFilePath(testFilePath);
        CommandHistoryStorage.writeToFile("BLANK TEST");
        CommandHistoryStorage.writeToFile("MOCK TEST 1");

        ModelStub modelStub = new ModelStub();

        //To test next command
        PreviousCommandHistoryCommand previousCommandHistoryCommand = new PreviousCommandHistoryCommand();
        previousCommandHistoryCommand.execute(modelStub);
        previousCommandHistoryCommand.execute(modelStub);

        NextCommandHistoryCommand nextCommandHistoryCommand = new NextCommandHistoryCommand();
        CommandResult commandResult = nextCommandHistoryCommand.execute(modelStub);
        assertEquals("The next command is : MOCK TEST 1", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nextCommandInHistory_failure() throws CommandException, IOException {
        CommandHistoryStorage commandHistoryStorage = new CommandHistoryStorage();
        commandHistoryStorage.setCommandHistoryFilePath(testFilePath);
        ModelStub modelStub = new ModelStub();

        NextCommandHistoryCommand nextCommandHistoryCommand = new NextCommandHistoryCommand();
        CommandResult commandResult = nextCommandHistoryCommand.execute(modelStub);
        assertEquals("There are no more next commands.", commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        NextCommandHistoryCommand command = new NextCommandHistoryCommand();
        assertTrue(command.equals(command));

        NextCommandHistoryCommand commandCopy = new NextCommandHistoryCommand();
        assertTrue(command.equals(commandCopy));

        assertFalse(command.equals(1));
        assertFalse(command.equals(null));

        NextCommandHistoryCommand commandDifferent = new NextCommandHistoryCommand();
        assertTrue(command.equals(commandDifferent));
    }

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

        public Client getLastViewedClient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLastViewedClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public String getPreviousCommand() {
            return commandHistoryStorage.getPreviousCommand();
        }
        @Override
        public String getNextCommand() {
            return commandHistoryStorage.getNextCommand();
        }
    }

}
