package commands;

import exceptions.*;

/**
 * Класс для команд без каких-либо аргументов
 */


public interface CommandWithoutArguments extends Command{
    /**
     * Исполняет команду
     * @param isInFile была ли вызвана команда из файла
     */
    void execute(boolean isInFile) throws ExitProgramException;
}
