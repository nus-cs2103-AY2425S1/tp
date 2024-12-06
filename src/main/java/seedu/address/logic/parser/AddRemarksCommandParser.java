package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_REMARK;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.InvalidIdException;
import seedu.address.logic.commands.AddRemarksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;


/**
 * Parses input arguments and adds notes to a Patient.
 */
public class AddRemarksCommandParser implements Parser<AddRemarksCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CreatePatientCommand
     * and returns an CreatePatientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRemarksCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_REMARK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemarksCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ID, PREFIX_REMARK);
        String remarkInput = argMultimap.getAllValues(PREFIX_REMARK).get(0);
        if (remarkInput.trim().isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_REMARK);
        }
        int patientId;
        try {
            patientId = ParserUtil.parsePersonId(argMultimap.getAllValues(PREFIX_ID).get(0));
        } catch (InvalidIdException e) {
            throw new ParseException(MESSAGE_INVALID_ID, e);
        }

        Remark remark = new Remark(argMultimap.getValue(PREFIX_REMARK).orElse(""));

        return new AddRemarksCommand(patientId, remark);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
