package commands;

import collections.*;
import collections.forms.VehicleForm;
import utils.*;

import java.util.*;

/**Класс команды add_if_min {element}*/
public class AddIfMinCommand implements CommandWithListOfArguments{

    private final CollectionManager collectionManager;
    private final Scanner scanner;
    public AddIfMinCommand(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }

    /**
     * Проверяет, является ли данный объект меньше минимального в коллекции
     * @param newVehicle проверяемый объект
     * @return true если является, false иначе
     */
    public boolean checkIfMin(Vehicle newVehicle) {
        Collection <Vehicle> collection = collectionManager.getCollection();
        if (collection == null) {
            return true;
        }
        return newVehicle.compareTo(collection.stream().filter(Objects::nonNull).
                min(Vehicle::compareTo).orElse(null)) < 0;

        //1) collectionManager.getCollection(): Вызывается метод getCollection() объекта collectionManager, который
        // возвращает коллекцию объектов типа Vehicle.

        //2) .stream(): Метод stream() преобразует коллекцию в поток объектов типа Vehicle,позволяя выполнять
        // последующие операции над элементами этого потока.

        //3) .filter(Objects::nonNull): Метод filter() принимает предикат Objects::nonNull, который фильтрует поток,
        // оставляя только элементы, не являющиеся null. То есть, он удаляет все null элементы из потока.

        //4) .min(Vehicle::compareTo): Метод min() принимает компаратор Vehicle::compareTo, который используется для
        // нахождения минимального элемента в потоке. Компаратор Vehicle::compareTo сравнивает два объекта Vehicle на
        // основе их естественного порядка.

        //5) .orElse(null): Если поток пустой (т.е., коллекция не содержит элементов), метод min() возвращает
        // Optional.empty(). Метод orElse(null) указывает, что если Optional пустой, то возвращаемое значение должно
        // быть null.

        //6) newVehicle.compareTo(...): Вызывается метод compareTo() объекта newVehicle и передается в качестве
        // аргумента результат выражения collectionManager.getCollection().stream().filter(Objects::nonNull).
        // min(Vehicle::compareTo).orElse(null). Метод compareTo() сравнивает два объекта типа Vehicle по их
        // естественному порядку.

        //7) >= 1: Результат сравнения будет true, если newVehicle больше (имеет более высокий порядок) минимального
        // элемента в коллекции, или false, если он меньше или равен минимальному элементу.
    }

    /**
     * Исполняет команду
     * @param args аргументы команды
     * @param isInFile была ли вызвана команда из файла
     */
    @Override
    public void execute(ArrayList<String> args, boolean isInFile) {
        VehicleForm vehicleForm = new VehicleForm(scanner);
        Vehicle newVehicle = vehicleForm.build(args, isInFile);
        if (checkIfMin(newVehicle)) {
            collectionManager.add(newVehicle);
        } else {
            ColorOutput.printlnCyan("Объект оказался больше минимального.");
        }
    }
    /**
     * Возвращает описание команды
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    /**
     * Возвращает имя команды
     * @return имя команды
     */
    @Override
    public String getName() {
        return "add_if_min {element}";
    }
}
