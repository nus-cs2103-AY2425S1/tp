package seedu.address.testutil.buyer;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.buyer.Address;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.BuyerType;
import seedu.address.model.buyer.Email;
import seedu.address.model.buyer.Name;
import seedu.address.model.buyer.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleBuyerDataUtil;

/**
 * A utility class to help with building Buyer objects.
 */
public class BuyerBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BUYER_TYPE = "buyer";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private BuyerType buyerType;
    private Set<Tag> tags;

    /**
     * Creates a {@code BuyerBuilder} with the default details.
     */
    public BuyerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        buyerType = new BuyerType(DEFAULT_BUYER_TYPE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the BuyerBuilder with the data of {@code buyerToCopy}.
     */
    public BuyerBuilder(Buyer buyerToCopy) {
        name = buyerToCopy.getName();
        phone = buyerToCopy.getPhone();
        email = buyerToCopy.getEmail();
        address = buyerToCopy.getAddress();
        buyerType = buyerToCopy.getBuyerType();
        tags = new HashSet<>(buyerToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Buyer} that we are building.
     */
    public BuyerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Buyer} that we are building.
     */
    public BuyerBuilder withTags(String ... tags) {
        this.tags = SampleBuyerDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Buyer} that we are building.
     */
    public BuyerBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Buyer} that we are building.
     */
    public BuyerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Buyer} that we are building.
     */
    public BuyerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code BuyerType} of the {@code Buyer} that we are building.
     */
    public BuyerBuilder withBuyerType(String buyerType) {
        this.buyerType = new BuyerType(buyerType);
        return this;
    }

    public Buyer build() {
        return new Buyer(name, phone, email, address, buyerType, tags);
    }

}
