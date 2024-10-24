package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.task.TaskList;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT,
                        PREFIX_ADDRESS, PREFIX_SUBJECT, PREFIX_LEVEL, PREFIX_LESSON_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMERGENCY_CONTACT, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        //Parse all arguments
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMERGENCY_CONTACT, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        EmergencyContact emergencyContact = ParserUtil
                .parseEmergencyContact(argMultimap.getValue(PREFIX_EMERGENCY_CONTACT)
                .get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Note note = new Note("");
        TaskList taskList = new TaskList();

        //Parse optional arguments
        Level level = new Level("NONE NONE");
        if (argMultimap.getValue(PREFIX_LEVEL).isPresent()) {
            level = ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get());
        }
        Set<Subject> subjectList = new HashSet<>();
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            if (argMultimap.getValue(PREFIX_LEVEL).isEmpty()) {
                throw new ParseException(Subject.MESSAGE_LEVEL_NEEDED);
            }
            subjectList = ParserUtil.parseSubjectsByLevel(level, argMultimap.getAllValues(PREFIX_SUBJECT));
        }
        Set<LessonTime> lessonTimes = ParserUtil.parseLessonTimes(argMultimap.getAllValues(PREFIX_LESSON_TIME));

        Person person = new Person(name, phone, emergencyContact, address, note, subjectList,
                level, taskList, lessonTimes);

        return new AddCommand(person);
    }



}
