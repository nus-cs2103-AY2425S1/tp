package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Combines two predicates: one for name and one for phone.
 */
public class CombinedPredicate implements Predicate<Person> {
    private final Predicate<Person> namePredicate;
    private final Predicate<Person> phonePredicate;

    /**
     * Constructs a {@code CombinedPredicate} that combines a name predicate and a phone predicate.
     * The combined predicate will return {@code true} if either the name predicate or the phone predicate
     * returns {@code true}.
     *
     * @param namePredicate A predicate that tests if a person's name matches any of the specified keywords.
     * @param phonePredicate A predicate that tests if a person's phone number matches any of the specified keywords.
     */
    public CombinedPredicate(Predicate<Person> namePredicate, Predicate<Person> phonePredicate) {
        this.namePredicate = namePredicate;
        this.phonePredicate = phonePredicate;
    }

    @Override
    public boolean test(Person person) {
        return namePredicate.test(person) || phonePredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof CombinedPredicate)) {
            return false;
        }

        CombinedPredicate otherPredicate = (CombinedPredicate) other;
        return namePredicate.equals(otherPredicate.namePredicate)
                && phonePredicate.equals(otherPredicate.phonePredicate);
    }
}
