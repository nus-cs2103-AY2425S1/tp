package seedu.address.model.person.comparator;

public class ComparatorManager {

    public PersonComparator getComparator(SortField sortField, SortOrder sortOrder) {
        PersonComparator result;
        switch (sortField) {
        case NAME:
            result = new PersonComparatorByName(sortOrder);
            break;
        case GITHUB:
            result = new PersonComparatorByGithub(sortOrder);
            break;
        case TELEGRAM:
            result = new PersonComparatorByTelegram(sortOrder);
            break;
        default:
            result = null;
            assert false : "No comparator given for the specified sortField";
        }
        return result;
    }
}
