package seedu.address.model.person;

/**
 * Class to store statistics for each job code
 */
public class JobCodeStatistics {
    private int n;
    private int tp;
    private int tc;
    private int bp;
    private int bc;
    private int a;
    private int r;
    private int totalApplicants;

    /**
     * Initiallises class with 0 applicants for each tag, and 0 total applicants
     */
    public JobCodeStatistics() {
        n = 0;
        tp = 0;
        tc = 0;
        bp = 0;
        bc = 0;
        a = 0;
        r = 0;
        totalApplicants = 0;
    }

    /**
     * Increments the number of applicants in each interview stage.
     * @param tagValue
     */
    public void incrementTag(String tagValue) {
        totalApplicants++;

        switch (tagValue) {
        case "New":
            n++;
            break;
        case "Technical Interview in Progress":
            tp++;
            break;
        case "Technical Interview Confirmed":
            tc++;
            break;
        case "Behavioral Interview in Progress":
            bp++;
            break;
        case "Behavioral Interview Confirmed":
            bc++;
            break;
        case "Accepted":
            a++;
            break;
        case "Rejected":
            r++;
            break;
        default:
            throw new IllegalArgumentException("Invalid Tag Value: " + tagValue);
        }
    }
    public int getTotalApplicants() {
        return totalApplicants;
    }

    public int getN() {
        return n;
    }

    public int getTP() {
        return tp;
    }

    public int getTC() {
        return tc;
    }

    public int getBP() {
        return bp;
    }

    public int getBC() {
        return bc;
    }

    public int getA() {
        return a;
    }

    public int getR() {
        return r;
    }
}
