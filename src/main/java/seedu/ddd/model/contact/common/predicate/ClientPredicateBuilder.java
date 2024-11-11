package seedu.ddd.model.contact.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.Contact;

/**
 * {@code ContactPredicateBuilder} for {@code Client}.
 */
public class ClientPredicateBuilder extends ContactPredicateBuilder {

    public ClientPredicateBuilder(ArgumentMultimap argMultimap) {
        super(argMultimap);
    }

    @Override
    public Predicate<Contact> build() throws ParseException {
        Predicate<Contact> combinedPredicate = new ClientTypePredicate();
        return addAllPredicates(combinedPredicate);
    }

}
