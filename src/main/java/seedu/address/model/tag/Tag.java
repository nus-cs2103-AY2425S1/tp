package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {
    public static final int TAG_MAX_LEN = 16;
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String MESSAGE_CHAR_LIMIT = "Tag names cannot exceed " + TAG_MAX_LEN + " characters!";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        checkArgument(isWithinCharLimit(tagName), MESSAGE_CHAR_LIMIT);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isWithinCharLimit(String test) {
        return test.length() <= TAG_MAX_LEN;
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
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

    /**
     * Utility method to convert Set of Tags to strings delimited by commas
     * @param tagList The Set of Tags to convert
     * @return String of tags delimited by commas
     */
    public static String tagSetToString(Set<Tag> tagList) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Tag> it = tagList.iterator();
        for (int i = 0; i < tagList.size(); i++) {
            Tag t = it.next();
            stringBuilder.append(t);
            if (i != tagList.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Utility method to parse tags delimited by spaces into a Set of Tags
     * @param tagString Input string to parse
     * @return
     */
    public static Set<Tag> stringToTagSet(String tagString) {
        String[] strArr = tagString.split("\\s+"); // regex to catch multiple spaces
        // Catch edge case if strArr is length 1 with element ""
        if (strArr.length == 0 || strArr[0].equals("")) {
            return new HashSet<>();
        } else {
            Set<Tag> tagSet = Arrays.stream(strArr).map(Tag::new).collect(Collectors.toSet());
            return tagSet;
        }
    }

}
