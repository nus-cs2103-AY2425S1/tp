package tahub.contacts.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_NAME;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_PHONE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import tahub.contacts.logic.commands.person.PersonEditCommand;
import tahub.contacts.logic.commands.person.PersonEditCommand.EditPersonDescriptor;
import tahub.contacts.logic.parser.ArgumentMultimap;
import tahub.contacts.logic.parser.ArgumentTokenizer;
import tahub.contacts.logic.parser.Parser;
import tahub.contacts.logic.parser.ParserUtil;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.tag.Tag;

/**
 * Parses input arguments and creates a new PersonEditCommand object
 */
public class PersonEditCommandParser implements Parser<PersonEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PersonEditCommand
     * and returns an PersonEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PersonEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MATRICULATION_NUMBER, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MATRICULATION_NUMBER, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS);

        if (argMultimap.getValue(PREFIX_MATRICULATION_NUMBER).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PersonEditCommand.MESSAGE_USAGE));
        }

        MatriculationNumber matriculationNumber = ParserUtil.parseMatriculationNumber(
                argMultimap.getValue(PREFIX_MATRICULATION_NUMBER).get());

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
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }


        editPersonDescriptor.setTags(parseTagsForEdit(argMultimap
                .getAllValues(PREFIX_TAG)).orElse(null));

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(PersonEditCommand.MESSAGE_NOT_EDITED);
        }

        return new PersonEditCommand(matriculationNumber, editPersonDescriptor);
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

}
