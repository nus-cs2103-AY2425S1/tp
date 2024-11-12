package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
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

/**
 * Adds students to a specific lesson.
 */
public class AddToLessonCommand extends Command {

    public static final String COMMAND_WORD = "addtolesson";
    public static final CommandType COMMAND_TYPE = CommandType.LESSON;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds students to the lesson identified "
            + "by the index number used in the displayed lesson list."
            + "\nAt least one of the optional parameters must be provided."
            + "\nParameters: LESSON_INDEX "
            + "[" + PREFIX_NAME + "NAME]… "
            + "[" + PREFIX_INDEX + "INDEX]… "
            + "\nExample: " + COMMAND_WORD + " 1 n/John Doe i/2";

    public static final String MESSAGE_ADD_TO_LESSON_SUCCESS = "Added students to the Lesson: %1$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student not found: %s";
    public static final String MESSAGE_DUPLICATE_STUDENT_IN_LESSON_BY_NAME =
            "%s is already added to the lesson!";
    public static final String MESSAGE_DUPLICATE_STUDENT_IN_LESSON_BY_INDEX =
            "%s at index %d is already added to the lesson!";


    private final Index index;
    private final List<Name> studentNames;
    private final List<Index> indices;
    private final Logger logger = LogsCenter.getLogger(AddToLessonCommand.class);

    /**
     * @param index of the lesson in the filtered lesson list
     * @param studentNames list of student names to add to the lesson
     * @param indices list of indices of students in the current filtered list to add to the lesson
     */
    public AddToLessonCommand(Index index, List<Name> studentNames, List<Index> indices) {
        requireNonNull(index);
        requireNonNull(studentNames);
        requireNonNull(indices);

        this.index = index;
        this.studentNames = studentNames;
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Adding students to lesson");
        List<Lesson> lastShownLessonList = model.getFilteredLessonList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownLessonList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_LESSON_DISPLAYED_INDEX,
                    index.getOneBased()));
        }

        Lesson targetLesson = lastShownLessonList.get(index.getZeroBased());
        Lesson editedLesson = new Lesson(targetLesson);

        logger.info("Lesson to edit: " + targetLesson.toString());

        Set<Student> studentsToAddSet = new HashSet<>();
        ArrayList<Student> studentsToAddArr = new ArrayList<>();

        for (Name studentName : studentNames) {
            Student student = model.findStudentByName(studentName)
                    .orElseThrow(() -> new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, studentName)));

            // if student was already in the lesson before this command, throw error
            if (editedLesson.hasStudent(student)) {
                logger.warning("Students were not added to lesson " + targetLesson.toString()
                        + " because the student " + studentName + " was already in lesson");
                throw new CommandException(
                        String.format(MESSAGE_DUPLICATE_STUDENT_IN_LESSON_BY_NAME, studentName));
            }

            //if student was not previously in the lesson before this command, add it to the set
            if (!studentsToAddSet.contains(student)) {
                studentsToAddArr.add(student);
            }
            studentsToAddSet.add(student);
        }

        // checking if the indices are out of bounds
        boolean throwException = false;
        Set<Index> outOfBounds = new HashSet<>();

        for (Index item : indices) {
            if (item.getZeroBased() >= lastShownStudentList.size()) {
                throwException = true;
                outOfBounds.add(item);
            }
        }

        if (throwException) {
            String formattedOutOfBoundIndices = outOfBounds.stream()
                    .map(index -> String.valueOf(index.getOneBased()))
                    .collect(Collectors.joining(", "));
            logger.warning("Students were not added to lesson " + targetLesson.toString()
                    + " because indices " + formattedOutOfBoundIndices + " were out of bounds");
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                    formattedOutOfBoundIndices));
        }

        // checking if the students at these indices were already in the lesson
        for (Index studentIndex : indices) {
            Student student = lastShownStudentList.get(studentIndex.getZeroBased());

            if (editedLesson.hasStudent(student)) {
                logger.warning("Students were not added to lesson " + targetLesson.toString()
                        + " because student " + student.getName() + " was already in the lesson");
                throw new CommandException(
                        String.format(MESSAGE_DUPLICATE_STUDENT_IN_LESSON_BY_INDEX, student.getName(),
                                studentIndex.getOneBased()));
            }

            if (!studentsToAddSet.contains(student)) {
                studentsToAddArr.add(student);
            }
            studentsToAddSet.add(student);
        }

        // actually adding the students into the lesson
        for (Student student: studentsToAddArr) {
            editedLesson.addStudent(student);
        }

        model.setLesson(targetLesson, editedLesson);
        logger.fine("Successfully added students to lesson " + targetLesson.toString());
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
