package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class CompoundedPredicate implements Predicate<Person> {

    NameContainsKeywordsPredicate namePredicate;
    OrgContainsKeywordsPredicate orgPredicate;

    public CompoundedPredicate(NameContainsKeywordsPredicate namePredicate, OrgContainsKeywordsPredicate orgPredicate) {
        this.namePredicate = namePredicate;
        this.orgPredicate = orgPredicate;
    }
    @Override
    public boolean test(Person person) {
        return namePredicate.test(person) || orgPredicate.test(person);
    }
}
