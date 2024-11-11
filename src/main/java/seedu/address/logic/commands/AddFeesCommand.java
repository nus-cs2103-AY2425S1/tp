package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_PERSON_NOT_ENROLLED_FOR_PAYMENT;
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
 * Adds fees to a student's overdue amount
 */
public class AddFeesCommand extends Command {
    public static final String COMMAND_WORD = "addfees";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the tutorial fees to student's overdue amount\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PAYMENT + "PAYMENT\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PAYMENT + "200";
    public static final String MESSAGE_MARKED_PAID_SUCCESS = "Fees updated for student %1$s \n%2$s";

    private static final Logger logger = LogsCenter.getLogger(AddFeesCommand.class);
    private final Index targetIndex;
    private final Fees feesToCharge;

    /**
     * Increments a student's fees
     * @param targetIndex index of the student to mark payment based on the displayed list
     */
    public AddFeesCommand(Index targetIndex, Fees feesToCharge) {
        this.targetIndex = targetIndex;
        this.feesToCharge = feesToCharge;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToMarkPayment = getPersonFromModel(model);
        Person updatedPerson = createUpdatedPerson(personToMarkPayment);

        updateParticipations(model, personToMarkPayment, updatedPerson);

        model.setPerson(personToMarkPayment, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_MARKED_PAID_SUCCESS,
                updatedPerson.getFullName(), updatedPerson.getPayment()));
    }

    /**
     * Retrieves the target person based on the index in the model's filtered person list.
     */
    private Person getPersonFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, AddFeesCommand.class));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Creates an updated person with the new payment balance.
     */
    private Person createUpdatedPerson(Person student) {
        Payment updatedPayment = calculatePayment(student.getPayment(), feesToCharge);

        return new Person(student.getName(), student.getPhone(),
                student.getEmail(), student.getAddress(), updatedPayment, student.getTags());
    }

    /**
     * Updates the participations of the target person in the model with the new payment status.
     */
    private void updateParticipations(
            Model model, Person originalStudent, Person updatedStudent) throws CommandException {
        List<Participation> participationsToUpdate = model.getParticipationList()
                .filtered(participation -> participation.getStudent().isSamePerson(originalStudent));

        if (participationsToUpdate.isEmpty()) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, AddFeesCommand.class));
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_ENROLLED_FOR_PAYMENT,
                    updatedStudent.getName()));
        }

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
        if (!(other instanceof AddFeesCommand)) {
            return false;
        }

        AddFeesCommand e = (AddFeesCommand) other;
        return targetIndex.equals(e.targetIndex)
                && feesToCharge.equals(e.feesToCharge);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

    /**
     * Calculates the new overdue amount after fees added for student
     *
     * @param currentPayment current overdue amount
     * @param feesToCharge Fees paid by student
     * @return new overdue amount
     */
    private Payment calculatePayment(Payment currentPayment, Fees feesToCharge) {
        int currentBalance = Integer.parseInt(currentPayment.overdueAmount);
        int amountPaid = Integer.parseInt(feesToCharge.value);

        Integer finalAmount = currentBalance + amountPaid;
        return new Payment(finalAmount.toString());
    }
}

