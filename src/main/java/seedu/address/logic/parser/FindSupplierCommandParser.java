package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_KEYWORD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;

import java.util.function.Predicate;

import seedu.address.logic.commands.FindSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.CompanyContainsKeywordPredicate;
import seedu.address.model.person.predicates.NameContainsPredicate;
import seedu.address.model.person.predicates.ProductContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindSupplierCommand object.
 */
public class FindSupplierCommandParser implements Parser<FindSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDeliveryCommand
     * and returns a FindDeliveryCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FindSupplierCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRODUCT, PREFIX_COMPANY);

        if (!argMultimap.getValue(PREFIX_NAME).isPresent()
                && !argMultimap.getValue(PREFIX_PRODUCT).isPresent()
                && !argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSupplierCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PRODUCT, PREFIX_COMPANY);
        Predicate<Person> supplierPredicates = x -> true;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameKeyword = argMultimap.getValue(PREFIX_NAME).get();
            if (nameKeyword.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_NAME));
            }
            supplierPredicates = supplierPredicates.and(new NameContainsPredicate(nameKeyword));
        }

        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            String companyKeyword = argMultimap.getValue(PREFIX_COMPANY).get();
            if (companyKeyword.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_COMPANY));
            }
            supplierPredicates = supplierPredicates.and(new CompanyContainsKeywordPredicate(companyKeyword));
        }

        if (argMultimap.getValue(PREFIX_PRODUCT).isPresent()) {
            String productKeyword = argMultimap.getValue(PREFIX_PRODUCT).get();
            if (productKeyword.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_PRODUCT));
            }
            supplierPredicates = supplierPredicates.and(new ProductContainsKeywordPredicate(productKeyword));
        }

        return new FindSupplierCommand(supplierPredicates);
    }
}
