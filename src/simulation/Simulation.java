package simulation;

import factories.ConsumerFactory;
import factories.DistributorFactory;
import factories.ProducerFactory;
import input.Consumer;
import input.Distributor;
import input.DistributorChange;
import input.InitialData;
import input.Input;
import input.MonthlyUpdate;
import input.Producer;
import input.ProducerChange;
import output.Contract;
import output.MonthlyStat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * simularea curenta presupune rularea mai multor runde (luni), inclusiev runda 0,
 * si tine evidenta consumatorilor si distribuitorilor care inca nu au dat faliment
 */
public class Simulation {
    private List<CurrentStateConsumer> currentConsumers = new ArrayList<>();
    private List<CurrentStateDistributor> currentDistributors = new ArrayList<>();
    private List<CurrentStateProducer> currentProducers = new ArrayList<>();

    private List<CurrentStateProducer> updatedProducers = new ArrayList<>();
    private List<CurrentStateDistributor> distributorsWithUpdatedProducers = new ArrayList<>();

    private LinkedHashMap<Integer, CurrentStateConsumer> consumersMap = new LinkedHashMap<>();
    private LinkedHashMap<Integer, CurrentStateDistributor> distributorsMap = new LinkedHashMap<>();
    private LinkedHashMap<Integer, CurrentStateProducer> producersMap = new LinkedHashMap<>();

    private int bestDistributorId;

    private static Simulation simulation = null;

    /**
     * returneaza instanta curenta pentru Singleton
     * @return
     */
    public static Simulation getInstance() {
        if (simulation == null) {
            simulation = new Simulation();
        }

        return simulation;
    }

    private Simulation() {
    }

    /**
     * curata continutul Singleton-ului pentru urmatoarea rulare
     */
    public static void reset() {
        simulation = null;
    }

    public Simulation(final List<CurrentStateConsumer> currentConsumers,
                      final List<CurrentStateDistributor> currentDistributors,
                      final List<CurrentStateProducer> currentProducers) {
        this.currentConsumers = currentConsumers;
        this.currentDistributors = currentDistributors;
        this.currentProducers = currentProducers;
        this.bestDistributorId = -1;
    }

    public final List<CurrentStateConsumer> getCurrentConsumers() {
        return currentConsumers;
    }

    public final void setCurrentConsumers(final List<CurrentStateConsumer>
                                                  currentConsumers) {
        this.currentConsumers = currentConsumers;
    }

    public final List<CurrentStateDistributor> getCurrentDistributors() {
        return currentDistributors;
    }

    public final void setCurrentDistributors(final List<CurrentStateDistributor>
                                                     currentDistributors) {
        this.currentDistributors = currentDistributors;
    }

    public final List<CurrentStateProducer> getCurrentProducers() {
        return currentProducers;
    }

    public final void setCurrentProducers(List<CurrentStateProducer> currentProducers) {
        this.currentProducers = currentProducers;
    }

    public final List<CurrentStateProducer> getUpdatedProducers() {
        return updatedProducers;
    }

    public final void setUpdatedProducers(List<CurrentStateProducer> updatedProducers) {
        this.updatedProducers = updatedProducers;
    }

    public final List<CurrentStateDistributor> getDistributorsWithUpdatedProducers() {
        return distributorsWithUpdatedProducers;
    }

    public final void setDistributorsWithUpdatedProducers
            (List<CurrentStateDistributor> distributorsWithUpdatedProducers) {
        this.distributorsWithUpdatedProducers = distributorsWithUpdatedProducers;
    }

    public final LinkedHashMap<Integer, CurrentStateConsumer> getConsumersMap() {
        return consumersMap;
    }

    public final void setConsumersMap(final LinkedHashMap<Integer,
            CurrentStateConsumer> consumersMap) {
        this.consumersMap = consumersMap;
    }

    public final LinkedHashMap<Integer, CurrentStateDistributor> getDistributorsMap() {
        return distributorsMap;
    }

