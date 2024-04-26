package commands;

import exceptions.*;
/**
 * Класс для команд типа "имя_команды   аргумент_команды"
 */
public interface CommandWithArguments extends Command{
    /**
     * Исполняет команду
     * @param args аргументы команды
     * @param isInFile была ли вызвана команда из файла
     */
    void execute(String args, boolean isInFile) throws ExitProgramException;
}
