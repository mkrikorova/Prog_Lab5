package commands;

import collections.*;
import utils.*;

import java.util.Scanner;

/**Класс команды remove_by_id id*/
public class RemoveById implements CommandWithArguments{
    CollectionManager collectionManager;
    Scanner scanner;

    public RemoveById(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }
    /**
     * Исполняет команду
     * @param args аргументы команды
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(String args, boolean isInFile) {
        int index = Integer.parseInt(args);
        if (collectionManager.checkExit(index)) {
            collectionManager.removeById(index);
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
        return "Удаляет элемент из коллекции по его id";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_by_id id";
    }
}
