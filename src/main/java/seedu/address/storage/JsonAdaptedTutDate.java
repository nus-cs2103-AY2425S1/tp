package seedu.address.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tut.TutDate;

/**
 * Jackson-friendly version of {@link TutDate}.
 */
class JsonAdaptedTutDate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "TutDate's %s field is missing!";
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedTutDate} with the given tut date details.
     */
    @JsonCreator
    public JsonAdaptedTutDate(@JsonProperty("date") String date) {
        this.date = date;
    }

    /**
     * Converts a given {@code TutDate} into this class for Jackson use.
     */
    public JsonAdaptedTutDate(TutDate source) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        this.date = sdf.format(source.getDate());
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


        return new TutDate(modelDate);
    }

}
