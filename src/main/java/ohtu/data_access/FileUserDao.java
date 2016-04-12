package ohtu.data_access;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;

public class FileUserDao implements UserDao {

    private File tiedosto;
    private Scanner lukija;
    private List<User> users;
    private FileWriter kirjoittaja;

    public FileUserDao(String tiedostonNimi) {
        this.tiedosto = new File(tiedostonNimi);
        try {
            this.lukija = new Scanner(this.tiedosto);
        } catch (Exception e) {
            System.out.println("Tapahtui virhe.");
        }
    }
    
    private void createListOfUsers() {
        users = new ArrayList<User>();
        while (lukija.hasNext()) {
            String username = lukija.next();
            String password = lukija.next();
            User user = new User(username, password);
            users.add(user);
        }
        lukija.close();
    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        users.add(user);
        try {
            kirjoittaja = new FileWriter(tiedosto);
            kirjoittaja.append(user.getUsername() + " " + user.getPassword() + "\n");
            kirjoittaja.close();
        } catch (IOException ex) {
            System.out.println("Tapahtui virhe.");
        }
    }

}
