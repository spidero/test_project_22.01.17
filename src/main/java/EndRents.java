import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MyWORID on 23.02.2017.
 */
public class EndRents {
    public void execute() throws Exception {
        //This class monitors active rents (does they are actual or not). So it controls REALLY available
        // cars to rent at the moment. It starts with every new start of the program.
        findDateInString(MainClass.FILENAME_RENTED_CARS);
    }

    private String openFileAsString(String filename) throws IOException { //перероблюю файл у текст Стрінг
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filename));
        BufferedReader buff = new BufferedReader(isr);
        StringBuilder strBuff = new StringBuilder();
        int c;
        while ((c = buff.read()) != -1) {
            strBuff.append((char) c);
        }
        return strBuff.toString();
    }

    private void findDateInString(String filename) throws IOException, ParseException {
        ArrayList<String> datesString = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        String text = openFileAsString(filename); //create String from the file's text
        String patt = "\\d{1,2}\\.\\d{1,2}\\.\\d{4}\\ \\d{1,2}\\:\\d{1,2}";
        Matcher matcher = Pattern.compile(patt).matcher(text);  // search for the date in the String
        while (matcher.find()) {
            datesString.add(matcher.group());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (String str : datesString) {
            dates.add(dateFormat.parse(str));
            RentCar.deleteAddString(filename, str + " "); //deletes dates from the file
        }

        ArrayList<String> cars = new ArrayList<>();
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(filename))); // add cars from the file to ArrayList
        String s = null;
        while ((s = file.readLine()) != null) {
            if (s.startsWith("ID"))
                cars.add(s);
        }
        file.close();

        for (String k : cars) {
            RentCar.deleteAddString(filename, k); //deletes cars from the file
        }
        LinkedHashMap<Date, String> Map = new LinkedHashMap<>();
        for (int i = 0; i < dates.size() && i < cars.size(); i++) { //add from Arrays into Map
            Map.put(dates.get(i), cars.get(i));
        }

        for (Iterator<java.util.Map.Entry<Date, String>> it = Map.entrySet().iterator(); it.hasNext(); ) { //compare dates in the map, and delete it in case of the past dates
            Map.Entry<Date, String> pair = it.next();
            Date dateFromMap = pair.getKey();
            Date currentTime = new Date();
            if (dateFromMap.before(currentTime)) {
                String car = pair.getValue();
                MainClass.StringWriteToFile(MainClass.FILENAME_AVAILABLE_CARS, car);
                it.remove();
            }
        }

        for (Map.Entry<Date, String> pair : Map.entrySet()) { //add Map to String to write it in file RentedCars
            Date date = pair.getKey();
            String dateFromMap = dateFormat.format(date);
            String carFromMap = pair.getValue();
            MainClass.StringWriteToFile(MainClass.FILENAME_RENTED_CARS, dateFromMap + " " + carFromMap);
        }
    }
}
