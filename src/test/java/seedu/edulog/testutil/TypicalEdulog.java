package seedu.edulog.testutil;

import seedu.edulog.model.EduLog;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.student.Student;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalEdulog {
    /**
     * Returns an {@code EduLog} with all the typical students and lessons.
     */
    public static EduLog getTypicalEduLog() {
        EduLog eduLog = new EduLog();
        for (Student student : TypicalStudents.getCopyOfTypicalStudents()) {
            eduLog.addStudent(student);
        }

        for (Lesson lesson : TypicalLessons.getTypicalLessons()) {
            eduLog.addLesson(lesson);
        }

        return eduLog;
    }
}
