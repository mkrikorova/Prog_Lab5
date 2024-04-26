package commands;

import collections.CollectionManager;


/**Класс команды show*/
public class ShowCommand implements CommandWithoutArguments {

    private final CollectionManager collectionManager;

    public ShowCommand (CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Исполняет команду
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(boolean isInFile) {
        collectionManager.show();
    }

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "show";
    }
}
