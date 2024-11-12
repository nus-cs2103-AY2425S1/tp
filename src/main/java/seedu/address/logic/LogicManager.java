package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
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
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;
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
    private final AddressBookParser addressBookParser;

    private final EventManager eventManager;


    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage, EventManager eventManager) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        this.eventManager = eventManager;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command;
        if (model.getSearchMode()) {
            logger.info("Searchmode command detected");
            command = addressBookParser.parseSearchModeCommand(commandText);

        } else {
            command = addressBookParser.parseCommand(commandText);

        }
        commandResult = command.execute(model, eventManager);

        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveEventManager(eventManager);
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return eventManager.getEventList();
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

    /**
     * Returns the search mode status.
     */
    @Override
    public BooleanProperty getSearchMode() {
        return model.searchModeProperty();

    }

    @Override
    public BooleanProperty getIsFindEvent() {
        return model.getIsFindEvent();
    }

    /**
     * Get all persons in the address book.
     */
    @Override
    public ObservableList<Person> getAllPersons() {
        return model.getAllPersons();
    }

    /**
     * Retrieves the contact list filtered specifically for finding events.
     *
     * @return an {@code ObservableList<Person>} containing the contacts related to the current event search.
     */
    @Override
    public ObservableList<Person> getContactListForFindEvent() {
        return model.getContactListForFindEvent();
    }
}
