package seedu.address.model.tag;

import java.util.HashMap;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.TagColors;


/**
 * A class to handle the tracking of currently used Tags in the AddressBook
 */
public class ActiveTags {
    private final HashMap<Tag, Integer> tagMap; // Maps Tags to their respective number of occurrences
    private final ObservableMap<String, String> tagColorMap; // Maps Tag names to their respective colors
    private final String[] tagColors;

    /**
     * Initialize tagMap and tagColors
     * @param tagMap
     * @param tagColors
     */
    public ActiveTags(HashMap<Tag, Integer> tagMap, TagColors tagColors) {
        this.tagMap = tagMap;
        this.tagColors = tagColors.getTagColors();
        this.tagColorMap = FXCollections.observableHashMap();

        for (Tag t:tagMap.keySet()) {
            assignTagColor(t);
        }
    }

    /**
     * Adds Tags to ActiveTags, and updates their respective occurrences
     * @param tagSet The Set of Tags to update
     */
    public void incrementTags(Set<Tag> tagSet) {
        for (Tag t:tagSet) {
            if (tagMap.containsKey(t)) {
                Integer i = tagMap.get(t);
                tagMap.replace(t, ++i);
            } else {
                // Add new entry if Tag does not exist in HashMap
                tagMap.put(t, 1);
                assignTagColor(t);
            }
        }
    }

    /**
     * Removes Tags from ActiveTags, and updates their respective occurrences
     * @param tagSet The Set of Tag to update
     */
    public void decrementTags(Set<Tag> tagSet) {
        for (Tag t : tagSet) {
            Integer i = tagMap.get(t);
            if (i == 1) {
                // Delete entry if Tag has no more occurrences
                tagMap.remove(t);
                unassignTagColor(t);
            } else {
                tagMap.replace(t, --i);
            }
        }
    }

    private void assignTagColor(Tag t) {
        String tagName = t.tagName;
        if (!tagColorMap.containsKey(tagName)) {
            String assigned = tagColors[tagColorMap.size() % tagColors.length]; // If more tags than colors, loops back
            tagColorMap.put(tagName.toLowerCase(), assigned);
        }
    }

    private void unassignTagColor(Tag t) {
        String tagName = t.tagName;
        tagColorMap.remove(tagName.toLowerCase());
    }

    public HashMap<Tag, Integer> getMap() {
        return this.tagMap;
    }

    public ObservableMap<String, String> getTagColorMap() {
        return this.tagColorMap;
    }
}
