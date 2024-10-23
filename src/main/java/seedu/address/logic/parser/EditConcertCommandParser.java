package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditConcertCommand;
import seedu.address.logic.commands.EditConcertCommand.EditConcertDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditConcertCommand object.
 */
public class EditConcertCommandParser implements Parser<EditConcertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditConcertCommand
     * and returns an EditConcertCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditConcertCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_DATE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditConcertCommand.MESSAGE_USAGE), e);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_DATE);

        EditConcertDescriptor editConcertDescriptor = new EditConcertDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editConcertDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editConcertDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editConcertDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)
                    .get()));
        }

        if (!editConcertDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditConcertCommand.MESSAGE_NOT_EDITED);
        }

        return new EditConcertCommand(index, editConcertDescriptor);
    }
}
