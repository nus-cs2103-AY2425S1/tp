package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.company.Address;
import seedu.address.model.company.ApplicationStatus;
import seedu.address.model.company.Bookmark;
import seedu.address.model.company.CareerPageUrl;
import seedu.address.model.company.Company;
import seedu.address.model.company.Email;
import seedu.address.model.company.Name;
import seedu.address.model.company.Phone;
import seedu.address.model.company.Remark;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Company}.
 */
class JsonAdaptedCompany {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Company's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String careerPageUrl;
    private final String remark;
    private final String applicationStatus;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    private final Bookmark isBookmark;

    /**
     * Constructs a {@code JsonAdaptedCompany} with the given company details.
     */
    @JsonCreator
    public JsonAdaptedCompany(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("email") String email, @JsonProperty("address") String address,
                              @JsonProperty("careerPageUrl") String careerPageUrl,
                              @JsonProperty("applicationStatus") String status,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("bookmark") Bookmark isBookmark,
                              @JsonProperty("remark") String remark) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.careerPageUrl = careerPageUrl;
        this.applicationStatus = status;
        this.address = address;
        this.remark = remark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.isBookmark = isBookmark;
    }

    /**
     * Converts a given {@code Company} into this class for Jackson use.
     */
    public JsonAdaptedCompany(Company source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        careerPageUrl = source.getCareerPageUrl().value;
        applicationStatus = source.getApplicationStatus().value;
        address = source.getAddress().value;
        remark = source.getRemark().value;
        tags.addAll(source.getTags().stream()
                .map(tag -> new JsonAdaptedTag(tag))
                .collect(Collectors.toList()));
        isBookmark = source.getIsBookmark();
    }

    /**
     * Converts this Jackson-friendly adapted company object into the model's
     * {@code Company} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted company.
     */
    public Company toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : this.tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (careerPageUrl == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CareerPageUrl.class.getSimpleName()));
        }
        if (!CareerPageUrl.isValidAddress(careerPageUrl)) {
            throw new IllegalValueException(CareerPageUrl.MESSAGE_CONSTRAINTS);
        }
        final CareerPageUrl modelCareerPageUrl = new CareerPageUrl(careerPageUrl);

        if (applicationStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ApplicationStatus.class.getSimpleName()));
        }
        final ApplicationStatus modelApplicationStatus = new ApplicationStatus(applicationStatus);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (isBookmark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Bookmark.class.getSimpleName()));
        }
        final Bookmark modelBookmark = new Bookmark(isBookmark.getIsBookmarkValue());

        return new Company(modelName, modelPhone, modelEmail, modelAddress, modelCareerPageUrl,
                modelApplicationStatus, modelTags, modelBookmark, modelRemark);
    }
}
