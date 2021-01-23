package input;

/**
 * consumatorul in formatul primit de la input
 */
public class Consumer {
    private final int id;
    private final int initialBudget;
    private final int monthlyIncome;

    public Consumer() {
        id = -1;
        initialBudget = 0;
        monthlyIncome = 0;
    }

    public final int getId() {
        return id;
    }

    public final int getInitialBudget() {
        return initialBudget;
    }

    public final int getMonthlyIncome() {
        return monthlyIncome;
    }
}
