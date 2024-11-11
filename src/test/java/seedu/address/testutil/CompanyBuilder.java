package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Company objects.
 */
public class CompanyBuilder {

    public static final String DEFAULT_NAME = "OpenAI";
    public static final String DEFAULT_PHONE = "00000010";
    public static final String DEFAULT_EMAIL = "openai@example.com";
    public static final String DEFAULT_URL = "www.openai-careers.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong East Ave 6, #08-111";
    public static final String DEFAULT_STATUS = "";
    public static final Bookmark DEFAULT_BOOKMARK = new Bookmark(false);
    public static final String DEFAULT_REMARK = "Leading AI company"; // New default remark

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private CareerPageUrl careerPageUrl;
    private ApplicationStatus applicationStatus;
    private Set<Tag> tags;
    private Remark remark;
    private Bookmark isBookmark;

    /**
     * Creates a {@code CompanyBuilder} with the default details.
     */
    public CompanyBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        careerPageUrl = new CareerPageUrl(DEFAULT_URL);
        applicationStatus = new ApplicationStatus(DEFAULT_STATUS);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
        isBookmark = DEFAULT_BOOKMARK;
    }

    /**
     * Initializes the CompanyBuilder with the data of {@code companyToCopy}.
     */
    public CompanyBuilder(Company companyToCopy) {
        name = companyToCopy.getName();
        phone = companyToCopy.getPhone();
        email = companyToCopy.getEmail();
        address = companyToCopy.getAddress();
        careerPageUrl = companyToCopy.getCareerPageUrl();
        applicationStatus = companyToCopy.getApplicationStatus();
        remark = companyToCopy.getRemark();
        tags = new HashSet<>(companyToCopy.getTags());
        isBookmark = companyToCopy.getIsBookmark();
    }

    /**
     * Sets the {@code Name} of the {@code Company} that we are building.
     */
    public CompanyBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code Company} that we are building.
     */
    public CompanyBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Company} that we are building.
     */
    public CompanyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Company} that we are building.
     */
    public CompanyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Company} that we are building.
     */
    public CompanyBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code CareerPageUrl} of the {@code Company} that we are building.
     */
    public CompanyBuilder withCareerPageUrl(String careerPageUrl) {
        this.careerPageUrl = new CareerPageUrl(careerPageUrl);
        return this;
    }

    /**
     * Sets the {@code ApplicationStatus} of the {@code Company} that we are building.
     */
    public CompanyBuilder withApplicationStatus(String status) {
        this.applicationStatus = new ApplicationStatus(status);
        return this;
    }
    /**
     * Sets the {@code Remark} of the {@code Company} that we are building
     */
    public CompanyBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code isBookmark} of the {@code Company} that we are building
     */
    public CompanyBuilder withIsBookmark(boolean value) {
        this.isBookmark = new Bookmark(value);
        return this;
    }

    /**
     * Builds and returns a {@code Company}.
     */
    public Company build() {
        return new Company(name, phone, email, address, careerPageUrl, applicationStatus, tags, isBookmark, remark);
    }

}
