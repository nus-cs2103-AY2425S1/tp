package seedu.ddd.model.contact.common.predicate;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

import seedu.ddd.logic.commands.ListCommand;
import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.ParserUtil;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Id;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.tag.Tag;

//This class was generated with the help of ChatGPT for the logic to combine different predicates.
/**
 * Builds a chain of predicates to the List command depending on the PREFIX present in argMultimap.
 */
public class ContactPredicateBuilder {
    private ArgumentMultimap argMultimap;

    public ContactPredicateBuilder(ArgumentMultimap argMultimap) {
        this.argMultimap = argMultimap;
    }

    /**
     * Builds a combined {@code Predicate<Contact>} based on all the provided flags and prefixes.
     * The method dynamically chains predicates to filter contacts by client, vendor, name, tags, and ID.
     *
     * @return a {@code Predicate<Contact>} that filters contacts based on multiple criteria.
     * @throws ParseException if any input is invalid.
     */
    public Predicate<Contact> build() throws ParseException {
        Predicate<Contact> combinedPredicate = contact -> true; // Start with a default predicate (all contacts).

        // Check for each prefix and chain predicates accordingly.

        if (argMultimap.getValue(FLAG_CLIENT).isPresent()) {
            combinedPredicate = combinedPredicate.and(new ClientTypePredicate());
        }

        if (argMultimap.getValue(FLAG_VENDOR).isPresent()) {
            combinedPredicate = combinedPredicate.and(new VendorTypePredicate());
        }
        combinedPredicate = addNamePredicate(argMultimap, combinedPredicate);
        combinedPredicate = addTagPredicate(argMultimap, combinedPredicate);
        combinedPredicate = addIdPredicate(argMultimap, combinedPredicate);
        combinedPredicate = addPhonePredicate(argMultimap, combinedPredicate);
        combinedPredicate = addEmailPredicate(argMultimap, combinedPredicate);
        combinedPredicate = addAddressPredicate(argMultimap, combinedPredicate);

        return combinedPredicate;
    }
    private Predicate<Contact> addAddressPredicate(ArgumentMultimap argMultimap, Predicate<Contact> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_ADDRESS).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            String[] addressKeywords = trimmedArgs.split("\\s+");
            return combinedPredicate.and(new AddressContainsKeywordsPredicate(Arrays.asList(addressKeywords)));
        }
        return combinedPredicate;
    }
    private Predicate<Contact> addEmailPredicate(ArgumentMultimap argMultimap, Predicate<Contact> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            Email email = new Email(argMultimap.getValue(PREFIX_EMAIL).get());
            combinedPredicate = combinedPredicate.and(new ContactEmailPredicate(email));
        }
        return combinedPredicate;
    }
    private Predicate<Contact> addPhonePredicate(ArgumentMultimap argMultimap, Predicate<Contact> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_PHONE).get();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_EVENT_USAGE));
            }
            Phone phoneNumber = new Phone(trimmedArgs);
            combinedPredicate = combinedPredicate.and(new ContactPhonePredicate(phoneNumber));
        }
        return combinedPredicate;
    }
    private Predicate<Contact> addIdPredicate(ArgumentMultimap argMultimap, Predicate<Contact> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_ID).get();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_EVENT_USAGE));
            }
            Id id = new Id(Integer.parseInt(trimmedArgs));
            combinedPredicate = combinedPredicate.and(new ContactIdPredicate(id));
        }
        return combinedPredicate;
    }
    private Predicate<Contact> addTagPredicate(ArgumentMultimap argMultimap, Predicate<Contact> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            combinedPredicate = combinedPredicate.and(new ContactContainsTagPredicate(tagSet));
        }
        return combinedPredicate;
    }
    private Predicate<Contact> addNamePredicate(ArgumentMultimap argMultimap, Predicate<Contact> combinedPredicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            String[] nameKeywords = trimmedArgs.split("\\s+");
            combinedPredicate = combinedPredicate.and(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        return combinedPredicate;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactPredicateBuilder)) {
            return false;
        }

        ContactPredicateBuilder otherContactPredicateBuilder = (ContactPredicateBuilder) other;
        return argMultimap.equals(otherContactPredicateBuilder.argMultimap);
    }

}
