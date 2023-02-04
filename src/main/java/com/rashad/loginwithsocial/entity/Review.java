package com.rashad.loginwithsocial.entity;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "std_reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @ManyToOne
    @JsonIncludeProperties({"id"})
    @JoinColumn(name = "stadium_id", referencedColumnName = "id", nullable = false)
    private Stadium stadium;

    @ManyToOne
    @JsonProperty("author")
    @JsonIncludeProperties({"id", "username"})
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonProperty("tags")
    @JsonIncludeProperties({"id", "username"})
    private List<User> users;

    @OneToMany(mappedBy = "review")
    private List<Reply> replies;

    public Review(String text, Stadium stadium, User user, List<User> users) {
        this.text = text;
        this.stadium = stadium;
        this.user = user;
        this.users = users;
    }

    public Review(String text, Stadium stadium, User user) {
        this.text = text;
        this.stadium = stadium;
        this.user = user;
    }
}
