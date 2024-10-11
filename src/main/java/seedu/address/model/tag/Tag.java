package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} ]*[\\p{Alnum}]?$";
    private static HashMap<String, String> dietaryRestrictionsMappings = new HashMap<>();

    private static String allMappings;

    static {
        dietaryRestrictionsMappings.put("v", "Vegan");
        dietaryRestrictionsMappings.put("vg", "Vegetarian");
        dietaryRestrictionsMappings.put("gf", "Gluten free");
        dietaryRestrictionsMappings.put("l", "Lactose Intolerant");
        dietaryRestrictionsMappings.put("na", "Nut Allergy");
        dietaryRestrictionsMappings.put("sa", "Soy Allergy");
        dietaryRestrictionsMappings.put("p", "Pescatarian");

        StringBuilder mappingsBuilder = new StringBuilder("Current Dietary Restriction Tags:\n");
        for (Map.Entry<String, String> entry : dietaryRestrictionsMappings.entrySet()) {
            mappingsBuilder.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        allMappings = mappingsBuilder.toString();
    }


    public final String tagName;
    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        //check if tagname exists in dietary_restrictions_map and replace it with that mapped value if exist
        String fullTagName = dietaryRestrictionsMappings.getOrDefault(tagName, tagName);
        checkArgument(isValidTagName(fullTagName), MESSAGE_CONSTRAINTS);
        this.tagName = fullTagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
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
     * @return string representation of all mappings
     */
    public static String getStringMappings() {
        return allMappings;
    }
    /**
     * @return hashmap of all tags and shortcuts
     */
    public static HashMap<String, String> getDietaryRestrictionsMappings() {
        return dietaryRestrictionsMappings;
    }

    /**
     * @param key is the shortcut users would want to use for dietary restriction
     * @param value is the actual value to be displayed
     */
    public static void addDietaryRestrictionMapping(String key, String value) {
        dietaryRestrictionsMappings.put(key, value);
    }

}
