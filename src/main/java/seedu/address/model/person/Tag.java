package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; tag code is valid as declared in {@link #isValidTagCode(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tag must be one of the following codes: N, BP, BC, TP, TC, A, R";
    public static final Map<String, String> TAG_OPTIONS = new HashMap<>() {
        {
            put("N", "New");
            put("BP", "Behavioral Interview in Progress");
            put("BC", "Behavioral Interview Confirmed");
            put("TP", "Technical Interview in Progress");
            put("TC", "Technical Interview Confirmed");
            put("A", "Accepted");
            put("R", "Rejected");
        }
    };

    // Create a list of keys in order of priority
    private static final List<String> PRIORITY_ORDER = Arrays.asList("N", "BP", "BC", "TP", "TC", "A", "R");
    public static final Comparator<Tag> TAG_COMPARATOR = Comparator.comparingInt(tag ->
            PRIORITY_ORDER.indexOf(tag.tagCode));

    public final String tagCode;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagCode A valid tag name.
     */
    public Tag(String tagCode) {
        requireNonNull(tagCode);
        String trimmedTagCode = tagCode.trim().toUpperCase();
        checkArgument(isValidTagCode(trimmedTagCode), MESSAGE_CONSTRAINTS);
        this.tagCode = trimmedTagCode;
    }

    /**
     * Returns true if a given string is a valid tag code.
     */
    public static boolean isValidTagCode(String test) {
        return TAG_OPTIONS.containsKey(test.toUpperCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagCode.equals(otherTag.tagCode);
    }

    @Override
    public int hashCode() {
        return tagCode.hashCode();
    }

    /**
     * Format state as text for viewing.
     * Returns the long form of the tag based on the short form.
     */
    @Override
    public String toString() {
        return TAG_OPTIONS.get(tagCode);
    }

}
