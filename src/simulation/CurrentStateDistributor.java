package simulation;

import factories.StrategyFactory;
import input.Distributor;
import output.Contract;
import strategies.Strategy;
import strategies.EnergyChoiceStrategyType;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * distributorul in timpul unei runde din simularea curenta
 */
public class CurrentStateDistributor implements Observer {
    private int id;
    private int energyNeededKW;
    private int contractCost;
    private int budget;
    private EnergyChoiceStrategyType producerStrategy;
    private int contractLength;
    private int infrastructureCost;
    private int productionCost;
    private boolean isBankrupt;
    private List<Contract> contracts;
    // all producers sorted
    private List<CurrentStateProducer> producers;
    private boolean hasToUpdateProducers;

    public CurrentStateDistributor(final Distributor distributor) {
        this.id = distributor.getId();
        this.energyNeededKW = distributor.getEnergyNeededKW();
        this.contractCost = 0;
        this.budget = distributor.getInitialBudget();
        this.producerStrategy = distributor.getProducerStrategy();
        this.contractLength = distributor.getContractLength();
        this.infrastructureCost = distributor.getInitialInfrastructureCost();
        this.productionCost = distributor.getInitialProductionCost();
        this.contracts = new ArrayList<>();
        this.producers = new ArrayList<>();
        this.hasToUpdateProducers = false;
    }

    public final int getId() {
        return id;
    }

    public final void setId(final int id) {
        this.id = id;
    }

    public final int getContractLength() {
        return contractLength;
    }

    public final void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public final int getInfrastructureCost() {
        return infrastructureCost;
    }

    public final void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public final int getProductionCost() {
        return productionCost;
    }

    public final void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public final boolean isBankrupt() {
        return isBankrupt;
    }

    public final void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public final int getBudget() {
        return budget;
    }

    public final void setBudget(final int budget) {
        this.budget = budget;
    }

    public final List<Contract> getContracts() {
        return contracts;
    }

    public final void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }

//    public final List<Producer> getProducers() { return producers; }
//
//    public final void setProducers(List<Producer> producers) { this.producers = producers; }


    public final List<CurrentStateProducer> getProducers() {
        return producers;
    }

    public final void setProducers(List<CurrentStateProducer> producers) {
        this.producers = producers;
    }

    public final int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public final void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public final EnergyChoiceStrategyType getProducerStrategy() { return producerStrategy; }

    public final void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public final int getContractCost() {
        return contractCost;
    }

    public final void setContractCost(int contractCost) {
        this.contractCost = contractCost;
    }

    public final boolean isHasToUpdateProducers() {
        return hasToUpdateProducers;
    }

    public final void setHasToUpdateProducers(boolean hasToUpdateProducers) {
        this.hasToUpdateProducers = hasToUpdateProducers;
    }

    /**
     * @return suma pe care consumatorul restant o datoreaza distributorului
     */
    public final int computeProfit() {
        final double profitConsant = 0.2;
        return (int) Math.round(Math.floor(profitConsant * this.productionCost));
    }

    /**
     * @return pretul unui contract pentru un distribuitor fara clienti
     */
    public final int computeContractNoClients() {
        return this.infrastructureCost + this.productionCost + this.computeProfit();
    }

    /**
     * @return pretul unui contract pentru un distribuitor acre deja are clienti
     */
    public final int computeContract() {
        return (int) Math.round(Math.floor(this.infrastructureCost / this.contracts.size())
                + this.productionCost + this.computeProfit());
    }

    /**
     * @return costurile totale pentru un distribuitor in luna respectiva
     */
    public final int computeTotalCosts() {
        return this.infrastructureCost + this.productionCost * this.contracts.size();
    }

    /**
     * distribuitorul plateste costurile totale lunare
     */
    public final void payCosts() {
        this.budget -= this.computeTotalCosts();
    }

    /**
     * distribuitorul incaseaza bani de la fiecare consumator
     * @param price pretul
     */
    public final void acceptMoney(final int price) {
        this.budget += price;
    }

    /**
     * se adauga un contract dat ca parametru la contractele actuale ale unui distribuitor
     * @param contract contractul
     */
    public final void addContractToDistributor(final Contract contract) {
        this.contracts.add(contract);
    }

    /**
     * observatorii sunt notificati ca producatorul lor si a schimbat conditiile
     * @param o obiectul observabil
     * @param arg parametrul cu care trebuie notificati observerii
     */
    @Override
    public void update(Observable o, Object arg) {
        this.setHasToUpdateProducers((Boolean) arg);
    }

    /**
     * se aleg producatorii pentru un distribuitor pe baza strategiei
     */
    public void chooseProducersByStrategy() {
        Strategy strategy = StrategyFactory.getInstance().
                createStrategy(this.getProducerStrategy());

        this.setProducers(strategy.getProducers(Simulation.getInstance().getCurrentProducers()));
    }

    /**
     * costul de productie se schimba
     */
    public void changeProductionCost() {
        double cost = 0;
        int energy = 0;

        if (this.getProducers() != null) {
            for (CurrentStateProducer producer : this.getProducers()) {
                if (energy  < this.getEnergyNeededKW()) {
                    energy += producer.getEnergyPerDistributor();
                    cost += producer.getEnergyPerDistributor() * producer.getPriceKW();

                    producer.addObserver(this);

                    if (producer.getAllDistributors().contains(this)) {
                        continue;
                    }

                    if (producer.getAllDistributors().size() < producer.getMaxDistributors()) {
                        List<CurrentStateDistributor> list =
                                new ArrayList<>(producer.getAllDistributors());
                        list.add(this);
                        producer.setAllDistributors(list);

                        List<CurrentStateProducer> prodList =
                                new ArrayList<>(this.getProducers());
                        prodList.add(producer);
                        this.setProducers(prodList);
                    }
                }
            }
        }

        final int number = 10;
        this.setProductionCost((int) Math.round(Math.floor(cost / number)));
    }
}
