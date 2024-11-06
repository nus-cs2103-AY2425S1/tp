package hallpointer.address.model.member;

import java.util.List;
import java.util.function.Predicate;

import hallpointer.address.commons.util.StringUtil;
import hallpointer.address.model.session.Session;

/**
 * Tests that a {@code Member} contains any {@code Session} whose {@code Name} matches any of the keywords given.
 */
public class SessionContainsKeywordsPredicate implements Predicate<Member> {
    private final List<String> keywords;

    /**
     * Constructs a {@code SessionContainsKeywordsPredicate} with the specified list of keywords.
     *
     * @param keywords The list of keywords to match against session names.
     */
    public SessionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }
    /**
     * Tests if any session within the given member matches any of the keywords.
     *
     * @param member The member whose sessions are to be tested.
     * @return True if any session within the member matches any of the keywords, false otherwise.
     */
    @Override
    public boolean test(Member member) {
        return member.getSessions().stream()
                .anyMatch(session -> keywords.stream()
                        .anyMatch(keyword ->
                                StringUtil.containsWordIgnoreCase(session.getSessionName().toString(), keyword)));
    }
    /**
     * Tests if the given session matches any of the keywords.
     *
     * @param session The session to be tested.
     * @return True if the session matches any of the keywords, false otherwise.
     */
    public boolean test(Session session) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(session.getSessionName().toString(), keyword));
    }

    /**
     * Checks if this predicate is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if object is a {@code SessionContainsKeywordsPredicate}with the same keywords, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SessionContainsKeywordsPredicate)) {
            return false;
        }

        SessionContainsKeywordsPredicate otherPredicate = (SessionContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }
}
