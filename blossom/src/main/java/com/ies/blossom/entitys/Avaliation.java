package com.ies.blossom.entitys;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "avaliations")
public class Avaliation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long avaliationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "timestamp")
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "comment")
    private String comment;

    public Avaliation() {}

    public Avaliation(User user, Integer stars, String comment) {
        this.user = user;
        this.stars = stars;
        this.comment = comment;
    }

    public Long getAvaliationId() {
        return avaliationId;
    }

    public void setAvaliationId(Long avaliationId) {
        this.avaliationId = avaliationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
