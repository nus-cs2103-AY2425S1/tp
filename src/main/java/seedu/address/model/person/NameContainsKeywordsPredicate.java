package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        } else if (keywords.get(keywords.size() - 1).contains("$")) {
            return isExact(person);
        } else {
            String[] nameParts = person.getName().fullName.split("\\s+");
            // Create a list to track which name parts have been matched
            List<String> remainingParts = new ArrayList<>(Arrays.asList(nameParts));

            for (String keyword : keywords) {
                boolean foundMatch = false;
                // Try to match this keyword with any remaining (unmatched) name part
                for (int i = remainingParts.size() - 1; i >= 0; i--) {
                    if (remainingParts.get(i).toLowerCase().startsWith(keyword.toLowerCase())) {
                        remainingParts.remove(i);
                        foundMatch = true;
                        break;
                    }
                }
                // If any keyword doesn't find a match, return false
                if (!foundMatch) {
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * Checks if the person's name is an exact match with the concatenated keywords.
     *
     * @param person The person to compare against the concatenated keywords.
     * @return {@code true} if the concatenated keywords, match the person's full name.
     */
    public boolean isExact(Person person) {
        String fullname = String.join("", keywords).toLowerCase().split("\\$")[0].trim();
        String personName = person.getName().fullName.trim().toLowerCase().replace(" ", "");
        return fullname.equals(personName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
