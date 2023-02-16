import redis.clients.jedis.Jedis;
import java.io.*;
import java.util.*;

public class autocomplete
{

    private final Jedis jedis;

    public autocomplete() {
        this.jedis = new Jedis();
    }

    public List<String> getNames() {
        return jedis.lrange("NAMES", 0, -1);
    }

    public static void main( String[] args )
    {

        autocomplete au = new autocomplete();

        try {
            Scanner sc = new Scanner(new File("./src/names.txt"));
            while (sc.hasNextLine()) {
                String name = sc.nextLine();
                au.jedis.rpush("NAMES",name);
            }
            sc.close();

            System.out.print("Search for ('Enter' for quit): ");
            sc = new Scanner(System.in);
            String input = sc.next().toLowerCase();

            ArrayList<String> autocomplete = new ArrayList<>();

            for (String n : au.getNames())  {
                if (n.startsWith(input)) {
                    autocomplete.add(n);
                }
            }

            for (String result : autocomplete) {
                System.out.println(result);
            }
            System.out.println();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

    }
}