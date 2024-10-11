package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.ClassId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Fees;
import seedu.address.model.person.MonthsPaid;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Marks an existing person in the address book as paid for a specified month.
 */
public class MarkpaidCommand extends Command {

    public static final String COMMAND_WORD = "markpaid";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the months paid for the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing months paid of that person will be overwritten by input to this command.\n"
            + "Parameters: INDEX (must be a positive integer) MONTHSPAID... (yyyy-mm format)\n"
            + "Example: " + COMMAND_WORD + " 1 2024-01 2024-02";

    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked Person: %1$s";
    private final Index index;
    private final MonthsPaid monthsPaid;

    /**
     * @param index of the person in the filtered person list to edit
     * @param monthPaid the month to mark the person as paid
     */
    public MarkpaidCommand(Index index, MonthsPaid monthsPaid) {
        requireNonNull(index);
        requireNonNull(monthsPaid);

        this.index = index;
        this.monthsPaid = monthsPaid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMark = lastShownList.get(index.getZeroBased());
        Person markedPerson = createMarkedPerson(personToMark, monthsPaid);

        model.setPerson(personToMark, markedPerson);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS, Messages.format(markedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * marked with {@code monthPaid}.
     */
    private static Person createMarkedPerson(Person personToMark, MonthsPaid monthsPaid) {
        assert personToMark != null;

        Name name = personToMark.getName();
        Phone phone = personToMark.getPhone();
        Email email = personToMark.getEmail();
        Address address = personToMark.getAddress();
        Fees fees = personToMark.getFees();
        ClassId classId = personToMark.getClassId();
        MonthsPaid markedMonthsPaid = personToMark.getMonthsPaid().concatenate(monthsPaid);
        Set<Tag> tags = personToMark.getTags();

        return new Person(name, phone, email, address, fees, classId,
                markedMonthsPaid, tags);
    }
}
