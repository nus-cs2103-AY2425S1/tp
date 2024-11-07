package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.skill.Skill;

/**
 * Parses input arguments and creates a new EditProjectCommand object
 */
public class EditProjectCommandParser implements Parser<EditProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditProjectCommand
     * and returns an EditProjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROJECT_ID,
                        PREFIX_PROJECT_NAME, PREFIX_SKILL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditProjectCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROJECT_ID, PREFIX_PROJECT_NAME);

        EditProjectCommand.EditProjectDescriptor editProjectDescriptor = new EditProjectCommand.EditProjectDescriptor();

        if (argMultimap.getValue(PREFIX_PROJECT_ID).isPresent()) {
            throw new ParseException(EditProjectCommand.MESSAGE_EDIT_PROJECT_ID);
        }
        if (argMultimap.getValue(PREFIX_PROJECT_NAME).isPresent()) {
            editProjectDescriptor.setName(ParserUtil.parseProjectName(argMultimap.getValue(PREFIX_PROJECT_NAME).get()));
        }
        parseSkillsForEdit(argMultimap.getAllValues(PREFIX_SKILL)).ifPresent(editProjectDescriptor::setSkills);

        if (!editProjectDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditProjectCommand.MESSAGE_NOT_EDITED);
        }

        return new EditProjectCommand(index, editProjectDescriptor);
    }

    /**
     * Parses {@code Collection<String> skills} into a {@code Set<Skill>} if {@code skills} is non-empty.
     * If {@code skills} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Skill>} containing zero skills.
     */
    private Optional<Set<Skill>> parseSkillsForEdit(Collection<String> skills) throws ParseException {
        assert skills != null;

        if (skills.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> skillSet = skills.size() == 1
                && skills.contains("") ? Collections.emptySet() : skills;
        return Optional.of(ParserUtil.parseSkills(skillSet));
    }
}
