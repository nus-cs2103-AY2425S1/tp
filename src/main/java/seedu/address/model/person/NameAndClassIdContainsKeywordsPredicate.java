package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code ClassId} matches any of the keywords given.
 */
public class NameAndClassIdContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> nameKeywords;
    private final List<String> classIdKeywords;

    /**
     * Constructs a predicate that checks if a person's name and classId contains any of the keywords given.
     * @param nameKeywords Keywords for name
     * @param classIdKeywords Keywords for classId
     */
    public NameAndClassIdContainsKeywordsPredicate(List<String> nameKeywords, List<String> classIdKeywords) {
        this.nameKeywords = nameKeywords;
        this.classIdKeywords = classIdKeywords;
    }

    @Override
    public boolean test(Person person) {
        return classIdKeywords.stream()
                .anyMatch(keyword -> regexMatch(person.getClassId().value, keyword))
                && nameKeywords.stream().anyMatch(keyword -> regexMatch(person.getName().fullName, keyword));
    }

    private boolean regexMatch(String name, String keyword) {
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(name).find();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameAndClassIdContainsKeywordsPredicate)) {
            return false;
        }

        NameAndClassIdContainsKeywordsPredicate otherNameAndClassIdContainsKeywordsPredicate =
                (NameAndClassIdContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherNameAndClassIdContainsKeywordsPredicate.nameKeywords)
                && classIdKeywords.equals(otherNameAndClassIdContainsKeywordsPredicate.classIdKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Name keywords", nameKeywords).add("ClassId keywords", classIdKeywords).toString();

    }
}
