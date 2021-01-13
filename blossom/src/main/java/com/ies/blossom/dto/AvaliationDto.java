package com.ies.blossom.dto;


public class AvaliationDto {

    private Long id;
    private Long userId;
    private Integer stars;
    private String comment;

    public AvaliationDto() {}

    public AvaliationDto(Integer stars, String comment) {
        this.stars = stars;
        this.comment = comment;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
