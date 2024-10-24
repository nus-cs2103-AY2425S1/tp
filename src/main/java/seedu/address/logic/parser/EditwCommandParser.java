package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditwCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new EditwCommand object.
 */
public class EditwCommandParser implements Parser<EditwCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditwCommand
     * and returns an EditwCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditwCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_VENUE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditwCommand.MESSAGE_USAGE), pe);
        }

        EditwCommand.EditWeddingDescriptor editWeddingDescriptor = new EditwCommand.EditWeddingDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editWeddingDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editWeddingDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_VENUE).isPresent()) {
            editWeddingDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get()));
        }

        if (!editWeddingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditwCommand.MESSAGE_USAGE);
        }

        return new EditwCommand(index, editWeddingDescriptor);
    }
}
