package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHPAID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.ClassId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Fees;
import seedu.address.model.person.MonthPaid;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Unmarks an existing person in the address book as paid for one or several months.
 */
public class UnmarkPaidCommand extends Command {

    public static final String COMMAND_WORD = "unmarkpaid";
    public static final String COMMAND_WORD_ALIAS = "ump";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks the months paid for the person identified "
            + "by the index number used in the displayed person list. "
            + "Non existing months paid of that person will be ignored, no change happen.\n"
            + "Parameters: UnmarkPaidTarget (must be a positive integer index or 'all') "
            + "[" + PREFIX_MONTHPAID + "MONTHPAID]...\n"
            + "Example 1: " + COMMAND_WORD + " 1 " + PREFIX_MONTHPAID + "2024-01\n"
            + "Example 2: " + COMMAND_WORD + " all "
            + PREFIX_MONTHPAID + "2024-01"
            + PREFIX_MONTHPAID + "2024-12";

    public static final String MESSAGE_UNMARKPAID_PERSON_SUCCESS = "Unmarked person as paid: %1$s";
    public static final String MESSAGE_UNMARKPAID_ALL_SUCCESS = "Unmarked all displayed persons as paid for: %1$s";

    private final UnmarkPaidTarget target;
    private final Set<MonthPaid> monthsPaid;

    /**
     * @param target of the person in the filtered person list to edit
     * @param monthsPaid the months to unmark the person as paid.
     */
    public UnmarkPaidCommand(UnmarkPaidTarget target, Set<MonthPaid> monthsPaid) {
        requireNonNull(target);
        requireNonNull(monthsPaid);
        this.target = target;
        this.monthsPaid = monthsPaid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (target.getUnmarkAll()) {
            return executeAll(model);
        }

        return executeSingle(model);
    }

    private CommandResult executeAll(Model model) {
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Person person : lastShownList) {
            Person unmarkedPerson = createUnmarkedPerson(person, monthsPaid);
            model.setPerson(person, unmarkedPerson);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        String monthsPaidStr = monthsPaid.toString().replaceAll("^\\[|\\]$", "");
        return new CommandResult(String.format(MESSAGE_UNMARKPAID_ALL_SUCCESS, monthsPaidStr));
    }

    private CommandResult executeSingle(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = target.getIndex();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToUnmark = lastShownList.get(index.getZeroBased());
        Person unmarkedPerson = createUnmarkedPerson(personToUnmark, monthsPaid);
        model.setPerson(personToUnmark, unmarkedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_UNMARKPAID_PERSON_SUCCESS,
                Messages.markPaidFormat(unmarkedPerson)));
    }

    private static Person createUnmarkedPerson(Person personToUnmark, Set<MonthPaid> monthsPaid) {
        assert personToUnmark != null;
        assert monthsPaid != null;

        Name name = personToUnmark.getName();
        Phone phone = personToUnmark.getPhone();
        Email email = personToUnmark.getEmail();
        Address address = personToUnmark.getAddress();
        Fees fees = personToUnmark.getFees();
        ClassId classId = personToUnmark.getClassId();
        Set<MonthPaid> updatedMonthsPaid = new HashSet<>(personToUnmark.getMonthsPaid());
        updatedMonthsPaid.removeAll(monthsPaid);
        Set<Tag> tags = personToUnmark.getTags();

        return new Person(name, phone, email, address, fees, classId,
                updatedMonthsPaid, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof UnmarkPaidCommand)) {
            return false;
        }
        UnmarkPaidCommand otherUnmarkPaidCommand = (UnmarkPaidCommand) other;
        return target.equals(otherUnmarkPaidCommand.target)
                && monthsPaid.equals(otherUnmarkPaidCommand.monthsPaid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("target", target)
                .add("monthsPaid", monthsPaid)
                .toString();
    }

    /**
     * Represents the target of the unmark paid operation.
     */
    public static class UnmarkPaidTarget {
        private final Index index;
        private final boolean isUnmarkAll;

        private UnmarkPaidTarget(Index index, boolean isUnmarkAll) {
            this.index = index;
            this.isUnmarkAll = isUnmarkAll;
        }

        public static UnmarkPaidTarget fromIndex(Index index) {
            return new UnmarkPaidTarget(index, false);
        }

        public static UnmarkPaidTarget all() {
            return new UnmarkPaidTarget(null, true);
        }

        public boolean getUnmarkAll() {
            return isUnmarkAll;
        }

        public Index getIndex() {
            if (isUnmarkAll) {
                throw new IllegalStateException("Cannot get index when target is 'all'");
            }
            return index;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("unmarkAll", isUnmarkAll)
                    .add("index", isUnmarkAll ? "all" : index.getOneBased())
                    .toString();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof UnmarkPaidTarget)) {
                return false;
            }
            UnmarkPaidTarget otherTarget = (UnmarkPaidTarget) other;
            return isUnmarkAll == otherTarget.isUnmarkAll && index.equals(otherTarget.index);
        }
    }
}
