package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;

import java.util.ArrayList;
import java.util.List;

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


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks that the amounts of fees the student has paid "
            + "Parameters: \n"
            + "{INDEX} (must be a positive integer) \n"
            + PREFIX_PAYMENT + "{AMOUNT} (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PAYMENT + "200";
    public static final String MESSAGE_MARKED_PAID_SUCCESS = "Fees updated for Person %1$s";
    private final Index targetIndex;
    private final Fees fees;

    /**
     * Marks whether an existing person has paid their fees for the month
     * @param targetIndex
     */
    public MarkPaidCommand(Index targetIndex, Fees fees) {
        this.targetIndex = targetIndex;
        this.fees = fees;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToMarkPayment = lastShownList.get(targetIndex.getZeroBased());
        Payment updatedPayment = calculatePayment(personToMarkPayment.getPayment(), fees);

        Person markedPerson = new Person(personToMarkPayment.getName(), personToMarkPayment.getPhone(),
                personToMarkPayment.getEmail(), personToMarkPayment.getAddress(),
                updatedPayment, personToMarkPayment.getParticipation(), personToMarkPayment.getTags());

        //List of participations to delete
        List<Participation> participationsToDelete = model.getParticipationList()
                .filtered(participation -> participation.getStudent().equals(personToMarkPayment));

        //Make list of participations with updated payment
        List<Participation> participationsToAdd = new ArrayList<Participation>();
        for (int i = 0; i < participationsToDelete.size(); i++) {
            participationsToAdd.add(new Participation(markedPerson, participationsToDelete.get(i).getTutorial(),
                    participationsToDelete.get(i).getAttendanceList()));
        }

        //Remove participations with corresponding student from participations list
        for (int i = 0; i < participationsToDelete.size(); i++) {
            model.deleteParticipation(participationsToDelete.get(i));
        }

        //Add new participations to participations list
        for (int i = 0; i < participationsToAdd.size(); i++) {
            model.addParticipation(participationsToAdd.get(i));
        }

        model.setPerson(personToMarkPayment, markedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARKED_PAID_SUCCESS, Messages.format(markedPerson)));
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
        return targetIndex.equals(e.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

    private Payment calculatePayment(Payment currentPayment, Fees paidFees) {
        int currentBalance = Integer.parseInt(currentPayment.overdueAmount);
        int amountPaid = Integer.parseInt(paidFees.value);

        Integer finalAmount = currentBalance - amountPaid;
        return new Payment(finalAmount.toString());
    }
}
