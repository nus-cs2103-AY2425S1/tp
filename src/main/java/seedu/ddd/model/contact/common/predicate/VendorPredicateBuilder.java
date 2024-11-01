package seedu.ddd.model.contact.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.Contact;

/**
 * {@code ContactPredicateBuilder} for {@code Vendor}.
 */
public class VendorPredicateBuilder extends ContactPredicateBuilder {

    public VendorPredicateBuilder(ArgumentMultimap argMultimap) {
        super(argMultimap);
    }

    @Override
    public Predicate<Contact> build() throws ParseException {
        Predicate<Contact> combinedPredicate = new VendorTypePredicate();
        return addAllPredicates(combinedPredicate);
    }

}
