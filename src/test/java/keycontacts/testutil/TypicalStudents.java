package keycontacts.testutil;

import static keycontacts.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static keycontacts.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_GRADE_LEVEL_AMY;
import static keycontacts.logic.commands.CommandTestUtil.VALID_GRADE_LEVEL_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static keycontacts.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static keycontacts.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static keycontacts.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import keycontacts.model.StudentDirectory;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.Time;
import keycontacts.model.student.Group;
import keycontacts.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final MakeupLesson MAKEUP_LESSON_1 =
            new MakeupLesson(new Date("30-09-2021"), new Time("12:00"), new Time("14:00"));
    public static final MakeupLesson MAKEUP_LESSON_2 =
            new MakeupLesson(new Date("01-10-2021"), new Time("12:00"), new Time("14:00"));
    public static final MakeupLesson MAKEUP_LESSON_3 =
            new MakeupLesson(new Date("02-10-2021"), new Time("18:00"), new Time("19:00"));
    public static final MakeupLesson MAKEUP_LESSON_4 =
            new MakeupLesson(new Date("03-10-2021"), new Time("09:00"), new Time("10:00"));
    public static final MakeupLesson MAKEUP_LESSON_5 =
            new MakeupLesson(new Date("04-10-2021"), new Time("11:00"), new Time("12:00"));
    public static final MakeupLesson MAKEUP_LESSON_6 =
            new MakeupLesson(new Date("05-10-2021"), new Time("20:00"), new Time("21:00"));
    public static final MakeupLesson MAKEUP_LESSON_7 =
            new MakeupLesson(new Date("06-10-2021"), new Time("15:00"), new Time("17:00"));

    /**
     * When editing any entry below, take note to change typicalStudentsStudentDirectory.json.
     * All typical students are not in a group initially.
     */
    public static final Student ALICE = new StudentBuilder()
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253")
            .withGradeLevel("ABRSM 2")
            .withGroup("Alice's Group")
            .withPianoPieces("Etude")
            .withRegularLesson("Monday", "12:00", "14:00")
            .withCancelledLessons("14-10-2024")
            .withMakeupLessons(MAKEUP_LESSON_1, MAKEUP_LESSON_2)
            .build();
    public static final Student BENSON = new StudentBuilder()
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withPhone("98765432")
            .withGradeLevel("RSL 3")
            .withGroup("Benson's Group")
            .withPianoPieces("Sonata I. X. 1905 \"From the Street\"", "Waltz No. 2")
            .withRegularLesson("Tuesday", "13:00", "15:00")
            .withCancelledLessons("15-10-2024")
            .withMakeupLessons(MAKEUP_LESSON_2)
            .build();
    public static final Student CARL = new StudentBuilder()
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withAddress("wall street")
            .withGradeLevel("ABRSM 1")
            .withGroup("Carl's Group")
            .withPianoPieces("Clair de Lune", "Le Sapin Op. 75 No. 5")
            .withRegularLesson("Wednesday", "18:00", "19:00")
            .withMakeupLessons(MAKEUP_LESSON_3)
            .build();
    public static final Student DANIEL = new StudentBuilder()
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withAddress("10th street")
            .withGroup("Daniel's Group")
            .withGradeLevel("ABRSM 3")
            .withRegularLesson("Thursday", "09:00", "10:00")
            .withMakeupLessons(MAKEUP_LESSON_4)
            .build();
    public static final Student ELLE = new StudentBuilder()
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withAddress("michegan ave")
            .withGroup(Group.NO_GROUP_STRING)
            .withGradeLevel("IMEB 1")
            .withRegularLesson("Friday", "11:00", "12:00")
            .withMakeupLessons(MAKEUP_LESSON_5)
            .build();
    public static final Student FIONA = new StudentBuilder()
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withAddress("little tokyo")
            .withGroup(Group.NO_GROUP_STRING)
            .withGradeLevel("RCM 2")
            .withRegularLesson("Saturday", "20:00", "21:00")
            .withMakeupLessons(MAKEUP_LESSON_6)
            .build();
    public static final Student GEORGE = new StudentBuilder()
            .withName("George Best")
            .withPhone("9482442")
            .withAddress("4th street")
            .withGroup(Group.NO_GROUP_STRING)
            .withGradeLevel("LCM 3")
            .withRegularLesson("Sunday", "15:00", "17:00")
            .withMakeupLessons(MAKEUP_LESSON_7)
            .build();

    // Manually added
    public static final Student HOON = new StudentBuilder()
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withAddress("little india")
            .withGradeLevel("AMEB 1")
            .build();
    public static final Student IDA = new StudentBuilder()
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withAddress("chicago ave")
            .withGradeLevel("Trinity 1")
            .build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withGradeLevel(VALID_GRADE_LEVEL_AMY)
            .withGroup(VALID_GROUP_AMY)
            .build();
    public static final Student BOB = new StudentBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withGradeLevel(VALID_GRADE_LEVEL_BOB)
            .withGroup(VALID_GROUP_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns a {@code StudentDirectory} with all the typical students.
     */
    public static StudentDirectory getTypicalStudentDirectory() {
        StudentDirectory sd = new StudentDirectory();
        for (Student student : getTypicalStudents()) {
            sd.addStudent(student);
        }
        return sd;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
