package commands;

import collections.CollectionManager;

/**Класс команды info*/
public class InfoCommand implements CommandWithoutArguments{

    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(boolean isInFile) {
        this.collectionManager.info();
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции " +
                "(тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "info";
    }
}
