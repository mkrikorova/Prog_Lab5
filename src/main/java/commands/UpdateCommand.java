package commands;

import collections.*;
import collections.forms.VehicleForm;
import utils.*;

import java.util.*;

/**Класс команды update id {element}*/
public class UpdateCommand implements CommandWithListOfArguments{
    private final CollectionManager collectionManager;
    private final Scanner scanner;

    public UpdateCommand(CollectionManager collectionManager, Scanner scanner) {
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
        int indexOfElement = Integer.parseInt(args.get(args.size() - 1));//берем последний элемент массива
        args.remove(args.size() - 1);
        if (collectionManager.checkExit(indexOfElement)) {
            collectionManager.updateById(indexOfElement, vehicleForm.build(args, isInFile));
        } else {
            ColorOutput.printlnRed("Такого индекса не существует.");
        }
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Обновляет значение элемента коллекции, id которого равен заданному.";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "update id {element}";
    }
}
