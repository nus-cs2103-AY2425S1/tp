package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.pet.Age;
import seedu.address.model.pet.Breed;
import seedu.address.model.pet.Sex;
import seedu.address.model.pet.Species;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Checks if a given string is of the form "px", where "x" is an integer of up to 3 digits.
     *
     * @param input The string to check.
     * @return {@code true} if the string is of the form "px", {@code false} otherwise.
     */

    public static boolean isValidInput(String input) {
        // The pattern matches 'p' or 'o' followed by an integer (1 to 3 digits)
        String pattern1 = "^p\\d{1,3}$";
        String pattern2 = "^o\\d{1,3}$";
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
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        Pair res = new Pair(Index.fromOneBased(Integer.parseInt(trimmedIndex.substring(1))),
                Character.toString(trimmedIndex.charAt(0)));
        return res;
        //return Index.fromOneBased(Integer.parseInt(trimmedIndex));
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
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
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
        return new seedu.address.model.pet.Name(capitalizeEachWord(trimmedPetName));
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
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
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
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
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
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.trim();
        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }
        return new Age(capitalizeEachWord(trimmedAge));
    }

    /**
     * Parses a {@code String breed} into an {@code Breed}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code breed} is invalid.
     */
    public static Breed parseBreed(String breed) throws ParseException {
        requireNonNull(breed);
        String trimmedBreed = breed.trim();
        if (!Breed.isValidBreed(trimmedBreed)) {
            throw new ParseException(Breed.MESSAGE_CONSTRAINTS);
        }
        return new Breed(capitalizeEachWord(trimmedBreed));
    }

    /**
     * Parses a {@code String sex} into an {@code Sex}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sex} is invalid.
     */
    public static Sex parseSex(String sex) throws ParseException {
        requireNonNull(sex);
        String trimmedSex = sex.trim();
        if (!Sex.isValidSex(trimmedSex)) {
            throw new ParseException(Sex.MESSAGE_CONSTRAINTS);
        }
        if (trimmedSex.equalsIgnoreCase("m")) {
            return new Sex("Male");
        } else if (trimmedSex.equalsIgnoreCase("f")) {
            return new Sex("Female");
        }
        return new Sex(capitalizeEachWord(trimmedSex));
    }

    /**
     * Parses a {@code String species} into an {@code Species}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code species} is invalid.
     */
    public static Species parseSpecies(String species) throws ParseException {
        requireNonNull(species);
        String trimmedSpecies = species.trim();
        if (!Species.isValidSpecies(trimmedSpecies)) {
            throw new ParseException(Species.MESSAGE_CONSTRAINTS);
        }
        return new Species(capitalizeEachWord(trimmedSpecies));
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

    /**
     * Helper function to capitalize the first letter of each word
     * while converting the rest of the characters to lowercase.
     *
     * @param input The string to be formatted.
     * @return A string with each word's first letter capitalized and the rest in lowercase.
     */
    public static String capitalizeEachWord(String input) {
        String[] words = input.toLowerCase().split("\\s+");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return formattedName.toString().trim();
    }

}
