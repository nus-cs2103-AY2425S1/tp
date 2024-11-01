package tahub.contacts.logic.commands.course;

import static java.util.Objects.requireNonNull;

import tahub.contacts.commons.util.ToStringBuilder;
import tahub.contacts.logic.Messages;
import tahub.contacts.logic.commands.Command;
import tahub.contacts.logic.commands.CommandResult;
import tahub.contacts.logic.commands.exceptions.CommandException;
import tahub.contacts.model.Model;
import tahub.contacts.model.course.Course;

/**
 * Adds a course to the course book.
 */
public class CourseAddCommand extends Command {

    public static final String COMMAND_WORD = "course-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a course to the course book. "
            + "eg course c/CS2103T n/Software Engineering";

    public static final String MESSAGE_SUCCESS = "New course added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This course already exists in the course book";

    private final Course toAdd;

    /**
     * Creates a CourseCommand to add the specified {@code Course}
     */
    public CourseAddCommand(Course course) {
        requireNonNull(course);
        toAdd = course;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCourse(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addCourse(toAdd);
        // model.addSca(new StudentCourseAssociation(
        // model.getFilteredPersonList().get(0), toAdd, new Tutorial("T01", toAdd)));
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CourseAddCommand)) {
            return false;
        }

        CourseAddCommand otherCourseAddCommand = (CourseAddCommand) other;
        return toAdd.equals(otherCourseAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
