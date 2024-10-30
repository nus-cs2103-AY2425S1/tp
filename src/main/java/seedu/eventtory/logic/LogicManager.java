package seedu.eventtory.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.eventtory.commons.core.GuiSettings;
import seedu.eventtory.commons.core.LogsCenter;
import seedu.eventtory.logic.commands.Command;
import seedu.eventtory.logic.commands.CommandResult;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.logic.parser.EventToryParser;
import seedu.eventtory.logic.parser.exceptions.ParseException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.storage.Storage;
import seedu.eventtory.ui.UiState;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT = "Could not save data to file %s due to insufficient"
            + " permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final EventToryParser eventToryParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        eventToryParser = new EventToryParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = eventToryParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveEventTory(model.getEventTory());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyEventTory getEventTory() {
        return model.getEventTory();
    }

    @Override
    public ObservableList<Vendor> getFilteredVendorList() {
        return model.getFilteredVendorList();
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public ObservableObjectValue<Vendor> getViewedVendor() {
        return model.getViewedVendor();
    }

    @Override
    public ObservableObjectValue<Event> getViewedEvent() {
        return model.getViewedEvent();
    }

    @Override
    public ObservableObjectValue<UiState> getUiState() {
        return model.getUiState();
    }

    @Override
    public ObservableList<Event> getAssociatedEvents(Vendor vendor) {
        return model.getAssociatedEvents(vendor);
    }

    @Override
    public ObservableList<Vendor> getAssociatedVendors(Event event) {
        return model.getAssociatedVendors(event);
    }

    @Override
    public ObservableList<Association> getAssociationList() {
        return model.getAssociationList();
    }

    @Override
    public Path getEventToryFilePath() {
        return model.getEventToryFilePath();
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
