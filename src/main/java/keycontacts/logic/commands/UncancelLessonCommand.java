package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import keycontacts.commons.core.index.Index;
import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.Messages;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.student.Student;

/**
 * Uncancels a currently cancelled lesson.
 */
public class UncancelLessonCommand extends Command {

    public static final String COMMAND_WORD = "uncancel";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Uncancels a cancelled regular lesson for a student identified by the date.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DATE + "06-07-2024";
    public static final String MESSAGE_SUCCESS = "Uncancelled regular lesson at %1$s for student: %2$s";
    public static final String MESSAGE_LESSON_NOT_FOUND = "No cancelled regular lesson with the "
            + "specified parameters found for student %1$s.\n"
            + "Check if you inputted the right index and date!";
    public static final String MESSAGE_CLASHING_LESSON =
            "Uncancelling this regular lesson would result in a clash with lesson: %1$s";

    private final CancelledLesson cancelledLesson;
    private final Index index;

    /**
     * @param index Index of the student attached to the lesson to be cancelled
     * @param cancelledLesson Cancelledl lesson to be uncanceleld
     */
    public UncancelLessonCommand(Index index, CancelledLesson cancelledLesson) {
        requireAllNonNull(index, cancelledLesson);

        this.index = index;
        this.cancelledLesson = cancelledLesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Student> lastShownList = model.getStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToUpdate = lastShownList.get(index.getZeroBased());
        if (!studentToUpdate.getCancelledLessons().contains(cancelledLesson)) {
            throw new CommandException(String.format(MESSAGE_LESSON_NOT_FOUND, Messages.format(studentToUpdate)));
        }
        ArrayList<Student> studentsInGroup = model.getStudentsInGroup(studentToUpdate.getGroup());

        // try to update everything first
        for (Student groupStudent : studentsInGroup) {
            model.setStudent(groupStudent, groupStudent.withoutCancelledLesson(cancelledLesson));
        }

        Set<Lesson> clashingLessons = model.getClashingLessons();
        if (!clashingLessons.isEmpty()) { // if there are clashing lessons
            // revert all updates
            for (Student groupStudent : studentsInGroup) {
                model.setStudent(groupStudent.withoutCancelledLesson(cancelledLesson), groupStudent);
            }
            throw new CommandException(String.format(MESSAGE_CLASHING_LESSON,
                    clashingLessons.stream()
                            .filter(lesson -> lesson instanceof MakeupLesson) // clash can only be from makeup lesson
                            .findFirst().get().toDisplay()));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, cancelledLesson.getLessonDate().toDisplay(),
                Messages.format(studentToUpdate)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UncancelLessonCommand)) {
            return false;
        }

        UncancelLessonCommand otherUncancelLessonCommand = (UncancelLessonCommand) other;
        return index.equals(otherUncancelLessonCommand.index)
                && cancelledLesson.equals(otherUncancelLessonCommand.cancelledLesson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("cancelledLesson", cancelledLesson)
                .toString();
    }

}
