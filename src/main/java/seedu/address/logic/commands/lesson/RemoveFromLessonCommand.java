package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Removes multiple students from a specific lesson identified by its index.
 */
public class RemoveFromLessonCommand extends Command {

    public static final String COMMAND_WORD = "removefromlesson";
    public static final CommandType COMMAND_TYPE = CommandType.REMOVEFROMLESSON;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes students from the lesson identified by the index.\n"
            + "Parameters: LESSON_INDEX (must be a positive integer) "
            + "NAME...\n"
            + "Example: " + COMMAND_WORD + " 1 n/Alex Yeoh n/Harry Ng";

    public static final String MESSAGE_REMOVE_FROM_LESSON_SUCCESS = "Removed students from Lesson: Date: %s; Time: %s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student(s) not found in the lesson.";

    private final Index lessonIndex;
    private final List<Name> studentNames;

    /**
     * @param lessonIndex  The index of the lesson in the filtered lesson list.
     * @param studentNames List of student names to remove from the lesson.
     */
    public RemoveFromLessonCommand(Index lessonIndex, List<Name> studentNames) {
        requireNonNull(lessonIndex);
        requireNonNull(studentNames);

        this.lessonIndex = lessonIndex;
        this.studentNames = studentNames;
    }

    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lastShownList = model.getFilteredLessonList();

        if (lessonIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("The lesson index provided is invalid.");
        }

        Lesson targetLesson = lastShownList.get(lessonIndex.getZeroBased());
        Lesson editedLesson = new Lesson(targetLesson);

        for (Name studentName : studentNames) {
            Student studentToRemove = model.findStudentByName(studentName)
                    .orElseThrow(() -> new CommandException("Student not found: " + studentName));

            if (!editedLesson.hasStudent(studentToRemove)) {
                throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
            }

            editedLesson.removeStudent(studentToRemove);
        }

        model.setLesson(targetLesson, editedLesson);

        String successMessage = String.format(MESSAGE_REMOVE_FROM_LESSON_SUCCESS,
                editedLesson.getDate().getValue(), editedLesson.getTime().getValue());

        return new CommandResult(successMessage, COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveFromLessonCommand // instanceof handles nulls
                        && lessonIndex.equals(((RemoveFromLessonCommand) other).lessonIndex)
                        && studentNames.equals(((RemoveFromLessonCommand) other).studentNames));
    }
}
