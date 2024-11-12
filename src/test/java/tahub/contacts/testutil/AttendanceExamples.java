package tahub.contacts.testutil;

import java.util.List;

import tahub.contacts.model.course.Attendance;
import tahub.contacts.model.course.AttendanceSession;

/**
 * A utility class to help build sample {@link Attendance} objects for testing.
 */
public class AttendanceExamples {
    //========== ATTENDANCE LISTS =========================================================================
    public static final AttendanceSession ATTENDED = AttendanceSession.createAttended();
    public static final AttendanceSession ABSENT = AttendanceSession.createAbsent();
    public static final List<AttendanceSession> EMPTY_ATTENDANCE_LIST = List.of();
    public static final List<AttendanceSession> SINGULAR_ATTENDED_ATTENDANCE_LIST =
            List.of(ATTENDED);
    public static final List<AttendanceSession> SINGULAR_ABSENT_ATTENDANCE_LIST =
            List.of(ABSENT);
    public static final List<AttendanceSession> EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5 =
            List.of(ABSENT, ATTENDED, ATTENDED, ABSENT, ATTENDED);

    //========== ATTENDANCE OBJECTS =========================================================================
    public static final Attendance ATTENDANCE_EXAMPLE_0 = new Attendance(EMPTY_ATTENDANCE_LIST);
    public static final Attendance ATTENDANCE_EXAMPLE_1 = new Attendance(EXAMPLE_ATTENDANCE_LIST_3_OUT_OF_5);
}
