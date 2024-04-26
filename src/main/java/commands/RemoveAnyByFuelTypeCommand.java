package commands;

import collections.*;
import utils.*;
import java.util.*;

/**Класс команды remove_any_by_fuel_type fuelType*/
public class RemoveAnyByFuelTypeCommand implements CommandWithArguments{
    private final CollectionManager collectionManager;

    public RemoveAnyByFuelTypeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     * @param args аргументы команды
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(String args, boolean isInFile) {
        FuelType fuelTypeToRemove = FuelType.getFuelType(args.toLowerCase());
        if (fuelTypeToRemove == null) {
            ColorOutput.printlnPurple("Элемент с заданным FuelType не найден");
        } else {
            Collection <Vehicle> collection = collectionManager.getCollection();
            if (collection == null) {
                ColorOutput.printlnRed("Коллекция пуста.");
            } else {
                Optional<Vehicle> vehicleFound = collection.stream().filter(Objects::nonNull).
                        filter(vehicle -> vehicle.getFuelType() == fuelTypeToRemove).findFirst();
                if (vehicleFound.isPresent()) {
                    Vehicle vehicleToRemove =  vehicleFound.get();
                    collectionManager.removeById(vehicleToRemove.getId());
                } else  {
                    ColorOutput.printlnPurple("Элемент с заданным FuelType не найден");
                }
            }

        }
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "удалить из коллекции один элемент, значение поля fuelType которого эквивалентно заданному.\n" +
                "Возможные варианты: ELECTRICITY, DIESEL, ALCOHOL, PLASMA";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_any_by_fuel_type fuelType";
    }
}
