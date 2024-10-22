package seedu.address.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonAdaptedPartner extends JsonAdaptedPerson {
    private final String preferredName;
    @JsonCreator
    public JsonAdaptedPartner(@JsonProperty("preferredName") String prefName,
                              @JsonProperty("name") String name,
                              @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                              @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(name, phone, email, address, tags);
        this.preferredName = prefName;
    }

    public String getPreferredName() {
        return preferredName;
    }
}