    public final void setDistributorsMap(final LinkedHashMap<Integer,
            CurrentStateDistributor> distributorsMap) {
        this.distributorsMap = distributorsMap;
    }

    public final LinkedHashMap<Integer, CurrentStateProducer> getProducersMap() {
        return producersMap;
    }

    public final void setProducersMap(LinkedHashMap<Integer, CurrentStateProducer> producersMap) {
        this.producersMap = producersMap;
    }

    public final int getBestDistributorId() {
        return bestDistributorId;
    }

    public final void setBestDistributorId(final int bestDistributorId) {
        this.bestDistributorId = bestDistributorId;
    }


    /**
     * se initializeaza baza de date a simularii curente cu informatiile initiale
     * venite de la input
     * @param input
     */
    public void initialState(final Input input) {
        InitialData initialData = input.getInitialData();

        for (Consumer consumer : initialData.getConsumers()) {
            CurrentStateConsumer currentStateConsumer =
                    ConsumerFactory.getInstance().createConsumer(consumer);
            currentConsumers.add(currentStateConsumer);
            consumersMap.put(consumer.getId(), currentStateConsumer);
        }

        for (Distributor distributor : initialData.getDistributors()) {
            CurrentStateDistributor currentStateDistributor =
                    DistributorFactory.getInstance().createDistributor(distributor);
            currentDistributors.add(currentStateDistributor);
            distributorsMap.put(distributor.getId(), currentStateDistributor);
        }

        for (Producer producer : initialData.getProducers()) {
            CurrentStateProducer currentStateProducer =
                    ProducerFactory.getInstance().createProducer(producer);
            currentProducers.add(currentStateProducer);
            producersMap.put(producer.getId(), currentStateProducer);
        }
    }

    /**
     * returneaza cel mai bun contract pe acre consumatorii care nu sunt anjajati intr-un
     * contract il pot alege in runda curenta
     *
     * acesta contine pretul si durata acestuia, iar id-ul consumatorului este -1,
     * urmand sa fie personalizat pentru fiecare consumator care va primi contractul
     * @return
     */
    public final Contract bestContract() {
        int bestPrice = Integer.MAX_VALUE;
        int contractLength = 0;

        for (CurrentStateDistributor currentStateDistributor
                : Simulation.getInstance().getCurrentDistributors()) {
            int price;
            if (currentStateDistributor.getContracts().size() == 0) {
                price = currentStateDistributor.computeContractNoClients();
            } else {
                price = currentStateDistributor.computeContract();
            }

            // adaug distribuitorului pretul curent al contractului
            currentStateDistributor.setContractCost(price);

            if (price < bestPrice) {
                bestPrice = price;
                contractLength = currentStateDistributor.getContractLength();
                Simulation.getInstance().setBestDistributorId(currentStateDistributor.getId());
                //currentStateDistributor.setContractCost(price);
            }
        }

        return new Contract(-1, bestPrice, contractLength);
    }

