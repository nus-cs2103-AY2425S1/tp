package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TAG;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.UpdateMemberCommand;
import hallpointer.address.logic.commands.UpdateMemberCommand.UpdateMemberDescriptor;
import hallpointer.address.logic.parser.exceptions.ParseException;
import hallpointer.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new UpdateMemberCommand object
 */
public class UpdateMemberCommandParser implements Parser<UpdateMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateMemberCommand
     * and returns an UpdateMemberCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UpdateMemberCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_ROOM, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateMemberCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_ROOM);

        UpdateMemberDescriptor updateMemberDescriptor = new UpdateMemberDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            updateMemberDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            updateMemberDescriptor.setTelegram(
                    ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get()));
        }
        if (argMultimap.getValue(PREFIX_ROOM).isPresent()) {
            updateMemberDescriptor.setRoom(ParserUtil.parseRoom(argMultimap.getValue(PREFIX_ROOM).get()));
        }
        parseTagsForUpdate(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(updateMemberDescriptor::setTags);

        if (!updateMemberDescriptor.isAnyFieldUpdated()) {
            throw new ParseException(UpdateMemberCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateMemberCommand(index, updateMemberDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForUpdate(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

