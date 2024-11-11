package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Fees;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;

/**
 * Marks the payment for a person as true
 */
public class MarkPaidCommand extends Command {
    public static final String COMMAND_WORD = "markpaid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Records amount of fees the student has paid\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PAYMENT + "PAYMENT\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PAYMENT + "200";
    public static final String MESSAGE_MARKED_PAID_SUCCESS = "Fees updated for student %1$s \n%2$s";

    private static final Logger logger = LogsCenter.getLogger(MarkPaidCommand.class);
    private final Index targetIndex;
    private final Fees feesPaid;

    /**
     * Marks whether an existing person has paid their feesPaidByStudent for the month
     * @param targetIndex index of the student to mark payment based on the displayed list
     */
    public MarkPaidCommand(Index targetIndex, Fees feesPaid) {
        this.targetIndex = targetIndex;
        this.feesPaid = feesPaid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, MarkPaidCommand.class));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMarkPayment = lastShownList.get(targetIndex.getZeroBased());
        Payment updatedPayment = calculatePayment(personToMarkPayment.getPayment(), feesPaid);

        Person markedPerson = new Person(personToMarkPayment.getName(), personToMarkPayment.getPhone(),
                personToMarkPayment.getEmail(), personToMarkPayment.getAddress(),
                updatedPayment, personToMarkPayment.getTags());

        updateParticipations(model, personToMarkPayment, markedPerson);

        model.setPerson(personToMarkPayment, markedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARKED_PAID_SUCCESS, markedPerson.getFullName(),
                markedPerson.getPayment().toString()));
    }

    private void updateParticipations(
            Model model, Person originalStudent, Person updatedStudent) throws CommandException {
        List<Participation> participationsToUpdate = model.getParticipationList()
                .filtered(participation -> participation.getStudent().isSamePerson(originalStudent));

        for (Participation participation : participationsToUpdate) {
            Participation updatedParticipation = new Participation(updatedStudent,
                    participation.getTutorial(), participation.getAttendanceList());
            model.setParticipation(participation, updatedParticipation);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof MarkPaidCommand)) {
            return false;
        }

        MarkPaidCommand e = (MarkPaidCommand) other;
        return targetIndex.equals(e.targetIndex)
                && feesPaid.equals(e.feesPaid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

    /**
     * Calculates the new overdue amount after feesPaidByStudent paid by student
     * @param currentPayment current overdue amount
     * @param feesPaid Fees paid by student
     * @return new overdue amount
     */
    private Payment calculatePayment(Payment currentPayment, Fees feesPaid) {
        int currentBalance = Integer.parseInt(currentPayment.overdueAmount);
        int amountPaid = Integer.parseInt(feesPaid.value);

        Integer finalAmount = currentBalance - amountPaid;
        return new Payment(finalAmount.toString());
    }
}
