package hallpointer.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Phone;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Member}.
 */
class JsonAdaptedMember {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Member's %s field is missing!";

    private final String name;
    private final String phone;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMember} with the given member details.
     */
    @JsonCreator
    public JsonAdaptedMember(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Member} into this class for Jackson use.
     */
    public JsonAdaptedMember(Member source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getRoom().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted member object into the model's {@code Member} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member.
     */
    public Member toModelType() throws IllegalValueException {
        final List<Tag> memberTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            memberTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Room.class.getSimpleName()));
        }
        if (!Room.isValidRoom(address)) {
            throw new IllegalValueException(Room.MESSAGE_CONSTRAINTS);
        }
        final Room modelRoom = new Room(address);

        final Set<Tag> modelTags = new HashSet<>(memberTags);
        return new Member(modelName, modelPhone, modelRoom, modelTags);
    }

}
