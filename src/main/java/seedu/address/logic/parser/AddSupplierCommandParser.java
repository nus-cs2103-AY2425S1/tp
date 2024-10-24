package seedu.address.logic.parser;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.product.Ingredients;
import seedu.address.model.tag.Tag;

public class AddSupplierCommandParser implements Parser<AddSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSupplierCommand
     * and returns an AddSupplierCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSupplierCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_PREFERENCE, PREFIX_INGREDIENTS_SUPPLIED, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_PREFERENCE, PREFIX_INGREDIENTS_SUPPLIED)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSupplierCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        DietaryPreference preference = ParserUtil.parsePreference(argMultimap.getValue(PREFIX_PREFERENCE).get());
        Ingredients ingredientsSupplied = ParserUtil.parseIngredients(argMultimap.getValue(PREFIX_INGREDIENTS_SUPPLIED).get());
        Remark remark = new Remark(""); // No direct remark input allowed
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        // Add the "supplier" tag explicitly
        tagList.add(new Tag("supplier"));

        Supplier supplier = new Supplier(name, phone, email, address, preference, ingredientsSupplied, remark, tagList);

        return new AddSupplierCommand(supplier);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
