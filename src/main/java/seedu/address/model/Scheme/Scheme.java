package seedu.address.model.Scheme;

import java.util.Optional;
import seedu.address.model.person.Person;

public abstract class Scheme {
    public final static String schemeName = null;
    public final static int incomeThreshold = 0;
    public final static int familySizeThreshold = 0;
    public final static int incomePerCapitaThreshold = 0;

    public abstract boolean isEligible(double income, int familySize, int incomePerCapita);

    public String getSchemeName() {
        return schemeName;
    }
}
