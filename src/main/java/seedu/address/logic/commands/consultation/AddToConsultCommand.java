package seedu.address.logic.commands.consultation;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Adds students to a specific consultation.
 */
public class AddToConsultCommand extends Command {

    public static final String COMMAND_WORD = "addtoconsult";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds students to the consultation identified "
            + "by the index number used in the displayed consultation list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME]...\n"
            + "Example: " + COMMAND_WORD + " 1 n/John Doe n/Harry Ng";

    public static final String MESSAGE_ADD_TO_CONSULT_SUCCESS = "Added students to Consultation: %1$s";

    private final Index index;
    private final List<Name> studentNames;

    /**
     * @param index of the consultation in the filtered consultation list
     * @param studentNames list of student names to add to the consultation
     */
    public AddToConsultCommand(Index index, List<Name> studentNames) {
        requireNonNull(index);
        requireNonNull(studentNames);

        this.index = index;
        this.studentNames = studentNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Consultation> lastShownList = model.getFilteredConsultationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX,
                index.getOneBased()));
        }

        Consultation consultationToEdit = lastShownList.get(index.getZeroBased());

        for (Name studentName : studentNames) {
            Student student = model.findStudentByName(studentName)
                    .orElseThrow(() -> new CommandException("Student not found: " + studentName));
            consultationToEdit.addStudent(student);
        }

        return new CommandResult(String.format(MESSAGE_ADD_TO_CONSULT_SUCCESS, Messages.format(consultationToEdit)));
    }

    /**
     * Returns Command Type ADDTOCONSULT
     *
     * @return Command Type ADDTOCONSULT
     */
    @Override
    public CommandType getCommandType() {
        return CommandType.ADDTOCONSULT;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddToConsultCommand)) {
            return false;
        }

        AddToConsultCommand otherCommand = (AddToConsultCommand) other;
        return index.equals(otherCommand.index)
                && studentNames.equals(otherCommand.studentNames);
    }
}
