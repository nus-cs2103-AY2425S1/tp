package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.commands.AddCustomerOrderCommand;
import seedu.address.logic.commands.AddSupplierOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;


public class AddSupplyOrderCommandParser implements Parser<AddSupplierOrderCommand> {

    public AddSupplierOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ORDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_ORDER, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElse(null));
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());

        String[] splitArgs = argMultimap.getValue(PREFIX_ORDER).orElse("").split("\\s+");

        if (splitArgs.length == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerOrderCommand.MESSAGE_USAGE));
        }

        ArrayList<Integer> idList = new ArrayList<>();

        for (int i = 0; i < splitArgs.length; i++) {
            try {
                idList.add(Integer.parseInt(splitArgs[i]));
            } catch (NumberFormatException e) {
                throw new ParseException("ID must be a valid integer.");
            }
        }

        return new AddSupplierOrderCommand(name, phone, idList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
