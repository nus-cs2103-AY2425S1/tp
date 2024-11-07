package seedu.address.logic.parser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.ClassComparatorUtil;
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
        case "class" -> comparator = Comparator.comparing(person -> {
            List<String> classes = Collections.singletonList(person.getClasses().toString());
            return ClassComparatorUtil.getPrimaryClassForSorting(classes);
        });
        default -> throw new ParseException(SortCommand.MESSAGE_USAGE);
        }

        return new SortCommand(comparator);
    }
}
