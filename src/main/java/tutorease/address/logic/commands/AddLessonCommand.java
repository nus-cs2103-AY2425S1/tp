package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.CollectionUtil.requireAllNonNull;
import static tutorease.address.commons.util.DateTimeUtil.dateTimeNowString;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_FEE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import tutorease.address.commons.core.LogsCenter;
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
            + " " + COMMAND_WORD + ": Adds a lesson to the lesson list.\n"
            + "Parameters: "
            + PREFIX_STUDENT_ID + "STUDENTID "
            + PREFIX_FEE + "PRICEPERHOUR "
            + PREFIX_START_DATE + "STARTDATETIME "
            + PREFIX_DURATION + "DURATION\n"
            + "Example: " + LessonCommand.COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_STUDENT_ID + "1 "
            + PREFIX_FEE + "10 "
            + PREFIX_START_DATE + dateTimeNowString() + " "
            + PREFIX_DURATION + "1\n";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s.";
    public static final String MESSAGE_OVERLAP_LESSON = "This lesson overlaps with another lesson.";
    public static final String MESSAGE_INVALID_ROLE = "Lessons can only be added to students.";
    public static final String TO_STRING_FORMAT = "AddLessonCommand[studentId=%s,"
            + " fee=%s, startDateTime=%s, endDateTime=%s]";
    private static Logger logger = LogsCenter.getLogger(AddLessonCommand.class);
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
        requireAllNonNull(studentId, fee, startDateTime, endDateTime);

        assert studentId != null : "Student ID cannot be null";
        assert fee != null : "Fee cannot be null";
        assert startDateTime != null : "Start date time cannot be null";
        assert endDateTime != null : "End date time cannot be null";

        this.studentId = studentId;
        this.fee = fee;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        logger.log(Level.INFO, this.toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.log(Level.INFO, "Executing AddLessonCommand");
        requireNonNull(model);

        // Validate and get student
        ObservableList<Person> personList = model.getFilteredPersonList();
        validateStudentId(personList);
        Person student = personList.get(studentId.getValue());
        assert student != null : "Student cannot be null";
        validateStudentRole(student);

        // Validate and add lesson
        Lesson lesson = new Lesson(student, fee, this.startDateTime, this.endDateTime);
        validateModelHasLesson(model, lesson);
        model.addLesson(lesson);

        String formattedString = String.format(MESSAGE_SUCCESS, lesson);
        logger.log(Level.INFO, formattedString);
        return new CommandResult(formattedString);
    }

    private static void validateModelHasLesson(Model model, Lesson lesson) throws CommandException {
        requireAllNonNull(model, lesson);
        assert model != null : "Model cannot be null";
        assert lesson != null : "Lesson cannot be null";

        if (model.hasLessons(lesson)) {
            logger.log(Level.WARNING, AddLessonCommand.MESSAGE_OVERLAP_LESSON);
            throw new CommandException(AddLessonCommand.MESSAGE_OVERLAP_LESSON);
        }
    }

    private static void validateStudentRole(Person student) throws CommandException {
        assert student != null : "Student cannot be null";
        requireNonNull(student);

        if (!student.isStudent()) {
            assert student.isGuardian() : "Person must be a guardian";
            logger.log(Level.WARNING, MESSAGE_INVALID_ROLE);
            throw new CommandException(MESSAGE_INVALID_ROLE);
        }
        assert student.isStudent() : "Person must be a student";
    }

    private void validateStudentId(ObservableList<Person> personList) throws CommandException {
        assert personList != null : "Person list cannot be null";
        requireNonNull(personList);

        if (studentId.getValue() >= personList.size()) {
            logger.log(Level.WARNING, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand)) {
            logger.log(Level.WARNING, "AddLessonCommand is not an instance of AddLessonCommand");
            return false;
        }

        AddLessonCommand otherAddLessonCommand = (AddLessonCommand) other;

        boolean isStudentIdEqual = studentId.equals(otherAddLessonCommand.studentId);
        boolean isFeeEqual = fee.equals(otherAddLessonCommand.fee);
        boolean isStartDateTimeEqual = startDateTime.equals(otherAddLessonCommand.startDateTime);
        boolean isEndDateTimeEqual = endDateTime.equals(otherAddLessonCommand.endDateTime);

        logger.log(Level.INFO, "Comparing AddLessonCommand: " + this + " with " + otherAddLessonCommand);
        logger.log(Level.INFO, "Comparing student ID: " + isStudentIdEqual
                + "Comparing fee: " + isFeeEqual
                + "Comparing start date time: " + isStartDateTimeEqual
                + "Comparing end date time: " + isEndDateTimeEqual);
        return isStudentIdEqual && isFeeEqual && isStartDateTimeEqual && isEndDateTimeEqual;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT,
                studentId, fee, startDateTime, endDateTime);
    }
}
