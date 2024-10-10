package seedu.address.model.tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Constructs a set of {@code Tag}s that are predefined by the user.
 */
public class TagList {
    private final Set<Tag> tags;

    public TagList() {
        tags = new HashSet<>();
    }

    /**
     * Adds a new tag if it is not already present.
     *
     * @param tag The tag to add.
     * @return true if the tag was added, false if it already exists.
     */
    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }

    /**
     * Returns true if the tag exists in the list.
     */
    public boolean contains(Tag tag) {
        return tags.contains(tag);
    }

    @Override
    public String toString() {
        return String.join(", ", tags.stream().map(Tag::getTagName).toList());
    }
}
