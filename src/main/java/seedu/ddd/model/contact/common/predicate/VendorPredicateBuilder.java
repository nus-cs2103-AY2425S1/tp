package seedu.ddd.model.contact.common.predicate;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.ddd.logic.commands.list.ListCommand;
import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.Contact;
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
            String trimmedArgs = argMultimap.getValue(PREFIX_SERVICE).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            String[] serviceKeywords = trimmedArgs.split("\\s+");
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
