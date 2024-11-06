package seedu.internbuddy.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.internbuddy.commons.core.LogsCenter;
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
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedCompany.class);
    private static final String INVALID_ISFAVOURITE_MESSAGE = "isFavourite should either be `true` or `false`";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String status;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedApplication> applications = new ArrayList<>();
    private final Boolean isFavourite;

    /**
     * Constructs a {@link JsonAdaptedCompany} with the given company details.
     */
    @JsonCreator
    public JsonAdaptedCompany(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                 @JsonProperty("email") String email, @JsonProperty("address") String address,
                 @JsonProperty("tags") List<JsonAdaptedTag> tags,
                 @JsonProperty("status") String status,
                 @JsonProperty("applications") List<JsonAdaptedApplication> applications,
                 @JsonProperty("isFavourite") Boolean isFavourite) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
        this.isFavourite = isFavourite;
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
        status = source.getStatus().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        applications.addAll(source.getApplications().stream()
                .map(JsonAdaptedApplication::new)
                .toList());
        isFavourite = source.getIsFavourite();
    }

    /**
     * Converts this Jackson-friendly adapted company object into the model's {@link Company} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Company toModelType() throws IllegalValueException {
        logger.info("Attempting to convert to " + Company.class + "...");
        final List<Tag> companyTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            companyTags.add(tag.toModelType());
        }

        final List<Application> modelApplications = new ArrayList<>();
        for (JsonAdaptedApplication application : applications) {
            modelApplications.add(application.toModelType());
        }

        if (name == null) {
            logger.info("Missing `name` field.");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            logger.info("`name` has invalid format.");
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Phone modelPhone;
        if (phone == null) {
            logger.info("`phone` is null, assuming `NO_PHONE` value.");
            modelPhone = Phone.NO_PHONE;
        } else if (!Phone.isValidPhone(phone)) {
            logger.info("`phone` has invalid format.");
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        } else {
            modelPhone = new Phone(phone);
        }

        if (email == null) {
            logger.info("Missing `email` field.");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            logger.info("`email` has invalid format.");
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final Address modelAddress;
        if (address == null) {
            logger.info("`address` is null, assuming `NO_ADDRESS` value.");
            modelAddress = Address.NO_ADDRESS;
        } else if (!Address.isValidAddress(address)) {
            logger.info("`address` has invalid format.");
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        } else {
            modelAddress = new Address(address);
        }

        if (status == null) {
            logger.info("Missing `status` field.");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        } else if (!Status.isValidStatus(status)) {
            logger.info("`status` has invalid format.");
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        final Set<Tag> modelTags = new HashSet<>(companyTags);

        if (isFavourite == null) {
            logger.info("`isFavourite` is missing or has invalid format.");
            throw new IllegalValueException(INVALID_ISFAVOURITE_MESSAGE);
        }
        final Boolean modelIsFavourite = isFavourite;

        return new Company(modelName, modelPhone, modelEmail, modelAddress, modelTags,
            modelStatus, modelApplications, modelIsFavourite);
    }

}
