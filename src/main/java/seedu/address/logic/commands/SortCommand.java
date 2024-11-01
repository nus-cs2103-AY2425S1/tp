package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.JobCode;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * Sort all persons in address book by the field specified by user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    //To change later
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort based on listed criteria\n"
            + "Example 1: " + COMMAND_WORD + "\n"
            + "Example 2: " + COMMAND_WORD + " j/\n"
            + "Example 3: " + COMMAND_WORD + " t/\n"
            + "Example 4: " + COMMAND_WORD + " j/ t/\n"
            + "Example 5: " + COMMAND_WORD + " t/ j/\n"
            + "Example 6: " + COMMAND_WORD + " t/\n";


    private final List<Prefix> sortCriteria;

    public SortCommand(List<Prefix> sortCriteria) {
        this.sortCriteria = sortCriteria;
    }


    @Override
    public CommandResult execute(Model model) {

        // Build a dynamic comparator based on the sortCriteria list
        Comparator<Person> personComparator = buildComparator(new ArrayList<>(sortCriteria));

        // Sort the list with the dynamic comparator
        model.sortPersonList(personComparator);

        return new CommandResult(String.format("List sorted based on your selected criteria(s)\n%s",
                this.sortCriteria.stream()
                        .map(Prefix::toString)  // Convert each Prefix to its String representation
                        .collect(Collectors.joining(", "))));
    }

    private Comparator<Person> buildComparator(List<Prefix> sortCriteria) {
        Comparator<Person> comparator = Comparator.comparing(Person::getName, Name.NAME_COMPARATOR);

        if (!sortCriteria.isEmpty() && sortCriteria.get(0).equals(PREFIX_TAG)) {
            comparator = Comparator.comparing(Person::getTag, Tag.TAG_COMPARATOR);
            sortCriteria.remove(0);
        } else if (!sortCriteria.isEmpty() && sortCriteria.get(0).equals(PREFIX_JOBCODE)) {
            comparator = Comparator.comparing(Person::getJobCode, JobCode.JOBCODE_COMPARATOR);
            sortCriteria.remove(0);
        }
        for (Prefix criterion : sortCriteria) {
            if (criterion.equals(PREFIX_JOBCODE)) {
                comparator = comparator.thenComparing(Person::getJobCode, JobCode.JOBCODE_COMPARATOR);
            } else if (criterion.equals(PREFIX_TAG)) {
                comparator = comparator.thenComparing(Person::getTag, Tag.TAG_COMPARATOR);
            } else { // Default case, we can assume the default comparator is by name
                comparator = comparator.thenComparing(Person::getName, Name.NAME_COMPARATOR);
            }
        }

        comparator = comparator.thenComparing(Person::getName, Name.NAME_COMPARATOR);

        return comparator;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.logic.commands.SortCommand)) {
            return false;
        }

        seedu.address.logic.commands.SortCommand otherSortCommand = (seedu.address.logic.commands
                .SortCommand) other;
        return sortCriteria.equals(otherSortCommand.sortCriteria);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sortCriteria", sortCriteria)
                .toString();
    }

}


