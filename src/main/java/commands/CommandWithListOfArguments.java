package commands;

import exceptions.ExitProgramException;

import java.util.ArrayList;

/**
 * Класс для команд, где вводится список аргументов.
 * Также допустимо, чтобы сама команда вводилась в виде имя_команды аргумент1_команды {остальные_аргументы}
 */

public interface CommandWithListOfArguments extends Command{
   /**
    * Исполняет команду
    * @param args аргументы команды
    * @param isInFile была ли вызвана команда из файла
    */
   void execute(ArrayList<String> args, boolean isInFile) throws ExitProgramException;
}
