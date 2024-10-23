package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Constructs an {@code ObservableList} of {@code Tag}s that are predefined by the user.
 */
public class TagList {
    public static final int MAXIMUM_TAGLIST_SIZE = 30;
    private final ObservableList<Tag> tags;

    public TagList() {
        tags = FXCollections.observableArrayList();
    }

    /**
     * Adds a new tag if it is not already present.
     *
     * @param tag The tag to add.
     * @return true if the tag was added, false if it already exists.
     */
    public boolean addTag(Tag tag) {
        if (tags.contains(tag)) {
            return false;
        }
        tags.add(tag);
        return true;
    }

    /**
     * Deletes a tag if it is already present.
     *
     * @param tag The tag to delete.
     * @return true if the tag was deleted, false if it does not exist.
     */
    public boolean deleteTag(Tag tag) {
        return tags.remove(tag);
    }

    /**
     * Sets the TagList based on a list from Storage.
     *
     * @param tags The tags to add.
     */
    public void setTags(List<Tag> tags) {
        requireNonNull(tags);
        this.tags.setAll(tags);
    }

    /**
     * Returns true if the tag exists in the list.
     */
    public boolean contains(Tag tag) {
        return tags.contains(tag);
    }

    /**
     * Returns true if the size of the tag list is below
     * or equal to the maximum size allowed.
     */
    public boolean checkAcceptableSize(int additionalTags) {
        return tags.size() + additionalTags <= MAXIMUM_TAGLIST_SIZE;
    }

    /**
     * Returns the backing set as an observable {@code ObservableList}.
     */
    public ObservableList<Tag> asObservableList() {
        return tags;
    }

    @Override
    public String toString() {
        if (tags.isEmpty()) {
            return "You have no tags.";
        }
        return String.join(", ", tags.stream().map(Tag::getTagName).toList());
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TagList)) {
            return false;
        }
        TagList otherTagList = (TagList) other;
        return tags.equals(otherTagList.tags);
    }
}
