package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddFCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.Email;
import seedu.address.model.person.HealthRecord;
import seedu.address.model.person.HealthRisk;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddFCommandParser implements Parser<AddFCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX,
                PREFIX_BIRTHDATE, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_ALLERGY,
                PREFIX_BLOODTYPE, PREFIX_HEALTHRECORD, PREFIX_NOTE, PREFIX_NOKNAME, PREFIX_NOKPHONE,
                PREFIX_HEALTHRISK);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX, PREFIX_BIRTHDATE, PREFIX_EMAIL,
                PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX, PREFIX_BIRTHDATE,
                PREFIX_ADDRESS, PREFIX_ALLERGY, PREFIX_BLOODTYPE, PREFIX_EMAIL,
                PREFIX_HEALTHRECORD, PREFIX_HEALTHRISK, PREFIX_NOKNAME, PREFIX_NOKPHONE,
                PREFIX_NOTE, PREFIX_PHONE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Sex sex = ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).get());
        Birthdate birthDate = ParserUtil.parseBirthDate(argMultimap.getValue(PREFIX_BIRTHDATE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(""));
        Allergy allergy = ParserUtil.parseAllergy(argMultimap.getValue(PREFIX_ALLERGY).orElse(""));
        BloodType bloodType = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE).orElse(""));
        HealthRecord healthRecord = ParserUtil.parseHealthRecord(
                argMultimap.getValue(PREFIX_HEALTHRECORD).orElse(""));
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).orElse(""));
        HealthRisk healthRIsk = ParserUtil.parseHealthRisk(
                argMultimap.getValue(PREFIX_HEALTHRISK).orElse(""));
        Name nokName = ParserUtil.parseNokName(argMultimap.getValue(PREFIX_NOKNAME).orElse(""));
        Phone nokPhone = ParserUtil.parseNokPhone(argMultimap.getValue(PREFIX_NOKPHONE).orElse(""));
        List<Appt> appts = new ArrayList<>();

        Person person = new Person(name, nric, birthDate, sex, phone, email, address, allergy,
                bloodType, healthRIsk, healthRecord, note, nokName, nokPhone, appts);

        return new AddFCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
