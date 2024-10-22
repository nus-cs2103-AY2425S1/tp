package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.model.person.Attendance;
import seedu.address.model.person.AttendanceList;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grade;
import seedu.address.model.person.GradeList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_COURSE = "CS2103/T";

    private Name name;
    private Phone phone;
    private Email email;
    private Course course;
    private Set<Tag> tags;
    private GradeList gradeList;
    private AttendanceList attendanceList;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        course = new Course(DEFAULT_COURSE);
        tags = new HashSet<>();
        gradeList = new GradeList();
        attendanceList = new AttendanceList();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        course = personToCopy.getCourse();
        tags = new HashSet<>(personToCopy.getTags());
        gradeList = personToCopy.getGradeList();
        attendanceList = personToCopy.getAttendanceList();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Course} of the {@code Person} that we are building.
     */
    public PersonBuilder withCourse(String course) {
        this.course = new Course(course);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code GradeList} of the {@code Person} that we are building.
     * This method allows adding a list of grades to the person's grade list.
     *
     * @param grades A list of grades to be added to the person's {@code GradeList}.
     * @return The {@code PersonBuilder} object, for method chaining.
     */
    public PersonBuilder withGrades(List<Grade> grades) {
        this.gradeList = new GradeList();
        for (Grade grade : grades) {
            this.gradeList = this.gradeList.addGrade(grade);
        }
        return this;
    }

    /**
     * Sets the {@code AttendanceList} of the {@code Person} that we are building.
     * This method allows adding a map of dates and attendances to the person's grade list.
     *
     * @param attendances A map to be added to the person's {@code GradeList}.
     * @return The {@code PersonBuilder} object, for method chaining.
     */
    public PersonBuilder withAttendances(Map<LocalDateTime, Attendance> attendances) {
        this.attendanceList = new AttendanceList();
        for (Map.Entry<LocalDateTime, Attendance> attendance : attendances.entrySet()) {
            this.attendanceList = this.attendanceList.setAttendance(attendance.getKey(), attendance.getValue());
        }
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, course, tags, gradeList, attendanceList);
    }

}
