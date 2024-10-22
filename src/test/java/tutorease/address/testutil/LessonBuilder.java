package tutorease.address.testutil;

import static java.util.Objects.requireNonNull;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_END_DATE;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_FEE;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_START_DATE;

import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Fee;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.person.Person;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {
    public static final String STUDENT = "Amy Bee";

    private Person student;
    private Fee fee;
    private StartDateTime startDateTime;
    private EndDateTime endDateTime;
    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() throws ParseException {
        student = new StudentBuilder().withName(STUDENT).build();
        fee = new Fee(VALID_FEE);
        startDateTime = StartDateTime.createStartDateTime(VALID_START_DATE);
        endDateTime = EndDateTime.createEndDateTime(VALID_END_DATE);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder withName(Person student) {
        requireNonNull(student);
        this.student = student;
        return this;
    }
    /**
     * Sets the StartDateTime of the {@code Lesson} that we are building.
     */
    public LessonBuilder withStartDateTime(String startDateTime) throws ParseException {
        requireNonNull(startDateTime);
        this.startDateTime = StartDateTime.createStartDateTime(startDateTime);
        return this;
    }
    /**
     * Sets the EndDateTime of the {@code Lesson} that we are building.
     */
    public LessonBuilder withEndDateTime(String endDateTime) throws ParseException {
        requireNonNull(endDateTime);
        this.endDateTime = EndDateTime.createEndDateTime(endDateTime);
        return this;
    }
    /**
     * Builds the Lesson object.
     */
    public Lesson build() {
        return new Lesson(student, fee, startDateTime, endDateTime);
    }

    /**
     * Sets the fee of the {@code Lesson} that we are building.
     */
    public LessonBuilder withFee(String fee) throws ParseException {
        requireNonNull(fee);
        this.fee = new Fee(fee);
        return this;
    }
}
