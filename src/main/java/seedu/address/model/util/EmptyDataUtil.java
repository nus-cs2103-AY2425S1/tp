package seedu.address.model.util;

import seedu.address.model.CampusConnect;
import seedu.address.model.ReadOnlyCampusConnect;

/**
 * Contains utility method for populating {@code CampusConnect} with empty data.
 */
public class EmptyDataUtil {
    public static ReadOnlyCampusConnect getSampleCampusConnect() {
        return new CampusConnect();
    }
}
