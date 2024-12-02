package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Remark} contains keywords given.
 */
public class RemarkPredicate implements Predicate<Person> {
    private final String remark;

    public RemarkPredicate(String remark) {
        this.remark = remark.toLowerCase();
    }

    @Override
    public boolean test(Person person) {
        if (remark.equals("")) {
            return person.getRemark().value.equals("");
        }
        return person.getRemark().value.toLowerCase().contains(remark);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkPredicate)) {
            return false;
        }

        RemarkPredicate otherPredicate = (RemarkPredicate) other;
        return remark.equals(otherPredicate.remark);
    }
}
