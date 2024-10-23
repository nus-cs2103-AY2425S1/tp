package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> telegramHandleKeywords;
    private final List<String> emailKeywords;
    private final List<String> studentStatusKeywords;
    private final List<String> roleKeywords;
    private final List<String> nicknameKeywords;

    /**
     * Constructs a {@code ContainsKeywordsPredicate}.
     *
     * @param nameKeywords keywords to match with name
     * @param telegramHandleKeywords keywords to match with telegram handle
     * @param emailKeywords keywords to match with email
     * @param studentStatusKeywords keywords to match with student status
     * @param roleKeywords keywords to match with role
     * @param nicknameKeywords keywords to match with nickname.
     */
    public ContainsKeywordsPredicate(List<String> nameKeywords, List<String> telegramHandleKeywords,
                                     List<String> emailKeywords, List<String> studentStatusKeywords,
                                     List<String> roleKeywords, List<String> nicknameKeywords) {
        this.nameKeywords = nameKeywords;
        this.telegramHandleKeywords = telegramHandleKeywords;
        this.emailKeywords = emailKeywords;
        this.studentStatusKeywords = studentStatusKeywords;
        this.roleKeywords = roleKeywords;
        this.nicknameKeywords = nicknameKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean containsNameKeywords = nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword));
        boolean containsTelegramHandleKeywords = telegramHandleKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsIgnoreCase(person.getTelegramHandle().value, keyword));
        boolean containsEmailKeywords = emailKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsIgnoreCase(person.getEmail().value, keyword));
        boolean containsStudentStatusKeywords = studentStatusKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsIgnoreCase(person.getStudentStatus().value, keyword));
        boolean containsRoleKeywords = roleKeywords.stream()
                .anyMatch(keyword -> person.getRoles().stream()
                        .anyMatch(role -> StringUtil.containsIgnoreCase(role.roleName, keyword)));
        boolean containsNicknameKeywords = nicknameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsIgnoreCase(person.getNickname().value, keyword));
        return containsNameKeywords || containsTelegramHandleKeywords || containsEmailKeywords
                || containsStudentStatusKeywords || containsRoleKeywords || containsNicknameKeywords;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContainsKeywordsPredicate)) {
            return false;
        }

        ContainsKeywordsPredicate otherContainsKeywordsPredicate = (ContainsKeywordsPredicate) other;
        boolean isEqualNameKeywords = nameKeywords.equals(otherContainsKeywordsPredicate.nameKeywords);
        boolean isEqualTelegramHandleKeywords =
                telegramHandleKeywords.equals(otherContainsKeywordsPredicate.telegramHandleKeywords);
        boolean isEqualEmailKeywords = emailKeywords.equals(otherContainsKeywordsPredicate.emailKeywords);
        boolean isEqualStudentStatusKeywords =
                studentStatusKeywords.equals(otherContainsKeywordsPredicate.studentStatusKeywords);
        boolean isEqualRoleKeywords = roleKeywords.equals(otherContainsKeywordsPredicate.roleKeywords);
        boolean isEqualNicknameKeywords = nicknameKeywords.equals(otherContainsKeywordsPredicate.nicknameKeywords);
        return isEqualNameKeywords && isEqualTelegramHandleKeywords && isEqualEmailKeywords
                && isEqualStudentStatusKeywords && isEqualRoleKeywords && isEqualNicknameKeywords;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("telegramHandleKeywords", telegramHandleKeywords)
                .add("emailKeywords", emailKeywords)
                .add("studentStatusKeywords", studentStatusKeywords)
                .add("roleKeywords", roleKeywords)
                .add("nicknameKeywords", nicknameKeywords).toString();
    }
}
