package seedu.address.logic;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_CANCEL_COMMAND;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private static final Set<String> CONFIRM_WORDS = Set.of("y", "yes");

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private CommandResult lastCommandResult = null;
    private boolean waitingForPrompt = false;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        CommandResult commandResult;

        if (waitingForPrompt) {
            waitingForPrompt = false;
            commandResult = executePrompt(commandText);
        } else {
            logger.info("----------------[USER COMMAND][" + commandText + "]");

            Command command = addressBookParser.parseCommand(commandText);
            commandResult = command.execute(model);
        }

        if (commandResult.getType() == CommandResult.Type.PROMPT) {
            waitingForPrompt = true;
        }

        saveData();

        lastCommandResult = commandResult;
        return commandResult;
    }

    /**
     * Checks if the user accepted a confirmation prompt. If the prompt was confirmed, executes the continuation
     * function of the most recent {@link CommandResult} and returns the result.
     */
    private CommandResult executePrompt(String userInput) throws CommandException {
        if (!isConfirmation(userInput)) {
            lastCommandResult = new CommandResult(MESSAGE_CANCEL_COMMAND);
        } else {
            lastCommandResult = lastCommandResult.confirmPrompt();
        }
        return lastCommandResult;
    }

    /**
     * Checks if the given user input corresponds to user confirming a prompt.
     */
    private boolean isConfirmation(String userInput) {
        return CONFIRM_WORDS.contains(userInput.trim().toLowerCase());
    }

    private void saveData() throws CommandException {
        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }
    }

    @Override
    public CommandResult processFile(File file) throws CommandException {
        if (file == null) {
            lastCommandResult = new CommandResult(MESSAGE_CANCEL_COMMAND);
            return lastCommandResult;
        }
        switch (lastCommandResult.getType()) {
        case IMPORT_DATA:
            logger.info(String.format("Importing data from &1%s", file.getPath()));
            lastCommandResult = lastCommandResult.processFile(importFile(file));
            break;

        case EXPORT_DATA:
            logger.info(String.format("Exporting data to &1%s", file.getPath()));
            lastCommandResult = lastCommandResult.processFile(exportFile(file));
            break;

        default:
            throw new CommandException("An error occurred during the execution of this command");
            // This line should not be reached
        }

        saveData();
        return lastCommandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Client> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<RentalInformation> getVisibleRentalInformationList() {
        return model.getVisibleRentalInformationList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObjectProperty<Client> getVisibleClient() {
        return model.getLastViewedClientAsObjectProperty();
    }

    @Override
    public boolean importFile(File file) {
        requireNonNull(file);

        ReadOnlyAddressBook addressBook;
        try {
            addressBook = storage.readAddressBook(file.toPath()).get();
        } catch (DataLoadingException | NoSuchElementException e) {
            return false;
        }

        model.setAddressBook(addressBook);
        return true;
    }

    @Override
    public boolean exportFile(File file) {
        requireNonNull(file);

        try {
            storage.saveAddressBook(model.getAddressBook(), file.toPath());
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
