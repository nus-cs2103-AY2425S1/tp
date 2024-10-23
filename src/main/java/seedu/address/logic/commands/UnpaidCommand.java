package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Frequency;
import seedu.address.model.person.Person;

/**
 * Marks the person identified by the index number to have made payment.
 */
public class UnpaidCommand extends Command {

    public static final String COMMAND_WORD = "unpaid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the person identified by the index number to have "
            + "not made payment and updates the policy renewal frequency to zero.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPAID_PERSON_SUCCESS = "Marked Person: %1$s";

    private final Index index;

    private final UnpaidPersonDescriptor unpaidPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to mark as paid
     * @param unpaidPersonDescriptor person that has been marked as paid
     */
    public UnpaidCommand(Index index, UnpaidPersonDescriptor unpaidPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(unpaidPersonDescriptor);

        this.index = index;
        this.unpaidPersonDescriptor = new UnpaidPersonDescriptor(unpaidPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person originalPerson = lastShownList.get(index.getZeroBased());
        Person unpaidPerson = createUnpaidPerson(originalPerson, unpaidPersonDescriptor);

        model.setPerson(originalPerson, unpaidPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_UNPAID_PERSON_SUCCESS, Messages.format(unpaidPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToPay}
     * edited with {@code paidPersonDescriptor}.
     */
    private static Person createUnpaidPerson(Person personToPay, UnpaidPersonDescriptor unpaidPersonDescriptor) {
        assert personToPay != null;

        Boolean updatedHasNotPaid = false;
        Frequency updatedFrequency = new Frequency("0");

        return new Person(personToPay.getName(), personToPay.getPhone(), personToPay.getEmail(),
                personToPay.getAddress(), personToPay.getBirthday(),
                personToPay.getTags(), updatedHasNotPaid, updatedFrequency);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UnpaidCommand)) {
            return false;
        }
        UnpaidCommand otherCommand = (UnpaidCommand) other;
        return index.equals(otherCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }

    /**
     * Stores the details for person to mark as paid. Same as the nested class in EditCommand but this
     * only changes the hasPaid variable
     */
    public static class UnpaidPersonDescriptor {
        private Boolean hasPaid;
        private Frequency frequency;

        /**
         * Constructor for UnpaidPersonDescriptor.
         */
        public UnpaidPersonDescriptor() {
            setHasNotPaid();
            setFrequencyToZero();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UnpaidPersonDescriptor(UnpaidPersonDescriptor toCopy) {
            setHasNotPaid();
            setFrequencyToZero();
        }

        public void setHasNotPaid() {
            this.hasPaid = false;
        }

        public Optional<Boolean> getHasPaid() {
            return Optional.ofNullable(hasPaid);
        }
        public void setFrequencyToZero() {
            this.frequency = new Frequency("0");
        }
        public Optional<Frequency> getFrequency() {
            return Optional.ofNullable(frequency);
        }


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof PaidCommand.PaidPersonDescriptor)) {
                return false;
            }

            UnpaidCommand.UnpaidPersonDescriptor otherUnpaidPersonDescriptor =
                    (UnpaidCommand.UnpaidPersonDescriptor) other;
            return Objects.equals(hasPaid, otherUnpaidPersonDescriptor.hasPaid)
                    && Objects.equals(frequency, otherUnpaidPersonDescriptor.frequency);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("hasNotPaid", hasPaid)
                    .add("frequency", frequency)
                    .toString();
        }
    }
}
