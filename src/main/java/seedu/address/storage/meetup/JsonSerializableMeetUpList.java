package seedu.address.storage.meetup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.MeetUpList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.meetup.MeetUp;

/**
 * An Immutable MeetUpList that is serializable to JSON format.
 */
@JsonRootName(value = "meetuplist")
public class JsonSerializableMeetUpList {

    public static final String MESSAGE_DUPLICATE_MEETUP = "Meet up list contains duplicate meet up(s).";

    private final List<JsonAdaptedMeetUp> meetUps = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMeetUpList} with the given buyers.
     */
    @JsonCreator
    public JsonSerializableMeetUpList(@JsonProperty("meetUps") List<JsonAdaptedMeetUp> meetUps) {
        this.meetUps.addAll(meetUps);
    }

    /**
     * Converts a given {@code ReadOnlyMeetUpList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMeetUpList}.
     */
    public JsonSerializableMeetUpList(ReadOnlyMeetUpList source) {
        meetUps.addAll(source.getMeetUpList().stream().map(JsonAdaptedMeetUp::new).collect(Collectors.toList()));
    }

    /**
     * Converts this buyer list into the model's {@code MeetUpList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MeetUpList toModelType() throws IllegalValueException {
        MeetUpList meetUpList = new MeetUpList();
        for (JsonAdaptedMeetUp jsonAdaptedMeetUp : meetUps) {
            MeetUp meetUp = jsonAdaptedMeetUp.toModelType();
            if (meetUpList.hasMeetUp(meetUp)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEETUP);
            }
            meetUpList.addMeetUp(meetUp);
        }
        return meetUpList;
    }

}
