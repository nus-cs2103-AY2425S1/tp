package seedu.address.logic.parser.meetup;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.meetup.AddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meetup.AddedBuyer;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.Subject;
import seedu.address.model.meetup.To;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_INFO, PREFIX_FROM, PREFIX_TO,
                        PREFIX_ADDED_BUYER);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT, PREFIX_INFO, PREFIX_FROM, PREFIX_TO, PREFIX_ADDED_BUYER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBJECT, PREFIX_INFO, PREFIX_FROM, PREFIX_TO);
        Subject name = ParserUtil.parseMeetUpSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
        Info info = ParserUtil.parseMeetUpInfo(argMultimap.getValue(PREFIX_INFO).get());
        From from = ParserUtil.parseMeetUpFrom(argMultimap.getValue(PREFIX_FROM).get());
        To to = ParserUtil.parseMeetUpTo(argMultimap.getValue(PREFIX_TO).get());
        Set<AddedBuyer> addedBuyersList = ParserUtil.parseAddedBuyers(argMultimap.getAllValues(PREFIX_ADDED_BUYER));

        MeetUp meetUp = new MeetUp(name, info, from, to, addedBuyersList);

        return new AddCommand(meetUp);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
