package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
        if (usernameIsValid(username) && passwordIsValid(password)) {
            return false;
        }
        
        return true;
    }
    
    private boolean usernameIsValid(String username) {
        ArrayList<String> usernamesInUse = new ArrayList<String>();
        for (User user : userDao.listAll()) {
            usernamesInUse.add(user.getUsername());
        }
        for (int i = 0; i < username.length(); i++) {
            String kirjaimet = "abcdefghijklmnopqrstuvwxyz";
            String merkki = "" + username.charAt(i);
            if (!kirjaimet.contains(merkki)) {
                return false;
            }
        }
        
        return username.length() >= 3 && !usernamesInUse.contains(username);
    }
    
    private boolean passwordIsValid(String password) {
        boolean sisaltaaErikoismerkinTaiNumeron = false;
        
        for (int i = 0; i < password.length(); i++) {
            String merkki = "" + password.charAt(i);
            String erikoismerkit = "!@#$%&*()_+=-|<>?{}[]~";
            String numerot = "0123456789";
            if (erikoismerkit.contains(merkki) || numerot.contains(merkki)) {
                sisaltaaErikoismerkinTaiNumeron = true;
            }
        }
        
        return password.length() >= 8 && sisaltaaErikoismerkinTaiNumeron;
    }
}
