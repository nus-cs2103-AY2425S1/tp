package spleetwaise.transaction.model.filterpredicate;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import spleetwaise.address.model.person.Person;
import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Tests if a {@code Transaction}'s {@code Person} matches the given {@code Person}.
 */
public class PersonFilterPredicate implements Predicate<Transaction> {

    private final Person personToFilter;

    /**
     * Creates a {@code PersonFilterPredicate} that tests if a {@code Transaction}'s {@code Person} matches the
     * given {@code Person}.
     *
     * @param personToFilter The {@code Person} to filter by.
     */
    public PersonFilterPredicate(Person personToFilter) {
        requireNonNull(personToFilter);
        this.personToFilter = personToFilter;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getPerson().equals(personToFilter);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonFilterPredicate otherPersonFilterPredicate)) {
            return false;
        }

        return personToFilter.equals(otherPersonFilterPredicate.personToFilter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person", personToFilter)
                .toString();
    }
}
