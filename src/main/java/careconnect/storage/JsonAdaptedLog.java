package careconnect.storage;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import careconnect.commons.exceptions.IllegalValueException;
import careconnect.model.log.Log;

/**
 * Jackson-friendly version of {@link Log}.
 */
class JsonAdaptedLog {

    private final Date date;
    private final String remark;

    /**
     * Constructs a {@code JsonAdaptedLog} with the given
     * {@code date} and {@code remark}.
     */
    @JsonCreator
    public JsonAdaptedLog(
            @JsonProperty("date") Date date,
            @JsonProperty("remark") String remark) {
        this.date = date;
        this.remark = remark;
    }

    /**
     * Converts a given {@code Log} into this class for Jackson use.
     */
    public JsonAdaptedLog(Log source) {
        date = source.getDate();
        remark = source.getRemark();
    }

    /**
     * Converts this Jackson-friendly adapted log object into the model's {@code Log} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted log.
     */
    public Log toModelType() throws IllegalValueException {
        if (!Log.isValidLogRemark(remark)) {
            throw new IllegalValueException(Log.MESSAGE_CONSTRAINTS);
        }
        return new Log(date, remark);
    }

}
