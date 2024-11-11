package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ComparatorUtil;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        Comparator<Person> comparator;
        logger.info("Starting to parse sort command.");

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

        logger.info("Sort command parsed successfully.");
        return new SortCommand(comparator);
    }
}
