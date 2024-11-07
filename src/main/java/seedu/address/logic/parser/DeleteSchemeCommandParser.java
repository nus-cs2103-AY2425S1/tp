package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteSchemeCommand;
import seedu.address.logic.commands.SchemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteSchemeCommand object
 */
public class DeleteSchemeCommandParser implements Parser<DeleteSchemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSchemeCommand
     * and returns a DeleteSchemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSchemeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_INDEX);
        Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SchemeCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_INDEX).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SchemeCommand.MESSAGE_USAGE));
        }

        String indexes = argMultimap.getValue(CliSyntax.PREFIX_INDEX).get();
        String[] indexArr = indexes.split(",");
        ArrayList<Index> schemeIndex = new ArrayList<>();
        for (String index : indexArr) {
            try {
                schemeIndex.add(ParserUtil.parseIndex(index));
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SchemeCommand.MESSAGE_USAGE), pe);
            }
        }
        return new DeleteSchemeCommand(personIndex, schemeIndex);
    }
}
