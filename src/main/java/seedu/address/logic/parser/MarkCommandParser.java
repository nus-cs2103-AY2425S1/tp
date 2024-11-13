package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;

import java.time.LocalDate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.person.Nric;



/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns an MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NOTES, PREFIX_DATE);

        String preamble = argMultimap.getPreamble();
        String notes = argMultimap.getValue(PREFIX_NOTES).orElse("");
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NOTES, PREFIX_DATE);

        ContactRecord contactRecord;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            contactRecord = new ContactRecord(date, notes);
        } else {
            contactRecord = ContactRecord.createCurrentRecord(notes);
        }

        if (ParserUtil.isParsingIndex(preamble)) {
            Index index = ParserUtil.parseIndex(preamble);
            return new MarkCommand(index, contactRecord);
        } else if (ParserUtil.isParsingNric(preamble)) {
            Nric nric = ParserUtil.parseNric(preamble);
            return new MarkCommand(nric, contactRecord);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
    }
}
