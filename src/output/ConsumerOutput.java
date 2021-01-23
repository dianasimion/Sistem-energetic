package output;

/**
 * consumatorul in formatul de output
 */
public class ConsumerOutput {
    private final int id;
    private final boolean isBankrupt;
    private final int budget;

    public final int getId() {
        return id;
    }

    public final boolean getIsBankrupt() {
        return isBankrupt;
    }

    public final int getBudget() {
        return budget;
    }

    /**
     * constructor pentru consumator in formatul de output
     * @param id
     * @param isBankrupt
     * @param budget
     */
    public ConsumerOutput(final int id, final boolean isBankrupt, final int budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
    }
}
