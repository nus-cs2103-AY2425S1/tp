package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.Arrays;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameMatchesKeywordPredicate;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        // Check for invalid format where prefix immediately follows number
        String trimmedArgs = args.trim();
        if (trimmedArgs.matches("\\d+" + PREFIX_WEDDING.getPrefix() + ".*")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_WEDDING);

        Index personIndex = null;
        NameMatchesKeywordPredicate predicate = null;

        try {
            String target = argMultimap.getPreamble();

            if (target.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            // Determine if the target is an personIndex or a name
            if (isNumeric(target)) {
                personIndex = ParserUtil.parseIndex(target);
            } else {
                String[] nameKeywords = target.split("\\s+");
                predicate = new NameMatchesKeywordPredicate(Arrays.asList(nameKeywords));
            }

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }

        boolean isDeleteAssignWeddings = !argMultimap.getAllValues(PREFIX_WEDDING).isEmpty();

        if (isDeleteAssignWeddings) {
            Set<Index> weddingIndices = ParserUtil.parseWeddingJobs(argMultimap.getAllValues(PREFIX_WEDDING));
            return new DeleteCommand(personIndex, predicate, weddingIndices);
        } else {
            // delete persons only
            return new DeleteCommand(personIndex, predicate, null);
        }
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+");
    }

}
