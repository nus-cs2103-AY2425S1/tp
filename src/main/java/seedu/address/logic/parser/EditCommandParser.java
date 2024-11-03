package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditContactDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Name;
import seedu.address.model.tag.Role;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TELEGRAM_HANDLE, PREFIX_EMAIL,
                        PREFIX_STUDENT_STATUS, PREFIX_ROLE, PREFIX_NICKNAME);

        Index index = null;
        String str = null;
        Name name = null;


        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TELEGRAM_HANDLE,
                PREFIX_EMAIL, PREFIX_STUDENT_STATUS, PREFIX_NICKNAME);

        EditCommand.EditContactDescriptor editContactDescriptor = new EditContactDescriptor();

        // for the future where we can apply polymorphism and not worry about .setName() .setHandle() etc
        /*
        List<Prefix> prefixList = List.of(PREFIX_NAME, PREFIX_TELEGRAM_HANDLE,
                PREFIX_EMAIL, PREFIX_STUDENT_STATUS, PREFIX_NICKNAME);

        prefixList.stream()
                .map(argMultimap::getValue) // Stream<Optional<String>>
                .map(x -> x.map(editContactDescriptor.set()))
         */

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editContactDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM_HANDLE).isPresent()) {
            editContactDescriptor.setTelegramHandle(ParserUtil
                    .parseTelegramHandle(argMultimap.getValue(PREFIX_TELEGRAM_HANDLE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editContactDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_STUDENT_STATUS).isPresent()) {
            editContactDescriptor.setStudentStatus(
                    ParserUtil.parseStudentStatus(argMultimap.getValue(PREFIX_STUDENT_STATUS).get()));
        }
        if (argMultimap.getValue(PREFIX_NICKNAME).isPresent()) {
            editContactDescriptor.setNickname(
                    ParserUtil.parseNickname(argMultimap.getValue(PREFIX_NICKNAME).get()));
        }
        parseRolesForEdit(argMultimap.getAllValues(PREFIX_ROLE)).ifPresent(editContactDescriptor::setRoles);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        // parse the index to edit, else parse the name
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            str = argMultimap.getPreamble();
            // throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }
        if (index == null) {
            try {
                name = ParserUtil.parseName(str);
            } catch (Exception ex) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), ex);
            }
            return new EditCommand(name, editContactDescriptor);
        }

        return new EditCommand(index, editContactDescriptor);
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
        return Optional.of(ParserUtil.parseRoles(roles));
    }
}
