package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;



/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    /**
     * This constructor takes a list of keywords and replaces any keyword that matches a short code
     * in the {@code Tag.DIETARY_RESTRICTION_MAP} with its corresponding full dietary restriction name.
     * If no match is found, the keyword remains unchanged. The modified list is then assigned to the
     * {@code keywords} field.
     *
     * @param keywords A list of keywords toi be processed and matched again
     *
     */
    public TagsContainsKeywordsPredicate(List<String> keywords) {
        List<String> mappedKeywords = new ArrayList<>();
        for (String item : keywords) {
            String mappedItem = Tag.getDietaryRestrictionsMappings().getOrDefault(item, item);
            mappedKeywords.add(mappedItem);
        }
        this.keywords = mappedKeywords;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> tags = person.getTags();
        //handling case where keywords are empty
        if (keywords.isEmpty()) {
            return false;
        }
        //converting tags and keywords to stream to iterate through effectively
        Stream<Tag> tagStream = tags.stream();
        Stream<String> keywordStream = keywords.stream().map(String::toLowerCase);

        // Stream through the tags and check if any match the keyword (case-insensitive)
        return tagStream.anyMatch(tag ->
                keywordStream.anyMatch(keyword -> tag.tagName.toLowerCase().contains(keyword))
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsContainsKeywordsPredicate otherTagsContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherTagsContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
