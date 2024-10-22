package seedu.address.model.Scheme;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class SCFAScheme extends Scheme {

    public final static String schemeName = "Student Care Fee Assistance (SCFA)";
    private final static int incomeThreshold = 4500;
    private final static int familySizeThreshold = 4;
    private final static int incomePerCapitaThreshold = 1125;

    public boolean isEligible(double income, int familySize, int incomePerCapita) {
        requireNonNull(income);
        requireNonNull(familySize);
        requireNonNull(incomePerCapita);

        return (income < incomeThreshold && familySize <= familySizeThreshold) ||
                (incomePerCapita < incomePerCapitaThreshold && familySize > familySizeThreshold);
    }

}
