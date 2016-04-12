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
        return username.length() >= 3 && !usernamesInUse.contains(username) && usernameContainsOnlyLetters(username);
    }
    
    private boolean usernameContainsOnlyLetters(String username) {
        for (int i = 0; i < username.length(); i++) {
            String kirjaimet = "abcdefghijklmnopqrstuvwxyz";
            String merkki = "" + username.charAt(i);
            if (!kirjaimet.contains(merkki)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean passwordIsValid(String password) {
        return password.length() >= 8 && passwordContainsSpecialCharacterOrNumber(password);
    }
    
    private boolean passwordContainsSpecialCharacterOrNumber(String password) {
        for (int i = 0; i < password.length(); i++) {
            String merkki = "" + password.charAt(i);
            String erikoismerkit = "!@#$%&*()_+=-|<>?{}[]~0123456789";
            if (erikoismerkit.contains(merkki)) {
                return true;
            }
        }
        return false;
    }
}
