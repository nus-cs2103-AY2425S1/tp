package seedu.address.logic.parser;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_OF_KIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.person.Address.EMPTY_ADDRESS;
import static seedu.address.model.person.DaysAttended.DEFAULT_INPUT_VALUE;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DaysAttended;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new AddStudentCommand object
 */
public class AddStudentCommandParser implements Parser<AddStudentCommand> {

    private static final String CLASS_NAME_VALIDATION_REGEX = "[A-Za-z0-9]+";
    private static final String MESSAGE_INVALID_CLASS = "Classes should be valid!";

    /**
     * Parses the given {@code String} of arguments in the context of the AddStudentCommand
     * and returns an AddStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_ADDRESS, PREFIX_TAG, PREFIX_SUBJECT, PREFIX_CLASSES, PREFIX_ATTENDANCE, PREFIX_NEXT_OF_KIN,
                        PREFIX_EMERGENCY_CONTACT);

        // Ensure all required prefixes are present
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_GENDER, PREFIX_EMAIL, PREFIX_SUBJECT,
                PREFIX_CLASSES, PREFIX_NEXT_OF_KIN, PREFIX_EMERGENCY_CONTACT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
        }

        // Parse individual components
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(EMPTY_ADDRESS));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<Subject> subjectList = ParserUtil.parseSubjects(argMultimap.getAllValues(PREFIX_SUBJECT));
        Set<String> classes = parseClasses(argMultimap.getValue(PREFIX_CLASSES).get());
        String attendanceString = argMultimap.getValue(PREFIX_ATTENDANCE).orElse(DEFAULT_INPUT_VALUE);
        int attendanceValue;
        try {
            attendanceValue = Integer.parseInt(attendanceString);
            if (attendanceValue < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new ParseException(DaysAttended.MESSAGE_CONSTRAINTS);
        }

        DaysAttended daysAttended = new DaysAttended(attendanceValue);
        Name nextOfKin = ParserUtil.parseName(argMultimap.getValue(PREFIX_NEXT_OF_KIN).get());
        Phone emergencyContact = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_EMERGENCY_CONTACT).get());

        // Create the Student object
        Student student = new Student(name, gender, phone, email, address, tagList, subjectList, classes, daysAttended,
                nextOfKin, emergencyContact);

        return new AddStudentCommand(student);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the classes string (comma-separated) and returns a set of class names.
     */
    public static Set<String> parseClasses(String classes) throws ParseException {
        requireNonNull(classes);

        Set<String> classSet = new HashSet<>();
        String[] classArray = classes.split(",");

        for (String className : classArray) {
            String trimmedClassName = className.trim(); // Trim to remove unnecessary spaces

            // Validate each class name
            if (!isValidClassName(trimmedClassName)) {
                throw new ParseException(MESSAGE_INVALID_CLASS);
            }

            classSet.add(trimmedClassName);
        }

        return classSet;
    }

    /**
     * Validates a class name.
     */
    public static boolean isValidClassName(String className) {
        return className.matches(CLASS_NAME_VALIDATION_REGEX);
    }
}
