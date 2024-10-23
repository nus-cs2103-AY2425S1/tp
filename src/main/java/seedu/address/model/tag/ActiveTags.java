package seedu.address.model.tag;

import java.util.HashMap;
import java.util.Set;

/**
 * A class to handle the tracking of currently used Tags in the AddressBook
 */
public class ActiveTags {
    private final HashMap<Tag, Integer> tagMap; // Maps Tags to their respective number of occurrences

    public ActiveTags(HashMap<Tag, Integer> tagMap) {
        this.tagMap = tagMap;
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
            }
        }
    }

    /**
     * Removes Tags from ActiveTags, and updates their respective occurrences
     * @param tagSet The Set of Tag to update
     */
    public void decrementTag(Set<Tag> tagSet) {
        for (Tag t : tagSet) {
            Integer i = tagMap.get(t);
            if (i == 1) {
                // Delete entry if Tag has no more occurrences
                tagMap.remove(t);
            } else {
                tagMap.replace(t, --i);
            }
        }
    }

    public HashMap<Tag, Integer> getMap() {
        return this.tagMap;
    }


}
