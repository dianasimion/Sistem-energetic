package output;

import java.util.List;

public class MonthlyStat {
    private final int month;
    private final List<Integer> distributorsIds;

    public final int getMonth() {
        return month;
    }

    public final List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public MonthlyStat(int month, List<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }
}
