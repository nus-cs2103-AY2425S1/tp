package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that input parameters matches all the requirements of a particular Person
 */
public class ArgumentPredicate implements Predicate<Person> {
    private final Map<String, Object> inputParameters;

    /**
     * Constructs an {@code ArgumentPredicate}
     *
     * @param parameterMap A map with parameters inputted by user
     */
    public ArgumentPredicate(Map<String, Object> parameterMap) {
        requireNonNull(parameterMap);
        inputParameters = parameterMap;
    }

    private void addIfPresent(String key, Predicate<Person> predicate,
                              Person person, List<Boolean> validParameters) {
        assert key != null : "Key should not be null";
        assert predicate != null : "Predicate should not be null";
        assert person != null : "Person should not be null";
        assert validParameters != null : "Valid parameters list should not be null";
        if (inputParameters.containsKey(key)) {
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
        addIfPresent("name", p -> {
            String[] nameKeywords = inputParameters.get("name").toString().split("\\s+");
            NameContainsKeywordsPredicate namePredicate =
                    new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
            return namePredicate.test(p);
        }, person, validParameters);

        addIfPresent("phone", p -> inputParameters.get("phone").equals(p.getPhone()),
                person, validParameters);

        addIfPresent("email", p -> inputParameters.get("email").equals(p.getEmail()),
                person, validParameters);

        addIfPresent("address", p -> inputParameters.get("address").equals(p.getAddress()),
                person, validParameters);

        addIfPresent("project status", p -> inputParameters.get("project status")
                .equals(p.getProjectStatus()), person, validParameters);

        addIfPresent("payment status", p -> inputParameters.get("payment status")
                    .equals(p.getPaymentStatus()), person, validParameters);

        addIfPresent("client status", p -> inputParameters.get("client status")
                    .equals(p.getClientStatus()), person, validParameters);

        addIfPresent("deadline", p -> inputParameters.get("deadline")
                .equals(p.getDeadline()), person, validParameters);

        Set<Tag> tags = (Set<Tag>) inputParameters.get("tags");
        addIfTagPresent(tags, p -> tags.stream()
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
        return inputParameters.equals(argumentPredicate.inputParameters);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("parameters", inputParameters).toString();
    }
}
