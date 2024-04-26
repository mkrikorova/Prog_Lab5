package utils;

import commands.*;
import exceptions.*;

import java.util.*;

/**Класс для работы с консолью*/
public class Console {
    private final CommandInvoker commandInvoker;
    private final Scanner scanner;

    public Console(CommandInvoker commandInvoker, Scanner scanner) {
        this.commandInvoker = commandInvoker;
        this.scanner = scanner;
    }

    /**
     * Метод считывающий данные из консоли
     * @throws ExitProgramException исключение для выхода из приложения
     */
    public void readFromConsole () throws ExitProgramException{
        String line;
        ArrayList<String> allArgs = new ArrayList<>();
        while (true) {
            System.out.println("\nВведите название команды:");
            System.out.print(">> ");
            line = scanner.nextLine();
            line = line.replaceAll("\\s{2,}", " ");
            if (line.equals("exit")) {
                scanner.close();
                throw new ExitProgramException();
            }
            String[] wordsOfLine = line.trim().split(" ");
            String command = wordsOfLine[0].toLowerCase(Locale.ROOT);
            if (wordsOfLine.length > 2) {
                ColorOutput.printlnRed("Некорректное количество аргументов. Для справки введите help.");
            } else if (wordsOfLine.length == 1) {
                commandInvoker.execute(command, null, allArgs);
            }
            else {
                commandInvoker.execute(command, wordsOfLine[1], allArgs);
            }
            allArgs.clear();
        }
    }
}
