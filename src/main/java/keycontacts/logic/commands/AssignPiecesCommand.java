package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PIECE_NAME;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import keycontacts.commons.core.index.Index;
import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.Messages;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.student.Student;

/**
 * Assigns one or more piano pieces to a student
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
            + " has the following piano piece(s): %1$s";
    private final Index index;
    private final Set<PianoPiece> pianoPieces;

    /**
     * @param index of the student in the filtered student list to edit
     * @param pianoPieces the names of the pieces to be added
     */
    public AssignPiecesCommand(Index index, Set<PianoPiece> pianoPieces) {
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

        Set<PianoPiece> duplicatePieces = pianoPieces.stream()
                .filter(studentToUpdate.getPianoPieces()::contains).collect(Collectors.toSet());
        if (!duplicatePieces.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PIANO_PIECE, Messages.format(duplicatePieces)));
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
