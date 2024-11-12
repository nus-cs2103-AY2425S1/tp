package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POINTS;

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
 * Marks chosen students' participation for a particular Lesson.
 */
public class MarkLessonParticipationCommand extends Command {

    public static final String COMMAND_WORD = "markp";
    public static final CommandType COMMAND_TYPE = CommandType.LESSON;
    public static final int LOWER_BOUND = 0;
    public static final int UPPER_BOUND = 100;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the participation of student(s) "
            + "in a lesson at the chosen index in the lesson list to the specified value. "
            + "\nIf their participation is set to a positive integer, also sets their attendance to true."
            + "\nParameters: LESSON_INDEX "
            + PREFIX_NAME + "NAME [" + PREFIX_NAME + "NAME]… "
            + PREFIX_POINTS + "PARTICIPATION (integer from 0-100)"
            + "\nExample: " + COMMAND_WORD + " 1 n/John Doe pt/1";

    public static final String MESSAGE_SUCCESS = "Marked the participation of %s as %s";
    public static final String MESSAGE_STUDENT_NOT_FOUND_IN_ADDRESS_BOOK = "Student not found in TAHub: %s";
    public static final String MESSAGE_STUDENT_NOT_FOUND_IN_LESSON = "Student not found in the lesson: %s";

    private final Index index;
    private final List<Name> studentNames;
    private final int participationScore;
    private final Logger logger = LogsCenter.getLogger(MarkLessonParticipationCommand.class);

    /**
     * @param index of the lesson in the filtered lesson list
     * @param studentNames list of student names to target
     * @param participationScore the student(s)' participation score
     */
    public MarkLessonParticipationCommand(Index index, List<Name> studentNames, int participationScore) {
        requireAllNonNull(index, studentNames);
        this.index = index;
        this.studentNames = studentNames;
        this.participationScore = participationScore;
    }

    /**
     * Returns true if the participation score is valid, defined as being within the bounds.
     */
    public static boolean isValidParticipation(int score) {
        return LOWER_BOUND <= score && score <= UPPER_BOUND;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Setting lesson participation of students");
        List<Lesson> lastShownLessonList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownLessonList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_LESSON_DISPLAYED_INDEX, index.getOneBased()));
        }

        // this should have been checked in the parser
        assert MarkLessonParticipationCommand.isValidParticipation(participationScore);

        Lesson targetLesson = lastShownLessonList.get(index.getZeroBased());
        Lesson newLesson = new Lesson(targetLesson);

        for (Name studentName : studentNames) {
            Student student = model.findStudentByName(studentName)
                    .orElseThrow(() -> new CommandException(
                            String.format(MESSAGE_STUDENT_NOT_FOUND_IN_ADDRESS_BOOK, studentName)));
            try {
                newLesson.setParticipation(student, participationScore);
                if (participationScore > 0) {
                    newLesson.setAttendance(student, true);
                }
            } catch (StudentNotFoundException e) {
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND_IN_LESSON, studentName));
            }
        }

        model.setLesson(targetLesson, newLesson);

        logger.fine("Successfully marked participation of students in lesson " + targetLesson.toString());
        String names = String.join(", ", studentNames.stream().map(x -> x.fullName).toList());
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, names, participationScore),
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

        if (!(other instanceof MarkLessonParticipationCommand)) {
            return false;
        }

        MarkLessonParticipationCommand otherCommand = (MarkLessonParticipationCommand) other;
        return index.equals(otherCommand.index)
                && studentNames.equals(otherCommand.studentNames)
                && (participationScore == otherCommand.participationScore);
    }
}
