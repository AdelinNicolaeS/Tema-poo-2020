package dataset;

import user.User;

import java.util.ArrayList;
import java.util.List;

public final class Users {
    private List<User> userList = new ArrayList<>();

    public Users(List<User> userList) {
        this.userList = new ArrayList<>(userList);
    }

    public Users() {

    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User findUserByName(String name) {
        for (User user : userList) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public String UsersListMessage(int n) {
        ArrayList<String> usernames = new ArrayList<>();
        for(int i = 0; i < Math.min(n, userList.size()); i++) {
            usernames.add(userList.get(i).getUsername());
        }
        return "Query result: " + usernames.toString();
    }
}
