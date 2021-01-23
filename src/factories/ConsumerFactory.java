package factories;

import input.Consumer;
import simulation.CurrentStateConsumer;

/**
 * creaza consumatori in formatul complex CurrentStateConsumer
 */
public final class ConsumerFactory {
    private static ConsumerFactory factory = null;

    /**
     * returneaza instanta curenta pentru Singleton
     * @return
     */
    public static ConsumerFactory getInstance() {
        if (factory == null) {
            factory = new ConsumerFactory();
        }

        return factory;
    }

    /**
     * curata continutul Singleton-ului pentru urmatoarea rulare
     */
    public static void reset() {
        factory = null;
    }

    private ConsumerFactory() {
    }

    /**
     * creeaza un consumator
     * @param consumer
     * @return
     */
    public CurrentStateConsumer createConsumer(final Consumer consumer) {
        return new CurrentStateConsumer(consumer);
    }
}
