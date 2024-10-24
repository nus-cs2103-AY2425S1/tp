package seedu.address.model.scheme;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import seedu.address.model.person.Person;

/**
 * Handles the logic of retriving schemes for a person.
 */
public class SchemeRetrieval {

    private final Person targetFamily;
    private final double income;
    private final int age;
    private final int familySize;

    private final int incomePerCapita;

    private final ArrayList<Scheme> schemes = new ArrayList<>();

    private final ArrayList<Scheme> allSchemes = new ArrayList<>();

    /**
     * Constructor for SchemeRetrieval.
     */
    public SchemeRetrieval(Person targetFamily) {
        this.targetFamily = targetFamily;
        this.income = targetFamily.getIncome().getValue();
        LocalDate currentDate = LocalDate.now();
        this.age = Period.between(targetFamily.getDateOfBirth().toLocalDate(), currentDate).getYears();
        this.familySize = targetFamily.getFamilySize().getValue();
        this.incomePerCapita = (int) Math.round(income / familySize);
        allSchemes.add(new MoeFinancialAssistanceScheme());
        allSchemes.add(new StudentCareFeeAssistanceScheme());
    }

    /**
     * Returns the schemes that the person is eligible for.
     * @return ArrayList of schemes that the person is eligible for.
     */
    public ArrayList<Scheme> getSchemes() {
        requireNonNull(targetFamily);
        for (Scheme scheme : allSchemes) {
            if (scheme.isEligible(income, familySize, incomePerCapita)) {
                schemes.add(scheme);
            }
        }
        return schemes;
    }





}
