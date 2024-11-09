package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.OperatingHours;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the application.
 * <p>
 * The {@code LogicManager} orchestrates the parsing and execution of user commands,
 * handles confirmation prompts, and manages data persistence by saving the address book
 * after commands that modify data.
 * </p>
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private boolean awaitingConfirmation = false;
    private Command pendingCommand;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    /**
     * Executes the user command and returns the result.
     * <p>
     * If a command requires confirmation, the method handles the confirmation flow by
     * storing the pending command and awaiting the user's response. If the command modifies
     * data and does not require confirmation, the address book is saved after execution.
     * </p>
     *
     * @param commandText The user input string.
     * @return The result of command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        CommandResult commandResult;

        if (awaitingConfirmation) {
            // Handle the user's response to the confirmation prompt
            String userResponse = commandText.trim();
            if (userResponse.equals("Y")) {
                // User confirmed; re-execute the pending command with confirmation
                if (pendingCommand instanceof RestoreCommand) {
                    RestoreCommand previousCommand = (RestoreCommand) pendingCommand;
                    RestoreCommand confirmedCommand = new RestoreCommand(previousCommand.getIndex(), true);
                    pendingCommand = null;
                    awaitingConfirmation = false;
                    commandResult = confirmedCommand.execute(model);
                } else {
                    // Handle other commands that might require confirmation
                    awaitingConfirmation = false;
                    pendingCommand = null;
                    throw new CommandException("Confirmation not handled for this command.");
                }
            } else {
                awaitingConfirmation = false;
                pendingCommand = null;
                return new CommandResult(RestoreCommand.MESSAGE_CANCELLED);
            }
        } else {
            // Parse and execute the new command
            Command command = addressBookParser.parseCommand(commandText);
            commandResult = command.execute(model);

            if (commandResult.requiresConfirmation()) {
                awaitingConfirmation = true;
                pendingCommand = command;
                return commandResult;
            }
        }

        // Save the address book if the command modifies data
        if (!commandResult.requiresConfirmation()) {
            try {
                storage.saveAddressBook(model.getAddressBook());
            } catch (AccessDeniedException e) {
                throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
            } catch (IOException ioe) {
                throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public OperatingHours getOperatingHours() {
        return model.getOperatingHours();
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
}
