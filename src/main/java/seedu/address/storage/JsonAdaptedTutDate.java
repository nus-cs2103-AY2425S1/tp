package seedu.address.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.StudentId;
import seedu.address.model.tut.TutDate;

/**
 * Jackson-friendly version of {@link TutDate}.
 */
class JsonAdaptedTutDate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "TutDate's %s field is missing!";
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    private final String date;
    private final List<String> studentIds;

    /**
     * Constructs a {@code JsonAdaptedTutDate} with the given tut date details.
     */
    @JsonCreator
    public JsonAdaptedTutDate(@JsonProperty("date") String date,
                              @JsonProperty("studentIds") List<String> studentIds) {
        this.date = date;
        this.studentIds = studentIds;
    }

    /**
     * Converts a given {@code TutDate} into this class for Jackson use.
     */
    public JsonAdaptedTutDate(TutDate source) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        this.date = sdf.format(source.getDate());

        // Convert StudentIds to Strings
        this.studentIds = source.getStudentIDs().stream()
                .map(studentId -> studentId.value)
                .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted tut date object into the model's {@code TutDate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tut date.
     */
    public TutDate toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        Date modelDate;
        try {
            modelDate = sdf.parse(date);
        } catch (ParseException e) {
            throw new IllegalValueException(TutDate.MESSAGE_CONSTRAINTS);
        }

        TutDate tutDate = new TutDate(modelDate);

        if (studentIds != null) {
            for (String studentIdStr : studentIds) {
                if (!StudentId.isValidStudentId(studentIdStr)) {
                    throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
                }
                StudentId studentId = new StudentId(studentIdStr);
                tutDate.add(studentId);
            }
        }

        return tutDate;
    }
}
