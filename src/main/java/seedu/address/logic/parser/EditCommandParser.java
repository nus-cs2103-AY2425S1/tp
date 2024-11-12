package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.StudyGroupTag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EMAIL, PREFIX_GENDER,
                PREFIX_AGE, PREFIX_DETAIL, PREFIX_STUDY_GROUP_TAG, PREFIX_REMOVE_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EMAIL, PREFIX_GENDER, PREFIX_AGE, PREFIX_DETAIL);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editPersonDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            editPersonDescriptor.setAge(ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get()));
        }
        parseStudyGroupsForEdit(argMultimap.getAllValues(PREFIX_STUDY_GROUP_TAG))
                .ifPresent(editPersonDescriptor::setStudyGroupTags);
        parseStudyGroupsForEdit(argMultimap.getAllValues(PREFIX_REMOVE_TAG))
                .ifPresent(editPersonDescriptor::setTagsToRemove);
        if (argMultimap.getValue(PREFIX_DETAIL).isPresent()) {
            editPersonDescriptor.setDetail(ParserUtil.parseDetail(argMultimap.getValue(PREFIX_DETAIL).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> studyGroups} into a
     * {@code Set<StudyGroupTag>} if {@code studyGroups} is non-empty. If
     * {@code studyGroups} contain only one element which is an empty string, it
     * will be parsed into a {@code Set<StudyGroupTag>} containing zero study
     * groups.
     */
    private Optional<Set<StudyGroupTag>> parseStudyGroupsForEdit(Collection<String> studyGroups) throws ParseException {
        assert studyGroups != null;

        if (studyGroups.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> studyGroupTagSet = studyGroups.size() == 1 && studyGroups.contains("")
                ? Collections.emptySet()
                : studyGroups;
        return Optional.of(ParserUtil.parseStudyGroups(studyGroupTagSet));
    }

}
