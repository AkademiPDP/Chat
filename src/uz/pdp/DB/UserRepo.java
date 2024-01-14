package uz.pdp.DB;

import uz.pdp.DB.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements Reposstory<User> {
    private List<User> users;
    private static UserRepo singleton;
    private static final String PATH = "src/uz/pdp/DB/user_db.txt";

    public UserRepo(List<User> users) {
        this.users = users;
    }

    public static UserRepo getInstance() {
        if (singleton == null) {
            singleton = new UserRepo(loatData());
        }
        return singleton;
    }

    private static List<User> loatData() {
        try (
                InputStream inputStream = new FileInputStream((PATH));
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ) {

          return  (List<User>) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(User user) {
        users.add(user);
        uploatData();
    }



    private void uploatData() {
        try (
                OutputStream outputStream = new FileOutputStream(PATH);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ) {
            objectOutputStream.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<User> finAll() {
        return users;
    }
    @Override
    public void delete(User user) {
    users.remove(user);
    uploatData();
    }
}
