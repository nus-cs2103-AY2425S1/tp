package seedu.address.storage.person;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAdaptedTag;

/**
 * Jackson friendly version of {@link Vendor}
 */
public class JsonAdaptedVendor extends JsonAdaptedPerson {
    private final String company;

    /**
     * Constructs a {@code JsonAdaptedVendor} with the given vendor details.
     */
    @JsonCreator
    public JsonAdaptedVendor(@JsonProperty("type") String type,
             @JsonProperty("name") String name,
             @JsonProperty("phone") String phone,
             @JsonProperty("email") String email,
             @JsonProperty("address") String address,
             @JsonProperty("company") String company,
             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(name, phone, email, address, tags);
        setType(type);
        this.company = company;
    }

    /**
     * Converts a given {@code Vendor} into this class for Jackson use.
     */
    public JsonAdaptedVendor(Vendor source) {
        super(source);
        this.company = source.getCompany().value;
    }

    /**
     * Validates the fields for the {@code Vendor}
     *
     * @throws IllegalValueException If a particular field is found to be invalid.
     */
    public void checkVendorFields() throws IllegalValueException {
        checkPersonFields();
        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
    }

    public Company getModelCompany() {
        return new Company(company);
    }

    /**
     * Converts this Jackson-friendly version of {@code Vendor} into the model's version of {@code Vendor}.
     */
    @Override
    public Vendor toModelType() throws IllegalValueException {
        checkVendorFields();

        Name modelName = getModelName();
        Phone modelPhone = getModelPhone();
        Email modelEmail = getModelEmail();
        Address modelAddress = getModelAddress();
        Set<Tag> modelTags = getModelTags();
        Company modelCompany = getModelCompany();
        return new Vendor(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelCompany);
    }

}
