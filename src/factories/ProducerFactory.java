package factories;

import input.Producer;
import simulation.CurrentStateProducer;

public final class ProducerFactory {
    private static ProducerFactory factory = null;

    /**
     * returneaza instanta curenta pentru Singleton
     * @return
     */
    public static ProducerFactory getInstance() {
        if (factory == null) {
            factory = new ProducerFactory();
        }

        return factory;
    }

    private ProducerFactory() {
    }

    /**
     * curata continutul Singleton-ului pentru urmatoarea rulare
     */
    public static void reset() {
        factory = null;
    }

    /**
     * reeaza un producator
     * @param producer
     * @return
     */
    public CurrentStateProducer createProducer(final Producer producer) {
        return new CurrentStateProducer(producer);
    }
}
