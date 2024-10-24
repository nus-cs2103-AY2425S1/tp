package seedu.address.model.company;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;


/**
 * Represents a Company in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Company {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final CareerPageUrl careerPageUrl;
    private final Bookmark isBookmark;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Company(Name name, Phone phone, Email email, Address address, CareerPageUrl careerPageUrl, Set<Tag> tags,
                   Bookmark isBookmark) {
        requireAllNonNull(name, phone, email, address, tags, isBookmark);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.careerPageUrl = careerPageUrl;
        this.tags.addAll(tags);
        this.isBookmark = isBookmark;
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

    public CareerPageUrl getCareerPageUrl() {
        return careerPageUrl;
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Bookmark getIsBookmark() {
        return isBookmark;
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
                && careerPageUrl.equals(otherCompany.careerPageUrl)
                && tags.equals(otherCompany.tags)
                && isBookmark.equals(otherCompany.isBookmark);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, careerPageUrl, tags, isBookmark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("url", careerPageUrl)
                .add("tags", tags)
                .add("bookmark", isBookmark)
                .toString();
    }

}
