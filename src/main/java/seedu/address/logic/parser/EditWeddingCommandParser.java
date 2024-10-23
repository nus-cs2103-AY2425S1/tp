package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditWeddingCommand;
import seedu.address.logic.commands.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Edit Wedding command parser class
 */
public class EditWeddingCommandParser implements Parser<EditWeddingCommand> {
    public EditWeddingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeddingCommand.MESSAGE_USAGE),
                    pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE);

        EditWeddingDescriptor editWeddingDescriptor = new EditWeddingDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editWeddingDescriptor.setWeddingName(ParserUtil.parseWeddingName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editWeddingDescriptor.setWeddingDate(ParserUtil.parseWeddingDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (!editWeddingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditWeddingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditWeddingCommand(index, editWeddingDescriptor);

    }
}
