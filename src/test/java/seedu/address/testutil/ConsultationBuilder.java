package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;
import seedu.address.model.student.Student;

/**
 * A utility class to help with building Consultation objects.
 */
public class ConsultationBuilder {

    public static final String DEFAULT_DATE = "2024-10-20";
    public static final String DEFAULT_TIME = "14:00";
    public static final String DEFAULT_NAMES = "";
    private Date date;
    private Time time;
    private final ArrayList<Student> students;

    /**
     * Creates a {@code ConsultationBuilder} with the default details.
     */
    public ConsultationBuilder() {
        date = new Date(DEFAULT_DATE);
        time = new Time(DEFAULT_TIME);
        students = new ArrayList<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code consultationToCopy}.
     */
    public ConsultationBuilder(Consultation consultationToCopy) {
        date = consultationToCopy.getDate();
        time = consultationToCopy.getTime();
        students = new ArrayList<>(consultationToCopy.getStudents());
    }

    /**
     * Sets the {@code Date} of the {@code Consultation} that we are building.
     */
    public ConsultationBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Consultation} that we are building.
     */
    public ConsultationBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Student} of the {@code Consultation} that we are building.
     */
    public ConsultationBuilder withStudent(Student student) {
        requireNonNull(student);
        this.students.add(student);
        return this;
    }

    public Consultation build() {
        return new Consultation(date, time, students);
    }

}
