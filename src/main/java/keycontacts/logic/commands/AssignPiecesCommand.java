package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PIECE_NAME;

import java.util.List;
import java.util.Set;

import keycontacts.commons.core.index.Index;
import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.Messages;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.student.Student;



/**
 * Adds a student to the student directory.
 */
public class AssignPiecesCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns one or more piano pieces to the student identified "
            + "by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PIECE_NAME + "PIECE_NAME...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PIECE_NAME + "Für Elise "
            + PREFIX_PIECE_NAME + "Moonlight Sonata";

    public static final String MESSAGE_SUCCESS = "Piano piece(s): %1$s\nAdded to student: %2$s";
    public static final String MESSAGE_DUPLICATE_PIANO_PIECE = "The student already"
            + " has one or more of the piano pieces";
    private final Index index;
    private final Set<PianoPiece> pianoPieces;

    /**
     * @param index of the student in the filtered student list to edit
     * @param pianoPieces the name of the piece to be added
     */
    public AssignPiecesCommand(Index index, Set<PianoPiece> pianoPieces) {
        requireNonNull(index);
        requireNonNull(pianoPieces);

        this.index = index;
        this.pianoPieces = pianoPieces;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToUpdate = lastShownList.get(index.getZeroBased());

        boolean hasDuplicate = pianoPieces.stream()
                .anyMatch(studentToUpdate.getPianoPieces()::contains);
        if (hasDuplicate) {
            throw new CommandException(MESSAGE_DUPLICATE_PIANO_PIECE);
        }

        Student updatedStudent = studentToUpdate.withAddedPianoPieces(pianoPieces);

        model.setStudent(studentToUpdate, updatedStudent);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(pianoPieces), Messages.format(updatedStudent)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignPiecesCommand)) {
            return false;
        }

        AssignPiecesCommand otherAssignPiecesCommand = (AssignPiecesCommand) other;
        return index.equals(otherAssignPiecesCommand.index) && pianoPieces.equals(otherAssignPiecesCommand.pianoPieces);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("pianoPieces", pianoPieces)
                .toString();
    }
}
