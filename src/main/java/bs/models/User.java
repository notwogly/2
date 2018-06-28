package bs.models;

import bs.entities.UserEntity;

import javax.validation.constraints.NotNull;

public class User {
    private Integer id;
    private String email;
    private String username;
    private String gender;
    private String intro;
    private int recordDay;

    public User(){}
    public User(@NotNull UserEntity userEntity){
        id = userEntity.getId();
        email = userEntity.getEmail();
        username = userEntity.getUsername();
        gender = userEntity.getGender();
        intro = userEntity.getIntro();
        recordDay = userEntity.getRecordDay();
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }
    public String getUsername() {
        return username;
    }
    public String getIntro() {
        return intro;
    }
    public int getRecordDay() {
        return recordDay;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }
    public void setRecordDay(int recordDay) {
        this.recordDay = recordDay;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
