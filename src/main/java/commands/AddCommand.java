package commands;

import collections.*;
import collections.forms.VehicleForm;

import java.util.*;

/**Класс команды add {element}*/

public class AddCommand implements CommandWithListOfArguments {
    private final CollectionManager collectionManager;
    private final Scanner scanner;
    public AddCommand(CollectionManager collectionManager, Scanner scanner) {
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
        collectionManager.add(vehicleForm.build(args, isInFile));
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "add {element}";
    }
}