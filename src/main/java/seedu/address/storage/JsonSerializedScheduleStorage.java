package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyScheduleList;
import seedu.address.model.ScheduleList;
import seedu.address.model.schedule.Meeting;

/**
 * A class to serialize and deserialize a schedule list in JSON format.
 */
@JsonRootName(value = "schedulelist")
public class JsonSerializedScheduleStorage {
    public static final String MESSAGE_DUPLICATE_MEETING = "Schedule list contains duplicate meeting(s).";
    private final List<JsonAdaptedMeeting> meetings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializedScheduleStorage} with the given meetings.
     */
    @JsonCreator
    public JsonSerializedScheduleStorage(@JsonProperty("meeting") List<JsonAdaptedMeeting> meetings) {
        this.meetings.addAll(meetings);
    }

    public JsonSerializedScheduleStorage(ReadOnlyScheduleList source) {
        meetings.addAll(source.getMeetingList().stream().map(JsonAdaptedMeeting::new).collect(Collectors.toList()));
    }

    /**
     * Converts this JSON-friendly schedule list into the model's {@code ScheduleList} object.
     *
     * @return A {@code ScheduleList} representing the deserialized data.
     * @throws IllegalValueException If any data constraints were violated, such as duplicate meetings.
     */
    public ScheduleList toModelType() throws IllegalValueException {
        ScheduleList scheduleList = new ScheduleList();
        for (JsonAdaptedMeeting jsonAdaptedMeeting : meetings) {
            Meeting meeting = jsonAdaptedMeeting.toModelType();
            if (scheduleList.hasMeeting(meeting)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEETING);
            }
            scheduleList.addMeeting(meeting);
        }
        return scheduleList;
    }
}
