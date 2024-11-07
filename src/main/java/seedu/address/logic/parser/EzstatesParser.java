package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ChatWindowCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.clientcommands.AddBuyerProfile;
import seedu.address.logic.commands.clientcommands.AddSellerProfile;
import seedu.address.logic.commands.clientcommands.DeleteClientProfileCommand;
import seedu.address.logic.commands.clientcommands.EditClientCommand;
import seedu.address.logic.commands.clientcommands.FindClientCommand;
import seedu.address.logic.commands.clientcommands.MoreInfoCommand;
import seedu.address.logic.commands.clientcommands.ShowClientsCommand;
import seedu.address.logic.commands.clientcommands.appointmentcommands.AddAppointmentCommand;
import seedu.address.logic.commands.clientcommands.appointmentcommands.DeleteAppointmentCommand;
import seedu.address.logic.commands.clientcommands.appointmentcommands.TodayCommand;
import seedu.address.logic.commands.listingcommands.AddBuyersToListingCommand;
import seedu.address.logic.commands.listingcommands.AddListingCommand;
import seedu.address.logic.commands.listingcommands.ClearListingCommand;
import seedu.address.logic.commands.listingcommands.DeleteListingCommand;
import seedu.address.logic.commands.listingcommands.EditListingCommand;
import seedu.address.logic.commands.listingcommands.FindListingCommand;
import seedu.address.logic.commands.listingcommands.RemoveBuyersFromListingCommand;
import seedu.address.logic.commands.listingcommands.ShowListingsCommand;
import seedu.address.logic.parser.clientcommandparsers.AddClientParser;
import seedu.address.logic.parser.clientcommandparsers.DeleteClientProfileCommandParser;
import seedu.address.logic.parser.clientcommandparsers.EditClientCommandParser;
import seedu.address.logic.parser.clientcommandparsers.FindClientCommandParser;
import seedu.address.logic.parser.clientcommandparsers.MoreInfoCommandParser;
import seedu.address.logic.parser.clientcommandparsers.appointmentcommandparsers.AddAppointmentCommandParser;
import seedu.address.logic.parser.clientcommandparsers.appointmentcommandparsers.DeleteAppointmentCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.listingcommandparsers.AddBuyersToListingCommandParser;
import seedu.address.logic.parser.listingcommandparsers.AddListingCommandParser;
import seedu.address.logic.parser.listingcommandparsers.DeleteListingCommandParser;
import seedu.address.logic.parser.listingcommandparsers.EditListingCommandParser;
import seedu.address.logic.parser.listingcommandparsers.FindListingsCommandParser;
import seedu.address.logic.parser.listingcommandparsers.RemoveBuyersFromListingCommandParser;

/**
 * Parses user input.
 */
public class EzstatesParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(EzstatesParser.class);

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
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        // ---------------------------- Client Commands ----------------------------

        case AddBuyerProfile.COMMAND_WORD, AddSellerProfile.COMMAND_WORD:
            return new AddClientParser(commandWord).parse(arguments);

        case EditClientCommand.COMMAND_WORD:
            return new EditClientCommandParser().parse(arguments);

        case DeleteClientProfileCommand.COMMAND_WORD:
            return new DeleteClientProfileCommandParser().parse(arguments);

        case MoreInfoCommand.COMMAND_WORD:
            return new MoreInfoCommandParser().parse(arguments);

        case FindClientCommand.COMMAND_WORD:
            return new FindClientCommandParser().parse(arguments);

        case ShowClientsCommand.COMMAND_WORD:
            return new ShowClientsCommand();

        // ---------------------------- Appointment Commands ----------------------------

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_WORD:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case TodayCommand.COMMAND_WORD:
            return new TodayCommand();

        // ---------------------------- Listing Commands ----------------------------

        case AddListingCommand.COMMAND_WORD:
            return new AddListingCommandParser().parse(arguments);

        case EditListingCommand.COMMAND_WORD:
            return new EditListingCommandParser().parse(arguments);

        case AddBuyersToListingCommand.COMMAND_WORD:
            return new AddBuyersToListingCommandParser().parse(arguments);

        case RemoveBuyersFromListingCommand.COMMAND_WORD:
            return new RemoveBuyersFromListingCommandParser().parse(arguments);

        case DeleteListingCommand.COMMAND_WORD:
            return new DeleteListingCommandParser().parse(arguments);

        case FindListingCommand.COMMAND_WORD:
            return new FindListingsCommandParser().parse(arguments);

        case ShowListingsCommand.COMMAND_WORD:
            return new ShowListingsCommand();

        case ClearListingCommand.COMMAND_WORD:
            return new ClearListingCommand();

        // ---------------------------- General Commands ----------------------------

        case ChatWindowCommand.COMMAND_WORD:
            return new ChatWindowCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
