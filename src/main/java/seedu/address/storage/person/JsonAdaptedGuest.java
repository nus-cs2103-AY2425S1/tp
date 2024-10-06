package seedu.address.storage.person;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rsvp;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAdaptedTag;


/**
 * Class that manages a {@code Guest} in JSON form.
 */
public class JsonAdaptedGuest extends JsonAdaptedPerson {
    private final String rsvp;

    /**
     * Constructs a {@code JsonAdaptedGuest} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedGuest(@JsonProperty("type") String type,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("rsvp") String rsvp,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(name, phone, email, address, tags);
        setType(type);
        this.rsvp = rsvp;
    }

    /**
     * Serializes a {@code Guest} from {@code Model} form to JSON form.
     *
     * @param source {@code Guest} object in {@code Model} form.
     */
    public JsonAdaptedGuest(Guest source) {
        super(source);
        this.rsvp = source.getRsvp().value;
    }

    /**
     * Validates the fields for the {@code Guest}
     *
     * @throws IllegalValueException If a particular field is found to be invalid.
     */
    public void checkGuestFields() throws IllegalValueException {
        checkPersonFields();
        if (rsvp == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rsvp.class.getSimpleName()));
        }
    }

    public Rsvp getModelRsvp() {
        return new Rsvp(rsvp);
    }

    /**
     * Convert {@code Guest} from JSON form into {@code Model} form.
     *
     * @return {@code Guest} in {@code Model} form.
     */
    @Override
    public Guest toModelType() throws IllegalValueException {
        checkGuestFields();

        Name modelName = getModelName();
        Phone modelPhone = getModelPhone();
        Email modelEmail = getModelEmail();
        Address modelAddress = getModelAddress();
        Set<Tag> modelTags = getModelTags();
        Rsvp modelRsvp = getModelRsvp();
        return new Guest(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelRsvp);
    }
}
