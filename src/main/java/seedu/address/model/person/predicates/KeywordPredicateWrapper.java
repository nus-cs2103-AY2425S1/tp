package seedu.address.model.person.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;


/**
 * Wrapper class for predicates that check if a field contains a keyword.
 */
public class KeywordPredicateWrapper {
    private final List<String> keywords;
    private final String field;

    private final Predicate<Person> predicate;

    /**
     * Constructor for KeywordPredicateWrapper.
     * @param keywords list of keywords to check for
     * @param field field to check for keywords
     */
    public KeywordPredicateWrapper(List<String> keywords, String field) {
        this.keywords = keywords;
        this.field = field;
        // predicate to check if field contains keyword
        this.predicate = null;


    }


    /**
     * Creates a predicate based on the field.
     * @param field field to create predicate for
     * @return predicate for the field
     */
    public Predicate<Person> createPredicate(String field) {
        switch(field) {
        case "address":
            return new AddressContainsKeywordsPredicate(keywords);
        case "phone":
            return new PhoneNumberContainsKeywordPredicate(keywords);
        case "email":
            return new EmailContainsKeywordsPredicate(keywords);
        case "telegram":
            return new TelegramContainsKeywordsPredicate(keywords);
        case "name":
            return new NameContainsKeywordsPredicate(keywords);
        case "role":
            List<Role> roles = new ArrayList<>();
            for (String keyword : keywords) {
                try {
                    roles.add(RoleHandler.getRole(keyword));

                } catch (InvalidRoleException e) {
                    // ignore invalid roles
                }
            }
            return new PersonIsRolePredicate(roles);
        default:
            return null;
        }
    }

    /**
     * Gets the predicate.
     * @return predicate
     */
    public Predicate<Person> getPredicate() {
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof KeywordPredicateWrapper)) {
            return false;
        }

        KeywordPredicateWrapper otherPredicateWrapper = (KeywordPredicateWrapper) other;
        return keywords.equals(otherPredicateWrapper.keywords)
                && field.equals(otherPredicateWrapper.field);
    }

}
