package mensagens;
import redis.clients.jedis.Jedis;
import java.util.*;
public class MessageFormatter {

    private Jedis jedis;

    public void add(String user){
        if (getUsers().contains(user)) {
            System.out.println("'" + user + "' is already added.");
        } else {
            jedis.rpush(user, "");
        }
    }

    public Set<String> getUsers() {
        return jedis.keys("*");
    }

    public List<String> getAssociations(String user) {
        return jedis.lrange(user + "_association", 0, -1);
    }

    public void associate(String userA, String userB) {
        if (getAssociations(userB).contains(userA)) {
            System.out.println("'" + userA + "' is already associated.");
        } else {
            jedis.rpush(userB + "_association", userA);
        }
    }

    public void storeMsg(String user, List<String> message) {
        if (getUsers().contains(user)) {
            String msg = "";
            for (String str : message) {
                msg += str + " ";
            }
            for (String association : getAssociations(user)) {
                System.out.println("[" + user + " : " + association + "] " + msg);
            }
            jedis.rpush(user, msg);
        } else {
            System.out.println("'" + user + "' doesn't bellong to the database");
        }
    }

    public void readMsg(String user) {
        List<String> msgs = jedis.lrange(user, 0, -1);
        for (String message : msgs) {
            if (!message.equals(""))
                System.out.println(message);
        }
        System.out.println();
    }

    public void showUsers(){
        int ctr = 0;
        for (String user : getUsers()) {
            System.out.println(ctr + ": " + user);
            ctr ++;
        }
    }

    public MessageFormatter() {
        this.jedis = new Jedis();
    }

    public static void showMenu() {
        System.out.println("------------------------------------- MENU -----------------------------------------");
        System.out.println();
        System.out.println("msg add [username] -> Add a user to the database");
        System.out.println("msg associate [userA] [userB] -> userA associates with userB, receiving his messages");
        System.out.println("msg storeMsg [userA] [message] -> userA sends a message");
        System.out.println("msg readMsg [userA] -> Lists 'userA' message history");
        System.out.println("msg showUsers -> Lists all on the database");
        System.out.println("msg exit -> Close the program");
        System.out.println();
    }

    public static void main(String[] args) {
        MessageFormatter form = new MessageFormatter();
        Scanner sc = new Scanner(System.in);

        showMenu();

        boolean exit = false;
        while (exit == false){
            System.out.print("Pick a option: ");
            String[] command = sc.nextLine().split(" ");
            if (command[0].equals("msg")) {
                System.out.println("Entre");
                switch (command[1]) {
                    case "add":
                        if (command.length == 3){
                            form.add(command[2]);
                        } else {
                            System.out.println("Usage: msg add [username]");
                        }
                        break;
                    case "associate":
                        if (command.length == 4){
                            form.associate(command[2], command[3]);
                        } else {
                            System.out.println("Usage: msg associate [userA] [userB]");
                        }
                        break;
                    case "storeMsg":
                        if (command.length == 4){
                            form.storeMsg(command[2], Arrays.asList(command).subList(3, command.length));
                        } else {
                            System.out.println("Usage: msg storeMsg [userA] [message]");
                        }
                        break;
                    case "readMsg":
                        if (command.length == 3){
                            form.readMsg(command[2]);
                        } else {
                            System.out.println("msg readMsg [userA]");
                        }
                        break;
                    case "showUsers":
                        if (command.length == 2){
                            form.showUsers();
                        } else {
                            System.out.println("msg showUsers");
                        }
                        break;
                    case "exit":
                        exit = true;
                        System.out.println("Thanks for using the application!");
                        break;
                    default:
                        System.out.println("Error! Non-existing command.\n");
                        showMenu();

                }
            }
        }

        sc.close();
    }
}
