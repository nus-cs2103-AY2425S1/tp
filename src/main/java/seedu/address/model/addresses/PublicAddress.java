package seedu.address.model.addresses;

import java.util.Objects;

/**
 * Represents a public address in the address book.
 */
public abstract class PublicAddress {

    public final String address;

    public final String tag;

    /**
     * Constructs a {@code PublicAddress}
     *
     * @param address A valid public address
     * @param tag     An identifier for the PublicAddress
     */
    public PublicAddress(String address, String tag) {
        this.address = address;
        this.tag = tag;
    }

    public abstract Network getNetwork();

    @Override
    public String toString() {
        return address;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PublicAddress)) {
            return false;
        }

        PublicAddress otherPublicAddress = (PublicAddress) other;
        return address.equals(otherPublicAddress.address) && tag.equals(otherPublicAddress.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, tag);
    }

}
