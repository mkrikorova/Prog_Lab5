package commands;

import collections.*;
import exceptions.*;

/**Класс команды clear*/
public class ClearCommand implements CommandWithoutArguments{
    private final CollectionManager collectionManager;

    public ClearCommand (CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "clear";
    }

    /**
     * Исполняет команду
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(boolean isInFile) throws ExitProgramException {
        collectionManager.clear();
        Vehicle.updateUniqueId(collectionManager.getCollection());
    }
}
