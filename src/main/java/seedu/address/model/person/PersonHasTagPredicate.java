package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the tags given.
 */
public class PersonHasTagPredicate implements Predicate<Person> {
    private List<Tag> tagList;
    public PersonHasTagPredicate(List<Tag> tagList) {
        this.tagList = tagList;
    }
    @Override
    public boolean test(Person person) {
        return tagList.stream().anyMatch(tag -> tag.equals(person.getTag()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonHasTagPredicate)) {
            return false;
        }

        PersonHasTagPredicate otherPersonHasTagPredicate = (PersonHasTagPredicate) other;
        System.out.println("1" + tagList.equals(otherPersonHasTagPredicate.tagList));
        return tagList.equals(otherPersonHasTagPredicate.tagList);
    }
}
