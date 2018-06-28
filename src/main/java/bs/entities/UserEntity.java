package bs.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//实体类User
@Entity
@Table( name = "user", indexes = {@Index(name = "index_email", columnList = "email")})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String email;

    @Column(name = "hashed_pwd", length = 44)
    private String hashedPassword;

    @Column(length = 24)
    private String salt;

    private String username;

    private String gender;

    private String intro;

    private int recordDay;

    //当前在学的词汇书信息
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    @JoinTable(name = "book_leaning_user", joinColumns = {@JoinColumn(name = "user_id")},
//    inverseJoinColumns = {@JoinColumn(name = "book_learning_id")})
    private List<BookLearningEntity> bookLearnings = new ArrayList<>();

    //用户的单词库
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    @JoinTable(name = "user_vocabs_user", joinColumns = {@JoinColumn(name = "user_id")},
//    inverseJoinColumns = {@JoinColumn(name = "vocabs_id")})
    private List<UserVocabsEntity> userVocabs = new ArrayList<>();

    //用户的每日的学习记录的维护
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    @JoinTable(name = "user_vocabs_user", joinColumns = {@JoinColumn(name = "user_id")},
//    inverseJoinColumns = {@JoinColumn(name = "vocabs_id")})
    private List<UserDailyLearningEntity> dailyLearning = new ArrayList<>();

    public UserEntity(){}
    public UserEntity(String email, String pwd, String salt){
        this.email = email;
        this.hashedPassword = pwd;
        this.salt = salt;
        this.username = email;
        UserVocabsEntity userVocabsEntity;
        //type from 1 to 4
        for(int i=1; i < 5; i++)
        {
            userVocabsEntity = new UserVocabsEntity(i,this);
            addUserVocabs(userVocabsEntity);
        }
    }

    public Integer getId(){return id;}
    public void setId(Integer id){ this.id = id;}

    public String getEmail(){return email;}
    public void setEmail(String email){ this.email = email;}

    public String getHashedPassword(){return hashedPassword;}
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

    public String getSalt(){return salt;}
    public void setSalt(String salt) { this.salt = salt;}

    public String getUsername(){return username;}
    public void setUsername(String username) { this.username = username;}

    public String getGender(){return gender;}
    public void setGender(String gender){ this.gender = gender;}

    public String getIntro(){return intro;}
    public void setIntro(String intro){ this.intro = intro;}

    public int getRecordDay(){return recordDay;}
    public void setRecordDay(int recordDay){ this.recordDay = recordDay;}
    public void addRecordDay(){ recordDay = recordDay+1;}

    public List<BookLearningEntity> getBookLearnings() {
        return bookLearnings;
    }
    public void setBookLearnings(List<BookLearningEntity> bookLearnings) {
        this.bookLearnings = bookLearnings;
    }
    public void addBookLearning(BookLearningEntity bookLearningEntity){
        bookLearningEntity.setUserEntity(this);
        bookLearnings.add(bookLearningEntity);
    }

    public List<UserVocabsEntity> getUserVocabs() {
        return userVocabs;
    }
    public void setUserVocabs(List<UserVocabsEntity> userVocabs) {
        this.userVocabs = userVocabs;
    }
    public void addUserVocabs(UserVocabsEntity userVocabsEntity){
        userVocabsEntity.setUser(this);
        userVocabs.add(userVocabsEntity);
    }

    public List<UserDailyLearningEntity> getDailyLearning() {
        return dailyLearning;
    }
    public void setDailyLearning(List<UserDailyLearningEntity> userDailyLearningEntities) {
        this.dailyLearning = userDailyLearningEntities;
    }
    public void addDailyRecord(UserDailyLearningEntity userDailyLearningEntity){
        dailyLearning.add(userDailyLearningEntity);
    }
}
