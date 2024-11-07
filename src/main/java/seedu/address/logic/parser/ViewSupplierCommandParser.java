package seedu.address.logic.parser;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.ViewSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.supplier.NameContainsKeywordsPredicate;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierTagsPredicate;

/**
 * Parses input arguments and creates a new ViewSupplierCommand object
 */
public class ViewSupplierCommandParser implements Parser<ViewSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewSupplierCommand
     * and returns a ViewSupplierCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewSupplierCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ViewSupplierCommand(PREDICATE_SHOW_ALL_SUPPLIERS);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_TAG);

        String nameKeyword = argMultimap.getValue(CliSyntax.PREFIX_NAME).orElse("");
        List<String> nameKeywords = nameKeyword.isEmpty() ? new ArrayList<>() : List.of(nameKeyword);
        List<String> tags = argMultimap.getAllValues(CliSyntax.PREFIX_TAG);

        Predicate<Supplier> predicate = supplier -> true;

        if (!nameKeywords.isEmpty()) {
            predicate = predicate.and(new NameContainsKeywordsPredicate(nameKeywords));
        }

        if (!tags.isEmpty()) {
            predicate = predicate.and(new SupplierTagsPredicate(tags));
        }

        return new ViewSupplierCommand(predicate);
    }
}
