package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.role.Member;

/**
 * Tests that a {@code Person}'s {@code Role} includes {@code Member}.
 */
public class RoleIsMemberPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return person.getRoles().stream().anyMatch(x -> x.equals(new Member()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RoleIsMemberPredicate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", Member.MEMBER_ROLE).toString();
    }
}
