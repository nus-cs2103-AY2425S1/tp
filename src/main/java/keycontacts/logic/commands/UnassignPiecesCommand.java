package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;
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
 * Unassigns one or more piano pieces from a student
 */
public class UnassignPiecesCommand extends Command {

    public static final String COMMAND_WORD = "unassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unassigns one or more piano pieces from the student identified "
            + "by the index number used in the displayed student list\n"
            + "All piano pieces will be unassigned if none are given as argument\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PIECE_NAME + "[PIECE_NAME]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PIECE_NAME + "Für Elise "
            + PREFIX_PIECE_NAME + "Moonlight Sonata";

    public static final String MESSAGE_SUCCESS = "Piano piece(s): %1$s\nRemoved from student: %2$s";
    public static final String MESSAGE_PIANO_PIECE_NOT_FOUND = "The student does not have"
            + " the piano piece(s) %1$s assigned.";
    public static final String MESSAGE_NO_PIANO_PIECE_FOUND = "The student does not have any"
            + " piano pieces assigned";
    private final Index index;
    private final Set<PianoPiece> pianoPieces;

    /**
     * @param index of the student in the filtered student list to edit
     * @param pianoPieces the names of the pieces to be removed
     */
    public UnassignPiecesCommand(Index index, Set<PianoPiece> pianoPieces) {
        requireAllNonNull(index, pianoPieces);

        this.index = index;
        this.pianoPieces = pianoPieces;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToUpdate = lastShownList.get(index.getZeroBased());

        if (studentToUpdate.getPianoPieces().isEmpty()) {
            throw new CommandException(MESSAGE_NO_PIANO_PIECE_FOUND);
        }

        Set<PianoPiece> piecesToRemove = pianoPieces;
        if (piecesToRemove.isEmpty()) {
            piecesToRemove = studentToUpdate.getPianoPieces();
        }

        List<PianoPiece> piecesNotInStudent = piecesToRemove.stream()
                .filter(pianoPiece -> !studentToUpdate.getPianoPieces().contains(pianoPiece)).toList();
        if (!piecesNotInStudent.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PIANO_PIECE_NOT_FOUND,
                    Messages.format(piecesNotInStudent)));
        }

        Student updatedStudent = studentToUpdate.withRemovedPianoPieces(piecesToRemove);

        model.setStudent(studentToUpdate, updatedStudent);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(piecesToRemove), Messages.format(updatedStudent)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignPiecesCommand)) {
            return false;
        }

        UnassignPiecesCommand otherUnassignPiecesCommand = (UnassignPiecesCommand) other;
        return index.equals(otherUnassignPiecesCommand.index)
                && pianoPieces.equals(otherUnassignPiecesCommand.pianoPieces);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("pianoPieces", pianoPieces)
                .toString();
    }
}
