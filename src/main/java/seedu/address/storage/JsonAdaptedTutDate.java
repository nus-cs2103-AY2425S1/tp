package seedu.address.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Student;
import seedu.address.model.tut.TutDate;

/**
 * Jackson-friendly version of {@link TutDate}.
 */
class JsonAdaptedTutDate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "TutDate's %s field is missing!";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    private final String date;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTutDate} with the given tut date details.
     */
    @JsonCreator
    public JsonAdaptedTutDate(@JsonProperty("date") String date,
                              @JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.date = date;
        if (students != null) {
            this.students.addAll(students);
        }
    }

    /**
     * Converts a given {@code TutDate} into this class for Jackson use.
     */
    public JsonAdaptedTutDate(TutDate source) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        this.date = sdf.format(source.getDate());
        this.students.addAll(source.getStudents().stream()
                .map(JsonAdaptedStudent::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted tut date object into the model's {@code TutDate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tut date.
     */
    public TutDate toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        Date modelDate;
        try {
            modelDate = sdf.parse(date);
        } catch (ParseException e) {
            throw new IllegalValueException(TutDate.MESSAGE_CONSTRAINTS);
        }

        final List<Student> modelStudents = new ArrayList<>();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            modelStudents.add(jsonAdaptedStudent.toModelType());
        }

        TutDate tutDate = new TutDate(modelDate);
        for (Student student : modelStudents) {
            tutDate.add(student);
        }

        return tutDate;
    }

}
