package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;

/**
 * Tests that a {@code Person}'s details matches any of the keywords given.
 */
public class FilterPredicate implements Predicate<Person> {
    private final FilterPersonDescriptor filterPersonDescriptor;

    public FilterPredicate(FilterPersonDescriptor filterPersonDescriptor) {
        this.filterPersonDescriptor = filterPersonDescriptor;
    }

    @Override
    public boolean test(Person person) {
        boolean nameMatched = filterName(person);
        boolean phoneMatched = filterPhone(person);
        boolean genderMatched = filterGender(person);
        boolean modulesMatched = filterModules(person);
        boolean tagsMatched = filterTags(person);

        return nameMatched && phoneMatched && genderMatched && modulesMatched && tagsMatched;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterPredicate otherPredicate)) {
            return false;
        }

        return filterPersonDescriptor.equals(otherPredicate.filterPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("conditions", filterPersonDescriptor)
                .toString();
    }

    /**
     * Filters a person based on the name specified in the filerPersonDescriptor.
     * If the name in the filterPersonDescriptor is not present,
     * this method will return true, indicating that the person passes the filter.
     */
    private boolean filterName(Person person) {
        return filterPersonDescriptor.getName()
                .map(name -> StringUtil.containsWordIgnoreCase(person.getName().fullName, name.fullName))
                .orElse(true);
    }

    /**
     * Filters a person based on the phone specified in the filerPersonDescriptor.
     * If the phone in the filterPersonDescriptor is not present,
     * this method will return true, indicating that the person passes the filter.
     */
    private boolean filterPhone(Person person) {
        return filterPersonDescriptor.getPhone()
                .map(phone -> StringUtil.containsWordIgnoreCase(person.getPhone().value, phone.value))
                .orElse(true);
    }

    /**
     * Filters a person based on the gender specified in the filerPersonDescriptor.
     * If the gender in the filterPersonDescriptor is not present,
     * this method will return true, indicating that the person passes the filter.
     */
    private boolean filterGender(Person person) {
        return filterPersonDescriptor.getGender()
                .map(gender -> StringUtil.containsWordIgnoreCase(person.getGender().gender, gender.gender))
                .orElse(true);
    }

    /**
     * Filters a person based on the modules specified in the filerPersonDescriptor.
     * If the modules in the filterPersonDescriptor is not present,
     * this method will return true, indicating that the person passes the filter.
     */
    private boolean filterModules(Person person) {
        return filterPersonDescriptor.getModules()
                .map(modules -> modules.stream()
                        .anyMatch(module -> person.getModules().stream()
                                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(module.module, keyword.module)))
                )
                .orElse(true);
    }

    /**
     * Filters a person based on the tags specified in the filerPersonDescriptor.
     * If the tags in the filterPersonDescriptor is not present,
     * this method will return true, indicating that the person passes the filter.
     */
    private boolean filterTags(Person person) {
        return filterPersonDescriptor.getTags()
                .map(tags -> tags.stream()
                        .anyMatch(tag -> person.getTags().stream()
                                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword.tagName)))
                )
                .orElse(true);
    }
}
