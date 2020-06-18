package me.apex.hades.user;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
public class UserManager {
    private List<User> users;
    public static UserManager INSTANCE;

    public UserManager() {
        users = Collections.synchronizedList(new ArrayList<>());
        INSTANCE = this;
    }

    public User getUser(UUID uuid) {
        for (User user : users) {
            if (user.getPlayer().getUniqueId() == uuid || user.getPlayer().getUniqueId().equals(uuid)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public void removeUser(User user) {
        if (users.contains(user)) {
            //user.getExecutorService().shutdownNow();
            users.remove(user);
        }
    }
}
