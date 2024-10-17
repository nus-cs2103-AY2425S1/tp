package tahub.contacts.model.courseclass.recitation;

import tahub.contacts.model.course.Course;
import tahub.contacts.model.courseclass.CourseClass;

import java.util.Objects;

/**
 * Represents a recitation in a course.
 */
public class Recitation extends CourseClass {
    private String recitationId;
    private Course course;

    public Recitation(String recitationId, Course course) {
        this.recitationId = recitationId;
        this.course = course;
    }

    /**
     * Gets the ID of the recitation.
     *
     * @return The ID of the recitation.
     */
    public String getRecitationId() {
        return this.recitationId;
    }

    /**
     * Retrieves the Course associated with the Recitation.
     *
     * @return The Course object for the Recitation.
     */
    public Course getCourse() {
        return this.course;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Recitation)) {
            return false;
        }

        Recitation otherRecitation = (Recitation) other;
        return this.recitationId.equals(otherRecitation.recitationId) &&
                this.course.equals(otherRecitation.course);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.recitationId, this.course);
    }
}
