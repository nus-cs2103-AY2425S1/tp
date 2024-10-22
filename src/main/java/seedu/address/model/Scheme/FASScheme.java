package seedu.address.model.Scheme;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class FASScheme extends Scheme {
    public final static String schemeName = "MOE Financial Assistance Scheme (MOE FAS)";
    public final static int incomeThreshold = 3000;
    public final static int familySizeThreshold = 0;

    public final static int incomePerCapitaThreshold = 750;

    @Override
    public boolean isEligible(double income, int familySize, int incomePerCapita) {
        requireNonNull(income);
        requireNonNull(familySize);
        requireNonNull(incomePerCapita);

        return (income < incomeThreshold) || (incomePerCapita < incomePerCapitaThreshold);
    }

    @Override
    public String getSchemeName() {
        return schemeName;
    }
}
