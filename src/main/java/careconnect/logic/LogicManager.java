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
import careconnect.logic.commands.AddLogCommand;
import careconnect.logic.commands.ClearCommand;
import careconnect.logic.commands.Command;
import careconnect.logic.commands.CommandResult;
import careconnect.logic.commands.ConfirmationNoCommand;
import careconnect.logic.commands.ConfirmationYesCommand;
import careconnect.logic.commands.DeleteCommand;
import careconnect.logic.commands.DeleteLogCommand;
import careconnect.logic.commands.EditCommand;
import careconnect.logic.commands.ExitCommand;
import careconnect.logic.commands.FindCommand;
import careconnect.logic.commands.HelpCommand;
import careconnect.logic.commands.ListCommand;
import careconnect.logic.commands.SetAppointmentCommand;
import careconnect.logic.commands.TagCommand;
import careconnect.logic.commands.UntagCommand;
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
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following "
            + "error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file "
                    + "or the folder.";

    private static Command commandToConfirm = null;

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
            ViewCommand.COMMAND_WORD,
            AddLogCommand.COMMAND_WORD,
            DeleteLogCommand.COMMAND_WORD,
            TagCommand.COMMAND_WORD,
            UntagCommand.COMMAND_WORD,
            ConfirmationYesCommand.COMMAND_WORD,
            ConfirmationNoCommand.COMMAND_WORD,
            SetAppointmentCommand.COMMAND_WORD
    ));

    private final ArrayList<Class<? extends Command>> confirmationCommandsList = new ArrayList<>(Arrays.asList(
            ConfirmationNoCommand.class,
            ConfirmationYesCommand.class
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

    public static void setCommandToConfirm(Command command) {
        commandToConfirm = command;
    }

    public static Command getCommandToConfirm() {
        return commandToConfirm;
    }

    private boolean isConfirmationCommand(Command command) {
        return confirmationCommandsList.contains(command.getClass());
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        CommandResult commandResult;

        Command command = addressBookParser.parseCommand(commandText);

        // If there is no command to confirm, we should not
        // expect ConfirmationYesCommand or ConfirmationNoCommand
        if (commandToConfirm == null && isConfirmationCommand(command)) {
            throw new CommandException(Messages.MESSAGE_NO_EXECUTABLE_COMMAND);
        }

        // If there is a command waiting to be confirmed,
        // the user should not be allowed to execute other command
        if (commandToConfirm != null && !isConfirmationCommand(command)) {
            throw new CommandException(Messages.MESSAGE_UNCOMFIRMED_COMMAND);
        }

        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT,
                    e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    /**
     * {@inheritDoc}
     * <p>
     * In this implementation, ties for autocomplete suggestions will be broken by
     * lexicographical order.
     */
    @Override
    public String autocompleteCommand(String commandText) throws AutocompleteException {
        return autocompleter.autocompleteWithLexicalPriority(commandText, commandsList);
    }

    @Override
    public ValidateSyntaxResultEnum validateSyntax(String syntax) {
        syntax = syntax.trim();
        try {
            addressBookParser.parseCommand(syntax);
            return ValidateSyntaxResultEnum.VALID_FULL_COMMAND; // 1st priority
        } catch (ParseException e) {
            if (commandsList.contains(syntax)) {
                return ValidateSyntaxResultEnum.VALID_COMMAND_WORD; // 2nd priority
            }
            return ValidateSyntaxResultEnum.INVALID_COMMAND; // 3rd priority
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
