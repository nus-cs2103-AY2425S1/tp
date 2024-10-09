package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Tests if a {@code Person} has been selected by the user.
 */
public class IsSelectedPredicate implements Predicate<Person> {
    private final Person person;

    /**
     * @param model current state of address book.
     * @param index the index of the person selected.
     */
    public IsSelectedPredicate(Model model, Index index) {
        requireAllNonNull(model, index);
        this.person = model.getFilteredPersonList().get(index.getZeroBased());
    }

    @Override
    public boolean test(Person person) {
        return this.person.isSamePerson(person);
    }
}
