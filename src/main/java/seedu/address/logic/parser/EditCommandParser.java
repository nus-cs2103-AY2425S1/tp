package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOC_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOC_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOC_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_TO_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_EMERGENCY_CONTACT_TO_EDIT,
                        PREFIX_EMERGENCY_CONTACT_NAME, PREFIX_EMERGENCY_CONTACT_PHONE,
                        PREFIX_EMERGENCY_CONTACT_RELATIONSHIP, PREFIX_DOC_NAME, PREFIX_DOC_PHONE, PREFIX_DOC_EMAIL,
                        PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_EMERGENCY_CONTACT_TO_EDIT,
                PREFIX_EMERGENCY_CONTACT_NAME, PREFIX_EMERGENCY_CONTACT_PHONE, PREFIX_EMERGENCY_CONTACT_RELATIONSHIP,
                PREFIX_DOC_NAME, PREFIX_DOC_PHONE, PREFIX_DOC_EMAIL);

        if (!isEmergencyContactIndexProvided(args, argMultimap)) {
            throw new ParseException(EditCommand.MESSAGE_EMERGENCY_CONTACT_FIELDS_INVALID);
        }

        if (!isEmergencyContactFieldsProvided(args, argMultimap)) {
            throw new ParseException(EditCommand.MESSAGE_EMERGENCY_CONTACT_NOT_EDITED);
        }

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
        if (argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_TO_EDIT).isPresent()) {
            editPersonDescriptor.setIndexOfEmergencyContactToEdit(
                    ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_TO_EDIT).get()));
        }
        if (argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_NAME).isPresent()) {
            editPersonDescriptor.setEmergencyContactName(
                    ParserUtil.parseName(argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_PHONE).isPresent()) {
            editPersonDescriptor.setEmergencyContactPhone(ParserUtil.parsePhone(
                    argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_RELATIONSHIP).isPresent()) {
            editPersonDescriptor.setEmergencyContactRelationship(ParserUtil.parseRelationship(
                    argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_RELATIONSHIP).get()));
        }
        if (argMultimap.getValue(PREFIX_DOC_NAME).isPresent()) {
            editPersonDescriptor.setDoctorName(ParserUtil.parseDoctorName(
                    argMultimap.getValue(PREFIX_DOC_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DOC_PHONE).isPresent()) {
            editPersonDescriptor.setDoctorPhone(ParserUtil.parsePhone(
                    argMultimap.getValue(PREFIX_DOC_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_DOC_EMAIL).isPresent()) {
            editPersonDescriptor.setDoctorEmail(ParserUtil.parseEmail(
                    argMultimap.getValue(PREFIX_DOC_EMAIL).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    private Boolean isEmergencyContactFieldsProvided(String args, ArgumentMultimap argMultimap) {
        if (argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_TO_EDIT).isPresent()
            && !argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_NAME).isPresent()
            && !argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_PHONE).isPresent()
            && !argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_RELATIONSHIP).isPresent()) {
            return false;
        }
        return true;
    }

    private Boolean isEmergencyContactIndexProvided(String args, ArgumentMultimap argMultimap) {
        if (!argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_TO_EDIT).isPresent()
               && (argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_NAME).isPresent()
                || argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_PHONE).isPresent()
                || argMultimap.getValue(PREFIX_EMERGENCY_CONTACT_RELATIONSHIP).isPresent())) {
            return false;
        }
        return true;
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
