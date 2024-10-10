package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.car.Car;
import seedu.address.model.car.CarMake;
import seedu.address.model.car.CarModel;
import seedu.address.model.car.Vin;
import seedu.address.model.car.Vrn;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;




/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String vin} into a {@code Vin}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code vin} is invalid.
     */
    public static Vin parseVin(String vin) throws ParseException {
        requireNonNull(vin);
        String trimmedVin = vin.trim();
        if (!Vin.isValidVin(trimmedVin)) {
            throw new ParseException(Vin.MESSAGE_CONSTRAINTS);
        }
        return new Vin(trimmedVin);
    }

    /**
     * Parses a {@code String vrn} into a {@code Vrn}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code vrn} is invalid.
     */
    public static Vrn parseVrn(String vrn) throws ParseException {
        requireNonNull(vrn);
        String trimmedVrn = vrn.trim();
        if (!Vrn.isValidVrn(trimmedVrn)) {
            throw new ParseException(Vrn.MESSAGE_CONSTRAINTS);
        }
        return new Vrn(trimmedVrn);
    }

    /**
     * Parses a {@code String make} into a {@code CarMake}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code make} is invalid.
     */
    public static CarMake parseCarMake(String make) throws ParseException {
        requireNonNull(make);
        String trimmedMake = make.trim();
        if (!CarMake.isValidCarMake(trimmedMake)) {
            throw new ParseException(CarMake.MESSAGE_CONSTRAINTS);
        }
        return new CarMake(trimmedMake);
    }

    /**
     * Parses a {@code String model} into a {@code CarModel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code model} is invalid.
     */
    public static CarModel parseCarModel(String model) throws ParseException {
        requireNonNull(model);
        String trimmedModel = model.trim();
        if (!CarModel.isValidCarModel(trimmedModel)) {
            throw new ParseException(CarModel.MESSAGE_CONSTRAINTS);
        }
        return new CarModel(trimmedModel);
    }

    /**
     * Parses a {@code String vrn} into a {@code Vrn}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code vrn} is invalid.
     */
    public static Car parseCar(String vrn, String vin, String make, String model) throws ParseException {
        requireNonNull(vrn);
        requireNonNull(vin);
        requireNonNull(make);
        requireNonNull(model);

        if (!Vin.isValidVin(vin)) {
            throw new ParseException(Vin.MESSAGE_CONSTRAINTS);
        }

        if (!Vrn.isValidVrn(vrn)) {
            throw new ParseException(Vrn.MESSAGE_CONSTRAINTS);
        }

        if (!CarMake.isValidCarMake(make)) {
            throw new ParseException(CarMake.MESSAGE_CONSTRAINTS);
        }

        if (!CarModel.isValidCarModel(model)) {
            throw new ParseException(CarModel.MESSAGE_CONSTRAINTS);
        }

        Vrn vrnObj = new Vrn(vrn.trim());
        Vin vinObj = new Vin(vin.trim());
        CarMake makeObj = new CarMake(make.trim());
        CarModel modelObj = new CarModel(model.trim());

        return new Car(vrnObj, vinObj, makeObj, modelObj);
    }


    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
