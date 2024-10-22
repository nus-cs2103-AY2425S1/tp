package seedu.address.storage;

//import java.util.ArrayList;
//import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.model.person.Address;
//import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;


/**
 * Jackson-friendly version of {@link Wedding}.
 */
public class JsonAdaptedWedding {
    private final String weddingName;
    //private final int peopleCount;
    //private final Person partner1;
    //private final Person partner2;
    //private final List<JsonAdaptedPerson> guestList = new ArrayList<>();
    //private final Address address;
    //private final String date;

    /**
     * Constructs a {@code JsonAdaptedWedding} with the given {@code weddingName}.
     */
    @JsonCreator
    public JsonAdaptedWedding(
            //@JsonProperty("weddingName")
            String weddingName
                              //@JsonProperty("peopleCount") int peopleCount,
                              //@JsonProperty("partner1") Person partner1,
                              //@JsonProperty("partner2") Person partner2,
                              //@JsonProperty("guestlist") List<JsonAdaptedPerson> guestlist,
                              //@JsonProperty("address") Address address,
                              //@JsonProperty("date") String date
                              ) {
        this.weddingName = weddingName;
        //this.peopleCount = peopleCount;
        //this.partner1 = partner1;
        //this.partner2 = partner2;
        //if (guestlist != null) {
        //    this.guestList.addAll(guestList);
        //}
        //this.address = address;
        //this.date = date;
    }

    /**
     * Converts a given {@code Wedding} into this class for Jackson use.
     */
    public JsonAdaptedWedding(Wedding source) {
        weddingName = source.getWeddingName().toString();
        //peopleCount = source.getPeopleCount();
        //partner1 = source.getPartner1();
        //partner2 = source.getPartner2();
        //        guestList.addAll(source.getGuestList().stream()
        //                .map(JsonAdaptedPerson::new)
        //                .toList());
        //address = source.getAddress();
        //date = source.getDate();
    }

    @JsonValue
    public String getWeddingName() {
        return weddingName;
    }

    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wedding.
     */
    public Wedding toModelType() throws IllegalValueException {
        if (!Wedding.isValidWeddingName(weddingName)) {
            throw new IllegalValueException(WeddingName.MESSAGE_CONSTRAINTS);
        }
        return new Wedding(new WeddingName(weddingName));
    }
}
