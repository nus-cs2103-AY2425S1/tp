package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DAY;
import static keycontacts.logic.parser.CliSyntax.PREFIX_END_TIME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.List;
import java.util.Set;

import keycontacts.commons.core.index.Index;
import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.Messages;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.student.Student;

/**
 * Schedules the regular lesson for a student identified using it's displayed index from the student directory.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules the regular lesson for the student identified by the index number"
            + " used in the displayed student list. This will overwrite the student's existing regular lesson,"
            + " if it exists. The scheduled lesson cannot clash with any existing lessons for other students.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "Monday "
            + PREFIX_START_TIME + "16:00 "
            + PREFIX_END_TIME + "18:00";

    public static final String MESSAGE_SCHEDULE_LESSON_SUCCESS = "Scheduled lesson at %1$s for student: %2$s";
    public static final String MESSAGE_LESSON_UNCHANGED = "Lesson for the student is already at that time!";
    public static final String MESSAGE_CLASHING_LESSON = "Could not create lesson due to clash with lesson: %1$s.";

    private final Index index;
    private final RegularLesson regularLesson;

    /**
     * Creates a ScheduleCommand to schedule the given {@code regularLesson}
     */
    public ScheduleCommand(Index index, RegularLesson regularLesson) {
        requireAllNonNull(index, regularLesson);
        this.index = index;
        this.regularLesson = regularLesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToUpdate = lastShownList.get(index.getZeroBased());
        Student updatedStudent = studentToUpdate.withRegularLesson(regularLesson);
        if (studentToUpdate.equals(updatedStudent)) {
            throw new CommandException(MESSAGE_LESSON_UNCHANGED);
        }

        model.setStudent(studentToUpdate, updatedStudent);

        Set<Lesson> clashingLessons = model.getClashingLessons();
        if (!clashingLessons.isEmpty()) { // if there are clashing lessons
            model.setStudent(updatedStudent, studentToUpdate); // revert change if clash
            throw new CommandException(String.format(MESSAGE_CLASHING_LESSON,
                    clashingLessons.stream()
                            .filter(lesson -> lesson == regularLesson)
                            .findFirst().get().toDisplay()));
        }

        return new CommandResult(String.format(MESSAGE_SCHEDULE_LESSON_SUCCESS, regularLesson.toDisplay(),
                Messages.format(studentToUpdate)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCommand otherScheduleCommand)) {
            return false;
        }

        return index.equals(otherScheduleCommand.index)
                && regularLesson.equals(otherScheduleCommand.regularLesson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("regularLesson", regularLesson)
                .toString();
    }
}
