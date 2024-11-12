package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_KEYWORD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.predicates.CompanyContainsKeywordPredicate;
import seedu.address.model.supplier.predicates.NameContainsKeywordPredicate;
import seedu.address.model.supplier.predicates.ProductContainsKeywordPredicate;

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
        List<Predicate<Supplier>> supplierPredicates = new ArrayList<>();

        addNamePredicateIfPresent(argMultimap, supplierPredicates);
        addCompanyPredicateIfPresent(argMultimap, supplierPredicates);
        addProductPredicateIfPresent(argMultimap, supplierPredicates);


        return new FindSupplierCommand(supplierPredicates);
    }

    /**
     * Adds a NameContainsKeywordPredicate to the supplierPredicates list
     * if the name prefix is present and its value is not empty.
     *
     * @param argMultimap The argument map containing the arguments given by user.
     * @param supplierPredicates The list to add the predicate to.
     * @throws ParseException if the argument for the name prefix is empty.
     */
    private void addNamePredicateIfPresent(ArgumentMultimap argMultimap,
                                           List<Predicate<Supplier>> supplierPredicates) throws ParseException {

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {

            String nameKeyword = argMultimap.getValue(PREFIX_NAME).get();

            if (nameKeyword.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_NAME));
            }

            supplierPredicates.add(new NameContainsKeywordPredicate(nameKeyword));
        }
    }

    /**
     * Adds a CompanyContainsKeywordPredicate to the supplierPredicates list
     * if the company prefix is present and its value is not empty.
     *
     * @param argMultimap The argument map containing the arguments given by user.
     * @param supplierPredicates The list to add the predicate to.
     * @throws ParseException if the argument for the company prefix is empty.
     */
    private void addCompanyPredicateIfPresent(ArgumentMultimap argMultimap,
                                           List<Predicate<Supplier>> supplierPredicates) throws ParseException {

        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {

            String companyKeyword = argMultimap.getValue(PREFIX_COMPANY).get();

            if (companyKeyword.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_COMPANY));
            }

            supplierPredicates.add(new CompanyContainsKeywordPredicate(companyKeyword));
        }
    }

    /**
     * Adds a ProductContainsKeywordPredicate to the supplierPredicates list
     * if the product prefix is present and its value is not empty.
     *
     * @param argMultimap The argument map containing the arguments given by user.
     * @param supplierPredicates The list to add the predicate to.
     * @throws ParseException if the argument for the product prefix is empty.
     */
    private void addProductPredicateIfPresent(ArgumentMultimap argMultimap,
                                              List<Predicate<Supplier>> supplierPredicates) throws ParseException {

        if (argMultimap.getValue(PREFIX_PRODUCT).isPresent()) {

            String productKeyword = argMultimap.getValue(PREFIX_PRODUCT).get();

            if (productKeyword.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_PRODUCT));
            }

            supplierPredicates.add(new ProductContainsKeywordPredicate(productKeyword));
        }
    }

}
