package seedu.address.logic.parser;

import java.util.Comparator;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments to create a new {@code SortCommand}.
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        Comparator<? extends Person> comparator;

        switch (trimmedArgs) {
        case "name" -> comparator = Comparator.comparing(Person::getNameString);
        case "subject" -> comparator = Comparator.comparing(Person::getSubjectString);
        case "classes" -> comparator = Comparator.comparing(Person::getClassesString);
        default -> throw new ParseException(SortCommand.MESSAGE_USAGE);
        }

        return new SortCommand(comparator);
    }
}
