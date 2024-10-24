package careconnect.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import careconnect.commons.core.GuiSettings;
import careconnect.commons.core.LogsCenter;
import careconnect.logic.autocompleter.Autocompleter;
import careconnect.logic.autocompleter.exceptions.AutocompleteException;
import careconnect.logic.commands.AddCommand;
import careconnect.logic.commands.ClearCommand;
import careconnect.logic.commands.Command;
import careconnect.logic.commands.CommandResult;
import careconnect.logic.commands.DeleteCommand;
import careconnect.logic.commands.EditCommand;
import careconnect.logic.commands.ExitCommand;
import careconnect.logic.commands.FindCommand;
import careconnect.logic.commands.HelpCommand;
import careconnect.logic.commands.ListCommand;
import careconnect.logic.commands.ViewCommand;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.logic.parser.AddressBookParser;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.Model;
import careconnect.model.ReadOnlyAddressBook;
import careconnect.model.person.Person;
import careconnect.storage.Storage;
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
    private final AddressBookParser addressBookParser;
    private final Autocompleter autocompleter;
    private final ArrayList<String> commandsList = new ArrayList<>(Arrays.asList(
            AddCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            ViewCommand.COMMAND_WORD
    ));

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        autocompleter = new Autocompleter();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    /**
     * {@inheritDoc}
     *
     * In this implementation, ties for autocomplete suggestions will be broken by
     * lexicographical order.
     */
    @Override
    public String autocompleteCommand(String commandText) throws AutocompleteException {
        return autocompleter.autocompleteWithLexicalPriority(commandText, commandsList);
    }

    @Override
    public boolean validateSyntax(String syntax) {
        syntax = syntax.trim();
        if (commandsList.contains(syntax)) {
            return true;
        }
        try {
            addressBookParser.parseCommand(syntax);
            return true;
        } catch (ParseException e) {
            return false;
        }
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
}
