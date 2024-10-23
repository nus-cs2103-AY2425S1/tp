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
 * Marks an existing person in the address book as paid for a specified month.
 */
public class MarkPaidCommand extends Command {

    public static final String COMMAND_WORD = "markpaid";
    public static final String COMMAND_WORD_ALIAS = "mp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the months paid for the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing months paid of that person will be overwritten by input to this command.\n"
            + "Parameters: MarkPaidTarget (must be a positive integer index or 'all') "
            + "MONTHSPAID... (yyyy-mm format)\n"
            + "Example 1: " + COMMAND_WORD + " 1 " + PREFIX_MONTHPAID + "2024-01\n"
            + "Example 2: " + COMMAND_WORD + " all "
            + PREFIX_MONTHPAID + "2024-01"
            + PREFIX_MONTHPAID + "2024-12";

    public static final String MESSAGE_MARKPAID_PERSON_SUCCESS = "Marked person as paid: %1$s";
    public static final String MESSAGE_MARKPAID_ALL_SUCCESS = "Marked all displayed persons as paid for: %1$s";

    private final MarkPaidTarget target;
    private final Set<MonthPaid> monthsPaid;
    /**
     * @param target of the person in the filtered person list to edit
     * @param monthsPaid the month to mark the person as paid
     */
    public MarkPaidCommand(MarkPaidTarget target, Set<MonthPaid> monthsPaid) {
        requireNonNull(target);
        requireNonNull(monthsPaid);

        this.target = target;
        this.monthsPaid = monthsPaid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (target.getMarkAll()) {
            markAllPersons(lastShownList, monthsPaid, model);
            String monthsPaidStr = monthsPaid.toString().replaceAll("^\\[|\\]$", "");
            return new CommandResult(String.format(MESSAGE_MARKPAID_ALL_SUCCESS, monthsPaidStr));
        }

        return executeMarkSingle(model);
    }

    private void markAllPersons(List<Person> persons, Set<MonthPaid> monthsPaid, Model model) {
        for (Person person : persons) {
            Person markedPerson = createMarkedPerson(person, monthsPaid);
            model.setPerson(person, markedPerson);
        }
        model.updateFilteredPersonList(person -> person.getMonthsPaid().containsAll(monthsPaid));
    }
    private CommandResult executeMarkSingle(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = target.getIndex();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToMark = lastShownList.get(index.getZeroBased());
        Person markedPerson = createMarkedPerson(personToMark, monthsPaid);
        model.setPerson(personToMark, markedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARKPAID_PERSON_SUCCESS,
                Messages.markPaidFormat(markedPerson)));
    }
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * marked with {@code monthPaid}.
     */
    private static Person createMarkedPerson(Person personToMark, Set<MonthPaid> monthPaid) {
        assert personToMark != null;
        assert monthPaid != null;
        // TODO: should we use editPersonDescriptor here instead?
        Name name = personToMark.getName();
        Phone phone = personToMark.getPhone();
        Email email = personToMark.getEmail();
        Address address = personToMark.getAddress();
        Fees fees = personToMark.getFees();
        ClassId classId = personToMark.getClassId();
        Set<MonthPaid> existingMonthsPaid = personToMark.getMonthsPaid();
        Set<MonthPaid> combinedMonthsPaid = new HashSet<>(existingMonthsPaid);
        combinedMonthsPaid.addAll(monthPaid);
        Set<Tag> tags = personToMark.getTags();

        return new Person(name, phone, email, address, fees, classId,
                combinedMonthsPaid, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof MarkPaidCommand)) {
            return false;
        }
        MarkPaidCommand otherMarkPaidCommand = (MarkPaidCommand) other;
        return target.equals(otherMarkPaidCommand.target)
                && monthsPaid.equals(otherMarkPaidCommand.monthsPaid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("target", target)
                .add("monthsPaid", monthsPaid)
                .toString();
    }

    /**
     * Represents the target of the mark paid operation.
     */
    public static class MarkPaidTarget {
        private final Index index;
        private final boolean isMarkAll;

        private MarkPaidTarget(Index index, boolean isMarkAll) {
            this.index = index;
            this.isMarkAll = isMarkAll;
        }

        public static MarkPaidTarget fromIndex(Index index) {
            return new MarkPaidTarget(index, false);
        }

        public static MarkPaidTarget all() {
            return new MarkPaidTarget(null, true);
        }

        public boolean getMarkAll() {
            return isMarkAll;
        }

        public Index getIndex() {
            if (isMarkAll) {
                throw new IllegalStateException("Cannot get index when target is 'all'");
            }
            return index;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("markAll", isMarkAll)
                    .add("index", isMarkAll ? "all" : index.getOneBased())
                    .toString();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MarkPaidTarget)) {
                return false;
            }
            MarkPaidTarget otherTarget = (MarkPaidTarget) other;
            return isMarkAll == otherTarget.isMarkAll && index.equals(otherTarget.index);
        }
    }
}
