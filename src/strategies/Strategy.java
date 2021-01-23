package strategies;

import simulation.CurrentStateProducer;

import java.util.List;

public interface Strategy {
    /**
     * returneaza lista de producatori corespunzatoare strategiei
     * @param producers
     * @return
     */
    List<CurrentStateProducer> getProducers(List<CurrentStateProducer> producers);
}
