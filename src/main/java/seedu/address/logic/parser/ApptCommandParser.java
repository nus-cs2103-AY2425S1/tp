package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Name;

public class ApptCommandParser implements Parser<ApptCommand> {

    public ApptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_APPT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ApptCommand.MESSAGE_USAGE), ive);
        }

        Appt date = ParserUtil.parseAppt(argMultimap.getValue(PREFIX_APPT).get());
        Name name = new Name(index.getOneBased() + "");

        return new ApptCommand(date, name);
    }
}
