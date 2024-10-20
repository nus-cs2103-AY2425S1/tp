//package seedu.address.storage;
//
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
///**
// * Jackson-friendly representation of an attendance record.
// */
//class JsonAttendanceRecord {
//
//    private final String studentId;
//    private final boolean isPresent;
//
//    /**
//     * Constructs a {@code JsonAttendanceRecord} with the given details.
//     */
//    @JsonCreator
//    public JsonAttendanceRecord(@JsonProperty("studentId") String studentId,
//                                @JsonProperty("isPresent") boolean isPresent) {
//        this.studentId = studentId;
//        this.isPresent = isPresent;
//    }
//
//    public String getStudentId() {
//        return studentId;
//    }
//
//    public boolean isPresent() {
//        return isPresent;
//    }
//}
