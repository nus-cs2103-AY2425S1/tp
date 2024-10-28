package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteBuyerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Phone;


/**
 * Parses input arguments and creates a new {@code DeleteBuyerCommand} object.
 */
public class DeleteBuyerCommandParser implements Parser<DeleteBuyerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteBuyerCommand}
     * and returns a {@code DeleteBuyerCommand} object for execution.
     *
     * @param args The input arguments to parse.
     * @return A {@code DeleteBuyerCommand} object based on the parsed arguments.
     * @throws ParseException If the user input does not conform to the expected format or the phone number is invalid.
     */
    public DeleteBuyerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBuyerCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE);
        Phone phoneNumber = ParserUtil.parseClientPhone(argMultimap.getValue(PREFIX_PHONE).get());
        return new DeleteBuyerCommand(phoneNumber);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The argument multimap that holds the parsed arguments.
     * @param prefixes The prefixes to check for presence.
     * @return True if all prefixes contain non-empty values, false otherwise.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
