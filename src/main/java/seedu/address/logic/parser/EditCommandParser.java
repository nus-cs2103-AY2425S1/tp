package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.COMMAND_FORMAT_PREAMBLE;
import static seedu.address.logic.Messages.LINE_BREAK;
import static seedu.address.logic.Messages.MESSAGE_HELP_PROMPT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX_OR_NAME;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_WAYS_FORBIDDEN;
import static seedu.address.logic.Messages.WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditContactDescriptor;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Role;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    public static final String MESSAGE_END_PART = COMMAND_FORMAT_PREAMBLE + WHITESPACE
            + EditCommand.MESSAGE_COMMAND_FORMAT + "\n"
            + String.format(MESSAGE_HELP_PROMPT,
            HelpCommand.COMMAND_WORD + " " + EditCommand.COMMAND_WORD);

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

        String str = argMultimap.getPreamble();
        if (str.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    "Missing index or Full name. " + LINE_BREAK + MESSAGE_END_PART));
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
        parseRolesForEdit(argMultimap.getAllValues(PREFIX_ROLE)).ifPresent(editContactDescriptor::setRoles);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_MISSING_PREFIX); // Fields missing or at least 1....in
            // edit or in general?
        }

        // parse the index to edit, else parse the name
        //boolean isIntegerIndex = true;
        //str.matches("^[0-9]$");

        try {
            index = ParserUtil.parseIndex(str);
            return new EditCommand(index, editContactDescriptor);
        } catch (ParseException pe) {
            assert index == null;
            // throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand
            // .MESSAGE_FUNCTION), pe);
        }

        try { // removed if (index == null) because it will always be true
            name = ParserUtil.parseName(str);
            return new EditCommand(name, editContactDescriptor);
        } catch (Exception ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_INVALID_INDEX_OR_NAME
                            + String.format(MESSAGE_MULTIPLE_WAYS_FORBIDDEN, EditCommand.COMMAND_WORD)));
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
