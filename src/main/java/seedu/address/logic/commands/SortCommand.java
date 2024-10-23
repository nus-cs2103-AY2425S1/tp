package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
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
 * Sorts the list of contacts based on the attribute
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list based on the attribute\n"
            + "Attributes: name, phone, email, address, register number, sex, \n"
            + "student class, emergency contact name, emergency contact number";

    public static final String MESSAGE_SUCCESS = "Sorted the list based on %1$s";
    public static final String MESSAGE_SORT_ATTRIBUTE_NOT_SPECIFIED = "The sort attribute is not specified!";
    private final SortAttribute sortAttribute;

    /**
     * @param sortAttribute attribute to sort the persons in the list
     */
    public SortCommand(SortAttribute sortAttribute) {
        requireNonNull(sortAttribute);
        this.sortAttribute = sortAttribute;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Comparator<Person> comparator = this.getComparator();
        model.sortFilteredPersonList(comparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.sortAttribute));
    }


    /**
     * Retrieves the comparator needed to sort the list.
     * @throws CommandException if the comparator cannot be found.
     */
    public Comparator<Person> getComparator() throws CommandException {

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
            throw new CommandException(MESSAGE_SORT_ATTRIBUTE_NOT_SPECIFIED);
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

        SortCommand s = (SortCommand) other;
        return sortAttribute.equals(s.sortAttribute);
    }
}
