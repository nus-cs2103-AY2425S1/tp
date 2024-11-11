package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Tests whether a {@code Person}'s Priority matches the given Priority.
 */
public class PriorityMatchesPredicate implements Predicate<Person> {

    private final Priority priority;

    /**
     * Constructs a {@code PriorityMatchesPredicate} to test if a {@code Person}'s Priority matches
     * the specified Priority.
     *
     * @param priority The priority level to compare against the person's priority.
     */
    public PriorityMatchesPredicate(Priority priority) {
        this.priority = priority;
    }

    /**
     * Tests if the {@code Person}'s Priority matches the given Priority.
     *
     * @param person The person whose priority will be tested.
     * @return true if the person's Priority matches the given Priority, false otherwise.
     */
    @Override
    public boolean test(Person person) {
        return person.getPriority().equals(priority);
    }

    /**
     * Compares this {@code PriorityMatchesPredicate} with another object.
     *
     * @param other The other object to compare to.
     * @return true if both predicates have the same priority, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof PriorityMatchesPredicate)) {
            return false;
        }

        PriorityMatchesPredicate predicate = (PriorityMatchesPredicate) other;
        return priority.equals(predicate.priority);
    }

    /**
     * Returns the string representation of this {@code PriorityMatchesPredicate}.
     *
     * @return A string representation that includes the Priority being matched.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("priority", priority).toString();
    }
}
