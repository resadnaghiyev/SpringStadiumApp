package com.rashad.loginwithsocial.service.impl;

import com.rashad.loginwithsocial.entity.ERole;
import com.rashad.loginwithsocial.entity.Role;
import com.rashad.loginwithsocial.entity.User;
import com.rashad.loginwithsocial.model.BioRequest;
import com.rashad.loginwithsocial.model.RatingRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, Object> signUpUser(User user);

    User registerGoogle(User user);

    void saveUser(User user);

    void setActiveUser(String username);

    void saveRole(Role role);

    void addRoleToUser(String username, ERole roleName);

    void addBioToUser(Long id, BioRequest request);

    String uploadAvatar(Long id, MultipartFile file) throws IOException;

    void deleteAvatar(Long id) throws IOException;

    User getUserMe();

    void setPrivate(Long userId);

    User getUserFromUsername(String username);

    Object getUserFromId(Long id);

    List<User> getAllUsers();

    void deleteUser(Long id);

    String addRatingToUser(Long userId, RatingRequest request);
}
