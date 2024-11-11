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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Unable save changes due to unexpected I/O error.";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Unable to save changes due to denied storage data file access.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    /**
     * Executes the command entered by the user, parses the command, and processes it to update the address book.
     * Saves the updated address book to storage if the command does not signal an exit.
     *
     * @param commandText The text of the command to be executed.
     * @return The result of executing the command.
     * @throws CommandException If an error occurs during command execution or storage access.
     * @throws ParseException If there is an error in parsing the command.
     */
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        logger.info("----------------[PARSED COMMAND]");

        commandResult = command.execute(model);
        logger.info("----------------[EXECUTED COMMAND]");

        try {
            if (!commandResult.isExit()) {
                storage.saveAddressBook(model.getAddressBook());
            }
        } catch (AccessDeniedException e) {
            throw new CommandException(FILE_OPS_PERMISSION_ERROR_FORMAT);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_FORMAT);
        }

        return commandResult;
    }

    /**
     * Retrieves the current address book.
     *
     * @return A read-only view of the address book.
     */
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    /**
     * Retrieves the filtered list of persons based on the current filtering criteria.
     *
     * @return An observable list of persons.
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    /**
     * Retrieves the file path to the address book.
     *
     * @return The path to the address book file.
     */
    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    /**
     * Retrieves the current GUI settings, including window size and position.
     *
     * @return The current GUI settings.
     */
    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    /**
     * Sets the GUI settings, such as window size and position.
     *
     * @param guiSettings The GUI settings to be applied.
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
