package seedu.eventfulnus.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eventfulnus.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.logic.commands.EditCommand;
import seedu.eventfulnus.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.eventfulnus.logic.parser.exceptions.ParseException;
import seedu.eventfulnus.model.person.role.Role;

/**
 * Parses input arguments and creates a new {@link EditCommand} object.
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        parseRolesForEdit(argMultimap.getAllValues(PREFIX_ROLE)).ifPresent(editPersonDescriptor::setRoles);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>} if {@code roles} is non-empty.
     * If {@code roles} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Role>} containing zero roles.
     */
    private Optional<Set<Role>> parseRolesForEdit(Collection<String> roles) throws ParseException {
        assert roles != null;

        if (roles.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> roleSet = roles.size() == 1 && roles.contains("") ? Collections.emptySet() : roles;
        return Optional.of(ParserUtil.parseRoles(roleSet));
    }
}
