package seedu.address.logic.commands.consultation;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Removes multiple students from a specific consultation identified by its index.
 */
public class RemoveFromConsultCommand extends Command {

    public static final String COMMAND_WORD = "removefromconsult";
    public static final CommandType COMMAND_TYPE = CommandType.REMOVEFROMCONSULT;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes students from the consultation identified by the index.\n"
            + "Parameters: CONSULT_INDEX (must be a positive integer) "
            + "NAME...\n"
            + "Example: " + COMMAND_WORD + " 1 n/Alex Yeoh n/Harry Ng";

    public static final String MESSAGE_REMOVE_FROM_CONSULT_SUCCESS =
        "Removed students from Consultation: Date: %s; Time: %s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student(s) not found in the consultation.";

    private final Index consultIndex;
    private final List<Name> studentNames;

    /**
     * @param consultIndex The index of the consultation in the filtered consultation list.
     * @param studentNames List of student names to remove from the consultation.
     */
    public RemoveFromConsultCommand(Index consultIndex, List<Name> studentNames) {
        requireNonNull(consultIndex);
        requireNonNull(studentNames);

        this.consultIndex = consultIndex;
        this.studentNames = studentNames;
    }

    /**
     * Returns Command Type REMOVEFROMCONSULT
     *
     * @return Command Type REMOVEFROMCONSULT
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Consultation> lastShownList = model.getFilteredConsultationList();

        if (consultIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("The consultation index provided is invalid.");
        }

        Consultation targetConsultation = lastShownList.get(consultIndex.getZeroBased());
        Consultation editedConsultation = new Consultation(targetConsultation);

        for (Name studentName : studentNames) {
            Student studentToRemove = model.findStudentByName(studentName)
                    .orElseThrow(() -> new CommandException("Student not found: " + studentName));

            if (!editedConsultation.hasStudent(studentToRemove)) {
                throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
            }

            editedConsultation.removeStudent(studentToRemove);
        }

        model.setConsult(targetConsultation, editedConsultation);

        String successMessage = String.format(MESSAGE_REMOVE_FROM_CONSULT_SUCCESS,
                editedConsultation.getDate().getValue(), editedConsultation.getTime().getValue());

        return new CommandResult(successMessage, COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveFromConsultCommand // instanceof handles nulls
                && consultIndex.equals(((RemoveFromConsultCommand) other).consultIndex)
                && studentNames.equals(((RemoveFromConsultCommand) other).studentNames));
    }
}
