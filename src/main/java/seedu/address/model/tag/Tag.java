package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.shortcut.ShortCut;


/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric and within 30 characters";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} ]{0,28}[\\p{Alnum}]?$";
    private static final Logger logger = LogsCenter.getLogger(Tag.class);
    private static HashMap<String, String> shortCutMap = new HashMap<>();
    public final String tagName;
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
     */
    public static void updateShortCutMappings(Model model) {
        ObservableList<ShortCut> shortCutList = model.getShortCutList();
        HashMap<String, String> mapping = new HashMap<>();
        for (ShortCut shortCut : shortCutList) {
            // Put alias as key and fullTagName as value in the HashMap
            mapping.put(shortCut.getAlias().toString(), shortCut.getFullTagName().toString());
        }
        shortCutMap = mapping;
        logger.info("ShortCut Mappings updated");
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
     * @return hashmap of all tags and shortcuts
     */
    public static HashMap<String, String> getShortCutMappings() {
        return shortCutMap;
    }
}
