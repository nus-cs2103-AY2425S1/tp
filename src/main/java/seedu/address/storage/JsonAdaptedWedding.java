package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.ContactMap;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Husband;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wife;

/**
 * Jackson-friendly version of {@link Wedding}.
 */
class JsonAdaptedWedding {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wedding's %s field is missing!";

    private final JsonAdaptedPerson bride;
    private final JsonAdaptedPerson groom;
    private final String venue;
    private final String date;
    private final JsonAdaptedContactMap contactMap;

    /**
     * Constructs a {@code JsonAdaptedWedding} with the given wedding details.
     */
    @JsonCreator
    public JsonAdaptedWedding(
            @JsonProperty("bride") JsonAdaptedPartner bride,
            @JsonProperty("groom") JsonAdaptedPartner groom,
            @JsonProperty("venue") String venue,
            @JsonProperty("date") String date,
            @JsonProperty("contactMap") JsonAdaptedContactMap contactMap) {
        this.bride = bride;
        this.groom = groom;
        this.venue = venue;
        this.date = date;
        this.contactMap = contactMap;
    }

    /**
     * Converts a given {@code Wedding} into this class for Jackson use.
     */
    public JsonAdaptedWedding(Wedding source) {
        bride = new JsonAdaptedPerson(source.getWife().getPerson());
        groom = new JsonAdaptedPerson(source.getHusband().getPerson());
        venue = source.getVenue().toString();
        date = source.getDate().toString();
        contactMap = new JsonAdaptedContactMap(source.getContactList());
    }

    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wedding.
     */
    public Wedding toModelType() throws IllegalValueException {
        if (bride == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Bride"));
        }
        final Wife modelBride = new Wife(bride.toModelType().getName().toString());

        if (groom == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Groom"));
        }
        final Husband modelGroom = new Husband(groom.toModelType().getName().toString());

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        if (!Venue.isValidVenue(venue)) {
            throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
        }
        final Venue modelVenue = new Venue(venue);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        final ContactMap modelRelatedPersons = contactMap.toModelType();


        return new Wedding(modelGroom,modelBride, modelDate, modelVenue, modelRelatedPersons);
    }
}