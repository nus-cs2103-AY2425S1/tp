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
 * Marks an existings person in the address book as paid for a specific month.
 */
public class MarkpaidCommand extends Command {

    public static final String COMMAND_WORD = "markpaid";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks paid for the person identified "
            + "by the index number used in the displayed person list"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) MONTHPAID (must be in yyyy-mm format)\n"
            + "Example: " + COMMAND_WORD + " 1 2024-01";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Marked Person: %1$s";

    private final Index index;
    private final String monthPaid;

    /**
     * @param index of the person in the filtered person list to edit
     * @param monthPaid the month to mark the person as paid
     */
    public MarkpaidCommand(Index index, String monthPaid) {
        requireNonNull(index);
        requireNonNull(monthPaid);

        this.index = index;
        this.monthPaid = monthPaid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMark = lastShownList.get(index.getZeroBased());
        Person markedPerson = createMarkedPerson(personToMark, monthPaid);

        model.setPerson(personToMark, markedPerson);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(markedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * marked with {@code monthPaid}.
     */
    private static Person createMarkedPerson(Person personToMark, String monthPaid) {
        assert personToMark != null;

        Name name = personToMark.getName();
        Phone phone = personToMark.getPhone();
        Email email = personToMark.getEmail();
        Address address = personToMark.getAddress();
        Fees fees = personToMark.getFees();
        ClassId classId = personToMark.getClassId();
        MonthsPaid markedMonthsPaid = new MonthsPaid(personToMark.getMonthsPaid() + " " + monthPaid);
        Set<Tag> tags = personToMark.getTags();

        return new Person(name, phone, email, address, fees, classId,
                markedMonthsPaid, tags);
    }
}
