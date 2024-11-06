package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_INCOMPLETE_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGISTER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.EcName;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RegisterNumber;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StudentClass;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format and if predicates are empty.
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_REGISTER_NUMBER, PREFIX_SEX, PREFIX_STUDENT_CLASS, PREFIX_ECNAME, PREFIX_ECNUMBER, PREFIX_TAG);

        List<String> names = new ArrayList<>();
        List<String> phones = new ArrayList<>();
        List<String> emails = new ArrayList<>();
        List<String> addresses = new ArrayList<>();
        List<String> registerNumbers = new ArrayList<>();
        List<String> sexes = new ArrayList<>();
        List<String> classes = new ArrayList<>();
        List<String> ecNames = new ArrayList<>();
        List<String> ecNumbers = new ArrayList<>();
        List<String> tags = new ArrayList<>();

        for (String rawName : FilterCommandParserHelper.parseNames(argMultimap)) {
            if (rawName.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            try {
                names.add(ParserUtil.parseName(rawName).toString());
            } catch (ParseException e) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }
        }

        for (String rawPhone : FilterCommandParserHelper.parsePhones(argMultimap)) {
            if (rawPhone.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            try {
                phones.add(ParserUtil.parsePhone(rawPhone).toString());
            } catch (ParseException e) {
                throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
            }
        }

        for (String rawEmail : FilterCommandParserHelper.parseEmails(argMultimap)) {
            if (rawEmail.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            try {
                emails.add(ParserUtil.parseEmail(rawEmail).toString());
            } catch (ParseException e) {
                throw new ParseException(Email.MESSAGE_CONSTRAINTS);
            }
        }

        for (String rawAddress : FilterCommandParserHelper.parseAddresses(argMultimap)) {
            if (rawAddress.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            try {
                addresses.add(ParserUtil.parseAddress(rawAddress).toString());
            } catch (ParseException e) {
                throw new ParseException(Address.MESSAGE_CONSTRAINTS);
            }
        }

        for (String rawRegisterNumber : FilterCommandParserHelper.parseRegisterNumbers(argMultimap)) {
            if (rawRegisterNumber.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            try {
                registerNumbers.add(ParserUtil.parseRegisterNumber(rawRegisterNumber).toString());
            } catch (ParseException e) {
                throw new ParseException(RegisterNumber.MESSAGE_CONSTRAINTS);
            }
        }

        for (String rawSex : FilterCommandParserHelper.parseSexes(argMultimap)) {
            if (rawSex.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            try {
                sexes.add(ParserUtil.parseSex(rawSex).toString());
            } catch (ParseException e) {
                throw new ParseException(Sex.MESSAGE_CONSTRAINTS);
            }
        }

        for (String rawClass : FilterCommandParserHelper.parseClasses(argMultimap)) {
            if (rawClass.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            try {
                classes.add(ParserUtil.parseStudentClass(rawClass).toString());
            } catch (ParseException e) {
                throw new ParseException(StudentClass.MESSAGE_CONSTRAINTS);
            }
        }

        for (String rawEcName : FilterCommandParserHelper.parseEcNames(argMultimap)) {
            if (rawEcName.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            try {
                ecNames.add(ParserUtil.parseEcName(rawEcName).toString());
            } catch (ParseException e) {
                throw new ParseException(EcName.MESSAGE_CONSTRAINTS);
            }
        }

        for (String rawEcNumber : FilterCommandParserHelper.parseEcNumbers(argMultimap)) {
            if (rawEcNumber.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            try {
                ecNumbers.add(ParserUtil.parseEcNumber(rawEcNumber).toString());
            } catch (ParseException e) {
                throw new ParseException(EcNumber.MESSAGE_CONSTRAINTS);
            }
        }

        for (String rawTag : FilterCommandParserHelper.parseTags(argMultimap)) {
            if (rawTag.isEmpty()) {
                throw new ParseException(MESSAGE_INCOMPLETE_COMMAND);
            }
            tags.add(rawTag);
        }

        // if all predicates are empty
        if (names.isEmpty() && phones.isEmpty() && emails.isEmpty() && addresses.isEmpty()
                && registerNumbers.isEmpty() && sexes.isEmpty() && classes.isEmpty()
                && ecNames.isEmpty() && ecNumbers.isEmpty() && tags.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        PersonPredicate predicate = new PersonPredicate(names, phones, emails, addresses, registerNumbers, sexes,
                classes, ecNames, ecNumbers, tags);

        return new FilterCommand(predicate);
    }
}
