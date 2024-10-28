package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;

/**
 * Adds students to a specific lesson.
 */
public class AddToLessonCommand extends Command {

    public static final String COMMAND_WORD = "addtolesson";
    public static final CommandType COMMAND_TYPE = CommandType.ADDTOLESSON;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds students to the lesson identified "
            + "by the index number used in the displayed lesson list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME]...\n"
            + "Example: " + COMMAND_WORD + " 1 n/John Doe n/Harry Ng";

    public static final String MESSAGE_ADD_TO_LESSON_SUCCESS = "Added students to the Lesson: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT_IN_LESSON = "%s is already added to the lesson!";

    private final Index index;
    private final List<Name> studentNames;

    /**
     * @param index        of the lesson in the filtered lesson list
     * @param studentNames list of student names to add to the lesson
     */
    public AddToLessonCommand(Index index, List<Name> studentNames) {
        requireNonNull(index);
        requireNonNull(studentNames);

        this.index = index;
        this.studentNames = studentNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lastShownList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_LESSON_DISPLAYED_INDEX,
                    index.getOneBased()));
        }

        Lesson targetLesson = lastShownList.get(index.getZeroBased());
        Lesson editedLesson = new Lesson(targetLesson);

        for (Name studentName : studentNames) {
            Student student = model.findStudentByName(studentName)
                    .orElseThrow(() -> new CommandException("Student not found: " + studentName));
            try {
                editedLesson.addStudent(student);
            } catch (DuplicateStudentException e) {
                throw new CommandException(
                        String.format(MESSAGE_DUPLICATE_STUDENT_IN_LESSON, studentName));
            }
        }

        model.setLesson(targetLesson, editedLesson);

        return new CommandResult(
                String.format(MESSAGE_ADD_TO_LESSON_SUCCESS, Messages.format(editedLesson)),
                COMMAND_TYPE);
    }

    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddToLessonCommand)) {
            return false;
        }

        AddToLessonCommand otherCommand = (AddToLessonCommand) other;
        return index.equals(otherCommand.index)
                && studentNames.equals(otherCommand.studentNames);
    }
}
