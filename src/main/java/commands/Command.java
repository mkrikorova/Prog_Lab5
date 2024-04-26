package commands;

/**Интерфейс, который реалируют все команды*/
public interface Command {
    /**
     * Возвращает описание команды
     * @return описание команды
     */
    default String getDescription() {
        return "Описание работы данной команды еще не реализовано";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    default String getName() {
        return "Имя команды ";
    }
}
