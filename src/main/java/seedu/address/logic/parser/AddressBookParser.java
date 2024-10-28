package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.eventcommands.AddEventCommand;
import seedu.address.logic.commands.eventcommands.ClearEventCommand;
import seedu.address.logic.commands.eventcommands.DeleteEventCommand;
import seedu.address.logic.commands.eventcommands.EditEventCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.eventcommands.ScheduleCommand;
import seedu.address.logic.commands.eventcommands.SearchEventCommand;
import seedu.address.logic.commands.personcommands.AddPersonCommand;
import seedu.address.logic.commands.personcommands.ClearPersonCommand;
import seedu.address.logic.commands.personcommands.DeletePersonCommand;
import seedu.address.logic.commands.personcommands.EditPersonCommand;
import seedu.address.logic.commands.personcommands.FindPersonCommand;
import seedu.address.logic.commands.personcommands.LinkPersonCommand;
import seedu.address.logic.commands.personcommands.SearchPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
        "(?<commandWord>\\S+)(?<combined>\\s+(?<modelType>\\S+)?(?<arguments>.*)?)?"
    );
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String modelTypeShortHand = matcher.group("modelType");
        final ModelType modelType = ModelType.fromShorthand(modelTypeShortHand);
        final String arguments = (modelType == ModelType.NEITHER)
                ? matcher.group("combined")
                : matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Model Type: " + modelType + "; Arguments: " + arguments);

        if (ClearCommand.isPrompted()) {
            switch (commandWord) {
            case "y":
                return new ClearCommandParser().parseClear();
            case "n":
                return new ClearCommandParser().parseAbort();
            default:
                logger.finer("This user input caused a ParseException: " + userInput);
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }

        switch (commandWord) {

        case AddPersonCommand.COMMAND_WORD:
            return new AddCommandParser().parse(modelType, arguments);

        case EditPersonCommand.COMMAND_WORD:
            return new EditCommandParser().parse(modelType, arguments);

        case DeletePersonCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(modelType, arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(modelType, arguments);

        case FindPersonCommand.COMMAND_WORD:
            return new FindCommandParser().parse(modelType, arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SearchPersonCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(modelType, arguments);

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleParser().parse(modelType, arguments);

        case LinkPersonCommand.COMMAND_WORD:
            return new LinkCommandParser().parse(modelType, arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Provides usage hints based on the partial command entered.
     *
     * @param userInput The partially entered user input.
     * @return Usage information as a hint for the user.
     */
    public String getHint(String userInput) {
        if (userInput.startsWith("a")) {
            return getAddHint(userInput);
        } else if (userInput.startsWith("d")) {
            return getDeleteHint(userInput);
        } else if (userInput.startsWith("e")) {
            return getEditHint(userInput);
        } else if (userInput.startsWith("f")) {
            return getFindHint(userInput);
        } else if (userInput.startsWith("s")) {
            if (userInput.startsWith("se")) {
                return getSearchHint(userInput);
            } else if (userInput.startsWith("sc")) {
                return ScheduleCommand.MESSAGE_USAGE;
            } else {
                return SearchCommand.MESSAGE_USAGE + "\n" + ScheduleCommand.MESSAGE_USAGE;
            }
        } else if (userInput.startsWith("l")) {
            if (userInput.startsWith("lis")) {
                return ListCommand.MESSAGE_USAGE;
            } else if (userInput.startsWith("lin")) {
                return LinkPersonCommand.MESSAGE_USAGE;
            } else {
                return ListCommand.MESSAGE_USAGE + "\n" + LinkPersonCommand.MESSAGE_USAGE;
            }
        } else if (userInput.startsWith("c")) {
            return getClearHint(userInput);
        } else if (userInput.startsWith("h")) {
            return HelpCommand.MESSAGE_USAGE;
        } else {
            return "";
        }
    }

    private String getAddHint(String userInput) {
        if (userInput.startsWith("add e")) {
            return AddEventCommand.MESSAGE_USAGE;
        } else if (userInput.startsWith("add p")) {
            return AddPersonCommand.MESSAGE_USAGE;
        } else {
            return AddCommand.MESSAGE_USAGE;
        }
    }

    private String getDeleteHint(String userInput) {
        if (userInput.startsWith("delete e")) {
            return DeleteEventCommand.MESSAGE_USAGE;
        } else if (userInput.startsWith("delete p")) {
            return DeletePersonCommand.MESSAGE_USAGE;
        } else {
            return DeleteCommand.MESSAGE_USAGE;
        }
    }

    private String getFindHint(String userInput) {
        if (userInput.startsWith("find e")) {
            return FindEventCommand.MESSAGE_USAGE;
        } else if (userInput.startsWith("find p")) {
            return FindPersonCommand.MESSAGE_USAGE;
        } else {
            return FindCommand.MESSAGE_USAGE;
        }
    }

    private String getSearchHint(String userInput) {
        if (userInput.startsWith("search e")) {
            return SearchEventCommand.MESSAGE_USAGE;
        } else if (userInput.startsWith("search p")) {
            return SearchPersonCommand.MESSAGE_USAGE;
        } else {
            return SearchCommand.MESSAGE_USAGE;
        }
    }

    private String getEditHint(String userInput) {
        if (userInput.startsWith("edit e")) {
            return EditEventCommand.MESSAGE_USAGE;
        } else if (userInput.startsWith("edit p")) {
            return EditPersonCommand.MESSAGE_USAGE;
        } else {
            return EditCommand.MESSAGE_USAGE;
        }
    }

    private String getClearHint(String userInput) {
        if (userInput.startsWith("clear e")) {
            return ClearEventCommand.MESSAGE_USAGE;
        } else if (userInput.startsWith("clear p")) {
            return ClearPersonCommand.MESSAGE_USAGE;
        } else {
            return ClearCommand.MESSAGE_USAGE;
        }
    }
}