    /**
     * se executa actiunile lunare ale unui consumator:
     *  - incaseaza salariul
     *  - alege un nou contract daca este cazul
     *  - se actualizeaza statusul de restanta sau falit
     *  - plateste contractul actual daca poate
     *
     * @param best = cel mai bun contract pe care un consumator fara contact il poate alege
     */
    public final void consumersStuff(final Contract best) {
        // retin id-ul distribuitorului care ofera cel mai bun contract
        int bestId = Simulation.getInstance().getBestDistributorId();

        for (CurrentStateConsumer consumer : getCurrentConsumers()) {
            // creez cel mai bun contract dava va avea consumatorul nevoie de el
            Contract contract = new Contract(best);

            // se primeste salariul
            consumer.getPaid();

            // contractul s-a terminat
            if (consumer.getCurrentDistributorId() != -1
                    && consumer.getContract().getRemainedContractMonths() == 0) {
                // consumatorul ramane fara distribuitor
                consumer.setCurrentDistributorId(-1);
            }

            // consumatorul primeste contract daca nu are
            if (consumer.getCurrentDistributorId() == -1) {

                consumer.setCurrentDistributorId(bestId);

                contract.setConsumerId(consumer.getId());

                consumer.setContract(contract);

                // contractul este adaugat la distribuitorul corespunzator
                distributorsMap.get(bestId).addContractToDistributor(contract);
            }

            // se retine contractul curent al consumatorului
            Contract contractActual = consumer.getContract();

            if (!consumer.isRestant()) {
                int nextBudget = consumer.getBudget() - contractActual.getPrice();

                if (nextBudget < 0) {
                    // consumatorul nu poate plati si devine restant
                    consumer.setRestant(true);

                    consumer.setRestantDistributorId(consumer.getCurrentDistributorId());
                } else {
                    // consumatorul poate plati
                    consumer.payContract(consumer.getCurrentDistributorId());
                }
            } else {
                // consumatorul este deja restant
                int nextBudget = consumer.getBudget() - consumer.computeRestantAndCurrentContract();

                if (nextBudget < 0) {
                    // consumatorul nu poate plati si da faliment
                    consumer.setBankrupt(true); //todo sa l dau afara
                    // consumatorul urmeaza sa fie eliminat din simulare
                    FinalState.getInstance().getFinalConsumersMap().put(consumer.getId(), consumer);
                } else {
                    // consumatprul isi achita restanta si contractul curent
                    consumer.payRestantAndCurrentContract();
                    consumer.setRestant(false);
                    consumer.setRestantDistributorId(-1);
                }
            }
        }
    }

    /**
     * distributorii:
     *  - isi platesc costurile
     *  - tin evidenta lunilor trecute, scazand o luna din contracte la fiecare luna
     */
    public final void distributorsStuff() {
        for (CurrentStateDistributor distributor : getCurrentDistributors()) {
            distributor.payCosts();

            for (Contract contract : distributor.getContracts()) {
                contract.setRemainedContractMonths(contract.getRemainedContractMonths() - 1);
            }
        }
    }

    /**
     * distributotii care dau faliment sunt eliminati
     */
    public final void removeDistributors() {
        List<CurrentStateDistributor> list = new ArrayList<>();

        for (CurrentStateDistributor distributor : getCurrentDistributors()) {
            if (distributor.getBudget() < 0) {
                distributor.setBankrupt(true);

                FinalState.getInstance().getFinalDistributorsMap().put(distributor.getId(),
                                                                        distributor);
                list.add(distributor);
            }
        }

        for (CurrentStateDistributor distributor : list) {

            for (CurrentStateProducer producer : distributor.getProducers()) {
                producer.getAllDistributors().remove(distributor);
                producer.deleteObserver(distributor);
            }

            getCurrentDistributors().remove(distributor);

            if (distributorsWithUpdatedProducers.contains(distributor)) {
                distributorsWithUpdatedProducers.remove(distributor);
            }
        }
    }

    /**
     * se elimina consumatorii faliti
     */
    public final void removeConsumers() {
        List<CurrentStateConsumer> list = new ArrayList<>();
        for (CurrentStateConsumer consumer : getCurrentConsumers()) {
            if (consumer.isBankrupt()) {
                list.add(consumer);
            }
        }

        // contractele consumatorilor faliti sunt eliminate
        for (CurrentStateConsumer consumer : list) {
            for (CurrentStateDistributor distributor : getCurrentDistributors()) {
                Iterator<Contract> itr = distributor.getContracts().iterator();
                while (itr.hasNext()) {
                    Contract nextContract = itr.next();
                    if (nextContract.getConsumerId() == consumer.getId()) {
                        itr.remove();
                    }
                }
            }

            // consumatorii falimentati sunt eliminati
            getCurrentConsumers().remove(consumer);
        }
    }

