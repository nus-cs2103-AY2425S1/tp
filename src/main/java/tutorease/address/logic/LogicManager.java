package tutorease.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import tutorease.address.commons.core.GuiSettings;
import tutorease.address.commons.core.LogsCenter;
import tutorease.address.logic.commands.Command;
import tutorease.address.logic.commands.CommandResult;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.logic.parser.TutorEaseParser;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.Model;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.Person;
import tutorease.address.storage.Storage;

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
    private final TutorEaseParser tutorEaseParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        tutorEaseParser = new TutorEaseParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = tutorEaseParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveTutorEase(model.getTutorEase());
            storage.saveLessonSchedule(model.getLessonSchedule());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyTutorEase getTutorEase() {
        return model.getTutorEase();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getTutorEaseFilePath() {
        return model.getTutorEaseFilePath();
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
    public ObservableList<Lesson> getFilteredLessonList() {
        return model.getFilteredLessonList();
    }
}
