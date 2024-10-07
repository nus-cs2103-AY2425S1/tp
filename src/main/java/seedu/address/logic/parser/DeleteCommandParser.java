package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    public static final String MESSAGE_EMPTY_INDEX = "Index is not provided.";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DELETE_INDEX);

        if (argMultimap.arePrefixesPresent(PREFIX_DELETE_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        try {

            Set<Index> indices;
            Optional<Set<Index>> optionalIndices = parseIndicesForDelete(argMultimap.getAllValues(PREFIX_DELETE_INDEX));
            if (optionalIndices.isPresent()) {
                indices = optionalIndices.get();
                return new DeleteCommand(indices);
            } else {
                throw new ParseException(MESSAGE_EMPTY_INDEX);
            }

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    private Optional<Set<Index>> parseIndicesForDelete(Collection<String> indices) throws ParseException {
        assert indices != null;

        if (indices.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> indicesSet = indices.size() == 1 && indices.contains("") ? Collections.emptySet() : indices;
        return Optional.of(ParserUtil.parseIndices(indicesSet));
    }

}
