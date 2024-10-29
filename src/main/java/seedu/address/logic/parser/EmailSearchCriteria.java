package seedu.address.logic.parser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Email;
import seedu.address.model.person.Person;



/**
 * Tests that a {@code Person}'s {@code Email} matches any of the keywords given.
 */
public class EmailSearchCriteria implements SearchCriteria {
    private final Set<Email> emails;

    public EmailSearchCriteria(List<String> emails) {
        this.emails = emails.stream().map(Email::new).collect(Collectors.toSet());
    }
    @Override
    public boolean test(Person person) {
        return emails.stream().anyMatch(person::hasEmail);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof EmailSearchCriteria)) {
            return false;
        }

        EmailSearchCriteria otherEmailSearchCriteria = (EmailSearchCriteria) other;
        return emails.equals(otherEmailSearchCriteria.emails);
    }
}
