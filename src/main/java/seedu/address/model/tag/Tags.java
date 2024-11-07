package seedu.address.model.tag;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a set of tags that belongs to a Person.
 */
public class Tags {
    public final Set<Tag> tags;
    public Tags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Tags() {
        this.tags = new HashSet<>();
    }

    /**
     * Returns true if any of otherTags already exist in the existing tag,
     * regardless of upper or lower case.
     */
    public boolean tagExists(Tags otherTags) {
        Set<String> tagNamesLower = tags.stream()
                .map(tag -> tag.tagName.toLowerCase())
                .collect(Collectors.toSet());

        Set<String> otherTagsLower = otherTags.tags.stream()
                .map(tag -> tag.tagName.toLowerCase())
                .collect(Collectors.toSet());

        return tagNamesLower.stream()
                .anyMatch(otherTagsLower::contains);
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    /**
     * Adds all tags to existing tags.
     */
    public void addAllTags(Tags tags) {
        this.tags.addAll(tags.getTags());
    }

    /**
     * Removes all specified tags previously in existing tags.
     */
    public void removeAllTags(Tags tags) {
        this.tags.removeAll(tags.getTags());
    }
}
