package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateTimeUtil.dateTimeNowString;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.EndDateTime;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LocationIndex;
import seedu.address.model.lesson.StartDateTime;
import seedu.address.model.lesson.StudentId;
import seedu.address.model.person.Person;

/**
 * Adds a lesson to the lesson list.
 */
public class AddLessonCommand extends LessonCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = LessonCommand.COMMAND_WORD
            + " " + COMMAND_WORD + ": Adds a lesson to the lesson list. "
            + "Parameters: "
            + PREFIX_STUDENT_ID + "1 "
            + PREFIX_START_DATE + dateTimeNowString() + " "
            + PREFIX_LOCATION_INDEX + "1 "
            + PREFIX_DURATION + "1\n";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_OVERLAP_LESSON = "This lesson overlaps with another lesson";

    private final StudentId studentId;
    private final StartDateTime startDateTime;
    private final LocationIndex locationIndex;
    private final EndDateTime endDateTime;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}.
     *
     * @param studentId     The student ID of the student to add the lesson to.
     * @param startDateTime The start date time of the lesson.
     * @param locationIndex The location index of the lesson.
     * @param endDateTime   The end date time of the lesson.
     */
    public AddLessonCommand(StudentId studentId, StartDateTime startDateTime, LocationIndex locationIndex,
                            EndDateTime endDateTime) {
        this.studentId = studentId;
        this.startDateTime = startDateTime;
        this.locationIndex = locationIndex;
        this.endDateTime = endDateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> personList = model.getAddressBook().getPersonList();

        if (studentId.getValue() > personList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person student = personList.get(studentId.getValue());
        Lesson lesson = new Lesson(student, this.locationIndex, this.startDateTime, this.endDateTime);
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
                && startDateTime.equals(otherAddLessonCommand.startDateTime)
                && locationIndex.equals(otherAddLessonCommand.locationIndex)
                && endDateTime.equals(otherAddLessonCommand.endDateTime);
    }
    @Override
    public String toString() {
        return String.format("AddLessonCommand[studentId=%s, startDateTime=%s, locationIndex=%s, endDateTime=%s]",
                studentId, startDateTime, locationIndex, endDateTime);
    }
}
