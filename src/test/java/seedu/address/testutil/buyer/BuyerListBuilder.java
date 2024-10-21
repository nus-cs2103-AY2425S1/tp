package seedu.address.testutil.buyer;

import seedu.address.model.BuyerList;
import seedu.address.model.buyer.Buyer;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code BuyerList ab = new BuyerListBuilder().withBuyer("John", "Doe").build();}
 */
public class BuyerListBuilder {

    private BuyerList buyerList;

    public BuyerListBuilder() {
        buyerList = new BuyerList();
    }

    public BuyerListBuilder(BuyerList buyerList) {
        this.buyerList = buyerList;
    }

    /**
     * Adds a new {@code Buyer} to the {@code BuyerList} that we are building.
     */
    public BuyerListBuilder withBuyer(Buyer buyer) {
        buyerList.addBuyer(buyer);
        return this;
    }

    public BuyerList build() {
        return buyerList;
    }
}
