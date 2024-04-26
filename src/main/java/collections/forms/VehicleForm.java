package collections.forms;

import collections.*;
import exceptions.*;
import utils.*;

import java.util.*;
import java.util.regex.Pattern;

/**Класс отвечающий за создание объекта Vehicle и проверку полей на корректность*/
public class VehicleForm {
    private Vehicle newVehicle = null;


    private final Scanner scanner;

    private final static int maxCountOfAttempts = 50000;

    public VehicleForm (Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Конструирует объект
     * @param args аргументы (могут быть пустыми)
     * @param isInFile  была ли вызвана команда из файла
     * @return новый объект класса Vehicle
     */
    public Vehicle build(ArrayList<String> args, boolean isInFile) {
        String name = null;
        int x = 0;
        double y = 0;
        Coordinates coordinates = null;
        double enginePower = 0;
        int numberOfWheels = 0;
        Double fuelConsumption = null;
        String nameOfFuelType = null;
        if (isInFile) {
            if (args == null) {
                ColorOutput.printlnRed("Аргументы для команды add не указаны. Исправьте файл и попробуйте ещё.");
            } else if (args.size() < 6 || args.size() > 7) {
                ColorOutput.printlnRed("В файле неверное количество аргументов для команды add. Исправьте файл и попробуйте ещё.");
            } else {
                try {
                    name = askName(args.get(0));
                    x = askCoordinateX(args.get(1));
                    y = askCoordinateY(args.get(2));
                    coordinates = new Coordinates(x, y);
                    enginePower =  askEnginePower(args.get(3));
                    numberOfWheels =  askNumberOfWheels(args.get(4));
                    fuelConsumption = askFuelConsumption(args.get(5));
                    if (args.size() == 7) {
                        nameOfFuelType = askFuelType(args.get(6));
                    }
                    newVehicle = new Vehicle(name, coordinates, enginePower, numberOfWheels, fuelConsumption, nameOfFuelType);
                } catch (ToMuchAttemptsException t) {
                    ColorOutput.printlnRed("Ты совсем долбаёб? Блять так сложно что ли прочитать что от тебя просят. " +
                            "Нет конечно интереснее долбиться до последнего. Мало ли сработает?))\n" +
                            "Используй свой мозг по назначению, пожалуйста.");
                } catch (NumberFormatException n) {
                    ColorOutput.printlnRed("Введенные значения не соответствуют типам Java.");
                }

            }
        } else {
            try {
                name = askName("");
                x = askCoordinateX("");
                y = askCoordinateY("");
                coordinates = new Coordinates(x, y);
                enginePower = askEnginePower("");
                numberOfWheels = askNumberOfWheels("");
                fuelConsumption = askFuelConsumption("");
                nameOfFuelType = askFuelType("");
                newVehicle = new Vehicle(name, coordinates, enginePower, numberOfWheels,
                        fuelConsumption, nameOfFuelType);
            } catch (ToMuchAttemptsException t) {
                ColorOutput.printlnRed("Ты совсем долбаёб? Блять так сложно что ли прочитать что от тебя просят. " +
                        "Нет конечно интереснее долбиться до последнего. Мало ли сработает?))\n" +
                        "Используй свой мозг по назначению, пожалуйста.");
            } catch (NumberFormatException n) {
                ColorOutput.printlnRed("Введенные значения не соответствуют типам Java.");
            }

        }
        return newVehicle;

    }

    /**
     * Запрашивает имя
     * @param str строка для имени
     * @return верное имя
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public String askName(String str) throws ToMuchAttemptsException {
        String name;
        if (str.isEmpty()) {
            System.out.println("Введите имя транспорта (поле не может быть null, строка не может быть пустой): ");
            System.out.print(">> ");
            str = scanner.nextLine();
            name = checkName(str, scanner);
        } else {
            name = checkName(str, scanner);
        }
        return name;
    }

    /**
     * Запрашивает координату X
     * @param argForX строка для координаты X
     * @return коордианату подходящую под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public Integer askCoordinateX(String argForX) throws ToMuchAttemptsException {
        int x = 0;
        if (argForX.isEmpty()) {
            System.out.println("Введите координату X (типа int, значение поля должно быть больше -971):");
            System.out.print(">> ");
            argForX = scanner.nextLine();
        }
        x = checkIfInteger(argForX, scanner, "Координата X");
        while (x <= -971) {
            ColorOutput.printlnRed("Введенные данные не соответствуют требованиям. Попробуйте ещё.");
            System.out.print(">> ");
            x = checkIfInteger(scanner.nextLine(), scanner, "Координата X");
        }
        return x;
    }

    /**
     * Запрашивает координату Y
     * @param argForY строка для координаты Y
     * @return коордианату подходящую под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public Double askCoordinateY(String argForY) throws ToMuchAttemptsException {
        double y = 0;
        if (argForY.isEmpty()) {
            System.out.println("Введите координату Y (типа double):");
            System.out.print(">> ");
            argForY = scanner.nextLine();
        }
        y = checkIfDouble(argForY, scanner, "Координата Y");
        return y;
    }


    /**
     * Запрашивает мощность двигателя
     * @param arg строка для мощности двигателя
     * @return мощность двигателя подходящую под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public double askEnginePower(String arg) throws ToMuchAttemptsException {
        double enginePower;
        if (arg.isEmpty()) {
            System.out.println("Введите мощность двигателя (типа double, значение поля должно быть больше 0):");
            System.out.print(">> ");
            arg = scanner.nextLine();
        }
        enginePower = checkIfDouble(arg, scanner, "Мощность двигателя");
        while (enginePower <= 0) {
            ColorOutput.printlnRed("Введенные данные не соответствуют требованиям. Попробуйте ещё.");
            System.out.print(">> ");
            enginePower = checkIfDouble(scanner.nextLine(), scanner, "Мощность двигателя");
        }
        return enginePower;
    }

    /**
     * Запрашивает количество колес
     * @param arg строка для количества колес
     * @return количсетво колес подходящее под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public int askNumberOfWheels(String arg) throws ToMuchAttemptsException {
        int numberOfWheels;
        if (arg.isEmpty()) {
            System.out.println("Введите количество колес (типа int, значение поля должно быть больше 0):");
            System.out.print(">> ");
            arg = scanner.nextLine();
        }
        numberOfWheels = checkIfInteger(arg, scanner, "Количество колес");
        while (numberOfWheels <= 0) {
            ColorOutput.printlnRed("Введенные данные не соответствуют требованиям. Попробуйте ещё.");
            System.out.print(">> ");
            numberOfWheels = checkIfInteger(scanner.nextLine(), scanner, "Количество колес");
        }
        return numberOfWheels;
    }

    /**
     * Запрашивает потребление топлива
     * @param arg строка для потребления топлива
     * @return потребление топлива подходящее под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public Double askFuelConsumption(String arg) throws ToMuchAttemptsException {
        double fuelConsumption;
        if (arg.isEmpty()) {
            System.out.println("Введите потребление топлива (типа Double, поле не может быть null, значение поля должно быть больше 0):");
            System.out.print(">> ");
            arg = scanner.nextLine();
        }
        fuelConsumption = checkIfDouble(arg, scanner, "Потребление топлива");
        while (fuelConsumption <= 0) {
            ColorOutput.printlnRed("Введенные данные не соответствуют требованиям. Попробуйте ещё.");
            System.out.print(">> ");
            fuelConsumption = checkIfDouble(scanner.nextLine(), scanner, "Потребление топлива");
        }
        return fuelConsumption;
    }

    /**
     * Запрашивает тип топлива
     * @param arg строка для типа топлива
     * @return строку с именем типа топлива подходящего под требования
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public String askFuelType(String arg) throws ToMuchAttemptsException {
        String nameOfFuelType;
        if (arg.isEmpty()) {
            System.out.println("Введите тип топлива (выберите один из перечисленных:  ELECTRICITY, DIESEL, ALCOHOL, PLASMA): ");
            System.out.print(">> ");
            arg = scanner.nextLine();
        }
        nameOfFuelType = checkIfConstantFuelType(arg, scanner);
        if (nameOfFuelType.isEmpty()) {
            return null;
        } else {
            return nameOfFuelType;
        }
    }

    /**
     * Проверяет, является ли данная строка str числом типа int
     * @param str строка
     * @param scanner сканер для запрашивания повторного ввода
     * @param fieldName имя поля, для которого идет проверка
     * @return числовое значение для поля
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public static Integer checkIfInteger (String str, Scanner scanner, String fieldName) throws ToMuchAttemptsException {
        int n = 0;
        str = str.toLowerCase(Locale.ROOT);
        if ('i' == str.charAt(str.length() - 1) || 'l' == str.charAt(str.length() - 1)) {
            str = str.substring(0, str.length() - 1);
        }
        while (!(Pattern.matches("-\\d+|\\d+", str))) {
            if (n > maxCountOfAttempts) {
                throw new ToMuchAttemptsException();
            }
            ColorOutput.printlnRed(fieldName + " не типа int. Попробуйте еще:");
            System.out.print(">> ");
            str = scanner.nextLine();
            if ('i' == str.charAt(str.length() - 1) || 'l' == str.charAt(str.length() - 1)) {
                str = str.substring(0, str.length() - 1);
            }
            n++;
        }
        return Integer.parseInt(str);
    }

    /**
     * Проверяет, является ли данная строка str числом типа double
     * @param str строка
     * @param scanner сканер для запрашивания повторного ввода
     * @param fieldName имя поля, для которого идет проверка
     * @return числовое значение для поля
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public static Double checkIfDouble (String str, Scanner scanner, String fieldName) throws ToMuchAttemptsException {
        int n = 0;
        str = str.toLowerCase(Locale.ROOT);
        str = str.replace(",", ".");
        if ('f' == str.charAt(str.length() - 1) || 'd' == str.charAt(str.length() - 1)) {
            str = str.substring(0, str.length() - 1);
        }
        while (!(Pattern.matches("^[-+]?[0-9]*[.,]?[0-9]+(?:[eE][-+]?[0-9]+)?$", str))) {
            if (n > maxCountOfAttempts) {
                throw new ToMuchAttemptsException();
            }
            ColorOutput.printlnRed(fieldName + " в неправильном формате. Попробуйте еще:");
            System.out.print(">> ");
            str = scanner.nextLine();
            if ('f' == str.charAt(str.length() - 1) || 'd' == str.charAt(str.length() - 1)) {
                str = str.substring(0, str.length() - 1);
            }
            n++;
        }
        return Double.parseDouble(str);
    }
    /**
     * Проверяет, является ли данная строка str одной из констант FuelType
     * @param str строка
     * @param scanner сканер для запрашивания повторного ввода
     * @return строку с именем константы
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public static String checkIfConstantFuelType (String str, Scanner scanner) throws ToMuchAttemptsException {
        int n = 0;

        class IfConstant {
            final ArrayList<String> constants = new ArrayList<>();

            IfConstant () {
                constants.add("electricity");
                constants.add("diesel");
                constants.add("alcohol");
                constants.add("plasma");
                constants.add("1");
                constants.add("2");
                constants.add("3");
                constants.add("4");
            }

            public boolean checker (String str) {
                return constants.contains(str.toLowerCase());
            }
        }

        IfConstant ifConstant = new IfConstant();
        while (! (ifConstant.checker(str))) {
            if (str.isEmpty())
                return str;

            if (n > maxCountOfAttempts) {
                throw new ToMuchAttemptsException();
            }
            ColorOutput.printlnRed("Такой константы нет. Попробуйте еще. Если ни одна константа не подходит, нажмите ENTER");
            System.out.print(">> ");
            str = scanner.nextLine();
            n++;
        }
        return str;
    }

    /**
     * Проверяет, соответствует ли строка str требованиям к полю имени
     * @param str строка
     * @param scanner сканер для запрашивания повторного ввода
     * @return верное имя
     * @throws ToMuchAttemptsException исключение для превышения попыток ввода
     */
    public static String checkName(String str, Scanner scanner) throws ToMuchAttemptsException {
        int n = 0;
        while (str == null || str.isEmpty()) {
            if (n > maxCountOfAttempts) {
                throw new ToMuchAttemptsException();
            }
            ColorOutput.printlnRed("Имя в неправильном формате. Попробуйте еще:");
            System.out.print(">> ");
            str = scanner.nextLine();
            n++;
        }
        return str;
    }

}
