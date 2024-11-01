package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

public class TelegramContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TelegramContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTelegramUsername().toString(), keyword)
                                    ||person.getTelegramUsername().toString().toLowerCase().contains(keyword.toLowerCase()));
    }


}
