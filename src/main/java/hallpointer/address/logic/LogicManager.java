package hallpointer.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import hallpointer.address.commons.core.GuiSettings;
import hallpointer.address.commons.core.LogsCenter;
import hallpointer.address.logic.commands.Command;
import hallpointer.address.logic.commands.CommandResult;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.logic.parser.HallPointerParser;
import hallpointer.address.logic.parser.exceptions.ParseException;
import hallpointer.address.model.Model;
import hallpointer.address.model.ReadOnlyHallPointer;
import hallpointer.address.model.member.Member;
import hallpointer.address.storage.Storage;
import javafx.collections.ObservableList;

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
    private final HallPointerParser hallPointerParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        hallPointerParser = new HallPointerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = hallPointerParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveHallPointer(model.getHallPointer());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyHallPointer getHallPointer() {
        return model.getHallPointer();
    }

    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return model.getFilteredMemberList();
    }

    @Override
    public Path getHallPointerFilePath() {
        return model.getHallPointerFilePath();
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
