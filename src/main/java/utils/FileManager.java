package utils;

import collections.*;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.*;
import exceptions.ExitProgramException;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**Класс для работы с файлами*/
public class FileManager {
    private final CollectionManager collectionManager;
    private String text;
    private final XStream xStream = new XStream();
    public FileManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.xStream.alias("Vehicle", Vehicle.class);
        this.xStream.alias("arrayDeque", CollectionManager.class);
        this.xStream.addPermission(AnyTypePermission.ANY);
        this.xStream.addImplicitCollection(CollectionManager.class, "collection");
    }

    /**
     * Метод ищет файл по пути указанному в переменной окружения "PATH"
     * @throws ExitProgramException исключение для выхода из приложения
     */
    public void findFile() throws ExitProgramException {
        String filePath = System.getenv("file_path");
        if (filePath == null || filePath.isEmpty()) {
            ColorOutput.printlnRed("Путь должен быть в переменной окружения в переменной 'file_path'");
            throw new ExitProgramException();
        } else
            ColorOutput.printlnCyan("Путь получен успешно.");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder allLines = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                allLines.append(line);
            }
            reader.close();

            if (allLines.isEmpty()) {
                ColorOutput.printlnRed("Файл пустой.");
                this.text = "</arrayDeque>";
            }
            this.text = allLines.toString();

        } catch (FileNotFoundException f) {
            ColorOutput.printlnRed("Файл не найден.");
        } catch (IOException ioe){
            ColorOutput.printlnRed("Ошибка ввода/вывода" + ioe);
        }
    }

    /**
     * Метод создает объекты на основе данных полученных из файла с помощью метода FileManager.readFile
     * @throws ExitProgramException исключение для выхода из приложения
     */
    public void createObjects() throws ExitProgramException {
        try{
            XStream xstream = new XStream();
            xstream.alias("Vehicle", Vehicle.class);
            xstream.alias("arrayDeque", CollectionManager.class);
            xstream.addPermission(AnyTypePermission.ANY);
            xstream.addImplicitCollection(CollectionManager.class, "collection");
            CollectionManager collectionManagerWithObjects = (CollectionManager) xstream.fromXML(this.text);
            for(Vehicle v : collectionManagerWithObjects.getCollection()){
                if (this.collectionManager.checkExit(v.getId())){
                    ColorOutput.printlnRed("В файле повторяются айди.");
                    throw new ExitProgramException();
                }
                this.collectionManager.add(v);
            }

        } catch (StreamException e) {
            ColorOutput.printlnRed("Объекты в файле не валидны.");
        } catch (NullPointerException n) {
            ColorOutput.printlnGreen("Файл пустой.");
        } catch (ConversionException c) {
            ColorOutput.printlnRed("XML файл заполнен неверно. Все данные добавлены не будут.");
        }

        Vehicle.updateUniqueId(collectionManager.getCollection());
    }

    /**
     * Метод сохраняет коллекцию в файл, указанный в переменной окружения "PATH"
     * @throws ExitProgramException исключение для выхода из приложения
     */
    public void saveObjects() throws ExitProgramException {
        try{
            String file_path = System.getenv("file_path");
            if (file_path == null || file_path.isEmpty()) {
                ColorOutput.printlnRed("Путь должен быть в переменных окружения в переменной 'file_path'.");
                throw new ExitProgramException();
            }

            else
                ColorOutput.printlnCyan("Путь получен успешно.");

            OutputStream outputStream = new FileOutputStream(file_path);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

            writer.write(this.xStream.toXML(collectionManager));

            writer.close();
        } catch (FileNotFoundException e) {
            ColorOutput.printlnRed("Файл не существует.");
        }catch (IOException e) {
            ColorOutput.printlnRed("Ошибка ввода вывода.");
        }
    }
}
