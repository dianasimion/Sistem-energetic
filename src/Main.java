import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import factories.ConsumerFactory;
import factories.DistributorFactory;
import factories.ProducerFactory;
import input.Input;
import output.Output;
import simulation.FinalState;
import simulation.Simulation;

import java.io.File;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */

    public static void main(final String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper().
                enable(SerializationFeature.INDENT_OUTPUT);

        Input input = objectMapper.readValue(new File(args[0]), Input.class);

        Output output = new Output();

        ConsumerFactory consumerFactory = ConsumerFactory.getInstance();
        DistributorFactory distributorFactory = DistributorFactory.getInstance();
        ProducerFactory producerFactory = ProducerFactory.getInstance();

        Simulation simulation = Simulation.getInstance();
        FinalState finalState = FinalState.getInstance();

        simulation.initialState(input);
        simulation.runFirstTurn();

        for (int i = 0; i < input.getNumberOfTurns(); i++) {
            simulation.runTurn(input, i);
        }

        simulation.leftInSimulation();

        output.createOutput();

        objectMapper.writeValue(new File(args[1]), output);

        ConsumerFactory.reset();
        DistributorFactory.reset();
        FinalState.reset();
        Simulation.reset();

//        "D:\facultate\an2\poo_proiect\proiect etapa 2\teme\teme\proiect-etapa2-energy-system\checker\resources\in\basic_12.json" "D:\\facultate\\an2\\poo_proiect\\proiect etapa 2\\teme\\teme\\proiect-etapa2-energy-system\checker\resources\outFiles\out.json"
    }
}
