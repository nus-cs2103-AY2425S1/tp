package seedu.address.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.EndDateTime;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LocationIndex;
import seedu.address.model.lesson.StartDateTime;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {
    public static final String STUDENT = "Amy Bee";

    private Person student;
    private LocationIndex locationIndex;
    private StartDateTime startDateTime;
    private EndDateTime endDateTime;
    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() throws ParseException {
        student = new PersonBuilder().withName(STUDENT).build();
        locationIndex = new LocationIndex(VALID_LOCATION_INDEX);
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
     * Sets the LocationIndex of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLocationIndex(String locationIndex) throws ParseException {
        requireNonNull(locationIndex);
        this.locationIndex = new LocationIndex(locationIndex);
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
        return new Lesson(student, locationIndex, startDateTime, endDateTime);
    }
}
