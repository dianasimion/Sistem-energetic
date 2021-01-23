package strategies;

import simulation.CurrentStateProducer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuantityStrategy implements Strategy {
    /**
     * returneaza lista de producatori corespunzatoare strategiei
     * @param producers lista de producatori disponibili
     * @return lista de producatori in ordinea descisa de strategie
     */
    @Override
    public List<CurrentStateProducer> getProducers(List<CurrentStateProducer> producers) {
        List<CurrentStateProducer> tmpList = new ArrayList<>(producers);

        tmpList.sort(Comparator.comparing(CurrentStateProducer::getEnergyPerDistributor,
                Comparator.reverseOrder()).
                thenComparing(CurrentStateProducer::getId));

        List<CurrentStateProducer> list = new ArrayList<>(tmpList);

        // exclud producatorii care deja au atins limita maxima de distribuitori
        list.removeIf(producer -> producer.getAllDistributors().size()
                >= producer.getMaxDistributors());

        return list;
    }
}
