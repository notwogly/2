package bs.entities;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "users_daily_learning")
public class UserDailyLearningEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private long dailyNum;
    private long learning;
    private long mastered;
    private long newVocab;

    //保存该天的时间
    private Calendar today;

    //该学习日期记录的用户
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "daily_learning_user")
    private UserEntity user;

    public UserDailyLearningEntity(){}
    public UserDailyLearningEntity(UserEntity userEntity, long dailyNum, Calendar today){
        this.user = userEntity;
        userEntity.addDailyRecord(this);
        this.dailyNum = dailyNum;
        this.newVocab = dailyNum;
        this.learning = 0;
        this.mastered = 0;
        this.today = today;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public long getDailyNum() {
        return dailyNum;
    }
    public void setDailyNum(long dailyNum) {
        this.dailyNum = dailyNum;
    }

    public long getLearning() {
        return learning;
    }
    public void setLearning(long learning) {
        this.learning = learning;
    }

    public long getMastered() {
        return mastered;
    }
    public void setMastered(long mastered) {
        this.mastered = mastered;
    }

    public long getNewVocab() {
        return newVocab;
    }
    public void setNewVocab(long newVocab) {
        this.newVocab = newVocab;
    }

    public Calendar getToday() {
        return today;
    }
    public void setToday(Calendar today) {
        this.today = today;
    }

    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
}
