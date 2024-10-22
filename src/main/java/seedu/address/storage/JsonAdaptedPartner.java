package seedu.address.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.wedding.Partner;

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

    public JsonAdaptedPartner(Partner source) {
        super(source.getPerson().getName().fullName, source.getPerson().getPhone().value,
                source.getPerson().getEmail().value, source.getPerson().getAddress().value,
                source.getPerson().getTags().stream()
                        .map(JsonAdaptedTag::new)
                        .collect(java.util.stream.Collectors.toList()));
        this.preferredName = source.getNameToUse().toString();
    }

    public String getPreferredName() {
        return preferredName;
    }
}
