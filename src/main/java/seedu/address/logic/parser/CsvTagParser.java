package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Parses tag data from a CSV field, extracting tags as a set of Tag objects.
 */
public class CsvTagParser {
    /**
     * Parses the tags field from a CSV field.
     *
     * @param tagField string representing tags, separated by commas.
     * @return a set of Tag objects.
     */
    public static Set<Tag> parseTags(String tagField) {
        requireNonNull(tagField);
        Set<Tag> tags = new HashSet<>();
        String trimmedTagField = tagField.trim();
        if (trimmedTagField.isEmpty()) {
            return tags;
        }
        // Split on commas first, then remove the square brackets from each tag
        String[] tagArray = trimmedTagField.split(",");
        for (String tag : tagArray) {
            tag = tag.trim(); // Remove any extra spaces
            if (tag.startsWith("[") && tag.endsWith("]")) {
                tag = tag.substring(1, tag.length() - 1); // Remove the brackets
            }
            if (!tag.isEmpty()) {
                tags.add(new Tag(tag));
            }
        }
        return tags;
    }
}
