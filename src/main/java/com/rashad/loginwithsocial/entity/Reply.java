package com.rashad.loginwithsocial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "std_reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "review_id", referencedColumnName = "id", nullable = false)
    private Review review;

    @ManyToOne
    @JsonProperty("author")
    @JsonIncludeProperties({"id", "username"})
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonProperty("tags")
    @JsonIncludeProperties({"id", "username"})
    private List<User> users;

    public Reply(String text, Review review, User user, List<User> users) {
        this.text = text;
        this.review = review;
        this.user = user;
        this.users = users;
    }
}
