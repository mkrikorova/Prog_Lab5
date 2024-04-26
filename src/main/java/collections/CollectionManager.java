package collections;

import utils.ColorOutput;

import java.time.*;
import java.time.format.*;
import java.util.*;

/**Класс реализующий работу с коллекцией*/
public class CollectionManager {
    ArrayDeque <Vehicle> collection;
    LocalDateTime collectionInitialization;

    public CollectionManager() {
        this.collection = new ArrayDeque<>();
        collectionInitialization = LocalDateTime.now();
    }

    /**
     * Возвращает коллекцию
     * @return ArrayDeque
     */
    public ArrayDeque<Vehicle> getCollection() {
        return this.collection;
    }

    /**
     * Выводит информацию о коллекции
     */
    public void info() {
        ColorOutput.printPurple("Коллекция: ");
        ColorOutput.println(collection.getClass().getSimpleName());

        ColorOutput.printPurple("Тип элементов коллекции: ");
        ColorOutput.println(Vehicle.class.getName());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ColorOutput.printPurple("Время инициализации коллекции: ");
        ColorOutput.println(collectionInitialization.format(timeFormatter));
        ColorOutput.printPurple("Колличество элементов: ");
        ColorOutput.println(String.valueOf(collection.size()));
    }

    /**
     * Выводит элементы коллекции
     */
    public void show() {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            for (Vehicle i: collection) {
                System.out.println(i.toString());
            }
        }
    }

    /**
     * Возвращает элемент коллекции у которого id соответствует данному индексу
     * @param index индекс, по которому ищется элемент
     * @return найденный элемент
     */
    public Vehicle getById (int index) {
        for (Vehicle element : collection) {
            if (element.getId() == index)
                return element;
        }
        return null;
    }

    /**
     * Добавляет новый элемент в коллекцию
     * @param vehicle элемент, который нужно добавить
     */
    public void add(Vehicle vehicle) {
        if (vehicle == null) {
            ColorOutput.printlnRed("Объект типа Vehicle равен null");
        } else {
            if (!vehicle.validate()) {
                ColorOutput.printlnRed("Объект типа Vehicle не соответствует требованиям. Проверьте корректность введеных значений и попробуйте еще раз.");
            } else {
                collection.add(vehicle);
                ColorOutput.printlnGreen("Вы добавили элемент.");
            }
        }
    }

    /**
     * Проверяет, существует ли элемент, id которого равно данному
     * @param index индекс, по которому ищется элемент
     * @return true если существует, иначе false
     */
    public boolean checkExit(int index) {
        return collection.stream().anyMatch((x) -> x.getId() == index);
    }

    /**
     * Обновляет все поля элемента коллекции, если его id равен данному
     * @param index индекс, по которому ищется элемент
     * @param newVehicle элемент который нужно добавить
     */
    public void updateById(int index, Vehicle newVehicle){
        Vehicle lastVehicle = this.getById(index);
        collection.remove(lastVehicle);
        newVehicle.setId(index);
        this.add(newVehicle);
    }

    /**
     * Удаляет элемент, id которого равен данному
     * @param index индекс, по которому ищется элемент
     */
    public void removeById(int index) {
        collection.remove(getById(index));
        ColorOutput.printlnGreen("Вы удалили элемент.");
    }

    /**
     * Очищает коллекцию
     */
    public void clear() {
        collection.clear();
        ColorOutput.printlnGreen("Ваша коллекция очищена.");
    }

}
