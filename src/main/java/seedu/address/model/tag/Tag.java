package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.shortcut.ShortCut;


/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} ]*[\\p{Alnum}]?$";
    private static HashMap<String, String> shortCutMap = new HashMap<>();
    private static String allMappings;
    public final String tagName;
    static {
        if (shortCutMap.isEmpty()) {
            allMappings = "no shortcuts assigned";
        } else {
            // If there are mappings, format the mappings as a string and assign to allMappings
            StringBuilder mappingsBuilder = new StringBuilder();
            shortCutMap.forEach((alias, fullTagName) -> {
                mappingsBuilder.append("Alias: ").append(alias)
                        .append(" -> FullTagName: ").append(fullTagName)
                        .append("\n");
            });
            allMappings = mappingsBuilder.toString().trim();
        }
    }
    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        //check if tagname exists in shortCutMap and replace it with that mapped value if exist
        String fullTagName = shortCutMap.getOrDefault(tagName, tagName);
        checkArgument(isValidTagName(fullTagName), MESSAGE_CONSTRAINTS);
        this.tagName = fullTagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    /**
     * Updates the hashmap containing the alias and fullTagName
     * @param model
     */
    public static void updateShortCutMappings(Model model) {
        ObservableList<ShortCut> shortCutList = model.getShortCutList();
        HashMap<String, String> mapping = new HashMap<>();
        for (ShortCut shortCut : shortCutList) {
            // Put alias as key and fullTagName as value in the HashMap
            mapping.put(shortCut.getAlias().toString(), shortCut.getFullTagName().toString());
        }
        shortCutMap = mapping;
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

    public String getTagName() {
        return tagName;
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
        return shortCutMap;
    }
}