    /**
     * se elimina contractele incheiate de la toti distribuitorii
     */
    public final void removeEndedContracts() {
        for (CurrentStateDistributor distributor : getCurrentDistributors()) {
            Iterator<Contract> itr = distributor.getContracts().iterator();
            while (itr.hasNext()) {
                Contract nextContract = itr.next();

                if (nextContract.getRemainedContractMonths() == 0) {
                    itr.remove();
                }
            }
        }
    }

    /**
     * se elimina toti consumatorii si distrubuitorii la finalul simularii
     */
    public final void leftInSimulation() {
        for (CurrentStateConsumer consumer : getCurrentConsumers()) {
            FinalState.getInstance().getFinalConsumersMap().put(consumer.getId(), consumer);
        }

        for (CurrentStateDistributor distributor : getCurrentDistributors()) {
            FinalState.getInstance().getFinalDistributorsMap().put(distributor.getId(),
                                                                    distributor);
        }

        for (CurrentStateProducer producer : getCurrentProducers()) {
            FinalState.getInstance().getFinalProducersMap().put(producer.getId(),
                    producer);
        }
    }

    /**
     * se executa actiunile specifice primei runde
     */
    public final void runFirstTurn() {
        currentDistributors.forEach(CurrentStateDistributor::chooseProducersByStrategy);

        // toti isi aleg listele de producatori
        modifyProductionCost(currentDistributors);
        //currentDistributors.forEach(CurrentStateDistributor::changeProductionCost);

        // start etapa 1
        Contract contract = bestContract();

        consumersStuff(contract);

        distributorsStuff();

        removeConsumers();

        removeDistributors();
        // end etapa 1

    }

    /**
     * se executa actiunile specifice unei runde
     * @param input     informatiile primite
     * @param i         luna curenta
     */
    public final void runTurn(final Input input, final int i) {
        //addProducers(input);
        // start etapa 1
        updatedProducers = new ArrayList<>();
        distributorsWithUpdatedProducers = new ArrayList<>();

        makeMonthlyUpdates(input, i);

        //for (CurrentStateDistributor distributor : )
        //currentDistributors.forEach(CurrentStateDistributor::changeProductionCost);
        // foarte IMPORTANT SA NU EXISTE ACEASTA LINIE
        //modifyProductionCost(currentDistributors);

//etsapa1
        Contract contract = bestContract();

        removeEndedContracts();

        consumersStuff(contract);

        distributorsStuff();

        removeConsumers();

        removeDistributors();
        //end etapa 1

        // etapa 2 :
        //TODO NOTIFICARE OBSERVERI PENTRU PRODUCATORII UPDATATI
        for (CurrentStateProducer producer : updatedProducers) {
            producer.notifyDistributors();
        }

        for (CurrentStateProducer producer : updatedProducers) {
            for (CurrentStateDistributor distributor : producer.getAllDistributors()) {
                distributorsWithUpdatedProducers.add(distributor);
            }
        }

        // todo astia isi aleg iar stategia daca e cazul
//        producerChoiceByStrategy(distributorsWithUpdatedProducers);
//        modifyProductionCost(distributorsWithUpdatedProducers);

        for (CurrentStateDistributor distributor : distributorsWithUpdatedProducers) {

            for (CurrentStateProducer producer : distributor.getProducers()) {
                producer.getAllDistributors().remove(distributor);

                producer.deleteObserver(distributor);
            }

            //distributor.setHasToUpdateProducers(false);
            //producerChoiceByStrategy(distributor);
            distributor.chooseProducersByStrategy();
//
//            List<CurrentStateDistributor> list = new ArrayList<>();
//            list.add(distributor);
//            modifyProductionCost(list);
            //distributor.changeProductionCost();
        }

        modifyProductionCost(distributorsWithUpdatedProducers);
        //distributorsWithUpdatedProducers.forEach(CurrentStateDistributor::changeProductionCost);


        for (CurrentStateDistributor distributor : distributorsWithUpdatedProducers) {
            distributor.setHasToUpdateProducers(false);
        }

        for (CurrentStateProducer producer : updatedProducers) {
            producer.setUpdated(false);
        }

        createMonthlyStat(i + 1);
    }

