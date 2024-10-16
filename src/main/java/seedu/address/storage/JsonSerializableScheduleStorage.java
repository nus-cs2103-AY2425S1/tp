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
 * An Immutable ScheduleList that is serializable to JSON format.
 */
@JsonRootName(value = "schedulelist")
public class JsonSerializableScheduleStorage {
    public static final String MEETING_DUPLICATE_PERSON = "Schedule list contains duplicate meeting(s).";

    private final List<JsonAdaptedMeeting> meetings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableScheduleStorage} with the given persons.
     */
    @JsonCreator
    public JsonSerializableScheduleStorage(@JsonProperty("meetings") List<JsonAdaptedMeeting> meetings) {
        this.meetings.addAll(meetings);
    }

    /**
     * Converts a given {@code ReadOnlyScheduleList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableScheduleStorage}.
     */
    public JsonSerializableScheduleStorage(ReadOnlyScheduleList source) {
        meetings.addAll(source.getMeetingList().stream().map(JsonAdaptedMeeting::new).collect(Collectors.toList()));
    }

    /**
     * Converts this schedule list into the model's {@code ScheduleList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ScheduleList toModelType() throws IllegalValueException {
        ScheduleList scheduleList = new ScheduleList();
        for (JsonAdaptedMeeting jsonAdaptedMeeting : meetings) {
            Meeting meeting = jsonAdaptedMeeting.toModelType();
            if (scheduleList.hasMeeting(meeting)) {
                throw new IllegalValueException(MEETING_DUPLICATE_PERSON);
            }
            scheduleList.addMeeting(meeting);
        }
        return scheduleList;
    }
}
