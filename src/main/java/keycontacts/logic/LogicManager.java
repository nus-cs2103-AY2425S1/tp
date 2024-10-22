package keycontacts.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import keycontacts.commons.core.GuiSettings;
import keycontacts.commons.core.LogsCenter;
import keycontacts.logic.commands.Command;
import keycontacts.logic.commands.CommandResult;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.logic.parser.KeyContactsParser;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.Model;
import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.student.Student;
import keycontacts.storage.Storage;

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
    private final KeyContactsParser keyContactsParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        keyContactsParser = new KeyContactsParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = keyContactsParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveStudentDirectory(model.getStudentDirectory());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyStudentDirectory getStudentDirectory() {
        return model.getStudentDirectory();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return model.getStudentList();
    }

    @Override
    public Path getStudentDirectoryFilePath() {
        return model.getStudentDirectoryFilePath();
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
