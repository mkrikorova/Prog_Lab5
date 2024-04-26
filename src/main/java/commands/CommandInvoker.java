package commands;

import collections.*;
import exceptions.*;
import utils.*;

import java.util.*;

/**Командный менеджер*/
public class CommandInvoker {

    private final HashMap<String, Command> commands;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final Scanner scanner;

    public CommandInvoker (CollectionManager collectionManager, FileManager fileManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.scanner = scanner;
        commands = new HashMap<>();
        this.setCommands();
    }

    /**
     * Создает объекты всех используемых команд и записывает их в список
     */
    public void setCommands () {
        commands.put("help", new HelpCommand(this));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager, scanner));
        commands.put("update", new UpdateCommand(collectionManager, scanner));
        commands.put("remove_by_id", new RemoveById(collectionManager, scanner));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("save", new SaveCommand(fileManager));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("remove_head", new RemoveHeadCommand(collectionManager));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager, scanner));
        commands.put("remove_lower", new RemoveLowerCommand(collectionManager, scanner));
        commands.put("remove_any_by_fuel_type", new RemoveAnyByFuelTypeCommand(collectionManager));
        commands.put("average_of_number_of_wheels", new AverageOfNumberOfWheelsCommand(collectionManager));
        commands.put("filter_starts_with_name", new FilterStartsWithNameCommand(collectionManager));
    }

    /**
     * Возвращает список команд
     * @return коллекцию команд
     */
    public Collection<Command> getCommands () {
        return commands.values();
    }

    /**
     * Проверяет, является ли командой данная строка
     * @param commandName имя команды
     * @return true если является, иначе false
     */
    public boolean isInCommands (String commandName) {
        if (commandName == null || commandName.isEmpty()) return false;
        return commands.containsKey(commandName.toLowerCase(Locale.ROOT));
    }

    /**
     * Исполняет команду
     * @param commandName имя команды
     * @param argInLine аргумент идущий в одной строке с именем команды
     * @param args аргументы идущие в следующих строках
     * @throws ExitProgramException исключение для выхода из приложения
     */
    public void execute (String commandName, String argInLine, ArrayList<String> args) throws ExitProgramException {
        boolean isInFile = !(ExecuteScriptCommand.getFiles().isEmpty());
        if (isInFile) {
            if (this.isInCommands(commandName)) {
                if (argInLine == null || argInLine.isEmpty()) {
                    if (args.isEmpty()) {
                        CommandWithoutArguments command = (CommandWithoutArguments) commands.get(commandName.toLowerCase(Locale.ROOT));
                        command.execute(isInFile);
                    } else {
                        CommandWithListOfArguments command = (CommandWithListOfArguments) commands.get(commandName.toLowerCase(Locale.ROOT));
                        command.execute(args, isInFile);
                    }
                } else {
                    if (args.isEmpty()) {
                        CommandWithArguments command = (CommandWithArguments) commands.get(commandName.toLowerCase(Locale.ROOT));
                        command.execute(argInLine.trim(), isInFile);
                    } else { //это для update id {element}
                        args.add(argInLine.trim());
                        CommandWithListOfArguments command = (CommandWithListOfArguments)  commands.get(commandName.toLowerCase(Locale.ROOT));
                        command.execute(args, isInFile);
                    }
                }

            } else {
                ColorOutput.printlnRed("Команда " + commandName + " не распознана. Для справки введите команду help.");
            }
        } else {
            if (this.isInCommands(commandName)) {
                if (argInLine == null || argInLine.isEmpty()) {
                    if (commandName.equals("add") ||commandName.equals("add_if_min")
                            || commandName.equals("remove_lower")) {
                        try {
                            CommandWithListOfArguments command = (CommandWithListOfArguments)  commands.get(commandName.toLowerCase(Locale.ROOT));
                            command.execute(args, isInFile);
                        } catch (ClassCastException c) {
                            ColorOutput.printlnRed("Аргументы команды введены некорректно.");
                        }

                    } else {
                        try {
                            CommandWithoutArguments command = (CommandWithoutArguments) commands.get(commandName.toLowerCase(Locale.ROOT));
                            command.execute(isInFile);
                        } catch (ClassCastException c){
                            ColorOutput.printlnRed("Аргументы команды введены некорректно.");
                        }

                    }
                } else {
                    if (commandName.equals("update")) {
                        args.add(argInLine);
                        try {
                            CommandWithListOfArguments command = (CommandWithListOfArguments)  commands.get(commandName.toLowerCase(Locale.ROOT));
                            command.execute(args, isInFile);
                        } catch (ClassCastException c) {
                            ColorOutput.printlnRed("Аргументы команды введены некорректно.");
                        }

                    } else { //есть аргумент в одну строку, но нет списка аргументов
                        try {
                            CommandWithArguments command = (CommandWithArguments) commands.get(commandName.toLowerCase(Locale.ROOT));
                            command.execute(argInLine, isInFile);
                        } catch (ClassCastException c) {
                            ColorOutput.printlnRed("Аргументы команды введены некорректно.");
                        }

                    }
                }
            } else {
                ColorOutput.printlnRed("Команда " + commandName + " не распознана. Для справки введите команду help.");
            }
        }
    }
}
