package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone}, {@code Email},
 * {@code Address}, and {@code Tags} match the given criteria.
 */
public class PersonMeetsCriteriaPredicate implements Predicate<Person> {
    private final List<String> phoneCriteria;
    private final List<String> emailCriteria;
    private final List<String> addressCriteria;
    private final List<String> incomeCriteria;
    private final List<String> ageCriteria;
    private final List<String> tags;

    /**
     * Constructs a {@code PersonMeetsCriteriaPredicate} with the specified criteria.
     *
     * @param phoneCriteria The list of phone criteria to match.
     * @param emailCriteria The list of email criteria to match.
     * @param addressCriteria The list of address criteria to match.
     * @param tags The set of tags to match.
     */
    public PersonMeetsCriteriaPredicate(
        List<String> phoneCriteria,
        List<String> emailCriteria,
        List<String> addressCriteria,
        List<String> incomeCriteria,
        List<String> ageCriteria,
        List<String> tags) {
        this.phoneCriteria = phoneCriteria;
        this.emailCriteria = emailCriteria;
        this.addressCriteria = addressCriteria;
        this.incomeCriteria = incomeCriteria;
        this.ageCriteria = ageCriteria;
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        boolean phoneMatch = phoneCriteria.isEmpty();
        boolean tagsMatch = tags.isEmpty();
        boolean emailMatch = emailCriteria.isEmpty();
        boolean addressMatch = addressCriteria.isEmpty();
        boolean incomeMatch = incomeCriteria.isEmpty();
        boolean ageMatch = ageCriteria.isEmpty();

        if (!phoneCriteria.isEmpty()) {
            phoneMatch = phoneCriteria.stream()
                .anyMatch(searchTerm -> StringUtil.containsSubstringIgnoreCase(person.getPhone().value, searchTerm));
        }

        if (!tags.isEmpty() && !person.getTags().isEmpty()) {
            tagsMatch = tags.stream()
                .allMatch(tag -> person.getTags().stream()
                    .anyMatch(personTag -> personTag.tagName.toLowerCase().contains(tag.toLowerCase())));
        }

        if (!emailCriteria.isEmpty() && !person.getEmail().isEmpty()) {
            emailMatch = emailCriteria.stream()
                .anyMatch(searchTerm -> StringUtil.containsSubstringIgnoreCase(person.getEmail().value, searchTerm));
        }

        if (!addressCriteria.isEmpty() && !person.getAddress().isEmpty()) {
            addressMatch = addressCriteria.stream()
                .anyMatch(searchTerm -> StringUtil.containsSubstringIgnoreCase(person.getAddress().value, searchTerm));
        }

        if (!incomeCriteria.isEmpty() && !person.getIncome().isEmpty()) {
            incomeMatch = incomeCriteria.stream()
                .anyMatch(searchTerm ->
                    StringUtil.containsSubstringIgnoreCase(person.getIncome().toString(), searchTerm));
        }

        if (!ageCriteria.isEmpty() && !person.getAge().isEmpty()) {
            ageMatch = ageCriteria.stream()
                .allMatch(searchTerm -> {
                    if (searchTerm.charAt(0) == '>') {
                        return person.getAge().value > Integer.parseInt(searchTerm.substring(1));
                    } else if (searchTerm.charAt(0) == '<') {
                        return person.getAge().value < Integer.parseInt(searchTerm.substring(1));
                    } else {
                        return person.getAge().value == Integer.parseInt(searchTerm);
                    }
                });
        }

        return phoneMatch && emailMatch && addressMatch && incomeMatch && ageMatch && tagsMatch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonMeetsCriteriaPredicate)) {
            return false;
        }

        PersonMeetsCriteriaPredicate otherPersonMeetsCriteriaPredicate = (PersonMeetsCriteriaPredicate) other;
        return phoneCriteria.equals(otherPersonMeetsCriteriaPredicate.phoneCriteria)
            && emailCriteria.equals(otherPersonMeetsCriteriaPredicate.emailCriteria)
            && addressCriteria.equals(otherPersonMeetsCriteriaPredicate.addressCriteria)
            && incomeCriteria.equals(otherPersonMeetsCriteriaPredicate.incomeCriteria)
            && ageCriteria.equals(otherPersonMeetsCriteriaPredicate.ageCriteria)
            && tags.equals(otherPersonMeetsCriteriaPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("phone", phoneCriteria)
            .add("email", emailCriteria)
            .add("address", addressCriteria)
            .add("income", incomeCriteria)
            .add("age", ageCriteria)
            .add("tags", tags).toString();
    }
}
