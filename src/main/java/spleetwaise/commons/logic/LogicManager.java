package spleetwaise.commons.logic;

import static spleetwaise.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import spleetwaise.address.logic.parser.AddressBookParser;
import spleetwaise.address.model.ReadOnlyAddressBook;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.core.GuiSettings;
import spleetwaise.commons.core.LogsCenter;
import spleetwaise.commons.exceptions.SpleetWaiseCommandException;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.commons.storage.Storage;
import spleetwaise.transaction.logic.parser.TransactionBookParser;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final TransactionBookParser transactionParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Storage storage) {
        this.storage = storage;
        this.addressBookParser = new AddressBookParser();
        this.transactionParser = new TransactionBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws ParseException, SpleetWaiseCommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        // Parse command
        Command command;
        command = addressBookParser.parseCommand(commandText);
        if (command == null) {
            command = transactionParser.parseCommand(commandText);
        }
        if (command == null) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_COMMAND, commandText));
        }

        // Execute command
        CommandResult commandResult = command.execute();

        // Update storage
        try {
            CommonModel model = CommonModelManager.getInstance();
            storage.saveTransactionBook(model.getTransactionBook());
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return CommonModelManager.getInstance().getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return CommonModelManager.getInstance().getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return CommonModelManager.getInstance().getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return CommonModelManager.getInstance().getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        CommonModelManager.getInstance().setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return CommonModelManager.getInstance().getFilteredTransactionList();
    }
}
