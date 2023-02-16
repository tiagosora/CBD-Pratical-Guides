import redis.clients.jedis.Jedis;
import java.io.*;
import java.util.*;
import java.util.Map.*;

public class namespop {

    private final Jedis jedis;

    public namespop() {
        this.jedis = new Jedis("localhost");
    }

    private static Map<String, String> ordenarHashmap(Map<String, String> mapa)
    {
        List<Entry<String,String>> list = new LinkedList<Entry<String,String>>(mapa.entrySet());
        Collections.sort(list, new Comparator<Entry<String, String>>()
        {
            public int compare(Entry<String, String> str1, Entry<String, String> str2) {
                Integer num1 = Integer.parseInt(str1.getValue());
                Integer num2 = Integer.parseInt(str2.getValue());
                return num2.compareTo(num1);
            }
        });

        Map<String, String> ordenado = new LinkedHashMap<String, String>();
        for (Entry<String, String> entry : list)
        {
            ordenado.put(entry.getKey(), entry.getValue());
        }
        return ordenado;
    }

    public Map<String, String> getNames() {
        return jedis.hgetAll("NAME_POPULARITY");
    }

    public static void main( String[] args )
    {

        namespop np = new namespop();

        try {
            HashMap<String,String> mapa = new HashMap<>();
            Scanner sc = new Scanner(new File("./nomes-pt-2021.csv"));
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(";");
                String name = line[0];
                String counter = line[1];
                mapa.put(name,counter);
            }

            np.jedis.hmset("NAME_POPULARITY", mapa);
            sc.close();

            System.out.print("Search for ('Enter' for quit): ");
            sc = new Scanner(System.in);
            final String input = sc.next().toLowerCase();

            Map<String,String> map = np.getNames();
            Map<String, String> hashOrdenado = ordenarHashmap(map);

            for (Map.Entry<String,String> entrada : hashOrdenado.entrySet()){
                String nome = entrada.getKey().toLowerCase();
                if (nome.startsWith(input)) {
                    System.out.println(entrada.getKey());
                }
            }

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

    }

}
