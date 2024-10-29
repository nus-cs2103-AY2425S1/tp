package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;


/**
 * Tests that a {@code Meetup}'s {@code Name} range contains the keywords.
 */
public class MeetUpContainsKeywordsPredicate implements Predicate<MeetUp> {
    private final List<String> keywords;

    /**
     * Meetup name must be present and not null.
     */
    public MeetUpContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(MeetUp meetUp) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(meetUp.getName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetUpContainsKeywordsPredicate)) {
            return false;
        }

        MeetUpContainsKeywordsPredicate otherMeetUpContainsKeywordsPredicate = (MeetUpContainsKeywordsPredicate) other;
        return keywords.equals(otherMeetUpContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
