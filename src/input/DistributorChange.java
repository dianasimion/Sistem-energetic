package input;

public class DistributorChange {
    private final int id;
    private final int infrastructureCost;

    public DistributorChange() {
        id = -1;
        infrastructureCost = 0;
    }

    public final int getId() {
        return id;
    }

    public final int getInfrastructureCost() {
        return infrastructureCost;
    }
}
