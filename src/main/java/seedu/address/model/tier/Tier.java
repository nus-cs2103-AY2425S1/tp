package seedu.address.model.tier;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tier in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTierName(String)}
 */
public class Tier {

    public static final String MESSAGE_CONSTRAINTS =
            "The Tiers are gold, silver, bronze and reject. Please use one of them.";

    public final TierEnum tierName;

    /**
     * Constructs a {@code Tier}.
     *
     * @param tierName A valid tier name.
     */
    public Tier(String tierName) {
        requireNonNull(tierName);
        checkArgument(isValidTierName(tierName), MESSAGE_CONSTRAINTS);
        if (tierName.isEmpty()) {
            this.tierName = TierEnum.NA;
        } else {
            this.tierName = TierEnum.valueOf(tierName.toUpperCase());
        }
    }

    /**
     * Returns true if a given string is a valid tier name.
     */
    public static boolean isValidTierName(String test) {
        if (test.isEmpty()) {
            return false;
        }
        for (TierEnum c : TierEnum.values()) {
            if (c.name().equals(test.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tier)) {
            return false;
        }

        Tier otherTier = (Tier) other;
        return tierName.equals(otherTier.tierName);
    }

    @Override
    public int hashCode() {
        return tierName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tierName.toString() + ']';
    }

    /**
     * Format Tier for parsing/decoding. As TierEnum.NA is only for internal use,
     * if tierName equals TierEnum.NA, an empty string is returned
     */
    public String toParsableString() {
        if (tierName.equals(TierEnum.NA)) {
            return "";
        }
        return tierName.toString();
    }

    /**
     * The Enums for what values Tier can take.
     */
    public enum TierEnum {
        GOLD,
        SILVER,
        BRONZE,
        REJECT,
        NA
    }

}
