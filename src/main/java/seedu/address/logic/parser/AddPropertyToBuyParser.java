package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPropertyToBuyCommand;
import seedu.address.logic.commands.AddPropertyToSellCommand;
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
                PREFIX_POSTAL_CODE, PREFIX_UNIT_NUMBER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPropertyToBuyCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPropertyToBuyCommand.MESSAGE_USAGE), pe);
        }

        // Create a new Property object here and pass it to AddPropertyToBuyCommand(Property property);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HOUSING_TYPE,
                PREFIX_BUYING_PRICE, PREFIX_POSTAL_CODE, PREFIX_UNIT_NUMBER);
        HousingType housingType = ParserUtil.parseHousingType(argMultimap.getValue(PREFIX_HOUSING_TYPE).get());
        Price buyingPrice = ParserUtil.parseBuyingPrice(argMultimap.getValue(PREFIX_BUYING_PRICE).get());
        PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTAL_CODE).get());
        UnitNumber unitNumber = ParserUtil.parseUnitNumber(argMultimap.getValue(PREFIX_UNIT_NUMBER).get());
        if (!ParserUtil.isValidNumberOfPropertyTags(argMultimap.getAllValues(PREFIX_TAG))) {
            throw new ParseException(AddPropertyToSellCommand.MESSAGE_PROPERTY_TAG_LIMIT);
        }
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Property property = getSpecificPropertyObject(housingType, buyingPrice, postalCode, unitNumber, tagList);

        return new AddPropertyToBuyCommand(index, property);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Creates a specific Property object based on the given {@code HousingType}
     * The type of property will be one of {@code Condo}, {@code Hdb},
     * {@code Apartment}, {@code Bto}, or {@code OtherProperty} depending on
     * provided {@code HousingType}
     *
     * @param housingType the type of housing,
     *                    used to determine the specific subclass of {@code Property} to instantiate
     * @param buyingPrice the price at which the property is being bought
     * @param postalCode the postal code of the property
     * @param unitNumber the unit number of the property
     * @param tagList set of tags associated with the property
     * @return a specific {@code Property} object corresponding to the {@code HousingType},
     *          or {@code null} if {@code HousingType is not recognised}
     */
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
