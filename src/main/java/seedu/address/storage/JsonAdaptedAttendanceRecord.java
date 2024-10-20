package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jackson-friendly version of an attendance record.
 */
class JsonAdaptedAttendanceRecord {

    private final String studentId;
    private final boolean isPresent;

    /**
     * Constructs a {@code JsonAdaptedAttendanceRecord} with the given details.
     */
    @JsonCreator
    public JsonAdaptedAttendanceRecord(@JsonProperty("studentId") String studentId,
                                       @JsonProperty("isPresent") boolean isPresent) {
        this.studentId = studentId;
        this.isPresent = isPresent;
    }

    public String getStudentId() {
        return studentId;
    }

    public boolean isPresent() {
        return isPresent;
    }
}