    /**
     * se fac  update-urile lunare:
     *  - se adauga noii consumatori
     *  - se modifica diverse costuri pentru distribuitori
     *
     * @param input     informatiile primite
     * @param i         luna curenta
     */
    public final void makeMonthlyUpdates(final Input input, final int i) {
        MonthlyUpdate monthlyUpdate = input.getMonthlyUpdates().get(i);
        List<Consumer> newConsumers = monthlyUpdate.getNewConsumers();
        List<DistributorChange> distributorChanges = monthlyUpdate.getDistributorChanges();
        List<ProducerChange> producerChanges = monthlyUpdate.getProducerChanges();

        // actualizez schimbarile pentru consumatori
        for (Consumer consumer : newConsumers) {
            CurrentStateConsumer currentStateConsumer =
                    ConsumerFactory.getInstance().createConsumer(consumer);
            currentConsumers.add(currentStateConsumer);
            consumersMap.put(consumer.getId(), currentStateConsumer);
        }

        // actualizez schimbarile pentru distributori (costul infrastructurii)
        for (DistributorChange distributorChange : distributorChanges) {
            int id = distributorChange.getId();

            distributorsMap.get(id).
                    setInfrastructureCost(distributorChange.getInfrastructureCost());
        }

        // actualizez schimbarile pentru producatori si ii adaug in lista cu producatori actualizati
        for (ProducerChange producerChange : producerChanges) {
            CurrentStateProducer updatedProducer = producersMap.get(producerChange.getId());

            updatedProducer.setEnergyPerDistributor(producerChange.getEnergyPerDistributor());

            updatedProducer.setUpdated(true);

            updatedProducers.add(updatedProducer);
        }
    }

    /**
     * modific costurile de productie ale distribuitorilor in functie de strategia aleasa
     * @param distributorsToUpdate
     */
    void modifyProductionCost(List<CurrentStateDistributor> distributorsToUpdate) {
        for (CurrentStateDistributor distributor : distributorsToUpdate) {
            double cost = 0;
            long productionCost = 0;
            int energy = 0;

            if (distributor.getProducers() != null) {
                for (CurrentStateProducer producer : distributor.getProducers()) {
                    if (energy  < distributor.getEnergyNeededKW()) {
                        energy += producer.getEnergyPerDistributor();
                        cost += producer.getEnergyPerDistributor() * producer.getPriceKW();

                        producer.addObserver(distributor);
                        // oare
                        if (producer.getAllDistributors().contains(distributor)) {
                            continue;
                        }

                        List<CurrentStateDistributor> list =
                                new ArrayList<>(producer.getAllDistributors());
                        list.add(distributor);
                        producer.setAllDistributors(list);

                        //distributor.getProducers().add(producer);
                        List<CurrentStateProducer> prodList =
                                new ArrayList<>(distributor.getProducers());
                        prodList.add(producer);
                        distributor.setProducers(prodList);

                        //producer.setNumberOfDistributors(producer.getNumberOfDistributors() + 1);
                    }
                }
            }

            final int number = 10;
            productionCost = Math.round(Math.floor(cost / number));
            distributor.setProductionCost((int) productionCost);
        }
    }

    /**
     * fiecare producator afiseaza ce distribuitori a avut intr-o luna
     * @param index
     */
    void createMonthlyStat(int index) {
        for (CurrentStateProducer producer : currentProducers) {
            List<Integer> distributorsIds = new ArrayList<>();
            for (CurrentStateDistributor distributor : producer.getAllDistributors()) {
                distributorsIds.add(distributor.getId());
            }

            MonthlyStat monthlyStat = new MonthlyStat(index, distributorsIds);
            producer.getMonthlyStats().add(monthlyStat);
        }
    }
}
