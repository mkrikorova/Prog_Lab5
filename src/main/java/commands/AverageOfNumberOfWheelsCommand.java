package commands;

import collections.*;
import utils.*;

import java.util.*;

/**Класс команды average_of_number_of_wheels*/
public class AverageOfNumberOfWheelsCommand implements CommandWithoutArguments {
    private final CollectionManager collectionManager;

    public AverageOfNumberOfWheelsCommand (CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    /**
     * Исполняет команду
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(boolean isInFile) {
        Collection <Vehicle>  collection = collectionManager.getCollection();
        if (collection == null) {
            ColorOutput.printlnRed("Коллекция пуста.");
        } else {
            double averageNumberOfWheels = collection.stream()
                    .mapToDouble(Vehicle::getNumberOfWheels)
                    .average()
                    .orElse(0.0);
            ColorOutput.printlnPurple("Среднее значение numberOfWheels: " + averageNumberOfWheels);
        }
    }
    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести среднее значение поля numberOfWheels для всех элементов коллекции";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "average_of_number_of_wheels";
    }
}
