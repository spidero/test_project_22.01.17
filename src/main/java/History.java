import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by MyWORID on 23.02.2017.
 */
public class History {
    public static void writeHistory(ArrayList<String> listAdd, String action) throws IOException {
        String time = MainClass.CurrentTime();
        String CarsAdded = time + action;
        MainClass.StringWriteToFile(MainClass.FILENAME_HISTORY, CarsAdded);
        MainClass.ArrayWriteToFile(MainClass.FILENAME_HISTORY, listAdd);
    }
}
