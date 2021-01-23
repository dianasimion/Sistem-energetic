package input;

import java.util.ArrayList;
import java.util.List;

/**
 * informatiile initiale in formatul dat de input
 */
public class Input {
    private final int numberOfTurns;
    private final InitialData initialData;
    private final List<MonthlyUpdate> monthlyUpdates;

    public Input() {
        numberOfTurns = 0;
        initialData = null;
        monthlyUpdates = new ArrayList<>();
    }

    public final int getNumberOfTurns() {
        return numberOfTurns;
    }

    public final InitialData getInitialData() {
        return initialData;
    }

    public final List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }
}
