package seedu.internbuddy.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.internbuddy.commons.exceptions.IllegalValueException;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Address;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Email;
import seedu.internbuddy.model.company.Phone;
import seedu.internbuddy.model.company.Status;
import seedu.internbuddy.model.name.Name;
import seedu.internbuddy.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Company}.
 */
public class JsonAdaptedCompany {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Company's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String status;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedApplication> applications = new ArrayList<>();
    private final Boolean isFavourite;
    private final Boolean isShowingDetails;

    /**
     * Constructs a {@link JsonAdaptedCompany} with the given company details.
     */
    @JsonCreator
    public JsonAdaptedCompany(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                 @JsonProperty("email") String email, @JsonProperty("address") String address,
                 @JsonProperty("tags") List<JsonAdaptedTag> tags,
                 @JsonProperty("status") String status,
                 @JsonProperty("applications") List<JsonAdaptedApplication> applications,
                 @JsonProperty("isFavourite") Boolean isFavourite,
                 @JsonProperty("isShowingDetails") Boolean isShowingDetails) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
        this.isFavourite = isFavourite;
        this.isShowingDetails = isShowingDetails;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (applications != null) {
            this.applications.addAll(applications);
        }
    }

    /**
     * Converts a given {@link Company} into this class for Jackson use.
     */
    public JsonAdaptedCompany(Company source) {
        name = source.getName().fullName;
        phone = source.getPhone().getTrueValue();
        email = source.getEmail().value;
        address = source.getAddress().getTrueValue();
        status = source.getStatus().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        applications.addAll(source.getApplications().stream()
                .map(JsonAdaptedApplication::new)
                .toList());
        isFavourite = source.getIsFavourite();
        isShowingDetails = source.getIsShowingDetails();
    }

    /**
     * Converts this Jackson-friendly adapted company object into the model's {@link Company} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Company toModelType() throws IllegalValueException {
        final List<Tag> companyTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            companyTags.add(tag.toModelType());
        }

        final List<Application> modelApplications = new ArrayList<>();
        for (JsonAdaptedApplication application : applications) {
            modelApplications.add(application.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Phone modelPhone;
        if (phone == null) {
            modelPhone = Phone.NO_PHONE;
        } else if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        } else {
            modelPhone = new Phone(phone);
        }

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final Address modelAddress;
        if (address == null) {
            modelAddress = Address.NO_ADDRESS;
        } else if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        } else {
            modelAddress = new Address(address);
        }

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        final Status modelStatus = new Status(status);

        final Set<Tag> modelTags = new HashSet<>(companyTags);

        final Boolean modelIsFavourite = isFavourite;

        final Boolean modelIsShowingDetails = isShowingDetails;

        return new Company(modelName, modelPhone, modelEmail, modelAddress, modelTags,
            modelStatus, modelApplications, modelIsFavourite, modelIsShowingDetails);
    }

}
