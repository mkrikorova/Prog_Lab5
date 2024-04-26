package commands;

import exceptions.*;
import utils.*;

/**Класс команды save*/
public class SaveCommand implements CommandWithoutArguments{
    private final FileManager fileManager;

    public SaveCommand( FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Исполняет команду
     * @param isInFile была ли вызвана команда из файла
     */
    public void execute(boolean isInFile) throws ExitProgramException {
        fileManager.saveObjects();
        ColorOutput.printlnGreen("Ваша коллекция сохранена.");
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "save";
    }
}
