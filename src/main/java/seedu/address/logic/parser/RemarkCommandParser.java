package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_NRIC;
import static seedu.address.logic.Messages.MESSAGE_NOT_ALPHANUMERIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String[] argParts = trimmedArgs.split("\\s+");

        if (argParts.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        //Parse the nric
        String nricString = argParts[0];
        Nric nric;
        try {
            nric = ParserUtil.parseNric(nricString);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_NRIC));
        }

        String remarkStringArgs = Arrays.stream(argParts, 1, argParts.length)
                .collect(Collectors.joining(" "));
        String expectedRemarkPrefix = remarkStringArgs.substring(0, 2);
        String expectedRemarkValue = remarkStringArgs.substring(2);

        if (!expectedRemarkPrefix.equals("r/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        if (!expectedRemarkValue.isEmpty()) {
            if (!expectedRemarkValue.matches("[a-zA-Z0-9 ]+")) {
                throw new ParseException(MESSAGE_NOT_ALPHANUMERIC + PREFIX_REMARK);
            }
        }

        return new RemarkCommand(nric, new Remark(expectedRemarkValue));
    }
}
