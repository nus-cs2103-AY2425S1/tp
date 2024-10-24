package careconnect.model.person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import careconnect.commons.core.LogsCenter;
import careconnect.commons.util.StringUtil;
import careconnect.commons.util.ToStringBuilder;
import careconnect.model.ModelManager;
import careconnect.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameAndAddressAndTagsContainsKeywordsPredicate implements Predicate<Person> {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final List<String> nameKeywords;
    private final List<String> addressKeywords;

    private final List<String> tagKeywords;

    /**
     * Constructor for predicate taking in a list of name keywords and a list of address keywords for matching
     * @param nameKeywords
     * @param addressKeywords
     */
    public NameAndAddressAndTagsContainsKeywordsPredicate(
            List<String> nameKeywords, List<String> addressKeywords, List<String> tagKeywords
    ) {
        this.nameKeywords = nameKeywords;
        this.addressKeywords = addressKeywords;
        this.tagKeywords = tagKeywords;
    }

    /**
     * condition that seach keyword is tested against
     * @param person the input argument
     * @return boolean of whether predicate test passed
     */
    @Override
    public boolean test(Person person) {
        boolean[] passedTests = new boolean[3];

        if (nameKeywords.isEmpty()) {
            passedTests[0] = true;
        } else {
            passedTests[0] = nameKeywords.stream()
                    .anyMatch(
                            keyword -> StringUtil.containsPartialWordIgnoreCase(person.getName().fullName, keyword)
                    );
        }

        if (addressKeywords.isEmpty()) {
            passedTests[1] = true;
        } else {
            passedTests[1] = addressKeywords.stream()
                    .anyMatch(
                            keyword -> StringUtil.containsPartialWordIgnoreCase(person.getAddress().toString(), keyword)
                    );
        }

        if (tagKeywords.isEmpty()) {
            passedTests[2] = true;
        } else {
            Set<Tag> tagSet = person.getTags();
            Set<Tag> lowercaseTagSet = getAllLowercaseSet(tagSet);

            int numberOfTagsPresent = 0;
            for (String s: tagKeywords) {
                Tag tempTag = new Tag(s.toLowerCase());
                if (lowercaseTagSet.contains(tempTag)) {
                    numberOfTagsPresent += 1;
                }
            }
            passedTests[2] = numberOfTagsPresent == tagKeywords.size();
        }
        return passedTests[0] && passedTests[1] && passedTests[2];


    }

    /**
     * Create a duplicate of the tagSet with all lowercase, for easy searching
     * @param tags
     * @return
     */
    private Set<Tag> getAllLowercaseSet(Set<Tag> tags) {
        HashSet<Tag> lowercaseTags = new HashSet<Tag>();
        for (Tag t: tags) {
            lowercaseTags.add(new Tag(t.tagName.toLowerCase()));
        }
        return lowercaseTags;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameAndAddressAndTagsContainsKeywordsPredicate)) {
            return false;
        }

        NameAndAddressAndTagsContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (NameAndAddressAndTagsContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherNameContainsKeywordsPredicate.nameKeywords)
                && addressKeywords.equals(otherNameContainsKeywordsPredicate.addressKeywords)
                && tagKeywords.equals(otherNameContainsKeywordsPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("addressKeywords", addressKeywords).toString();
    }
}
