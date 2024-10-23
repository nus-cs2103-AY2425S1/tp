package tahub.contacts.testutil;

import tahub.contacts.logic.commands.EditCourseCommand.EditCourseDescriptor;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseName;

/**
 * A utility class to help with building EditCourseDescriptor objects.
 */
public class EditCourseDescriptorBuilder {

    private EditCourseDescriptor descriptor;

    public EditCourseDescriptorBuilder() {
        descriptor = new EditCourseDescriptor();
    }

    public EditCourseDescriptorBuilder(EditCourseDescriptor descriptor) {
        this.descriptor = new EditCourseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCourseDescriptor} with fields containing {@code course}'s details
     */
    public EditCourseDescriptorBuilder(Course course) {
        descriptor = new EditCourseDescriptor();
        descriptor.setCourseName(course.courseName);
    }

    /**
     * Sets the {@code CourseName} of the {@code EditCourseDescriptor} that we are building.
     */
    public EditCourseDescriptorBuilder withCourseName(String courseName) {
        descriptor.setCourseName(new CourseName(courseName));
        return this;
    }

    public EditCourseDescriptor build() {
        return descriptor;
    }
}
