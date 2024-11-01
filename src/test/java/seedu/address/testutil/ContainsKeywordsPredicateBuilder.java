package seedu.address.testutil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.contact.ContainsKeywordsPredicate;

/**
 * A utility class to help with building ContainsKeywordsPredicate objects.
 */
public class ContainsKeywordsPredicateBuilder {

    public static final List<String> DEFAULT_NAME_KEYWORDS = Collections.emptyList();
    public static final List<String> DEFAULT_TELEGRAM_KEYWORDS = Collections.emptyList();
    public static final List<String> DEFAULT_EMAIL_KEYWORDS = Collections.emptyList();
    public static final List<String> DEFAULT_STUDENT_STATUS_KEYWORDS = Collections.emptyList();
    public static final List<String> DEFAULT_ROLE_KEYWORDS = Collections.emptyList();
    public static final List<String> DEFAULT_NICKNAME_KEYWORDS = Collections.emptyList();

    private List<String> nameKeywords;
    private List<String> telegramHandleKeywords;
    private List<String> emailKeywords;
    private List<String> studentStatusKeywords;
    private List<String> roleKeywords;
    private List<String> nicknameKeywords;

    /**
     * Creates a {@code ContainsKeywordsPredicateBuilder} with the default details.
     */
    public ContainsKeywordsPredicateBuilder() {
        nameKeywords = DEFAULT_NAME_KEYWORDS;
        telegramHandleKeywords = DEFAULT_TELEGRAM_KEYWORDS;
        emailKeywords = DEFAULT_EMAIL_KEYWORDS;
        studentStatusKeywords = DEFAULT_STUDENT_STATUS_KEYWORDS;
        roleKeywords = DEFAULT_ROLE_KEYWORDS;
        nicknameKeywords = DEFAULT_NICKNAME_KEYWORDS;
    }

    /**
     * Sets the {@code nameKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withNameKeywords(String ... nameKeywords) {
        this.nameKeywords = Arrays.asList(nameKeywords);
        return this;
    }

    /**
     * Sets the {@code nameKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withNameKeywords(List<String> nameKeywords) {
        this.nameKeywords = nameKeywords;
        return this;
    }

    /**
     * Sets the {@code telegramHandleKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withTelegramHandleKeywords(String ... telegramHandleKeywords) {
        this.telegramHandleKeywords = Arrays.asList(telegramHandleKeywords);
        return this;
    }

    /**
     * Sets the {@code telegramHandleKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withTelegramHandleKeywords(List<String> telegramHandleKeywords) {
        this.telegramHandleKeywords = telegramHandleKeywords;
        return this;
    }

    /**
     * Sets the {@code emailKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withEmailKeywords(String ... emailKeywords) {
        this.emailKeywords = Arrays.asList(emailKeywords);
        return this;
    }

    /**
     * Sets the {@code emailKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withEmailKeywords(List<String> emailKeywords) {
        this.emailKeywords = emailKeywords;
        return this;
    }

    /**
     * Sets the {@code studentStatusKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withStudentStatusKeywords(String ... studentStatusKeywords) {
        this.studentStatusKeywords = Arrays.asList(studentStatusKeywords);
        return this;
    }

    /**
     * Sets the {@code studentStatusKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withStudentStatusKeywords(List<String> studentStatusKeywords) {
        this.studentStatusKeywords = studentStatusKeywords;
        return this;
    }

    /**
     * Sets the {@code roleKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withRoleKeywords(String ... roleKeywords) {
        this.roleKeywords = Arrays.asList(roleKeywords);
        return this;
    }

    /**
     * Sets the {@code roleKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withRoleKeywords(List<String> roleKeywords) {
        this.roleKeywords = roleKeywords;
        return this;
    }

    /**
     * Sets the {@code nicknameKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withNicknameKeywords(String ... nicknameKeywords) {
        this.nicknameKeywords = Arrays.asList(nicknameKeywords);
        return this;
    }

    /**
     * Sets the {@code nicknameKeywords} of the {@code ContainsKeywordsPredicate} that we are building.
     */
    public ContainsKeywordsPredicateBuilder withNicknameKeywords(List<String> nicknameKeywords) {
        this.nicknameKeywords = nicknameKeywords;
        return this;
    }

    /**
     * Builds a {@code ContainsKeywordsPredicate}.
     */
    public ContainsKeywordsPredicate build() {
        return new ContainsKeywordsPredicate(nameKeywords, telegramHandleKeywords, emailKeywords, studentStatusKeywords,
                roleKeywords, nicknameKeywords);
    }
}
