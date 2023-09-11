package com.ha.satoken.data.entity;



import javax.persistence.*;

@Entity(name = "usr_pro")
public class UserProEntity extends ActivatableEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String promission;

    @Override
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

    public String getPromission() {
        return promission;
    }

    public void setPromission(String promission) {
        this.promission = promission;
    }
}
