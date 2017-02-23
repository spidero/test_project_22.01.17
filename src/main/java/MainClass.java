import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by MyWORID on 23.02.2017.
 */
public class MainClass {
    public static String FILENAME_ADD_CLIENTS = "C://TestProgram//Clients.txt";
    public static String FILENAME_ADD_CARS = "C://TestProgram//AddCars.txt";
    public static String FILENAME_AVAILABLE_CARS = "C://TestProgram//AvailableCars.txt";
    public static String FILENAME_HISTORY = "C://TestProgram//History.txt";
    public static String FILENAME_RENTED_CARS = "C://TestProgram//RentedCars.txt";
    public static String FILENAME_ACTIVE_RENTS = "C://TestProgram//ActiveRents.txt";
    public static String FILENAME_CLIENT_ID = "C://TestProgram//ClientID.txt";
    public static String FILENAME_CAR_ID = "C://TestProgram//CarID.txt";

    private static String INSTRUKCJA = "TestProgram.Instrukcja obsługi\n\n" +
            "dodać auto - wprowadź \"add car\"\n" +
            "wyświetlić listę wszystkich aut - wprowadź \"cars\"\n" +
            "wyświetlić listę dostępnych aut - wprowadź \"available cars\"\n" +
            "dodać nowego Klienta - wprowadź \"add client\"\n" +
            "wyświetlić listę Klientów - wprowadź \"clients\"\n" +
            "zarejestrować wypożyczenie auta - wprowadź \"rent car\"\n" +
            "wyświetlić listę aktualnie wypozyczonych aut - wprowadź \"active rents\"\n" +
            "wyświetlić historię - wprowadź \"history\"\n" +
            "zamknąć program - wprowadź \"exit\"\n";

    public static void main(String[] args) throws Exception {
        String pathToFile = "C://TestProgram";
        File file = new File(pathToFile);
        file.mkdir();

        if ((new File(FILENAME_RENTED_CARS)).exists()) { //при кожному запуску перевіряємо чи минув термін оренди
            new EndRents().execute();
        }

        System.out.println(INSTRUKCJA);

        BufferedReader readerTest = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String start = readerTest.readLine();
            if (start.equals("add car")) new AddCar().execute();
            if (start.equals("cars")) Notepad(FILENAME_ADD_CARS);
            if (start.equals("available cars")) Notepad(FILENAME_AVAILABLE_CARS);
            if (start.equals("active rents")) Notepad(FILENAME_ACTIVE_RENTS);
            if (start.equals("add client")) new AddClient().execute();
            if (start.equals("rent car")) new RentCar().execute();
            if (start.equals("clients")) Notepad(FILENAME_ADD_CLIENTS);
            if (start.equals("history")) Notepad(FILENAME_HISTORY);
            if (start.equals("exit")) break;
            if (!start.equals("add client") && !start.equals("add car") && !start.equals("cars")
                    && !start.equals("available cars") && !start.equals("active rents") && !start.equals("clients")
                    && !start.equals("history") && !start.equals("rent car") && !start.equals("add client"))
                System.out.println("Nie znaleziono takiej komendy. Skorzystaj z instrukcji i spróbuj ponownie\n");
        }
    }

    public static void Notepad(String filename) {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("notepad" + " " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String CurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
        return sdf.format(new Date());
    }

    public static void StringWriteToFile(String filenameWriteTo, String toWrite) throws IOException { // записуємо стрінг в кінець файла
        RandomAccessFile fileAddString = new RandomAccessFile(filenameWriteTo, "rw");
        long LengthFile = 0, position = 0;
        LengthFile = fileAddString.length();
        fileAddString.seek(0);
        String PS = "";
        while (position < LengthFile) {
            PS += fileAddString.readLine();
            position = fileAddString.getFilePointer();
        }
        fileAddString.writeBytes(toWrite + "\r\n");
        fileAddString.close();
    }

    public static void ArrayWriteToFile(String filenameWriteTo, ArrayList<String> listToWrite) throws IOException { //записуємо аррай ліст в кінець файла
        RandomAccessFile fileWrite = new RandomAccessFile(filenameWriteTo, "rw");
        long LengthFileWrite, positionAdd = 0;
        LengthFileWrite = fileWrite.length();
        fileWrite.seek(0);
        String PS = "";
        while (positionAdd < LengthFileWrite) {
            PS += fileWrite.readLine();
            positionAdd = fileWrite.getFilePointer();
        }

        for (String s : listToWrite) {
            byte[] bytes = s.getBytes();
            for (byte byteWrite : bytes) {
                fileWrite.writeByte(byteWrite);
            }
        }
        fileWrite.close();
    }

    public static String createID(String filename) throws IOException { // нумерація. читаємо файл і беремо звідти цифру. записуємо на один більше. і по кругу.
        int newID = 1;
        if (!(new File(filename)).exists()) {
            Writer wr = new FileWriter(filename);
            wr.write("0");
            wr.close();
        }

        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextInt()) {
            newID = scanner.nextInt();
        }
        Writer wr = new FileWriter(filename);
        wr.write(Integer.toString(newID + 1));
        wr.close();
        return "ID" + Integer.toString(newID) + " ";
    }
}
