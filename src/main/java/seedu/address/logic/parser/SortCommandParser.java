package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;
import java.util.Comparator;

import seedu.address.commons.util.ComparatorUtil;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        Comparator<Person> comparator;

        switch (trimmedArgs) {
        case "name" -> comparator = Comparator.comparing(person -> person.getName().toString());
        case "subject" -> comparator = Comparator.comparing(person -> person.getSubjects().toString());
        case "class" -> comparator = Comparator.comparing(person ->
                ComparatorUtil.getPrimaryClassForSorting(Collections.singletonList(person.getClasses().toString()))
        );
        case "attendance" -> comparator = Comparator.comparing(
                Person::getDaysAttended,
                ComparatorUtil.getDaysAttendedComparator()
        );
        default -> throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(comparator);
    }
}
