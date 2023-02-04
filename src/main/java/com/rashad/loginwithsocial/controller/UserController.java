package com.rashad.loginwithsocial.controller;

import com.rashad.loginwithsocial.entity.User;
import com.rashad.loginwithsocial.model.*;
import com.rashad.loginwithsocial.repository.UserRepository;
import com.rashad.loginwithsocial.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerJwt")
@Tag(name = "2. User CRUD")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    @Operation(
            summary = "Upload user avatar",
            description = "For upload avatar you have to send form-data image file with user id",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/{id}/upload/avatar", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadAvatar(@PathVariable("id") Long userId,
                                          @RequestParam("file") MultipartFile file)
                                          throws IOException {
        String url = userServiceImpl.uploadAvatar(userId, file);
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("avatar_url", url);
        return new ResponseEntity<>(new CustomResponse(data), HttpStatus.OK);
    }

    @Operation(
            summary = "Change user avatar",
            description = "For change avatar you have to send form-data image file with user id",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/{id}/change/avatar", consumes = "multipart/form-data")
    public ResponseEntity<?> changeAvatar(@PathVariable("id") Long userId,
                                          @RequestParam("file") MultipartFile file)
                                          throws IOException {
        userServiceImpl.deleteAvatar(userId);
        String url = userServiceImpl.uploadAvatar(userId, file);
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("avatar_url", url);
        return new ResponseEntity<>(new CustomResponse(data), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete user avatar",
            description = "For delete avatar you have to send user id",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}/delete/avatar")
    public ResponseEntity<?> deleteAvatar(@PathVariable("id") Long userId) throws IOException {
        userServiceImpl.deleteAvatar(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("message", "Your avatar deleted successfully.");
        return new ResponseEntity<>(new CustomResponse(data), HttpStatus.OK);
    }

    @Operation(
            summary = "Add bio to user",
            description = "For adding bio you have to send body with example like shown below",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/add/bio")
    public ResponseEntity<?> addBio(@PathVariable("id") Long userId,
                                    @RequestBody @Valid BioRequest request) {
        userServiceImpl.addBioToUser(userId, request);
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("message", "Bio added successfully");
        return new ResponseEntity<>(new CustomResponse(data), HttpStatus.OK);
    }

    @Operation(
            summary = "Change user's bio",
            description = "For change bio you have to send body with example like shown below",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/change/bio")
    public ResponseEntity<?> changeBio(@PathVariable("id") Long userId,
                                       @RequestBody @Valid BioRequest request) {
        userServiceImpl.addBioToUser(userId, request);
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("message", "Bio changed successfully");
        return new ResponseEntity<>(new CustomResponse(data), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete user's bio",
            description = "For delete bio you have to send empty body with example like shown below",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}/delete/bio")
    public ResponseEntity<?> deleteBio(@PathVariable("id") Long userId,
                                       @RequestBody @Valid BioRequest request) {
        userServiceImpl.addBioToUser(userId, request);
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("message", "Bio deleted successfully");
        return new ResponseEntity<>(new CustomResponse(data), HttpStatus.OK);
    }

    @Operation(
            summary = "Get me",
            description = "Get your user details",
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @GetMapping("/me")
    public ResponseEntity<?> getUserMe() {
        User user = userServiceImpl.getUserMe();
        return new ResponseEntity<>(new CustomResponse(user), HttpStatus.OK);
    }

    @Operation(
            summary = "Get one user",
            description = "Get one user by id",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long userId) {
        Object user = userServiceImpl.getUserFromId(userId);
        return new ResponseEntity<>(new CustomResponse(user), HttpStatus.OK);
    }

    @Operation(
            summary = "Get all users",
            description = "Get all user list",
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userServiceImpl.getAllUsers();
        return new ResponseEntity<>(new CustomResponse(users), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete user",
            description = "Delete user by id",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        userServiceImpl.deleteUser(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("message", "User deleted successfully.");
        return new ResponseEntity<>(new CustomResponse(data), HttpStatus.OK);
    }

    @Operation(
            summary = "Set user profile private",
            description = "For setting user profile private you have to send user id",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/private")
    public ResponseEntity<?> setPrivate(@PathVariable("id") Long userId) {
        userServiceImpl.setPrivate(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", userId);
        data.put("message", "Your profile private now.");
        return new ResponseEntity<>(new CustomResponse(data), HttpStatus.OK);
    }

    @Operation(
            summary = "Add rating to user",
            description = "For add rating to user you have to send body like shown below",
            parameters = {@Parameter(name = "id", description = "userId", example = "5")},
            responses = {@ApiResponse(responseCode = "200", description = "Success response",
                    content = @Content(schema = @Schema(implementation = CustomResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))}
    )
    @PostMapping("/{id}/add/rating")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addRatingToUser(@PathVariable("id") Long userId,
                                             @RequestBody @Valid RatingRequest request) {
        String message = userServiceImpl.addRatingToUser(userId, request);
        userServiceImpl.getUserAverageRating(userId);
        return new ResponseEntity<>(new CustomResponse(message), HttpStatus.OK);
    }

}
