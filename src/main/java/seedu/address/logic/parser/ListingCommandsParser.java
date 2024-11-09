package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListingAddCommand;
import seedu.address.logic.commands.ListingDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Custom parser for deciding which listing sub-command to use
 */
public class ListingCommandsParser {
    private static final Pattern LISTING_COMMAND_FORMAT = Pattern.compile("(?<commandWord>[^0-9]+\\s+)(?<arguments>.*)");

    /**
     * Parses args to take out the "sub-command" (add/delete) before passing the respective Listing command
     * @param args The args from AddressBookParser minus the main command word - expected to be "listing"
     */
    public Command parse(String args) throws ParseException {
        final Matcher listingMatcher = LISTING_COMMAND_FORMAT.matcher(args.trim());

        if (!listingMatcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Valid listing commands are 'listing add' or 'listing delete'"));
        }

        final String listingCommandWord = listingMatcher.group("commandWord").trim();
        final String listingArguments = listingMatcher.group("arguments");

        if (listingCommandWord.equals(ListingAddCommand.COMMAND_WORD_SUFFIX)) {
            return new ListingAddCommandParser().parse(listingArguments);
        } else if (listingCommandWord.equals(ListingDeleteCommand.COMMAND_WORD_SUFFIX)) {
            return new ListingDeleteCommandParser().parse(listingArguments);
        }

        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
}
