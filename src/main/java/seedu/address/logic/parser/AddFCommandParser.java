package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentTokenizer.checkPrefixPresentAndValidPrefix;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXISTINGCONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddFCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.AllergyList;
import seedu.address.model.patient.ApptList;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.ExistingCondition;
import seedu.address.model.patient.HealthRisk;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Note;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Sex;


/**
 * Parses input arguments and creates a new AddFCommand object
 */
public class AddFCommandParser implements Parser<AddFCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddFCommand
     * and returns an AddFCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFCommand parse(String args) throws ParseException {

        assert args != null : "Argument cannot be null";

        checkPrefixPresentAndValidPrefix(args, AddFCommand.MESSAGE_USAGE, PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX,
                PREFIX_BIRTHDATE, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_ALLERGY,
                PREFIX_BLOODTYPE, PREFIX_EXISTINGCONDITION, PREFIX_NOTE, PREFIX_NOKNAME, PREFIX_NOKPHONE,
                PREFIX_HEALTHRISK);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX,
                PREFIX_BIRTHDATE, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_ALLERGY,
                PREFIX_BLOODTYPE, PREFIX_EXISTINGCONDITION, PREFIX_NOTE, PREFIX_NOKNAME, PREFIX_NOKPHONE,
                PREFIX_HEALTHRISK);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX, PREFIX_BIRTHDATE,
                PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX, PREFIX_BIRTHDATE,
                PREFIX_ADDRESS, PREFIX_BLOODTYPE, PREFIX_EMAIL,
                PREFIX_EXISTINGCONDITION, PREFIX_HEALTHRISK, PREFIX_NOKNAME, PREFIX_NOKPHONE,
                PREFIX_NOTE, PREFIX_PHONE);

        //Parse out each field to add to patient
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Sex sex = ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).get());
        Birthdate birthDate = ParserUtil.parseBirthDate(argMultimap.getValue(PREFIX_BIRTHDATE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(""));
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(""));
        BloodType bloodType = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE).orElse(""));
        ExistingCondition existingCondition = ParserUtil.parseExistingCondition(
                argMultimap.getValue(PREFIX_EXISTINGCONDITION).orElse(""));
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).orElse(""));
        HealthRisk healthRIsk = ParserUtil.parseHealthRisk(argMultimap.getValue(PREFIX_HEALTHRISK).orElse(""));
        Name nokName = ParserUtil.parseNokName(argMultimap.getValue(PREFIX_NOKNAME).orElse(""));
        Phone nokPhone = ParserUtil.parseNokPhone(argMultimap.getValue(PREFIX_NOKPHONE).orElse(""));
        AllergyList allergies = ParserUtil.parseAllergies(argMultimap.getAllValues(PREFIX_ALLERGY));
        ApptList appts = new ApptList();

        //Create the new patient object
        Patient patient = new Patient(name, nric, birthDate, sex, phone, email, address, allergies,
                bloodType, healthRIsk, existingCondition, note, nokName, nokPhone, appts);

        return new AddFCommand(patient);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
