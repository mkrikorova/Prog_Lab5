package utils;


import collections.*;
import commands.*;
import exceptions.*;
import java.util.*;

/**Класс приложения*/
public class Application {

    /**
     * Метод для начала работы приложения
     */
    public void start() {
        ColorOutput.printlnCyan("Данное консольное приложение реализует управление коллекцией объектов типа " +
                "Vehicle.\nДля справки о командах введите help.");
        ColorOutput.println("");
        Scanner scanner = new Scanner(System.in);
        FuelType.setFuelTypes();
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(collectionManager);
        CommandInvoker commandInvoker = new CommandInvoker(collectionManager, fileManager, scanner);
        Console console = new Console(commandInvoker, scanner);

        try {
            fileManager.findFile();
            fileManager.createObjects();
            console.readFromConsole();

        } catch (ExitProgramException | NoSuchElementException e) {
            ColorOutput.printlnCyan("Спасибо что использовали меня. До скорой встречи! (\u2060≧\u2060▽\u2060≦\u2060)");
        }
        scanner.close();
    }
}
