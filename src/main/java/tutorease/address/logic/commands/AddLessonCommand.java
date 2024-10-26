package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.DateTimeUtil.dateTimeNowString;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_FEE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import javafx.collections.ObservableList;
import tutorease.address.logic.Messages;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.Model;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Fee;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.model.person.Person;

/**
 * Adds a lesson to the lesson list.
 */
public class AddLessonCommand extends LessonCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = LessonCommand.COMMAND_WORD
            + " " + COMMAND_WORD + ": Adds a lesson to the lesson list. "
            + "Parameters: "
            + PREFIX_STUDENT_ID + "1 "
            + PREFIX_FEE + "10 "
            + PREFIX_START_DATE + dateTimeNowString() + " "
            + PREFIX_DURATION + "1\n";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_OVERLAP_LESSON = "This lesson overlaps with another lesson";
    public static final String TO_STRING_FORMAT = "AddLessonCommand[studentId=%s,"
            + " fee=%s, startDateTime=%s, endDateTime=%s]";

    private final StudentId studentId;
    private final Fee fee;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}.
     *
     * @param studentId     The student ID of the student to add the lesson to.
     * @param fee           The fee per hour of the lesson.
     * @param startDateTime The start date time of the lesson.
     * @param endDateTime   The end date time of the lesson.
     */
    public AddLessonCommand(StudentId studentId, Fee fee, StartDateTime startDateTime, EndDateTime endDateTime) {
        this.studentId = studentId;
        this.fee = fee;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> personList = model.getFilteredPersonList();

        if (studentId.getValue() >= personList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person student = personList.get(studentId.getValue());
        Lesson lesson = new Lesson(student, fee, this.startDateTime, this.endDateTime);
        if (model.hasLessons(lesson)) {
            throw new CommandException(MESSAGE_OVERLAP_LESSON);
        }
        model.addLesson(lesson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, lesson));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand)) {
            return false;
        }

        AddLessonCommand otherAddLessonCommand = (AddLessonCommand) other;
        return studentId.equals(otherAddLessonCommand.studentId)
                && fee.equals(otherAddLessonCommand.fee)
                && startDateTime.equals(otherAddLessonCommand.startDateTime)
                && endDateTime.equals(otherAddLessonCommand.endDateTime);
    }
    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT,
                studentId, fee, startDateTime, endDateTime);
    }
}
