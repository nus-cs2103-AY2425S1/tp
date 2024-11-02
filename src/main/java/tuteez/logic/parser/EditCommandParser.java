package tuteez.logic.parser;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_PHONE;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;
import static tuteez.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.EditCommand;
import tuteez.logic.commands.EditCommand.EditPersonDescriptor;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.Address;
import tuteez.model.person.Email;
import tuteez.model.person.TelegramUsername;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TELEGRAM, PREFIX_TAG, PREFIX_LESSON);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TELEGRAM);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String email = argMultimap.getValue(PREFIX_EMAIL).get();
            setEditedEmail(editPersonDescriptor, email);
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String address = argMultimap.getValue(PREFIX_ADDRESS).get();
            setEditedAddress(editPersonDescriptor, address);
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            String telegramUsername = argMultimap.getValue(PREFIX_TELEGRAM).get();
            setEditedTelegramUsername(editPersonDescriptor, telegramUsername);
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        parseLessonsForEdit(argMultimap.getAllValues(PREFIX_LESSON)).ifPresent(editPersonDescriptor::setLessons);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    private void setEditedEmail(EditPersonDescriptor editPersonDescriptor, String email) throws ParseException {
        if (email.isEmpty()) {
            editPersonDescriptor.setEmail(new Email(null));
        } else {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(email));
        }
    }

    private void setEditedAddress(EditPersonDescriptor editPersonDescriptor, String address) throws ParseException {
        if (address.isEmpty()) {
            editPersonDescriptor.setAddress(new Address(null));
        } else {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(address));
        }
    }

    private void setEditedTelegramUsername(EditPersonDescriptor editPersonDescriptor, String telegramUsername)
            throws ParseException {
        if (telegramUsername.isEmpty()) {
            editPersonDescriptor.setTelegramUsername(TelegramUsername.empty());
        } else {
            editPersonDescriptor.setTelegramUsername(ParserUtil.parseTelegramUsername(telegramUsername));
        }
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
     * Parses {@code Collection<String> lessons} into a {@code Set<Lesson>} if {@code lessons} is non-empty.
     * If {@code lessons} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Lesson>} containing zero lessons.
     */
    private Optional<List<Lesson>> parseLessonsForEdit(Collection<String> lessons) throws ParseException {
        assert lessons != null;

        if (lessons.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> lessonLst = lessons.size() == 1 && lessons.contains("") ? Collections.emptySet() : lessons;
        return Optional.of(ParserUtil.parseLessons(lessonLst));
    }

}
