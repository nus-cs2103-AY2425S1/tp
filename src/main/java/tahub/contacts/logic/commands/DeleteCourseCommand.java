package tahub.contacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_CODE;

import tahub.contacts.commons.util.ToStringBuilder;
import tahub.contacts.logic.Messages;
import tahub.contacts.logic.commands.exceptions.CommandException;
import tahub.contacts.model.Model;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.UniqueCourseList;

/**
 * Deletes a course identified using it's course code in the unique course list of address book.
 */
public class DeleteCourseCommand extends Command {

    public static final String COMMAND_WORD = "delete-course";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by its course code.\n"
            + "Parameters: COURSE_CODE (must be course code of an existing course)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_CODE + "CS1101S ";

    public static final String MESSAGE_DELETE_COURSE_SUCCESS = "Deleted Course: %1$s";

    private final CourseCode courseCode;
    
    /**
     * Creates a DeleteCourseCommand to delete the specified {@code Course}
     * 
     * @param courseCode of the course to be deleted
     */
    public DeleteCourseCommand(CourseCode courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        UniqueCourseList courseList = model.getCourseList();

        if (!courseList.containsCourseWithCourseCode(courseCode)) {
            throw new CommandException(Messages.MESSAGE_NO_EXISTING_COURSE);
        }
        
        Course courseToDelete = courseList.getCourseWithCourseCode(courseCode);
        model.deleteCourse(courseToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_COURSE_SUCCESS, Messages.format(courseToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCourseCommand)) {
            return false;
        }

        DeleteCourseCommand otherDeleteCourseCommand = (DeleteCourseCommand) other;
        return courseCode.equals(otherDeleteCourseCommand.courseCode);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("courseCode", courseCode)
                .toString();
    }
}
