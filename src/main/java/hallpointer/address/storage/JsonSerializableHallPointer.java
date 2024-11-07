package hallpointer.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.HallPointer;
import hallpointer.address.model.ReadOnlyHallPointer;
import hallpointer.address.model.member.Member;


/**
 * An Immutable HallPointer that is serializable to JSON format.
 */
@JsonRootName(value = "hallpointer")
class JsonSerializableHallPointer {

    public static final String MESSAGE_DUPLICATE_MEMBER = "Members list contains duplicate member(s).";
    private static final String MESSAGE_DUPLICATE_SESSION = "Session list contains duplicate session(s).";

    private final List<JsonAdaptedMember> members = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHallPointer} with the given members.
     */
    @JsonCreator
    public JsonSerializableHallPointer(
            @JsonProperty("members") List<JsonAdaptedMember> members,
            @JsonProperty("sessions") List<JsonAdaptedSession> sessions) {
        if (members != null) {
            this.members.addAll(members);
        }
    }

    /**
     * Converts a given {@code ReadOnlyHallPointer} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableHallPointer}.
     */
    public JsonSerializableHallPointer(ReadOnlyHallPointer source) {
        members.addAll(source.getMemberList().stream().map(JsonAdaptedMember::new).collect(Collectors.toList()));
    }

    /**
     * Converts this hall pointer into the model's {@code HallPointer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public HallPointer toModelType() throws IllegalValueException {
        HallPointer hallPointer = new HallPointer();
        for (JsonAdaptedMember jsonAdaptedMember : members) {
            Member member = jsonAdaptedMember.toModelType();
            if (hallPointer.hasMembers(member)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEMBER);
            }
            hallPointer.addMember(member);
        }
        return hallPointer;
    }

}
