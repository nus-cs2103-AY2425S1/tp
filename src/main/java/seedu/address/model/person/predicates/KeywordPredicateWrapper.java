package seedu.address.model.person.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;


/*
    * Wrapper around a predicate that checks if a keyword is in a field of a person.
 */
public class KeywordPredicateWrapper {
    private final List<String> keywords;
    private final String field;

    private final Predicate<Person> predicate;

    public KeywordPredicateWrapper(List<String> keywords, String field) {
        this.keywords = keywords;
        this.field = field;
        // predicate to check if field contains keyword
        this.predicate = null;


    }


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

            default:
                return null;
        }
    }

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
