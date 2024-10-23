package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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

        List<String> indicesList = ArgumentTokenizer.tokenizeWithDefault(args);
        if (indicesList.isEmpty() || indicesList.stream().anyMatch(String::isEmpty)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }


        try {
            Set<Index> indices;
            Optional<Set<Index>> optionalIndices = parseIndicesForDelete(indicesList);
            assert optionalIndices.isPresent() : "Optional set of indices should not be empty or return null";
            indices = optionalIndices.get();
            return new DeleteCommand(indices);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments and returns an optional set of Index objects.
     * @param indices A collection of Strings representing indices
     * @return An optional set which contains Index objects
     * @throws ParseException if the user input does not conform the expected format
     */
    private Optional<Set<Index>> parseIndicesForDelete(Collection<String> indices) throws ParseException {
        assert indices != null;

        if (indices.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> indicesSet = indices.size() == 1 && indices.contains("") ? Collections.emptySet() : indices;
        return Optional.of(ParserUtil.parseIndices(indicesSet));
    }


}
