package commands;

import collections.*;
import utils.*;

import java.util.*;

/**Класс команды filter_starts_with_name name*/
public class FilterStartsWithNameCommand implements CommandWithArguments {
    private final CollectionManager collectionManager;

    public FilterStartsWithNameCommand (CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     * @param args аргументы команды
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(String args, boolean isInFile) {
        Collection <Vehicle> collection = collectionManager.getCollection();
        if (collection == null) {
            ColorOutput.printlnRed("Коллекция пуста.");
        } else {
            List<Vehicle> found = collection.stream()
                    .filter(vehicle -> vehicle.getName().startsWith(args))
                    .toList();
            if (found.isEmpty()) {
                ColorOutput.printlnPurple("Таких элементов нет. :(");
            } else {
                for (Vehicle v : found) {
                    ColorOutput.printlnPurple(v.toString());
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
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "filter_starts_with_name name";
    }
}
