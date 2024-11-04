package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;
import seedu.address.model.tag.TagManager;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final TagManager tagManager = new TagManager();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasDuplicateInfo);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        Set<Tag> trackedTags = addTagsToManager(toAdd.getTags());
        toAdd = toAdd.setAllTags(trackedTags);
        internalList.add(toAdd);
    }

    /**
     * Adds a person to the specific position of the list.
     * The index must be valid
     */
    public void add(int ind, Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        Set<Tag> trackedTags = addTagsToManager(toAdd.getTags());
        toAdd = toAdd.setAllTags(trackedTags);
        internalList.add(ind, toAdd);
    }

    /**
     * Adds tags to the tagManager to be tracked.
     * Returns a set of tracked tag objects.
     */
    private Set<Tag> addTagsToManager(Set<Tag> tagSet) {
        Set<Tag> deduplicatedTags = new HashSet<>();
        for (Tag tag : tagSet) {
            Tag trackedTag = tagManager.getOrCreateTag(tag);
            deduplicatedTags.add(trackedTag);
        }
        return deduplicatedTags;
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }
        tagManager.removeTagOccurrence(target.getTags());
        Set<Tag> trackedTags = addTagsToManager(editedPerson.getTags());
        editedPerson = editedPerson.setAllTags(trackedTags);

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
        tagManager.removeTagOccurrence(toRemove.getTags());
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }
        tagManager.clearAllTags();
        List<Person> newPersons = new ArrayList<>();
        for (Person p : persons) {
            Set<Tag> trackedTags = tagManager.getOrCreateTag(p.getTags());
            newPersons.add(p.setAllTags(trackedTags));
        }
        internalList.setAll(newPersons);
    }

    /**
     * Deletes a tag from a person.
     */
    public void deletePersonTag(Person p, Tag t) {
        requireNonNull(p);
        Person replace = p.removeTag(t);
        // NOTE: no need to remove tag from tagManager here, setPerson takes care of it.
        setPerson(p, replace);
    }

    /**
     * Adds a set of tag to a person.
     */
    public void addPersonTags(Person p, Set<Tag> t) {
        requireNonNull(p);
        // remove tags already added to p
        Set<Tag> uniqueTags = filterUniqueTags(p.getTags(), t);
        Person replace = p.addTag(uniqueTags);
        // Note: no need to add tag to tagManager here, setPerson takes care of it.
        setPerson(p, replace);
    }

    private Set<Tag> filterUniqueTags(Set<Tag> fixed, Set<Tag> toFilter) {
        Set<Tag> uniqueTags = new HashSet<>();
        for (Tag t : toFilter) {
            if (!fixed.contains(t)) {
                uniqueTags.add(t);
            }
        }
        return uniqueTags;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the tag list of the Persons recorded.
     */
    public ObservableList<Tag> asTagList() {
        return FXCollections.observableArrayList(tagManager.asTagList());
    }

    /**
     * Updates the category of all occurrences of a {@code Tag}.
     * @param t {@code Tag} to be updated
     * @param cat updated {@code TagCategory}
     */
    public void updateTagCategory(Tag t, TagCategory cat) {
        tagManager.setTagCategory(t.tagName, cat);
    }

    /**
     * Returns the {@TagCategory} of a tracked {@Tag}.
     */
    public TagCategory getTagCategory(Tag t) {
        return tagManager.getTagCategory(t);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
