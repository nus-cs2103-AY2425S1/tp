package seedu.address.model.restaurant;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Restaurant in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Restaurant {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Price price;
    private boolean isFavourite;

    /**
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);

        // Price is set to 0.0 for now, will have functionality added in the next milestone
        this.price = new Price(0.0);

        // Default to not favourite
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

    public Price getPrice() {
        return price;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable tag set without $, $$, $$$ or $$$$, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    public Set<Tag> getTagsWithoutPrice() {
        Set<Tag> tagsWithoutPrice = new HashSet<>();
        for (Tag tag : tags) {
            if (!tag.tagName.equals("$")
                    && !tag.tagName.equals("$$")
                    && !tag.tagName.equals("$$$")
                    && !tag.tagName.equals("$$$$")) {
                tagsWithoutPrice.add(tag);
            }
        }
        return Collections.unmodifiableSet(tagsWithoutPrice);
    }

    /**
     * Returns an immutable tag set with only $, $$, $$$ or $$$$, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    public Set<Tag> getPriceTags() {
        Set<Tag> priceTags = new HashSet<>();
        for (Tag tag : tags) {
            if (tag.tagName.equals("$")
                    || tag.tagName.equals("$$")
                    || tag.tagName.equals("$$$")
                    || tag.tagName.equals("$$$$")) {
                priceTags.add(tag);
            }
        }
        return Collections.unmodifiableSet(priceTags);
    }

    /**
     * Returns true if both restaurants have the same name.
     * This defines a weaker notion of equality between two restaurants.
     */
    public boolean isSameRestaurant(Restaurant otherRestaurant) {
        if (otherRestaurant == this) {
            return true;
        }

        return otherRestaurant != null
                && otherRestaurant.getName().equals(getName());
    }

    /**
     * Returns the boolean attribute isFavourite.
     */
    public boolean isFavourite() {
        return isFavourite;
    }

    /**
     * Sets the boolean attribute isFavourite based on the favourite parameter.
     */
    public void setFavourite(boolean favourite) {
        this.isFavourite = favourite;
    }

    /**
     * Returns true if both restaurants have the same identity and data fields.
     * This defines a stronger notion of equality between two restaurants.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Restaurant)) {
            return false;
        }

        Restaurant otherRestaurant = (Restaurant) other;
        return name.equals(otherRestaurant.name)
                && phone.equals(otherRestaurant.phone)
                && email.equals(otherRestaurant.email)
                && address.equals(otherRestaurant.address)
                && tags.equals(otherRestaurant.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
