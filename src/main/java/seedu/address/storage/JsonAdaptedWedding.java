package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Jackson-friendly version of {@link Wedding}.
 */
public class JsonAdaptedWedding {
    private final String weddingName;
    private final int peopleCount;
    private final JsonAdaptedPerson partner1;
    private final JsonAdaptedPerson partner2;
    private List<JsonAdaptedPerson> guestList = new ArrayList<>();
    private final String address;
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedWedding} with the given wedding details.
     */
    @JsonCreator
    public JsonAdaptedWedding(
            @JsonProperty("weddingName") String weddingName,
            @JsonProperty("peopleCount") int peopleCount,
            @JsonProperty("partner1") JsonAdaptedPerson partner1,
            @JsonProperty("partner2") JsonAdaptedPerson partner2,
            @JsonProperty("guestList") List<JsonAdaptedPerson> guestList,
            @JsonProperty("address") String address,
            @JsonProperty("date") String date) {
        this.weddingName = weddingName;
        this.peopleCount = peopleCount;
        this.partner1 = partner1;
        this.partner2 = partner2;
        if (guestList != null) {
            this.guestList.addAll(guestList);
        }
        this.address = address;
        this.date = date;
    }

    /**
     * Converts a given {@code Wedding} into this class for Jackson use.
     */
    public JsonAdaptedWedding(Wedding source) {
        weddingName = source.getWeddingName().toString();
        peopleCount = source.getPeopleCount();
        partner1 = (source.getPartner1() != null) ? new JsonAdaptedPerson(source.getPartner1()) : null;
        partner2 = (source.getPartner2() != null) ? new JsonAdaptedPerson(source.getPartner2()) : null;
        if (source.getGuestList() != null) {
            guestList.addAll(source.getGuestList().stream().map(JsonAdaptedPerson::new).toList());
        } else {
            guestList = new ArrayList<>(); // Initialize as an empty list if null
        }
        address = (source.getAddress() != null) ? source.getAddress().toString() : null;
        date = source.getDate();
    }

    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wedding.
     */
    public Wedding toModelType() throws IllegalValueException {
        // Validate wedding name
        if (!Wedding.isValidWeddingName(weddingName)) {
            throw new IllegalValueException(WeddingName.MESSAGE_CONSTRAINTS);
        }

        // Handle null for partner1 and partner2
        Person modelPartner1 = (partner1 != null) ? partner1.toModelType() : null;
        Person modelPartner2 = (partner2 != null) ? partner2.toModelType() : null;

        // Handle null or empty guestList
        ArrayList<Person> modelGuestList = new ArrayList<>();
        if (guestList != null) {
            for (JsonAdaptedPerson guest : guestList) {
                modelGuestList.add(guest.toModelType());
            }
        }

        // Handle null for address
        Address modelAddress = (address != null) ? new Address(address) : null;

        // Return new Wedding object, handling null values gracefully
        return new Wedding(
                new WeddingName(weddingName),
                peopleCount,
                modelPartner1,
                modelPartner2,
                modelGuestList,
                modelAddress,
                date
        );
    }


    public String getWeddingName() {
        return weddingName;
    }

}
