package seedu.address.logic.parser.criteria;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;


/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneSearchCriteria implements SearchCriteria {

    private final Set<Phone> phones;

    public PhoneSearchCriteria(List<String> phones) {
        this.phones = phones.stream().map(Phone::new).collect(Collectors.toSet());
    }
    @Override
    public boolean test(Person person) {
        return phones.stream().anyMatch(person::hasPhone);
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof PhoneSearchCriteria)) {
            return false;
        }

        PhoneSearchCriteria otherPhoneSearchCriteria = (PhoneSearchCriteria) other;
        return phones.equals(otherPhoneSearchCriteria.phones);
    }
}
