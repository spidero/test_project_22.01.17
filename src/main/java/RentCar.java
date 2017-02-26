import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by MyWORID on 23.02.2017.
 */
public class RentCar {
    private String resultCar;
    private String date;
    private ArrayList<String> listToActiveRents = new ArrayList<>();

    public void execute() throws Exception {
        carID();
        clientID();
        term();
        MainClass.ArrayWriteToFile(MainClass.FILENAME_ACTIVE_RENTS, listToActiveRents);
        deleteAddString(MainClass.FILENAME_AVAILABLE_CARS, resultCar);
        String toRent = date + " " + resultCar;
        MainClass.StringWriteToFile(MainClass.FILENAME_RENTED_CARS, toRent);
        System.out.println("Rejestracja wypożyczenia powiodła się");
        History.writeHistory(listToActiveRents, "");
    }

    /*замінює конкретний текст на пусте місце (видаляє текст з файлу)*/
    public static void deleteAddString(String fileToDelete, String delete) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        Path path = Paths.get(fileToDelete);
        Files.write(path, new String(Files.readAllBytes(path), charset).replace(delete, "").getBytes(charset));
    }

    /*находить у файлі цілий рядок, який починається на конкретні символи. зберігає цілий рядок у стрінг*/
    private String search(String ID, String filename) throws Exception { 
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String str = null;
        String resultStr = null;
        while ((str = file.readLine()) != null) {
            if (str.startsWith("ID" + ID))
                resultStr = "" + str;
        }
        file.close();
        System.out.println(resultStr);
        return resultStr;
    }

    private void carID() throws Exception {
        BufferedReader reader4 = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Wprowadź ID dostępnego auta (listę dostępnych aut) i zakończ klawiszem ENTER");
            MainClass.Notepad(MainClass.FILENAME_AVAILABLE_CARS);
            int IDcar = Integer.parseInt(reader4.readLine());
            String IDCAR = Integer.toString(IDcar);
            resultCar = search(IDCAR, MainClass.FILENAME_AVAILABLE_CARS);
            String time = MainClass.CurrentTime();
            listToActiveRents.add(time + " Zarejestrowano nowe wypozyczenie auta " + resultCar);
        } catch (NumberFormatException e) {
            System.out.println("Wprowadzono literę. Proszę wprowadź liczbę");
            carID();
        }
    }

    private void clientID() throws Exception {
        BufferedReader reader5 = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Wprowadź ID klienta (zobacz listę Klientów) i zakończ klawiszem ENTER");
            MainClass.Notepad(MainClass.FILENAME_ADD_CLIENTS);
            int IDclient = Integer.parseInt(reader5.readLine());
            String IDCLIENT = Integer.toString(IDclient);
            String result = search(IDCLIENT, MainClass.FILENAME_ADD_CLIENTS);
            listToActiveRents.add(". Klient - " + result);
        } catch (NumberFormatException e) {
            System.out.println("Wprowadzono literę. Proszę wprowadź liczbę");
            clientID();
        }
    }

    private void term() throws IOException {
        BufferedReader reader6 = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Wprowadź termin wypożyczenia (ilość dni) i zakończ klawiszem ENTER");
            int term = Integer.parseInt(reader6.readLine());
            Calendar cal = new GregorianCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            cal.add(Calendar.DATE, term);
            dateFormat.setTimeZone(cal.getTimeZone());
            date = dateFormat.format(cal.getTime());
            listToActiveRents.add(". Termin wypozyczenia wygasa " + date + ".\r\n");
        } catch (NumberFormatException e) {
            System.out.println("Wprowadzono literę. Proszę wprowadź liczbę");
            term();
        }
    }
}
