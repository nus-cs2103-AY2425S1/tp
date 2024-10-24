package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Nric;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_BIRTHDATE, PREFIX_SEX,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_HEALTHSERVICE, PREFIX_ADDRESS, PREFIX_BLOODTYPE,
                        PREFIX_NOKNAME, PREFIX_NOKPHONE, PREFIX_ALLERGY, PREFIX_HEALTHRISK, PREFIX_HEALTHRECORD,
                        PREFIX_APPOINTMENT, PREFIX_NOTE);

        Nric nric;

        try {
            nric = ParserUtil.parseNric(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.WRONG_NRIC), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_NRIC, PREFIX_BIRTHDATE, PREFIX_SEX, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_HEALTHSERVICE, PREFIX_ADDRESS, PREFIX_BLOODTYPE, PREFIX_NOKNAME, PREFIX_NOKPHONE,
                PREFIX_ALLERGY, PREFIX_HEALTHRISK, PREFIX_HEALTHRECORD, PREFIX_APPOINTMENT, PREFIX_NOTE);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editPersonDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_BIRTHDATE).isPresent()) {
            editPersonDescriptor.setBirthDate(ParserUtil.parseBirthDate(argMultimap.getValue(PREFIX_BIRTHDATE).get()));
        }
        if (argMultimap.getValue(PREFIX_SEX).isPresent()) {
            editPersonDescriptor.setSex(ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).get()));
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
        if (argMultimap.getValue(PREFIX_BLOODTYPE).isPresent()) {
            editPersonDescriptor.setBloodType(ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_NOKNAME).isPresent()) {
            editPersonDescriptor.setNokName(ParserUtil.parseNokName(argMultimap.getValue(PREFIX_NOKNAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NOKPHONE).isPresent()) {
            editPersonDescriptor.setNokPhone(ParserUtil.parseNokPhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_ALLERGY).isPresent()) {
            editPersonDescriptor.setAllergy(ParserUtil.parseAllergy(argMultimap.getValue(PREFIX_ALLERGY).get()));
        }
        if (argMultimap.getValue(PREFIX_HEALTHRISK).isPresent()) {
            editPersonDescriptor.setHealthRisk(ParserUtil.parseHealthRisk(
                    argMultimap.getValue(PREFIX_HEALTHRISK).get()));
        }
        if (argMultimap.getValue(PREFIX_HEALTHRECORD).isPresent()) {
            editPersonDescriptor.setHealthRecord(ParserUtil.parseHealthRecord(
                    argMultimap.getValue(PREFIX_HEALTHRECORD).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editPersonDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }
        parseHealthServicesForEdit(argMultimap.getAllValues(PREFIX_HEALTHSERVICE))
                .ifPresent(editPersonDescriptor::setHealthServices);
        parseApptsForEdit(argMultimap.getAllValues(PREFIX_APPOINTMENT)).ifPresent(editPersonDescriptor::setAppts);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(nric, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> HealthServices} into a {@code Set<HealthService>} if {@code healthServices} is
     * non-empty.
     * If {@code healthServices} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<HealthService>} containing zero health service.
     */
    private Optional<Set<HealthService>> parseHealthServicesForEdit(Collection<String> healthServices)
            throws ParseException {
        assert healthServices != null;

        if (healthServices.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> healthServicesSet = healthServices.size() == 1 && healthServices.contains("")
                ? Collections.emptySet() : healthServices;
        return Optional.of(ParserUtil.parseHealthServices(healthServicesSet));
    }

    /**
     * Parses {@code Collection<String> dateTime} into a {@code List<Appt>} if {@code dateTime} is non-empty.
     * If {@code dateTime} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Appt>} containing zero appointments.
     */
    public Optional<List<Appt>> parseApptsForEdit(Collection<String> dateTime) throws ParseException {
        assert dateTime != null;

        if (dateTime.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> datesList = dateTime.size() == 1 && dateTime.contains("")
                ? Collections.emptyList() : dateTime;
        return Optional.of(ParserUtil.parseAppts(datesList));
    }


}
