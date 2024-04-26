package commands;

import collections.*;
import utils.*;

import java.util.*;

/**Класс команды remove_head*/
public class RemoveHeadCommand implements CommandWithoutArguments{
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(boolean isInFile) {
        Vehicle minElement = collectionManager.getCollection().stream().filter(Objects::nonNull).
                min(Vehicle::compareTo).orElse(null);
        if (minElement == null) {
            ColorOutput.printlnRed("Коллекция пуста.");
        } else {
            ColorOutput.printlnPurple(minElement.toString());
            collectionManager.removeById(minElement.getId());
        }

    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести первый элемент коллекции и удалить его";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_head";
    }
}
