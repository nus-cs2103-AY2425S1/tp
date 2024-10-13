package seedu.address.model.person;

/**
 * Represents types of role that a person can take.
 * @see #STUDENT
 * @see #TUTOR
 * @see #PROFESSOR
 */
public enum RoleType {
    /**
     * Represents a student studying under a course
     */
    STUDENT {
        @Override
        public String toString() {
            return "Student";
        }
    },

    /**
     * Represents a tutor teaching/mentoring tutorials/labs of a course
     */
    TUTOR {
        @Override
        public String toString() {
            return "Tutor";
        }
    },

    /**
     * Represents a professor who is in charge of teaching/managing a course
     */
    PROFESSOR {
        @Override
        public String toString() {
            return "Professor";
        }
    }
}
