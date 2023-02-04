package com.rashad.loginwithsocial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String phone;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String biography;

    private Double rating;

    private Integer userRating;

    @JsonIgnore
    private Boolean enabled = true;

    private Boolean locked = false;
    private Boolean isActive = false;
    private Boolean isPrivate = false;

    @ManyToMany(fetch = FetchType.LAZY) //(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ConfirmationToken> confirmationTokens = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<StdRating> stdRatings;

    @JsonIgnore
    @OneToMany(mappedBy = "takenUser", fetch = FetchType.LAZY)
    private List<UserRating> takenUserRatings;

    @JsonIgnore
    @OneToMany(mappedBy = "givenUser", fetch = FetchType.LAZY)
    private List<UserRating> givenUserRatings;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Company> companies;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Review> reviews;

    public User(String name,
                String surname,
                String email,
                String phone,
                String username,
                String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }
}
