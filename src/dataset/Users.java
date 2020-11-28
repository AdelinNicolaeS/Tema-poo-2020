package dataset;

import user.User;

import java.util.ArrayList;
import java.util.List;

public final class Users {
    private List<User> userList = new ArrayList<>();

    public Users(final List<User> userList) {
        this.userList = new ArrayList<>(userList);
    }

    public Users() {

    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(final List<User> userList) {
        this.userList = userList;
    }

    /**
     * cauta un user in baza de date
     * @param name numele user-ului cautat
     * @return user-ul daca e in baza sau null, daca nu e prezent
     */
    public User findUserByName(final String name) {
        for (User user : userList) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * scrie mesajul de afisat pentru comanda
     * @param n primii cati useri se afiseaza
     * @return mesajul pentru output
     */
    public String usersListMessage(final int n) {
        ArrayList<String> usernames = new ArrayList<>();
        for (int i = 0; i < Math.min(n, userList.size()); i++) {
            usernames.add(userList.get(i).getUsername());
        }
        return "Query result: " + usernames.toString();
    }
}
