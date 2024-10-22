package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteGradeCommand object.
 */
public class DeleteGradeCommandParser implements Parser<DeleteGradeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGradeCommand
     * and returns a DeleteGradeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        if (argMultiMap.getValue(PREFIX_NAME).isEmpty() || argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE));
        }

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        Index index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        String testName = ParserUtil.parseTestName(argMultiMap.getValue(PREFIX_NAME).get());

        return new DeleteGradeCommand(index, testName);
    }
}
