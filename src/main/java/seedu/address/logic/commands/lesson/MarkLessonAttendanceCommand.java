package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.StudentNotFoundException;

/**
 * Marks chosen students' attendance for a particular Lesson.
 */
public class MarkLessonAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "marka";
    public static final CommandType COMMAND_TYPE = CommandType.LESSON;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the attendance of student(s) "
            + "in a lesson at the chosen index in the lesson list to the specified value. "
            + "\nParameters: LESSON_INDEX "
            + PREFIX_NAME + "NAME [" + PREFIX_NAME + "NAME]… "
            + PREFIX_ATTENDANCE + "ATTENDANCE (1/y/Y or 0/n/N) "
            + "\nExample: " + COMMAND_WORD + " 1 n/John Doe n/Jane Doe a/y";

    public static final String MESSAGE_SUCCESS = "Marked the attendance of %s as %s";
    public static final String MESSAGE_STUDENT_NOT_FOUND_IN_ADDRESS_BOOK = "Student not found in TAHub: %s";
    public static final String MESSAGE_STUDENT_NOT_FOUND_IN_LESSON = "Student not found in the lesson: %s";

    private final Index index;
    private final List<Name> studentNames;
    private final boolean attendance;
    private final Logger logger = LogsCenter.getLogger(MarkLessonAttendanceCommand.class);

    /**
     * @param index of the lesson in the filtered lesson list
     * @param studentNames list of student names to target
     * @param attendance true if the given students have attended the lesson, false otherwise
     */
    public MarkLessonAttendanceCommand(Index index, List<Name> studentNames, boolean attendance) {
        requireAllNonNull(index, studentNames);
        this.index = index;
        this.studentNames = studentNames;
        this.attendance = attendance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Setting lesson attendance of students");
        List<Lesson> lastShownLessonList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownLessonList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_LESSON_DISPLAYED_INDEX, index.getOneBased()));
        }

        Lesson targetLesson = lastShownLessonList.get(index.getZeroBased());
        Lesson newLesson = new Lesson(targetLesson);

        for (Name studentName : studentNames) {
            Student student = model.findStudentByName(studentName)
                    .orElseThrow(() -> new CommandException(
                            String.format(MESSAGE_STUDENT_NOT_FOUND_IN_ADDRESS_BOOK, studentName)));
            try {
                newLesson.setAttendance(student, attendance);
            } catch (StudentNotFoundException e) {
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND_IN_LESSON, studentName));
            }
        }

        model.setLesson(targetLesson, newLesson);

        logger.fine("Successfully marked attendance of students in lesson " + targetLesson.toString());
        String names = String.join(", ", studentNames.stream().map(x -> x.fullName).toList());
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, names, attendance ? "true" : "false"),
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

        if (!(other instanceof MarkLessonAttendanceCommand)) {
            return false;
        }

        MarkLessonAttendanceCommand otherCommand = (MarkLessonAttendanceCommand) other;
        return index.equals(otherCommand.index)
                && studentNames.equals(otherCommand.studentNames)
                && (attendance == otherCommand.attendance);
    }
}
