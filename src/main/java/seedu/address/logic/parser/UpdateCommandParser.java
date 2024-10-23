package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AGE, PREFIX_GENDER,
                        PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_APPOINTMENT, PREFIX_TAG);

        // Get the NRIC or index from the preamble
        String preamble = argMultimap.getPreamble().trim();
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        // Parse the NRIC or index
        boolean isContainNric = Nric.isValidNric(preamble);
        Nric nric = null;
        Index index = null;


        try {
            if (isContainNric) {
                nric = ParserUtil.parseNric(preamble);
            } else {
                index = ParserUtil.parseIndex(preamble);
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_AGE, PREFIX_GENDER, PREFIX_NRIC,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_APPOINTMENT, PREFIX_ADDRESS, PREFIX_APPOINTMENT);

        // Create an UpdatePersonDescriptor from the arguments
        UpdateCommand.UpdatePersonDescriptor updatePersonDescriptor = new UpdatePersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            updatePersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            updatePersonDescriptor.setAge(ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get()));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            updatePersonDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            updatePersonDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            updatePersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            updatePersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            updatePersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_APPOINTMENT).isPresent()) {
            updatePersonDescriptor.setAppointment(ParserUtil
                    .parseAppointment(argMultimap.getValue(PREFIX_APPOINTMENT).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(updatePersonDescriptor::setTags);

        if (!updatePersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_EDITED);
        }

        // Return the appropriate command based on whether NRIC or name was used
        if (isContainNric) {
            return new UpdateCommand(nric, updatePersonDescriptor);
        } else {
            return new UpdateCommand(index, updatePersonDescriptor);
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

}
