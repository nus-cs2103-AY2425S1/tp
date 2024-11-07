package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Ward;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Notes EMPTY_NOTES = new Notes("");
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Id("P12345"), new Ward("A1"),
                    new Diagnosis("A. fib (Atrial Fibrillation)"),
                    new Medication("Metoprolol"), EMPTY_NOTES),
            new Person(new Name("Bernice Yu"), new Id("P23456"), new Ward("B1"),
                    new Diagnosis("Celiac Disease/Gluten Sensitivity"),
                    new Medication("Corticosteroids"),
                    new Notes("patient is on a strict gluten free diet")),
            new Person(new Name("Charlotte Oliveiro"), new Id("P34567"), new Ward("C2"),
                    new Diagnosis("Major Depressive Disorder - Recurrent"),
                    new Medication("Fluoxetine (Prozac)"), EMPTY_NOTES),
            new Person(new Name("David Li"), new Id("P45678"), new Ward("A1"),
                    new Diagnosis("Hypertensive Heart Disease - Stage 2"),
                    new Medication("Angiotensin-Converting Enzyme (ACE) Inhibitors"),
                    new Notes("patient is prone to falling")),
            new Person(new Name("Irfan Ibrahim"), new Id("P56789"), new Ward("D1"),
                    new Diagnosis("Acute Respiratory Distress Syndrome (ARDS) / COVID-19"),
                    new Medication("Oxygen Therapy"), new Notes("patient required checkup every 4 hours")),
            new Person(new Name("Roy Balakrishnan"), new Id("P67890"), new Ward("B1"),
                    new Diagnosis("Streptococcus Pneumoniae"),
                    new Medication("Penicillin (Penicillin G or Penicillin V)"), EMPTY_NOTES)

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }
}
