package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddPropertyToBuyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Apartment;
import seedu.address.model.person.Bto;
import seedu.address.model.person.Condo;
import seedu.address.model.person.Hdb;
import seedu.address.model.person.HousingType;
import seedu.address.model.person.OtherProperty;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddPropertyToBuyCommand object
 */
public class AddPropertyToBuyParser implements Parser<AddPropertyToBuyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPropertyToBuyCommand
     * and returns an AddPropertyToBuyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPropertyToBuyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HOUSING_TYPE, PREFIX_BUYING_PRICE,
                        PREFIX_POSTAL_CODE, PREFIX_UNIT_NUMBER, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_HOUSING_TYPE, PREFIX_BUYING_PRICE,
                PREFIX_POSTAL_CODE, PREFIX_UNIT_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "not implemented yet: AddPropertyToBuyCommand.MESSAGE_USAGE"));
        }

        // Create a new Property object here and pass it to AddPropertyToBuyCommand(Property property);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HOUSING_TYPE,
                PREFIX_BUYING_PRICE, PREFIX_POSTAL_CODE, PREFIX_UNIT_NUMBER);
        HousingType housingType = ParserUtil.parseHousingType(argMultimap.getValue(PREFIX_HOUSING_TYPE).get());
        Price sellingPrice = ParserUtil.parseSellingPrice(argMultimap.getValue(PREFIX_BUYING_PRICE).get());
        PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTAL_CODE).get());
        UnitNumber unitNumber = ParserUtil.parseUnitNumber(argMultimap.getValue(PREFIX_UNIT_NUMBER).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Property property = getSpecificPropertyObject(housingType, sellingPrice, postalCode, unitNumber, tagList);

        return new AddPropertyToBuyCommand(); // add property argument
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static Property getSpecificPropertyObject(HousingType housingType, Price buyingPrice,
                                                      PostalCode postalCode, UnitNumber unitNumber, Set<Tag> tagList) {
        return switch (housingType) {
        case CONDO -> new Condo(postalCode, unitNumber, buyingPrice, tagList);
        case HDB -> new Hdb(postalCode, unitNumber, buyingPrice, tagList);
        case APARTMENT -> new Apartment(postalCode, unitNumber, buyingPrice, tagList);
        case BTO -> new Bto(postalCode, unitNumber, buyingPrice, tagList);
        case OTHERS -> new OtherProperty(postalCode, unitNumber, buyingPrice, tagList);
        default -> null;
        };
    }
}
