package seedu.address.testutil.buyer;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.buyer.Budget;
import seedu.address.model.buyer.Buyer;
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
    public static final String DEFAULT_BUDGET = "1,000,000";

    private Name name;
    private Phone phone;
    private Email email;
    private Budget budget;
    private Set<Tag> tags;

    /**
     * Creates a {@code BuyerBuilder} with the default details.
     */
    public BuyerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        budget = new Budget(DEFAULT_BUDGET);
        tags = new HashSet<>();
    }

    /**
     * Initializes the BuyerBuilder with the data of {@code buyerToCopy}.
     */
    public BuyerBuilder(Buyer buyerToCopy) {
        name = buyerToCopy.getName();
        phone = buyerToCopy.getPhone();
        email = buyerToCopy.getEmail();
        budget = buyerToCopy.getBudget();
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
     * Sets the {@code Budget} of the {@code Buyer} that we are building.
     */
    public BuyerBuilder withBudget(String budget) {
        this.budget = new Budget(budget);
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

    public Buyer build() {
        return new Buyer(name, phone, email, budget, tags);
    }

}
