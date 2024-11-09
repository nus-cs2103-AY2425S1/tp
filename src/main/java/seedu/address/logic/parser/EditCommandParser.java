package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NETID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.list.GroupList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STUDENTID,
                        PREFIX_NETID, PREFIX_MAJOR, PREFIX_GROUP, PREFIX_YEAR);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            String errorMessage = String.format("%s \n%s",
                    pe.getMessage(),
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

            throw new ParseException(String.format(errorMessage), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_STUDENTID, PREFIX_NETID,
                PREFIX_MAJOR, PREFIX_YEAR);


        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_STUDENTID).isPresent()) {
            editPersonDescriptor.setStudentId(ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENTID).get()));
        }
        if (argMultimap.getValue(PREFIX_NETID).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseOptionalNetId(argMultimap.getValue(PREFIX_NETID).get()));
        }
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            editPersonDescriptor.setYear(ParserUtil.parseOptionalYear(argMultimap.getValue(PREFIX_YEAR).get()));
        }
        if (argMultimap.getValue(PREFIX_MAJOR).isPresent()) {
            editPersonDescriptor.setMajor(ParserUtil.parseOptionalMajor(argMultimap.getValue(PREFIX_MAJOR).get()));
        }
        parseGroupsForEdit(argMultimap.getAllValues(PREFIX_GROUP)).ifPresent(editPersonDescriptor::setGroups);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        long inputGroups = argMultimap.countPrefixesOf(PREFIX_GROUP);

        if (inputGroups > 1 && editPersonDescriptor.getGroups().isPresent()
                && inputGroups > editPersonDescriptor.getGroups().get().size()) {
            throw new ParseException(Messages.MESSAGE_DUPLICATE_GROUPS);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> groups} into a {@code Set<Group>} if {@code groups} is non-empty.
     * If {@code groups} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Group>} containing zero groups.
     */
    private Optional<GroupList> parseGroupsForEdit(Collection<String> groups) throws ParseException {
        assert groups != null;

        if (groups.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> groupSet = groups.size() == 1 && groups.contains("") ? Collections.emptySet() : groups;
        return Optional.of(ParserUtil.parseGroups(groupSet));
    }

}
