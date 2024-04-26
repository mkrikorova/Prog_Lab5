package commands;

import collections.*;
import collections.forms.VehicleForm;
import utils.*;

import java.util.*;

/**Класс команды remove_lower {element}*/
public class RemoveLowerCommand implements CommandWithListOfArguments{
    private final CollectionManager collectionManager;
    private final Scanner scanner;

    public RemoveLowerCommand(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }

    /**
     * Исполняет команду
     * @param args аргументы команды
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(ArrayList<String> args, boolean isInFile) {
        VehicleForm vehicleForm = new VehicleForm(scanner);
        Vehicle compareVehicle = vehicleForm.build(args, isInFile);
        Collection <Vehicle> collection = collectionManager.getCollection();
        if (collection == null) {
            ColorOutput.printlnRed("Коллекция пуста.");
        } else {
            Collection<Vehicle> toRemove = collection.stream().filter(Objects::nonNull)
                    .filter(vehicle -> vehicle.compareTo(compareVehicle) < 0).toList();
            for (Vehicle v: toRemove) {
                collectionManager.removeById(v.getId());
            }
        }

    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "удаляет из коллекции все элементы, меньшие, чем заданный";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_lower {element}";
    }}
