package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddSupplyOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.util.Remark;

/**
 * Parses input arguments and creates a new AddSupplyOrderCommand object.
 */
public class AddSupplyOrderCommandParser implements Parser<AddSupplyOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddSupplyOrderCommand}
     * and returns an {@code AddSupplyOrderCommand} object for execution.
     *
     * @param args the user input containing supplier name, phone number, and order IDs.
     * @return an {@code AddSupplyOrderCommand} object with the parsed supplier name, phone number, and order IDs.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddSupplyOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ORDER, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_ORDER, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddSupplyOrderCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_ORDER, PREFIX_REMARK);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElse("Guest Supplier"));
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Remark remark = new Remark(argMultimap.getValue(PREFIX_REMARK).orElse(""));

        String[] splitArgs = argMultimap.getValue(PREFIX_ORDER).orElse("").split("\\s+");

        if (splitArgs[0].isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSupplyOrderCommand.MESSAGE_USAGE));
        }
        ArrayList<Integer> idList = new ArrayList<>();

        for (int i = 0; i < splitArgs.length; i++) {
            try {
                idList.add(Integer.parseInt(splitArgs[i]));
            } catch (NumberFormatException e) {
                throw new ParseException("ID must be a valid integer.");
            }
        }

        return new AddSupplyOrderCommand(name, phone, idList, remark);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
