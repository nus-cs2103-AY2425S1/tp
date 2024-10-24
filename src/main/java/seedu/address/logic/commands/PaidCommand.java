package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
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
public class PaidCommand extends Command {

    public static final String COMMAND_WORD = "paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the person identified by the index number to have made "
            + "payment and updates the policy renewal frequency.\n"
            + "Parameters: INDEX (must be a positive integer) " + " " + PREFIX_FREQUENCY + "FREQUENCY\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FREQUENCY + "3";

    public static final String MESSAGE_NO_FREQUENCY = "Please provide a policy renewal frequency";
    public static final String MESSAGE_PAID_PERSON_SUCCESS = "Marked Person: %1$s";
    private final Index index;
    private final PaidPersonDescriptor paidPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to mark as paid
     * @param paidPersonDescriptor person that has been marked as paid
     */
    public PaidCommand(Index index, PaidPersonDescriptor paidPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(paidPersonDescriptor);

        this.index = index;
        this.paidPersonDescriptor = new PaidPersonDescriptor(paidPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToPay = lastShownList.get(index.getZeroBased());
        Person paidPerson = createPaidPerson(personToPay, paidPersonDescriptor);

        model.setPerson(personToPay, paidPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_PAID_PERSON_SUCCESS, Messages.format(paidPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToPay}
     * edited with {@code paidPersonDescriptor}.
     */
    private static Person createPaidPerson(Person personToPay, PaidPersonDescriptor paidPersonDescriptor) {
        assert personToPay != null;

        Boolean updatedHasPaid = true;
        Frequency updatedFrequency = paidPersonDescriptor.getFrequency().orElse(personToPay.getFrequency());

        return new Person(personToPay.getName(), personToPay.getPhone(), personToPay.getEmail(),
                personToPay.getAddress(), personToPay.getBirthday(), personToPay.getTags(),
                updatedHasPaid, updatedFrequency);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaidCommand)) {
            return false;
        }

        PaidCommand otherPaidCommand = (PaidCommand) other;
        return index.equals(otherPaidCommand.index);
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
    public static class PaidPersonDescriptor {
        private Boolean hasPaid;
        private Frequency frequency;

        public PaidPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public PaidPersonDescriptor(PaidPersonDescriptor toCopy) {
            setHasPaid();
            setFrequency(toCopy.frequency);
        }

        public void setHasPaid() {
            this.hasPaid = true;
        }

        public Optional<Boolean> getHasPaid() {
            return Optional.ofNullable(hasPaid);
        }

        public void setFrequency(Frequency frequency) {
            this.frequency = frequency;
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

            PaidCommand.PaidPersonDescriptor otherPaidPersonDescriptor = (PaidCommand.PaidPersonDescriptor) other;
            return Objects.equals(hasPaid, otherPaidPersonDescriptor.hasPaid)
                   && Objects.equals(frequency, otherPaidPersonDescriptor.frequency);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("hasPaid", hasPaid)
                    .add("frequency", frequency)
                    .toString();
        }
    }
}
