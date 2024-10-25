package seedu.academyassist.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.academyassist.commons.core.GuiSettings;
import seedu.academyassist.commons.core.LogsCenter;
import seedu.academyassist.logic.commands.Command;
import seedu.academyassist.logic.commands.CommandResult;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.logic.parser.AcademyAssistParser;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.storage.Storage;

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
    private final AcademyAssistParser academyAssistParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        academyAssistParser = new AcademyAssistParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = academyAssistParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAcademyAssist(model.getAcademyAssist());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAcademyAssist getAcademyAssist() {
        return model.getAcademyAssist();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAcademyAssistFilePath() {
        return model.getAcademyAssistFilePath();
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
    public Model getModel() {
        return model;
    }
}
