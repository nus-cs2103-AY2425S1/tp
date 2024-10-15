package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.car.Car;
import seedu.address.model.car.CarMake;
import seedu.address.model.car.CarModel;
import seedu.address.model.car.Vin;
import seedu.address.model.car.Vrn;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAKE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VRN;


/**
 * Parses input arguments and creates a new AddCarCommand object
 */
public class AddCarCommandParser implements Parser<AddCarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCarCommand
     * and returns an AddCarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddCarCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL);

        // Check if all required prefixes are present and for duplicate prefixes
        if (!arePrefixesPresent(argMultimap, PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCarCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL);

        // Prepare the index value from the ArgumentMultimap to pass to AddCarCommand
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCarCommand.MESSAGE_USAGE), pe);
        }

        // Prepare for car creation
        Vrn vrn = new Vrn(argMultimap.getValue(PREFIX_VRN).get());
        Vin vin = new Vin(argMultimap.getValue(PREFIX_VIN).get());
        CarMake make = new CarMake(argMultimap.getValue(PREFIX_MAKE).get());
        CarModel model = new CarModel(argMultimap.getValue(PREFIX_MODEL).get());

        Car carToAdd;

        carToAdd = new Car(vrn, vin, make, model);

        return new AddCarCommand(index, carToAdd);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     * This snippet was taken from the AddClientCommandParser class.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
