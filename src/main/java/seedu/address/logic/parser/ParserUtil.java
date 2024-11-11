package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index provided is invalid";
    public static final String MESSAGE_INVALID_INPUT = "Input is not of the correct format";

    /**
     * Checks if a given string is of the form "px", where "x" is an integer of up to 3 digits.
     *
     * @param input The string to check.
     * @return {@code true} if the string is of the form "px", {@code false} otherwise.
     */

    public static boolean isValidInput(String input) {
        // The pattern matches 'p' or 'o' followed by an optional '-' and then an integer (1 to 3 digits)
        String pattern1 = "^p-?\\d{1,3}$";
        String pattern2 = "^o-?\\d{1,3}$";
        return input.matches(pattern1) || input.matches(pattern2);
    }

    /**
     * Parses {@code oneBasedIndexAndType} into a {@code pair containing an index and string} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Pair parseIndexAndType(String oneBasedIndexAndType) throws ParseException {
        String trimmedIndex = oneBasedIndexAndType.trim();
        if (!isValidInput(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INPUT);
        }
        if (Integer.parseInt(trimmedIndex.substring(1)) <= 0) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        Pair res = new Pair(Index.fromOneBased(Integer.parseInt(trimmedIndex.substring(1))),
                Character.toString(trimmedIndex.charAt(0)));
        return res;
    }

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
    public static seedu.address.model.owner.Name parseOwnerName(String ownerName) throws ParseException {
        requireNonNull(ownerName);
        String trimmedName = ownerName.trim();
        if (!seedu.address.model.owner.Name.isValidName(trimmedName)) {
            throw new ParseException(seedu.address.model.owner.Name.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.owner.Name(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code Name} for a pet.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static seedu.address.model.pet.Name parsePetName(String petName) throws ParseException {
        requireNonNull(petName);
        String trimmedPetName = petName.trim();
        if (!seedu.address.model.pet.Name.isValidName(trimmedPetName)) {
            throw new ParseException(seedu.address.model.pet.Name.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.pet.Name(trimmedPetName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static seedu.address.model.owner.Phone parseOwnerPhone(String ownerPhone) throws ParseException {
        requireNonNull(ownerPhone);
        String trimmedPhone = ownerPhone.trim();
        if (!seedu.address.model.owner.Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(seedu.address.model.owner.Phone.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.owner.Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static seedu.address.model.owner.Address parseOwnerAddress(String ownerAddress) throws ParseException {
        requireNonNull(ownerAddress);
        String trimmedAddress = ownerAddress.trim();
        if (!seedu.address.model.owner.Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(seedu.address.model.owner.Address.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.owner.Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static seedu.address.model.owner.Email parseOwnerEmail(String ownerEmail) throws ParseException {
        requireNonNull(ownerEmail);
        String trimmedEmail = ownerEmail.trim();
        if (!seedu.address.model.owner.Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(seedu.address.model.owner.Email.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.owner.Email(trimmedEmail);
    }

    /**
     * Parses a {@code String icNumber} into an {@code IdentificationCardNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code icNumber} is invalid.
     */
    public static seedu.address.model.owner.IdentificationCardNumber parseOwnerIcNumber(String icNumber)
            throws ParseException {
        requireNonNull(icNumber);
        String trimmedIcNumber = icNumber.trim();
        if (!seedu.address.model.owner.IdentificationCardNumber.isValidIcNumber(trimmedIcNumber)) {
            throw new ParseException(seedu.address.model.owner.IdentificationCardNumber.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.owner.IdentificationCardNumber(trimmedIcNumber);
    }

    /**
     * Parses a {@code String age} into an {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static seedu.address.model.pet.Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.trim();
        if (!seedu.address.model.pet.Age.isValidAge(trimmedAge)) {
            throw new ParseException(seedu.address.model.pet.Age.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.pet.Age(trimmedAge);
    }

    /**
     * Parses a {@code String breed} into an {@code Breed}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code breed} is invalid.
     */
    public static seedu.address.model.pet.Breed parseBreed(String breed) throws ParseException {
        requireNonNull(breed);
        String trimmedBreed = breed.trim();
        if (!seedu.address.model.pet.Breed.isValidBreed(trimmedBreed)) {
            throw new ParseException(seedu.address.model.pet.Breed.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.pet.Breed(trimmedBreed);
    }

    /**
     * Parses a {@code String sex} into an {@code Sex}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sex} is invalid.
     */
    public static seedu.address.model.pet.Sex parseSex(String sex) throws ParseException {
        requireNonNull(sex);
        String trimmedSex = sex.trim();
        if (!seedu.address.model.pet.Sex.isValidSex(trimmedSex)) {
            throw new ParseException(seedu.address.model.pet.Sex.MESSAGE_CONSTRAINTS);
        }
        if (trimmedSex.equalsIgnoreCase("m")) {
            return new seedu.address.model.pet.Sex("Male");
        } else if (trimmedSex.equalsIgnoreCase("f")) {
            return new seedu.address.model.pet.Sex("Female");
        }
        return new seedu.address.model.pet.Sex(trimmedSex);
    }

    /**
     * Parses a {@code String species} into an {@code Species}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code species} is invalid.
     */
    public static seedu.address.model.pet.Species parseSpecies(String species) throws ParseException {
        requireNonNull(species);
        String trimmedSpecies = species.trim();
        if (!seedu.address.model.pet.Species.isValidSpecies(trimmedSpecies)) {
            throw new ParseException(seedu.address.model.pet.Species.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.pet.Species(trimmedSpecies);
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
