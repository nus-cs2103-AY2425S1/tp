package seedu.address.model.Scheme;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.time.LocalDate;

import seedu.address.model.person.Person;
import seedu.address.model.Scheme.Scheme;
import seedu.address.model.Scheme.FASScheme;
import seedu.address.model.Scheme.SCFAScheme;

public class SchemeRetrieval {

    private final Person targetFamily;
    private final double income;
    private final int age;
    private final int familySize;

    private final int incomePerCapita;

    private final ArrayList<Scheme> schemes = new ArrayList<>();

    private final ArrayList<Scheme> allSchemes = new ArrayList<>();

    public SchemeRetrieval(Person targetFamily) {
        this.targetFamily = targetFamily;
        this.income = targetFamily.getIncome().getValue();
        LocalDate currentDate = LocalDate.now();
        this.age = targetFamily.getDateOfBirth().toLocalDate().getYear() - currentDate.getYear();
        this.familySize = 1;
        this.incomePerCapita = (int) Math.round(income / familySize);
        allSchemes.add(new FASScheme());
        allSchemes.add(new SCFAScheme());
    }

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
