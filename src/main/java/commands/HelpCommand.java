package commands;

import utils.*;

/**Класс команды help*/
public class HelpCommand implements CommandWithoutArguments{
    private final CommandInvoker commandInvoker;

    public HelpCommand (CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    /**
     * Исполняет команду
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(boolean isInFile) {
        for (Command c : commandInvoker.getCommands()) {
            ColorOutput.printPurple(c.getName());
            ColorOutput.println(":  " + c.getDescription());
        }
        ColorOutput.printlnCyan("Замечание: аргументы в фигурных скобках вводятся с новой строки.");
    }


    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "help";
    }
}
