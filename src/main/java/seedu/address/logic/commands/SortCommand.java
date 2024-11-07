package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.JobCode;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;

/**
 * Sort all persons in address book by the field specified by user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    //To change later
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort based on listed criteria(s)\n"
            + "Allowed sorting criteria: Job Code, Tag, Name, Phone, Email\n"
            + "The sorting hierarchy is based on the order of criteria(s) provided. "
            + "List will be sorted by Name if no criteria given\n"
            + "Example 1: " + COMMAND_WORD + "\n"
            + "Example 2: " + COMMAND_WORD + " j/\n"
            + "Example 3: " + COMMAND_WORD + " t/\n"
            + "Example 4: " + COMMAND_WORD + " j/ t/ p/\n"
            + "Example 5: " + COMMAND_WORD + " t/ e/\n"
            + "Example 6: " + COMMAND_WORD + " t/ j/ n/ p/\n";


    private final List<Prefix> sortCriteria;

    public static final String MESSAGE_EMPTY = "Talentcy is empty. Please add contacts before attempting to sort.";

    public SortCommand(List<Prefix> sortCriteria) {
        this.sortCriteria = sortCriteria;
    }


    @Override
    public CommandResult execute(Model model) {

        // Build a default comparator
        Comparator<Person> personComparator = Comparator.comparing(Person::getName, Name.NAME_COMPARATOR);

        // Build a dynamic comparator based on the sortCriteria list
        for (Prefix criterion : this.sortCriteria) {
            personComparator = buildComparator(personComparator, criterion);
        }
        personComparator = personComparator.thenComparing(Person::getName, Name.NAME_COMPARATOR);

        // Sort the list with the dynamic comparator
        model.sortPersonList(personComparator);

        return new CommandResult(model.isListEmpty() ? MESSAGE_EMPTY
                : String.format("Contact list is sorted based on your selected criteria(s)\n%s",
                this.sortCriteria.isEmpty() ? "n/" :
                        this.sortCriteria.stream()
                                .map(Prefix::toString)
                                .collect(Collectors.joining(", "))));
    }

    private Comparator<Person> buildComparator(Comparator<Person> comparator, Prefix criterion) {

        if (criterion.equals(PREFIX_TAG)) {
            comparator =  this.sortCriteria.indexOf(criterion) == 0
                    ? Comparator.comparing(Person::getTag, Tag.TAG_COMPARATOR)
                    : comparator.thenComparing(Person::getTag, Tag.TAG_COMPARATOR);
        } else if (criterion.equals(PREFIX_JOBCODE)) {
            comparator = this.sortCriteria.indexOf(criterion) == 0
                    ? Comparator.comparing(Person::getJobCode, JobCode.JOBCODE_COMPARATOR)
                    : comparator.thenComparing(Person::getJobCode, JobCode.JOBCODE_COMPARATOR);
        } else if (criterion.equals(PREFIX_EMAIL)) {
            comparator = this.sortCriteria.indexOf(criterion) == 0
                    ? Comparator.comparing(Person::getEmail, Email.EMAIL_COMPARATOR)
                    : comparator.thenComparing(Person::getEmail, Email.EMAIL_COMPARATOR);
        } else if (criterion.equals(PREFIX_PHONE)) {
            comparator = this.sortCriteria.indexOf(criterion) == 0
                    ? Comparator.comparing(Person::getPhone, Phone.PHONE_COMPARATOR)
                    : comparator.thenComparing(Person::getPhone, Phone.PHONE_COMPARATOR);
        } else {
            comparator = this.sortCriteria.indexOf(criterion) == 0
                    ? Comparator.comparing(Person::getName, Name.NAME_COMPARATOR)
                    : comparator.thenComparing(Person::getName, Name.NAME_COMPARATOR);
        }

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


