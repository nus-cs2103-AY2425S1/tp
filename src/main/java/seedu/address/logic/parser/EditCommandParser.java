package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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
import seedu.address.model.contact.Role;

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

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TELEGRAM_HANDLE,
                PREFIX_EMAIL, PREFIX_STUDENT_STATUS, PREFIX_NICKNAME);

        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();

        Index index = null;
        Name name = null;

        String strPreamble = argMultimap.getPreamble();
        if (strPreamble.isEmpty()) {
            throw new ParseException(EditCommand.MESSAGE_MISSING_INDEX_OR_FULL_NAME);
        }

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
        parseRolesForEdit(argMultimap.getAllValues(PREFIX_ROLE)).ifPresent(editContactDescriptor::setAndSortRoles);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_MISSING_PREFIX); // Fields missing or at least 1....
        }

        // parse the index to edit, else parse the name
        final String regexNumber = "^-*?[0-9]+$";
        try {
            index = ParserUtil.parseIndex(strPreamble);
            return new EditCommand(index, editContactDescriptor);
        } catch (ParseException pe) {
            assert index == null;
            if (isInteger(strPreamble) || strPreamble.matches(regexNumber)
                    /*|| strPreamble.matches("^[^a-zA-Z]*$")*/) {
                throw new ParseException(pe.getMessage());
                // -1, 0
            }
            return createEditCommandByName(strPreamble, editContactDescriptor);
        }
    }

    private EditCommand createEditCommandByName(String strPreamble, EditContactDescriptor editContactDescriptor)
            throws ParseException {
        try {
            Name name = ParserUtil.parseName(strPreamble);
            return new EditCommand(name, editContactDescriptor);
        } catch (Exception exp) { // to try
            throw new ParseException("Invalid name field.\nRefer to user guide for valid name fields");
            // considered invalid name if it isn't an Integer
        }
    }

    private boolean isInteger(String args) {
        try {
            Integer.parseInt(args); // can pass negative integers as well
            return true;
        } catch (Exception exp) {
            return false;
        }
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
