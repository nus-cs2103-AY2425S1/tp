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
import seedu.address.model.person.Relation;
import seedu.address.model.person.Rsvp;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAdaptedTag;


/**
 * Jackson friendly version of {@link Guest}
 */
public class JsonAdaptedGuest extends JsonAdaptedPerson {
    private final String rsvp;
    private final String relation;

    /**
     * Constructs a {@code JsonAdaptedGuest} with the given guest details.
     */
    @JsonCreator
    public JsonAdaptedGuest(@JsonProperty("type") String type,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("rsvp") String rsvp,
            @JsonProperty("relation") String relation,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(name, phone, email, address, tags);
        setType(type);
        this.rsvp = rsvp;
        this.relation = relation;
    }

    /**
     * Converts a given {@code Guest} into this class for Jackson use.
     */
    public JsonAdaptedGuest(Guest source) {
        super(source);
        this.rsvp = source.getRsvp().value;
        this.relation = source.getRelation().relation;
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

        if (relation == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Relation.class.getSimpleName()));
        }
    }

    public Rsvp getModelRsvp() {
        return new Rsvp(rsvp);
    }

    public Relation getModelRelation() {
        return new Relation(relation);
    }

    /**
     * Converts this Jackson-friendly version of {@code Guest} into the model's version of {@code Guest}.
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
        Relation modelRelation = getModelRelation();
        return new Guest(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelRsvp, modelRelation);
    }
}
