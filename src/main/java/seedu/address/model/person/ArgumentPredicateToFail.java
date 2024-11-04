package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Like an {@code ArgumentPredicate}, but instead tests to see if the Person <i>fails</i> the predicate.
 * Essentially a logical NOT for {@code ArgumentPredicate}.
 */
public class ArgumentPredicateToFail extends ArgumentPredicate {

    private static ArgumentPredicateToFail notBlacklistedPredicate;

    /**
     * Constructs an {@code ArgumentPredicateToFail}
     *
     * @param parameterMap A map with parameters inputted by user
     */
    public ArgumentPredicateToFail(Map<String, Object> parameterMap) {
        super(parameterMap);
    }

    public static ArgumentPredicateToFail getNotBlacklistedPredicate() {
        if (notBlacklistedPredicate == null) {
            Map<String, Object> parameterMap = new HashMap<>();

            parameterMap.put(ClientStatus.CLIENT_STATUS_KEY, new ClientStatus("blacklisted"));

            Set<Tag> tagList = new HashSet<>();
            parameterMap.put(Tag.TAG_KEY, tagList);

            notBlacklistedPredicate = new ArgumentPredicateToFail(parameterMap);
        }

        return notBlacklistedPredicate;
    }

    private void addIfPresent(String key, Predicate<Person> predicate,
                              Person person, List<Boolean> validParameters) {
        assert key != null : "Key should not be null";
        assert predicate != null : "Predicate should not be null";
        assert person != null : "Person should not be null";
        assert validParameters != null : "Valid parameters list should not be null";
        if (getInputParameters().containsKey(key)) {
            validParameters.add(!predicate.test(person));
        }
    }

    private void addIfTagPresent(Set<Tag> tags, Predicate<Person> predicate,
                                 Person person, List<Boolean> validParameters) {
        assert tags != null : "Tags should not be null";
        assert predicate != null : "Predicate should not be null";
        assert person != null : "Person should not be null";
        assert validParameters != null : "Valid parameters list should not be null";
        if (!tags.isEmpty()) {
            validParameters.add(!predicate.test(person));
        }
    }

    private List<Boolean> getValidParameters(Person person) {
        List<Boolean> validParameters = new ArrayList<>();
        addIfPresent(Name.NAME_KEY, p -> {
            String[] nameKeywords = getInputParameters().get("name").toString().split("\\s+");
            NameContainsKeywordsPredicate namePredicate =
                    new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
            return namePredicate.test(p);
        }, person, validParameters);

        addIfPresent(Phone.PHONE_KEY, p -> getInputParameters().get("phone").equals(p.getPhone()),
                person, validParameters);

        addIfPresent(Email.EMAIL_KEY, p -> getInputParameters().get("email").equals(p.getEmail()),
                person, validParameters);

        addIfPresent(Address.ADDRESS_KEY, p -> getInputParameters().get("address").equals(p.getAddress()),
                person, validParameters);

        addIfPresent(ProjectStatus.PROJECT_STATUS_KEY, p -> getInputParameters().get("project status")
                .equals(p.getProjectStatus()), person, validParameters);

        addIfPresent(PaymentStatus.PAYMENT_STATUS_KEY, p -> getInputParameters().get("payment status")
                    .equals(p.getPaymentStatus()), person, validParameters);

        addIfPresent(ClientStatus.CLIENT_STATUS_KEY, p -> getInputParameters().get("client status")
                    .equals(p.getClientStatus()), person, validParameters);

        addIfPresent(Deadline.DEADLINE_KEY, p -> getInputParameters().get("deadline")
                .equals(p.getDeadline()), person, validParameters);

        @SuppressWarnings("unchecked")
        Set<Tag> tags = (Set<Tag>) getInputParameters().get(Tag.TAG_KEY);
        addIfTagPresent(tags, p -> tags.stream()
                .anyMatch(p.getTags()::contains), person, validParameters);

        return validParameters;
    }

    @Override
    public boolean test(Person person) {
        List<Boolean> validParameters = getValidParameters(person);
        return validParameters.stream().anyMatch(Boolean::booleanValue);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArgumentPredicateToFail)) {
            return false;
        }

        ArgumentPredicateToFail argumentPredicateToFail = (ArgumentPredicateToFail) other;
        return getInputParameters().equals(argumentPredicateToFail.getInputParameters());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("parameters to fail", getInputParameters()).toString();
    }
}
