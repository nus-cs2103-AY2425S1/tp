package seedu.internbuddy.model.company;

import static seedu.internbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.internbuddy.commons.util.ToStringBuilder;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.name.Name;
import seedu.internbuddy.model.tag.Tag;

/**
 * Represents a Company in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Company {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Status status;
    private final List<Application> applications;
    private final Set<Tag> tags = new HashSet<>();
    private final Boolean isFavourite;

    /**
     * Every field must be present and not null.
     */
    public Company(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Status status,
               List<Application> applications, Boolean isFavourite) {
        requireAllNonNull(name, phone, email, address, tags, status, isFavourite);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.status = status;
        this.applications = applications;
        this.isFavourite = isFavourite;
    }

    /**
     * Every field must be present and not null.
     */
    public Company(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Status status,
               List<Application> applications) {
        requireAllNonNull(name, phone, email, address, tags, status);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.status = status;
        this.applications = applications;
        this.isFavourite = false;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Status getStatus() {
        return status;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<Application> getApplications() {
        return Collections.unmodifiableList(applications);
    }

    public String getAppNameString() {
        StringBuilder appNames = new StringBuilder();
        for (Application application : applications) {
            appNames.append(application.getName().fullName).append(" ");
        }
        return appNames.toString();
    }

    public String getTagsString() {
        StringBuilder tagNames = new StringBuilder();
        for (Tag tag : tags) {
            tagNames.append(tag.tagName).append(" ");
        }
        return tagNames.toString();
    }

    public String getAppDescriptionString() {
        StringBuilder appDescriptions = new StringBuilder();
        for (Application application : applications) {
            appDescriptions.append(application.getDescription().fullDescription).append(" ");
        }
        return appDescriptions.toString();
    }

    /**
     * Returns true if both companies have the same name.
     * This defines a weaker notion of equality between two companies.
     */
    public boolean isSameCompany(Company otherCompany) {
        if (otherCompany == this) {
            return true;
        }

        return otherCompany != null
                && otherCompany.getName().equals(getName());
    }

    /**
     * Returns true if both companies have the same identity and data fields.
     * This defines a stronger notion of equality between two companies.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return name.equals(otherCompany.name)
                && phone.equals(otherCompany.phone)
                && email.equals(otherCompany.email)
                && address.equals(otherCompany.address)
                && status.equals(otherCompany.status)
                && tags.equals(otherCompany.tags)
                && applications.equals(otherCompany.applications)
                && isFavourite.equals(otherCompany.isFavourite);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, status, tags, applications);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("status", status)
                .add("tags", tags)
                .add("applications", applications)
                .toString();
    }

}
