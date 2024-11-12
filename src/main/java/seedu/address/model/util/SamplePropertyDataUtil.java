package seedu.address.model.util;

import seedu.address.model.PropertyList;
import seedu.address.model.ReadOnlyPropertyList;
import seedu.address.model.property.Address;
import seedu.address.model.property.AskingPrice;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.Phone;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyType;

/**
 * Contains utility methods for populating {@code BuyerList} with sample data.
 */
public class SamplePropertyDataUtil {
    public static Property[] getSampleProperties() {
        return new Property[] {
            new Property(new LandlordName("Bob"), new Phone("98222256"),
                    new Address("Bishan"), new AskingPrice("500000"),
                    new PropertyType("House")),
            new Property(new LandlordName("John"), new Phone("82932832"),
                    new Address("Botanic Gardens"), new AskingPrice("1000000"),
                    new PropertyType("Landed Property")),
        };
    }

    public static ReadOnlyPropertyList getSamplePropertyList() {
        PropertyList sampleP = new PropertyList();
        for (Property sampleProperty : getSampleProperties()) {
            sampleP.addProperty(sampleProperty);
        }
        return sampleP;
    }


}
