package seedu.address.logic.parser.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.wedding.EditWeddingCommand;
import seedu.address.logic.commands.wedding.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditWeddingCommand object
 */
public class EditWeddingCommandParser implements Parser<EditWeddingCommand> {
    private final Prefix[] weddingPrefixes = new Prefix[] {
        PREFIX_WEDDING,
        PREFIX_ADDRESS,
        PREFIX_DATE
    };

    /**
     * Parses the given {@code String} of arguments in the context of the EditWeddingCommand
     * and returns an EditWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditWeddingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, weddingPrefixes);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeddingCommand.MESSAGE_USAGE), pe);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(weddingPrefixes);

        EditWeddingDescriptor editWeddingDescriptor = new EditWeddingDescriptor();
        //Changes the name of the wedding
        if (argMultimap.getValue(PREFIX_WEDDING).isPresent()) {
            editWeddingDescriptor.setWeddingName(
                    ParserUtil.parseWeddingName(
                            argMultimap.getValue(PREFIX_WEDDING)
                                    .orElse(""))
            );
        }
        //Changes location/address of wedding
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editWeddingDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse("")));
        }
        //Changes date of wedding
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElse(""));
            ParserUtil.validateDateFormat(date);
            editWeddingDescriptor.setDate(date);
        }
        //if no edits, throw error
        if (!editWeddingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(Messages.MESSAGE_WEDDING_NOT_EDITED);
        }
        return new EditWeddingCommand(index, editWeddingDescriptor);
    }
}
