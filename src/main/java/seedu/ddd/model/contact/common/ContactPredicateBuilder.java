package seedu.ddd.model.contact.common;

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

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            String[] nameKeywords = trimmedArgs.split("\\s+");
            combinedPredicate = combinedPredicate.and(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            combinedPredicate = combinedPredicate.and(new ContactContainsTagPredicate(tagSet));
        }

        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            Id id = new Id(Integer.parseInt(argMultimap.getValue(PREFIX_ID).get()));
            combinedPredicate = combinedPredicate.and(new ContactIdPredicate(id));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Phone phoneNumber = new Phone(argMultimap.getValue(PREFIX_PHONE).get());
            combinedPredicate = combinedPredicate.and(new ContactPhonePredicate(phoneNumber));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            Email email = new Email(argMultimap.getValue(PREFIX_EMAIL).get());
            combinedPredicate = combinedPredicate.and(new ContactEmailPredicate(email));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_ADDRESS).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            String[] addressKeywords = trimmedArgs.split("\\s+");
            combinedPredicate = combinedPredicate.and(
                    new AddressContainsKeywordsPredicate(Arrays.asList(addressKeywords)));
        }

        return combinedPredicate;
    }
    // TODO: Implement this for parser test
    /*
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ContactPredicateBuilder other = (ContactPredicateBuilder) obj;
        try {
            Predicate<Contact> thisPredicate = this.build();
            Predicate<Contact> otherPredicate = other.build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    */
}
