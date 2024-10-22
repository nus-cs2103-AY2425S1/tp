package seedu.address.logic.parser.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.wedding.EditWeddingCommand;
import seedu.address.logic.commands.wedding.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditWeddingCommandParser implements Parser<EditWeddingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditWeddingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING, PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEDDING, PREFIX_ADDRESS);

        EditWeddingDescriptor editWeddingDescriptor = new EditWeddingDescriptor();

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editWeddingDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse("")));
        }

        parseWeddingsForEdit(argMultimap.getAllValues(PREFIX_WEDDING)).ifPresent(editWeddingDescriptor::setWeddings);

        if (!editWeddingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditWeddingCommand(index, editWeddingDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> weddings} into a {@code Set<Wedding>} if {@code weddings} is non-empty.
     * If {@code weddings} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Wedding>} containing zero tags.
     */
    private Optional<Set<Wedding>> parseWeddingsForEdit(Collection<String> weddings) throws ParseException {
        assert weddings != null;

        if (weddings.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> weddingSet = weddings.size() == 1 && weddings.contains("")
                ? Collections.emptySet() : weddings;
        return Optional.of(ParserUtil.parseWeddings(weddingSet));
    }
}
