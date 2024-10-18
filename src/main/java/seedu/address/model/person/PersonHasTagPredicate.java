package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.function.Predicate;

public class PersonHasTagPredicate implements Predicate<Person> {
    ArrayList<Tag> tagList;
    public PersonHasTagPredicate(ArrayList<Tag> tagList) {
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
        return tagList.equals(otherPersonHasTagPredicate.tagList);
    }
}
