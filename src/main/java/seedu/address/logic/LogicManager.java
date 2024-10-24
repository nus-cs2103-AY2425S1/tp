package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.State;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;
import seedu.address.model.task.Task;
import seedu.address.storage.Storage;
import seedu.address.storage.VersionHistoryStorage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final VersionHistoryStorage versionHistoryStorage;
    private VersionHistory versionHistory;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage, VersionHistoryStorage versionHistoryStorage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        this.versionHistoryStorage = versionHistoryStorage;
        VersionHistory tempVersionHistory = new VersionHistory();
        try {
            tempVersionHistory = versionHistoryStorage.readVersionHistory().orElseGet(() -> new VersionHistory());
        } catch (DataLoadingException e) {
            tempVersionHistory = new VersionHistory();
        }
        this.versionHistory = tempVersionHistory;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);
        this.versionHistory = command.updateVersionHistory(versionHistory, model);
        try {
            ReadOnlyAddressBook tempAddressBook = versionHistory.getVersions().get(versionHistory.getCurrentVersionIndex());
            ReadOnlyAddressBook currentAddressBook = new AddressBook().duplicateCopy(tempAddressBook);
            model.setAddressBook(currentAddressBook);
            storage.saveAddressBook(currentAddressBook);
            storage.saveUserPrefs(model.getUserPrefs());
            versionHistoryStorage.saveVersionHistory(versionHistory);
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        } catch (IndexOutOfBoundsException iob) {
            return commandResult;
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Student> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return model.getFilteredGroupList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
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
    public String getMostRecentGroupTaskDisplay() {
        return model.getMostRecentGroupTaskDisplay();
    }

    @Override
    public void setStateStudents() {
        this.model.setStateStudents();
    }

    @Override
    public void setStateGroups() {
        this.model.setStateGroups();
    }

    @Override
    public void setStateGroupTask() {
        this.model.setStateGroupTask();
    }

    @Override
    public State getState() {
        return this.model.getState();
    }

    @Override
    public void setMostRecentGroupTaskDisplay(String string) {
        model.setMostRecentGroupTaskDisplay(string);
    }

    @Override
    public void setMostRecentGroupTaskDisplay() {
        this.model.setMostRecentGroupTaskDisplay();
    }
}
