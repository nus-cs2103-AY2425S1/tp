package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Phone}, {@code Email},
 * {@code Address}, and {@code Tags} match the given criteria.
 */
public class PersonMeetsCriteriaPredicate implements Predicate<Person> {
    private final List<String> phoneCriteria;
    private final List<String> emailCriteria;
    private final List<String> addressCriteria;
    private final Set<Tag> tags;

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
        Set<Tag> tags) {
        this.phoneCriteria = phoneCriteria;
        this.emailCriteria = emailCriteria;
        this.addressCriteria = addressCriteria;
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        boolean phoneMatch = true;
        boolean tagsMatch = person.getTags().containsAll(tags);
        boolean emailMatch = true;
        boolean addressMatch = true;

        if (!phoneCriteria.isEmpty()) {
            phoneMatch = phoneCriteria.stream()
                .anyMatch(searchTerm -> StringUtil.containsSubstringIgnoreCase(person.getPhone().value, searchTerm));
        }

        if (!emailCriteria.isEmpty() && !person.getEmail().isEmpty()) {
            emailMatch = emailCriteria.stream()
                .anyMatch(searchTerm -> StringUtil.containsSubstringIgnoreCase(person.getEmail().value, searchTerm));
        }

        if (!addressCriteria.isEmpty() && !person.getAddress().isEmpty()) {
            addressMatch = addressCriteria.stream()
                .anyMatch(searchTerm -> StringUtil.containsSubstringIgnoreCase(person.getAddress().value, searchTerm));
        }

        return phoneMatch && emailMatch && addressMatch && tagsMatch;
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
            && tags.equals(otherPersonMeetsCriteriaPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("phone", phoneCriteria)
            .add("email", emailCriteria)
            .add("address", addressCriteria)
            .add("tags", tags).toString();
    }
}
