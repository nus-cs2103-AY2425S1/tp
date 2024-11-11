package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Schedule;

/**
 * Jackson-friendly version of {@link seedu.address.model.person.Schedule}.
 */
class JsonAdaptedSchedule {

    private final String dateTime;
    private final String note;
    private boolean isPaid;

    /**
     * Constructs a {@code JsonAdaptedSchedule} with the given {@code datetime} and {@code note}.
     */
    @JsonCreator
    public JsonAdaptedSchedule(@JsonProperty("dateTime")String dateTime, @JsonProperty("note") String note) {
        this.dateTime = dateTime;
        this.note = note;
        this.isPaid = false;
    }

    /**
     * Converts a given {@code Schedule} into this class for Jackson use.
     */
    public JsonAdaptedSchedule(Schedule source) {
        dateTime = source.getDateTime();
        note = source.getNotes();
    }

    @JsonProperty("dateTime")
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty("note")
    public String getNote() {
        return note;
    }

    @JsonProperty("payment")
    public boolean getPaymentStatus() {
        return isPaid;
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Schedule} object.
     *
     * @throws ParseException if there were any data constraints violated in the adapted schedule.
     */
    public Schedule toModelType() throws ParseException {
        Schedule.isValidDateTime(dateTime);
        return new Schedule(dateTime, note);
    }
}
