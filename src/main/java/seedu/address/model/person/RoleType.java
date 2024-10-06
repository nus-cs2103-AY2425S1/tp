package seedu.address.model.person;

/**
 * Represents types of role that a person can take.
 * @see #STUDENT
 * @see #TUTOR
 * @see #PROFESSOR
 */
public enum RoleType {
    /**
     * Represents student studying under a specific course
     */
    STUDENT,

    /**
     * Represents tutor teaching/mentoring tutorial/labs of a course
     */
    TUTOR,

    /**
     * Represents professors who are in charge of teaching/managing the course
     */
    PROFESSOR
}
