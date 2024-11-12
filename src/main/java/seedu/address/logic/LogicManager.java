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
import seedu.address.logic.parser.AbcliParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.ReadOnlyPropertyList;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.property.Property;
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

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = AbcliParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getBuyerList(), model.getMeetUpList(), model.getPropertyList());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyBuyerList getBuyerList() {
        return model.getBuyerList();
    }
    @Override
    public ObservableList<Buyer> getUnfilteredBuyerList() {
        return model.getUnfilteredBuyerList();
    }

    @Override
    public ObservableList<Buyer> getFilteredBuyerList() {
        return model.getFilteredBuyerList();
    }

    @Override
    public Path getBuyerListFilePath() {
        return model.getBuyerListFilePath();
    }

    @Override
    public ReadOnlyMeetUpList getMeetUpList() {
        return model.getMeetUpList();
    }
    @Override
    public ObservableList<MeetUp> getUnfilteredMeetUpList() {
        return model.getUnfilteredMeetUpList();
    }
    @Override
    public ObservableList<MeetUp> getFilteredMeetUpList() {
        return model.getFilteredMeetUpList();
    }

    @Override
    public Path getMeetUpListFilePath() {
        return model.getMeetUpListFilePath();
    }

    @Override
    public ReadOnlyPropertyList getPropertyList() {
        return model.getPropertyList();
    }
    @Override
    public ObservableList<Property> getUnfilteredPropertyList() {
        return model.getUnfilteredPropertyList();
    }
    @Override
    public ObservableList<Property> getFilteredPropertyList() {
        return model.getFilteredPropertyList();
    }

    @Override
    public Path getPropertyListFilePath() {
        return model.getPropertyListFilePath();
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
