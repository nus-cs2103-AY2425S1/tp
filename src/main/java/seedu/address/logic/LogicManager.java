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
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SuperFindCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CampusConnectParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCampusConnect;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.storage.Storage;

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
    private final CampusConnectParser campusConnectParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        campusConnectParser = new CampusConnectParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        Command command = campusConnectParser.parseCommand(commandText);
        if (shouldSaveCampusConnect(command)) {
            model.saveCurrentCampusConnect();
        }

        CommandResult commandResult;
        try {
            commandResult = command.execute(model);
            storage.saveCampusConnect(model.getCampusConnect());
        } catch (CommandException e) {
            if (!(command instanceof RedoCommand)) {
                model.undoExceptionalCommand();
            }
            throw e;
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCampusConnect getCampusConnect() {
        return model.getCampusConnect();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Tag> getListOfCurrentTags() {
        return model.getListOfCurrentTags().sorted();
    }

    @Override
    public Path getCampusConnectFilePath() {
        return model.getCampusConnectFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    private boolean shouldSaveCampusConnect(Command c) {
        boolean isUndoCommand = c instanceof UndoCommand;
        boolean isRedoCommand = c instanceof RedoCommand;
        boolean isListCommand = c instanceof ListCommand;
        boolean isSuperFindCommand = c instanceof SuperFindCommand;
        boolean isSaveCommand = !(isUndoCommand || isRedoCommand || isListCommand || isSuperFindCommand);
        return isSaveCommand;
    }
}
