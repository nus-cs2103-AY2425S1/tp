package seedu.ddd.model.contact.common.predicate;

import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.ParserUtil.verifyNoEmptyInput;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * {@code VendorPredicateBuilder} for {@code Vendor}.
 */
public class VendorPredicateBuilder extends ContactPredicateBuilder {

    public VendorPredicateBuilder(ArgumentMultimap argMultimap) {
        super(argMultimap);
    }

    @Override
    public Predicate<Contact> build() throws ParseException {
        Predicate<Contact> combinedPredicate = new VendorTypePredicate();
        combinedPredicate = addServicePredicate(this.getArgMultimap(), combinedPredicate);
        return addAllPredicates(combinedPredicate);
    }
    private Predicate<Contact> addServicePredicate(ArgumentMultimap argMultimap, Predicate<Contact> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_SERVICE).isPresent()) {
            if (!Service.isValidService(argMultimap.getValue(PREFIX_SERVICE).get())) {
                throw new ParseException(Service.MESSAGE_CONSTRAINTS);
            }
            String args = verifyNoEmptyInput(argMultimap, PREFIX_SERVICE);
            String[] serviceKeywords = args.split("\\s+");
            Predicate<Vendor> servicePredicate = new ServiceContainsKeywordsPredicate(Arrays.asList(serviceKeywords));
            //Convert Predicate<Vendor> into Predicate<Contact>
            Predicate<Contact> wrappedServicePredicate = contact ->
                    contact instanceof Vendor && servicePredicate.test((Vendor) contact);

            // Combine the wrapped predicate with the existing predicate chain
            return combinedPredicate.and(wrappedServicePredicate);
        }
        return combinedPredicate;
    }
}
