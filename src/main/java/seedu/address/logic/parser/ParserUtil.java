package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.PublicAddressFactory;
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
     *
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
     * Parses a {@code String publicAddress} into a {@code PublicAddress}.
     */
    public static PublicAddress parsePublicAddress(String publicAddress, String paLabel, String network)
            throws ParseException {
        requireNonNull(publicAddress);
        requireNonNull(paLabel);

        String trimmedPublicAddress = publicAddress.trim();
        String trimmedPaLabel = paLabel.trim();
        Network parsedNetwork = parseNetwork(network);

        try {
            return PublicAddressFactory.createPublicAddress(parsedNetwork, trimmedPublicAddress, trimmedPaLabel);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Parses {@code Collection<String> publicAddresses} into a {@code Map<Network, Set<PublicAddress>>}.
     */
    public static Map<Network, Set<PublicAddress>> parsePublicAddresses(Collection<String> publicAddresses)
            throws ParseException {
        requireNonNull(publicAddresses);
        Map<Network, Set<PublicAddress>> publicAddressesMap = new HashMap<>();
        for (String publicAddress : publicAddresses) {

            // TODO: Implement tokenizer in a separate file
            String delimiter = ">";
            String trimmedPublicAddress = publicAddress.trim();
            String[] addressArgs = trimmedPublicAddress.split(delimiter);

            if (addressArgs.length != 2) {
                throw new ParseException("Missing arguments for public address");
            }

            String network = addressArgs[0];
            String address = addressArgs[1];

            Network parsedNetwork = parseNetwork(network);
            PublicAddress parsedPublicAddress = parsePublicAddress(address, PublicAddress.DEFAULT_LABEL, network);

            if (!publicAddressesMap.containsKey(parsedNetwork)) {
                publicAddressesMap.put(parsedNetwork, new HashSet<>());
            }
            publicAddressesMap.get(parsedNetwork).add(parsedPublicAddress);
        }
        return publicAddressesMap;
    }

    /**
     * Parses Label for BTC Address by cleaning the string input given by the user
     *
     * @param label
     * @return
     */
    public static String parsePublicAddressLabel(String label) throws ParseException {
        requireNonNull(label);
        if (!PublicAddress.isValidPublicAddressLabel(label)) {
            throw new ParseException(PublicAddress.MESSAGE_CONSTRAINTS);
        }
        return label.strip();
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
     * Parses a {@code String network} into a {@code Network}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code network} is invalid.
     */
    public static Network parseNetwork(String network) throws ParseException {
        requireNonNull(network);
        String trimmedNetwork = network.trim();
        try {
            return Network.valueOf(trimmedNetwork);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Network.MESSAGE_CONSTRAINTS);
        }
    }

}
