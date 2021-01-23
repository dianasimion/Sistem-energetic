package input;

/**
 * schimbarile de cost
 */
public class CostsChange {
    private final int id;
    private final int infrastructureCost;
    private final int productionCost;

    public CostsChange() {
        id = -1;
        infrastructureCost = 0;
        productionCost = 0;
    }

    public final int getId() {
        return id;
    }

    public final int getInfrastructureCost() {
        return infrastructureCost;
    }

    public final int getProductionCost() {
        return productionCost;
    }
}
