package seedu.address.model.tier;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tier in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTierName(String)}
 */
public class Tier {

    public static final String MESSAGE_CONSTRAINTS = "Tier can be set to 'gold', 'silver', 'bronze', 'reject' or 'na'. "
            + "Please use one of these options if specifying a tier.";

    public final TierEnum tierName;

    /**
     * Constructs a {@code Tier}.
     *
     * @param tierName A valid tier name.
     */
    public Tier(String tierName) {
        requireNonNull(tierName);
        checkArgument(isValidTierName(tierName), MESSAGE_CONSTRAINTS);
        this.tierName = TierEnum.valueOf(tierName.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid tier name.
     */
    public static boolean isValidTierName(String test) {
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
     * Formats the tier into a string form, without square brackets.
     * @return The string value of the tier without brackets
     */
    public String getValue() {
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
