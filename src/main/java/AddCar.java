import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by MyWORID on 23.02.2017.
 */
public class AddCar {
    public AddCar() {
        System.out.println("Wprowadź nazwę auta i zakończ klawiszem ENTER");
    }

    public void execute() throws Exception {
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> listAddCar = new ArrayList<>();
        while (true) {
            String name = reader2.readLine() + "\r\n";
            if (name.equals("\r\n")) break;
            String ID = MainClass.createID(MainClass.FILENAME_CAR_ID);
            listAddCar.add(ID + name);
            System.out.println("Nowe auto dodano. Dodaj jeszcze lub zakończ klawiszem ENTER");
        }
        MainClass.ArrayWriteToFile(MainClass.FILENAME_ADD_CARS, listAddCar);
        MainClass.ArrayWriteToFile(MainClass.FILENAME_AVAILABLE_CARS, listAddCar);
        String AutoDodano = " Auto(-a) dodano:";
        History.writeHistory(listAddCar, AutoDodano);
        System.out.println("Auto (-a) dodano.\r\n" + "Wprowadź nową komendę");
    }
}
