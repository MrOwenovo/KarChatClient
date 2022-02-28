package Tomcat.service;

import jakarta.servlet.http.HttpSession;

public interface UserService {
    boolean isLogin(String username, String password, HttpSession session);

}
