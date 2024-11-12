package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.ParserUtil.SortAttribute;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.comparator.AddressCompare;
import seedu.address.model.person.comparator.EcNameCompare;
import seedu.address.model.person.comparator.EcNumberCompare;
import seedu.address.model.person.comparator.EmailCompare;
import seedu.address.model.person.comparator.NameCompare;
import seedu.address.model.person.comparator.PhoneCompare;
import seedu.address.model.person.comparator.RegisterNumberCompare;
import seedu.address.model.person.comparator.SexCompare;
import seedu.address.model.person.comparator.StudentClassCompare;

/**
 * Sorts the list of students based on the specified attribute in the displayed list.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list based on the attribute\n"
            + "Attributes: name, phone, email, address, register number, sex, \n"
            + "student class, emergency contact name, emergency contact number";
    public static final String MESSAGE_SORTED_SUCCESS = "Sorted the list based on %1$s";
    public static final String MESSAGE_UNSORTED_SUCCESS = "Your list is now unsorted and restored to before!";

    private static final Logger logger = LogsCenter.getLogger(SortCommand.class);

    private final SortAttribute sortAttribute;
    private Comparator<Person> comparator;


    /**
     * @param sortAttribute attribute to sort the persons in the list.
     */
    public SortCommand(SortAttribute sortAttribute) {
        requireNonNull(sortAttribute);

        this.sortAttribute = sortAttribute;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        this.comparator = this.getComparator();

        model.sortFilteredPersonList(this.comparator);
        logger.log(Level.INFO, "SortCommand has been executed.");
        return new CommandResult(this.generateSuccessMessage());
    }

    /**
     * Generates a command execution success message based on whether
     * the list is sorted or restored to its unsorted state.
     */
    private String generateSuccessMessage() {
        return isNull(comparator) ? MESSAGE_UNSORTED_SUCCESS : String.format(MESSAGE_SORTED_SUCCESS, sortAttribute);
    }

    /**
     * Returns the comparator needed to sort the list. Returns null to restore the unsorted list.
     */
    private Comparator<Person> getComparator() {
        assert sortAttribute != null;

        switch (sortAttribute) {

        case NAME:
            return new NameCompare();

        case PHONE:
            return new PhoneCompare();

        case EMAIL:
            return new EmailCompare();

        case ADDRESS:
            return new AddressCompare();

        case REGISTERNUMBER:
            return new RegisterNumberCompare();

        case SEX:
            return new SexCompare();

        case STUDENTCLASS:
            return new StudentClassCompare();

        case EMERGENCYCONTACTNAME:
            return new EcNameCompare();

        case EMERGENCYCONTACTNUMBER:
            return new EcNumberCompare();

        default:
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return sortAttribute.equals(otherSortCommand.sortAttribute);
    }
}
