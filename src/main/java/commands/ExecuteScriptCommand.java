package commands;

import exceptions.*;
import utils.*;

import java.io.*;
import java.util.*;

/**Класс команды execute_script script*/
public class ExecuteScriptCommand implements CommandWithArguments{

    private static final Stack<String> files = new Stack<>();
    private final CommandInvoker commandInvoker;

    public ExecuteScriptCommand (CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    /**
     * Возвращает файлы, которые были запущены с помощью команды execute_script
     * @return stack файлов
     */
    public static Stack<String> getFiles() {
        return files;
    }

    /**
     * Исполняет команду
     * @param filePath путь до файла
     * @param isInFile была ли вызвана команда из файла
     */
     @Override
    public void execute(String filePath, boolean isInFile) throws ExitProgramException {
        try {

            File ioFile = new File(filePath);
            if (!ioFile.canWrite() || ioFile.isDirectory() || !ioFile.isFile()) throw new IOException();
            if (files.contains(filePath)) throw new RecursiveCallException();
            files.add(filePath);


            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            ArrayList<String> allArgs = new ArrayList<>();

            String line = reader.readLine(); //начинаем считывать файл
            while (line != null) {
                String[] wordsOfLine = line.trim().split(" ");
                String command = wordsOfLine[0].toLowerCase();
                if (wordsOfLine.length > 2) {
                    ColorOutput.printlnRed("Некорректное количество аргументов. Для справки введите help");
                }


                if (command.equals("add") || command.equals("update") ||command.equals("add_if_min")
                        || command.equals("remove_lower")) {
                    String field = reader.readLine();
                    while (!(field == null || commandInvoker.isInCommands(field))) {
                        allArgs.add(field);
                        field = reader.readLine();
                    }
                    ColorOutput.printlnCyan("\nИсполнение команды " + command + ": ");
                    if (wordsOfLine.length == 1)
                        commandInvoker.execute(command, null, allArgs);
                    else
                        commandInvoker.execute(command, wordsOfLine[1], allArgs);
                    allArgs.clear();
                    line = field;
                    continue;
                }
                ColorOutput.printlnCyan("\nИсполнение команды " + command + ": ");
                if (wordsOfLine.length == 1)
                    commandInvoker.execute(command, null, allArgs);
                else
                    commandInvoker.execute(command, wordsOfLine[1], allArgs);
                allArgs.clear();
                line = reader.readLine();

            }
            files.remove(filePath);
            reader.close();
        } catch (IOException ex) {
            System.err.println("Доступ к файлу невозможен "+ ex.getMessage());
        } catch (RecursiveCallException r) {
            ColorOutput.printlnRed("Скрипт " + filePath + " уже был вызван (Рекурсивный вызов)");
        }


     }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, \n" +
                "в котором их вводит пользователь в интерактивном режиме. " +
                "Указывать абсолютный путь до скрипта";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "execute_script script";
    }
}
