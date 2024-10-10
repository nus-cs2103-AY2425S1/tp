package seedu.address.model.goods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a goods name for stored goods.
 * Garauntees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class GoodsName {
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private String name;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public GoodsName(String goodsName) {
        requireNonNull(goodsName);
        checkArgument(isValidName(goodsName), MESSAGE_CONSTRAINTS);
        this.name = goodsName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GoodsName)) {
            return false;
        }

        GoodsName otherName = (GoodsName) other;
        return name.equals(otherName.name);
    }
}
