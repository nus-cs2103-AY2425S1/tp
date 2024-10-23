package tahub.contacts.logic.commands;

import tahub.contacts.commons.core.index.Index;
import tahub.contacts.commons.util.CollectionUtil;
import tahub.contacts.commons.util.ToStringBuilder;
import tahub.contacts.logic.Messages;
import tahub.contacts.logic.commands.exceptions.CommandException;
import tahub.contacts.model.Model;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;
import tahub.contacts.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_CODE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_NAME;
import static tahub.contacts.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCourseCommand extends Command {

    public static final String COMMAND_WORD = "edit course";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the course identified "
            + "by its course code. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: COURSE_CODE (must be course code of an existing course) "
            + "[" + PREFIX_NAME + "NAME]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_CODE + "CS1101S "
            + PREFIX_NAME + "Programming basics";

    public static final String MESSAGE_EDIT_COURSE_SUCCESS = "Edited Course: %1$s";
    public static final String MESSAGE_COURSE_NOT_EDITED = "At least one field to edit must be provided.";
    
    private final EditCourseDescriptor editCourseDescriptor;
    private final CourseCode courseCode;
    
    public EditCourseCommand(CourseCode courseCode, EditCourseDescriptor editCourseDescriptor) {
        requireNonNull(courseCode);
        requireNonNull(editCourseDescriptor);

        this.courseCode = courseCode;
        this.editCourseDescriptor = new EditCourseDescriptor(editCourseDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        UniqueCourseList courseList = model.getCourseList();

        if (!courseList.containsCourseWithCourseCode(courseCode)) {
            throw new CommandException(Messages.MESSAGE_NO_EXISTING_COURSE);
        }

        Course courseToEdit = courseList.getCourseWithCourseCode(courseCode);
        Course editedCourse = createEditedCourse(courseToEdit, editCourseDescriptor);

//        model.setCourse(courseToEdit, editedCourse);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_COURSE_SUCCESS, Messages.format(editedCourse)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Course createEditedCourse(Course courseToEdit, EditCourseDescriptor editCourseDescriptor) {
        assert courseToEdit != null;

        CourseName updatedCourseName = editCourseDescriptor.getCourseName().orElse(courseToEdit.courseName);
        
        return new Course(courseToEdit.courseCode, updatedCourseName);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCourseCommand)) {
            return false;
        }

        EditCourseCommand otherEditCourseCommand = (EditCourseCommand) other;
        return courseCode.equals(otherEditCourseCommand.courseCode)
                && editCourseDescriptor.equals(otherEditCourseCommand.editCourseDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("courseCode", courseCode)
                .add("editCourseDescriptor", editCourseDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditCourseDescriptor {
        private CourseName courseName;

        public EditCourseDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditCourseDescriptor(EditCourseDescriptor toCopy) {
            setCourseName(toCopy.courseName);
        }

        /**
         * Returns true if at least course  name is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(courseName);
        }
        
        public void setCourseName(CourseName courseName) {
            this.courseName = courseName;
        }

        public Optional<CourseName> getCourseName() {
            return Optional.ofNullable(courseName);
        }
        
        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCourseDescriptor)) {
                return false;
            }

            EditCourseDescriptor otherEditCourseDescriptor = (EditCourseDescriptor) other;
            return Objects.equals(courseName, otherEditCourseDescriptor.courseName);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", courseName)
                    .toString();
        }
    }
}
