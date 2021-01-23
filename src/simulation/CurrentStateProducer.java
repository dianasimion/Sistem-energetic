package simulation;

import entities.EnergyType;
import input.Producer;
import output.MonthlyStat;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class CurrentStateProducer extends Observable {
    private int id;
    private EnergyType energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;
    private int numberOfDistributors;
    private List<MonthlyStat> monthlyStats;
    // toti distribuitorii dintr - o luna
    private List<CurrentStateDistributor> allDistributors;
    private boolean updated;

    public CurrentStateProducer(final Producer producer) {
        this.id = producer.getId();
        this.energyType = producer.getEnergyType();
        this.maxDistributors = producer.getMaxDistributors();
        this.priceKW = producer.getPriceKW();
        this.energyPerDistributor = producer.getEnergyPerDistributor();
        this.numberOfDistributors = 0;
        this.monthlyStats = new ArrayList<>();
        this.allDistributors = new ArrayList<>();
        this.updated = false;
    }

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final EnergyType getEnergyType() {
        return energyType;
    }

    public final void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }

    public final int getMaxDistributors() {
        return maxDistributors;
    }

    public final void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public final double getPriceKW() {
        return priceKW;
    }

    public final void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public final int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public final void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public final int getNumberOfDistributors() {
        return numberOfDistributors;
    }

    public final void setNumberOfDistributors(int numberOfDistributors) {
        this.numberOfDistributors = numberOfDistributors;
    }

    public final List<MonthlyStat> getMonthlyStats() {
        return monthlyStats;
    }

    public final void setMonthlyStats(List<MonthlyStat> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public final List<CurrentStateDistributor> getAllDistributors() {
        return allDistributors;
    }

    public final void setAllDistributors(List<CurrentStateDistributor> allDistributors) {
        this.allDistributors = allDistributors;
    }

    public final boolean isUpdated() {
        return updated;
    }

    public final void setUpdated(boolean updated) {
        this.updated = updated;
    }

    /**
     * spune daca un distribuitor are un producator care a facut modificari
     * @return
     */
    public final boolean producerHasRenewable() {
        return this.getEnergyType().isRenewable();
    }

    /**
     * producatorul notifica distributorii ca s-a intamplat ceva
     */
    public void notifyDistributors() {
        setChanged();
        notifyObservers(true);
//        System.out.println("of");

    }

    /**
     * producatorul scoate observatorii, dupa tura curenta
     */
    public void notifyDistributorsToRemove() {
        setChanged();
        notifyObservers(false);
//        System.out.println("off");
    }


}
