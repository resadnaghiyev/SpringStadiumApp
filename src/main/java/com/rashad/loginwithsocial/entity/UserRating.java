package com.rashad.loginwithsocial.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_ratings")
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer point;

    @ManyToOne
    @JoinColumn(name = "taken_user_id", referencedColumnName = "id")
    private User takenUser;

    @ManyToOne
    @JoinColumn(name = "given_user_id", referencedColumnName = "id")
    private User givenUser;

    public UserRating(Integer point) {
        this.point = point;
    }
}
