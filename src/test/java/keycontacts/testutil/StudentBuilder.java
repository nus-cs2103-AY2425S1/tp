package keycontacts.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.student.Address;
import keycontacts.model.student.GradeLevel;
import keycontacts.model.student.Group;
import keycontacts.model.student.Name;
import keycontacts.model.student.Phone;
import keycontacts.model.student.Student;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_GRADE_LEVEL = "ABRSM 3";
    public static final String DEFAULT_GROUP = "Amy's Group";
    public static final RegularLesson DEFAULT_REGULAR_LESSON = null;
    public static final Set<MakeupLesson> DEFAULT_MAKEUP_LESSONS = new HashSet<>();

    private Name name;
    private Phone phone;
    private Address address;
    private GradeLevel gradeLevel;
    private Group group;
    private Set<PianoPiece> pianoPieces;
    private RegularLesson regularLesson;
    private Set<CancelledLesson> cancelledLessons;
    private Set<MakeupLesson> makeupLessons;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        gradeLevel = new GradeLevel(DEFAULT_GRADE_LEVEL);
        group = new Group(DEFAULT_GROUP);
        pianoPieces = new HashSet<>();
        regularLesson = DEFAULT_REGULAR_LESSON;
        cancelledLessons = new HashSet<>();
        makeupLessons = DEFAULT_MAKEUP_LESSONS;
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        address = studentToCopy.getAddress();
        gradeLevel = studentToCopy.getGradeLevel();
        group = studentToCopy.getGroup();
        pianoPieces = new HashSet<>(studentToCopy.getPianoPieces());
        regularLesson = studentToCopy.getRegularLesson();
        cancelledLessons = new HashSet<>(studentToCopy.getCancelledLessons());
        makeupLessons = new HashSet<>(studentToCopy.getMakeupLessons());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code GradeLevel} of the {@code Student} that we are building.
     */
    public StudentBuilder withGradeLevel(String gradeLevel) {
        this.gradeLevel = new GradeLevel(gradeLevel);
        return this;
    }

    /**
     * Sets the {@code Group} of the {@code Student} that we are building.
     */
    public StudentBuilder withGroup(String group) {
        this.group = new Group(group);
        return this;
    }

    /**
     * Parses the {@code pianoPieces} into a {@code Set<PianoPiece>}
     * and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withPianoPieces(String... pianoPieces) {
        this.pianoPieces = PianoPiece.getPianoPieceSet(pianoPieces);
        return this;
    }

    /**
     * Sets the {@code RegularLesson} of the {@code Student} that we are building.
     */
    public StudentBuilder withRegularLesson(String day, String startTime, String endTime) {
        this.regularLesson = new RegularLesson(new Day(day), new Time(startTime), new Time(endTime));
        return this;
    }

    /**
     * Parses the {@code cancelledLessons} into a {@code Set<CancelledLesson>}
     * and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withCancelledLessons(String ... cancelledLessons) {
        this.cancelledLessons = CancelledLesson.getCancelledLessonSet(cancelledLessons);
        return this;
    }

    /**
     * Parses the {@code makeupLessons} into a {@code Set<MakeupLesson>}
     */
    public StudentBuilder withMakeupLessons(MakeupLesson... lessons) {
        this.makeupLessons = (lessons != null) ? new HashSet<>(Arrays.asList(lessons)) : new HashSet<>();
        return this;
    }

    /**
     * Builds the student.
     */

    public Student build() {
        return new Student(name, phone, address, gradeLevel, group, pianoPieces, regularLesson,
            cancelledLessons, makeupLessons);
    }

}
