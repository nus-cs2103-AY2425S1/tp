package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s arguments matches all the requirements
 */
public class ArgumentPredicate implements Predicate<Person> {
    private final Person toFind;

    /**
     * Constructs an {@code ArgumentPredicate}
     *
     * @param person The person with parameters to find
     */
    public ArgumentPredicate(Person person) {
        requireNonNull(person);
        toFind = person;
    }

    private void addIfPresent(Supplier<String> fieldSupplier, Predicate<Person> predicate,
                              Person person, List<Boolean> validParameters) {
        assert fieldSupplier != null : "Field supplier should not be null";
        assert predicate != null : "Predicate should not be null";
        assert person != null : "Person should not be null";
        assert validParameters != null : "Valid parameters list should not be null";
        if (!fieldSupplier.get().isEmpty()) {
            validParameters.add(predicate.test(person));
        }
    }

    private void addIfDeadlinePresent(Supplier<String> fieldSupplier, Predicate<Person> predicate,
                                      Person person, List<Boolean> validParameters) {
        assert fieldSupplier != null : "Field supplier should not be null";
        assert predicate != null : "Predicate should not be null";
        assert person != null : "Person should not be null";
        assert validParameters != null : "Valid parameters list should not be null";
        if (!fieldSupplier.get().equals(LocalDate.MIN.format(Deadline.OUTPUT_FORMATTER))) {
            validParameters.add(predicate.test(person));
        }
    }

    private void addIfTagPresent(Set<Tag> tags, Predicate<Person> predicate,
                                 Person person, List<Boolean> validParameters) {
        assert tags != null : "Tags should not be null";
        assert predicate != null : "Predicate should not be null";
        assert person != null : "Person should not be null";
        assert validParameters != null : "Valid parameters list should not be null";
        if (!tags.isEmpty()) {
            validParameters.add(predicate.test(person));
        }
    }

    private List<Boolean> getValidParameters(Person person) {
        List<Boolean> validParameters = new ArrayList<>();
        addIfPresent(() -> toFind.getName().toString(), p -> {
            String[] nameKeywords = toFind.getName().toString().split("\\s+");
            NameContainsKeywordsPredicate namePredicate =
                    new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
            return namePredicate.test(p);
        }, person, validParameters);

        addIfPresent(() -> toFind.getPhone().toString(), p -> toFind.getPhone().equals(p.getPhone()),
                person, validParameters);

        addIfPresent(() -> toFind.getEmail().toString(), p -> toFind.getEmail().equals(p.getEmail()),
                person, validParameters);

        addIfPresent(() -> toFind.getAddress().toString(), p -> toFind.getAddress().equals(p.getAddress()),
                person, validParameters);

        addIfPresent(() -> toFind.getProjectStatus().toString(), p -> toFind.getProjectStatus()
                .equals(p.getProjectStatus()), person, validParameters);

        addIfPresent(() -> toFind.getPaymentStatus().toString(), p -> toFind.getPaymentStatus()
                    .equals(p.getPaymentStatus()), person, validParameters);

        addIfPresent(() -> toFind.getClientStatus().toString(), p -> toFind.getClientStatus()
                    .equals(p.getClientStatus()), person, validParameters);

        addIfDeadlinePresent(() -> toFind.getDeadline().toString(), p -> toFind.getDeadline()
                .equals(p.getDeadline()), person, validParameters);

        addIfTagPresent(toFind.getTags(), p -> toFind.getTags().stream()
                .anyMatch(p.getTags()::contains), person, validParameters);

        return validParameters;
    }

    @Override
    public boolean test(Person person) {
        List<Boolean> validParameters = getValidParameters(person);
        return validParameters.stream().allMatch(Boolean::booleanValue);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArgumentPredicate)) {
            return false;
        }

        ArgumentPredicate argumentPredicate = (ArgumentPredicate) other;
        return toFind.equals(argumentPredicate.toFind);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("person", toFind).toString();
    }
}
