package seedu.address.model.tag;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The manager of all the tags currently being used.
 */
public class TagManager {

    private final HashMap<String, Tag> tagMap;

    /**
     * Constructs a new TagManager to track Tag usages.
     */
    public TagManager() {
        this.tagMap = new HashMap<>();
    }

    /**
     * Returns true if the Tag is being tracked in the TagManager.
     */
    public boolean containsTag(Tag t) {
        String tagName = t.tagName;
        return tagMap.containsKey(tagName);
    }

    /**
     * Returns the Tag with the corresponding tag name.
     * If the tag already exists, return the stored tag object.
     * Else if the tag does not yet exist, create a new tag and return it. <br>
     * <strong>Warning:</strong> only use this method if you want to add or increase tag tracking.
     * @param tagName String of the tag name
     * @return The new or existing tag with the corresponding tag name
     */
    public Tag getOrCreateTag(String tagName) {

        Tag tagToIncrement = tagMap.computeIfAbsent(tagName, Tag::new);
        tagToIncrement.incrementOccurrences();
        return tagToIncrement;
    }

    public Tag getOrCreateTag(Tag tag) {
        if (!tagMap.containsKey(tag.tagName)) {
            tagMap.put(tag.tagName, new Tag(tag.tagName, tag.getTagCategory()));
        }
        Tag tagToIncrement = tagMap.get(tag.tagName);
        tagToIncrement.incrementOccurrences();
        return tagToIncrement;
    }

    /**
     * Returns a set of tags that are being tracked by TagManager, corresponding to the input set.
     */
    public Set<Tag> getOrCreateTag(Set<Tag> tags) {
        Set<Tag> trackedSet = new HashSet<>();
        for (Tag t : tags) {
            trackedSet.add(getOrCreateTag(t));
        }
        return trackedSet;
    }

    /**
     * Reduces the usage count of the tag and removes it if no more occurrences remain.
     */
    public void removeTagOccurrence(Tag t) {
        String tagName = t.tagName;
        Tag tagToDecrement = tagMap.get(tagName);
        assert tagToDecrement != null;
        if (tagToDecrement.getOccurrences() <= 1) {
            tagMap.remove(tagToDecrement.tagName);
            return;
        }
        tagToDecrement.decrementOccurrences();
    }

    /**
     * Reduces the usage count of each tag in the given set and removes it if no more occurrences remain.
     */
    public void removeTagOccurrence(Set<Tag> tags) {
        for (Tag t : tags) {
            removeTagOccurrence(t);
        }
    }

    /**
     * Returns the stored category of the tag with same name as the passed {@code Tag t}.
     */
    public TagCategory getTagCategory(Tag t) {
        String tagName = t.tagName;
        return tagMap.get(tagName).getTagCategory();
    }

    /**
     * Set the category of a tag.
     * Assumes that the tag already exists in the TagManager.
     */
    public void setTagCategory(String tagName, TagCategory cat) {
        Tag tag = tagMap.get(tagName);
        tag.setTagCategory(cat);
    }

    /**
     * Clears the TagManager of all stored tags to an empty state.
     */
    public void clearAllTags() {
        tagMap.clear();
    }

    /**
     * Remove all the tags from the passed set, regardless of how many occurrences remain.
     * @see TagManager#removeTagOccurrence(Set) removeTagOccurrence
     */
    public void removeTags(Set<Tag> tagSet) {
        for (Tag t : tagSet) {
            tagMap.remove(t.tagName);
        }
    }

    /**
     * Returns the existing tags as a Collection.
     */
    public Collection<Tag> asTagList() {
        return tagMap.values();
    }

}
