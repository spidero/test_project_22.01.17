import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by MyWORID on 23.02.2017.
 */
public class AddClient {
    public AddClient() throws IOException {
        System.out.println("Wprowadź nowego klienta i zakończ klawiszem ENTER");
    }

    public void execute() throws Exception {
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> listAddClient = new ArrayList<>();
        while (true) {
            String name = reader3.readLine() + "\r\n";
            if (name.equals("\r\n")) break;
            String ID = MainClass.createID(MainClass.FILENAME_CLIENT_ID);
            listAddClient.add(ID + name);
            System.out.println("Nowego Klienta dodano. Dodaj jeszcze lub zakończ klawiszem ENTER");
        }
        MainClass.ArrayWriteToFile(MainClass.FILENAME_ADD_CLIENTS, listAddClient);
        String ClientaDodano = " Clienta(-ow) dodano:";
        History.writeHistory(listAddClient, ClientaDodano);
        System.out.println("Klienta (-ów) dodano.\r\n" + "Wprowadź nową komendę");
    }
}
