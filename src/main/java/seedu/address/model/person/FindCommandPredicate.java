package seedu.address.model.person;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * A predicate class that use 4 different predicates to check a person
 */
public class FindCommandPredicate implements Predicate<Person> {
    private Predicate<Person> combinedpredicates = person -> true;
    private NameContainsKeywordsPredicate namePredicate;
    private PhonePredicate phonePredicate;
    private RoomNumberPredicate roomNumberPredicate;
    private TagContainsKeywordsPredicate tagPredicate;

    /**
     * create a FindCommandPredicate with 4 different kinds of predicates for searching
     */
    public FindCommandPredicate() {
        this.namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("null"));
        this.phonePredicate = new PhonePredicate("000");
        this.roomNumberPredicate = new RoomNumberPredicate("00-0000");
        this.tagPredicate = new TagContainsKeywordsPredicate(Arrays.asList("null"));
    }

    /**
     * Adds a new NameContainsKeywordsPredicate to the list.
     * @param predicate The predicate to add.
     */
    public void addNameContainsKeywordsPredicate(NameContainsKeywordsPredicate predicate) {
        combinedpredicates = combinedpredicates.and(predicate);
        namePredicate = predicate;
    }

    /**
     * Adds a new TagContainsKeywordsPredicate to the list.
     * @param predicate The predicate to add.
     */
    public void addTagContainsKeywordsPredicate(TagContainsKeywordsPredicate predicate) {
        combinedpredicates = combinedpredicates.and(predicate);
        tagPredicate = predicate;
    }

    /**
     * Adds a new PhonePredicate to the list.
     * @param predicate The predicate to add.
     */
    public void addPhonePredicate(PhonePredicate predicate) {
        combinedpredicates = combinedpredicates.and(predicate);
        phonePredicate = predicate;
    }

    /**
     * Adds a new RoomNumberPredicate to the list.
     * @param predicate The predicate to add.
     */
    public void addRoomNumberPredicate(RoomNumberPredicate predicate) {
        combinedpredicates = combinedpredicates.and(predicate);
        roomNumberPredicate = predicate;
    }


    /**
     * Evaluates this predicate on the given person.
     * @param person the person to evaluate the predicate on
     * @return true if the person matches all predicates, otherwise false
     */
    @Override
    public boolean test(Person person) {
        return combinedpredicates.test(person);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FindCommandPredicate)) {
            return false;
        }
        FindCommandPredicate other = (FindCommandPredicate) obj;

        return namePredicate.equals(other.namePredicate) && phonePredicate
                .equals(other.phonePredicate) && roomNumberPredicate
                .equals(other.roomNumberPredicate) && tagPredicate.equals(other.tagPredicate);
    }

    @Override
    public int hashCode() {
        return combinedpredicates.hashCode();
    }

    @Override
    public String toString() {
        return "FindPredicate{" + "predicates=" + combinedpredicates + '}';
    }
}
