package bs.models;

import bs.entities.UserDailyLearningEntity;
import bs.entities.UserEntity;

import javax.validation.constraints.NotNull;
import java.util.Calendar;

public class UserDailyLearning {
    private Integer id;
    private Integer userId;
    private long dailyNum;
    private long learning;
    private long mastered;
    private long newVocab;
    private Calendar today;

    public UserDailyLearning() {
    }
    public UserDailyLearning(@NotNull UserDailyLearningEntity userDailyLearningEntity){
        id = userDailyLearningEntity.getId();
        UserEntity userEntity = userDailyLearningEntity.getUser();
        userId = userEntity.getId();
        dailyNum = userDailyLearningEntity.getDailyNum();
        learning = userDailyLearningEntity.getLearning();
        mastered = userDailyLearningEntity.getMastered();
        newVocab = userDailyLearningEntity.getNewVocab();
        today = userDailyLearningEntity.getToday();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Calendar getToday() {
        return today;
    }
    public void setToday(Calendar today) {
        this.today = today;
    }

    public void setMastered(long mastered) {
        this.mastered = mastered;
    }
    public void setNewVocab(long newVocab) {
        this.newVocab = newVocab;
    }
    public void setLearning(long learning) {
        this.learning = learning;
    }
    public long getMastered() {
        return mastered;
    }
    public long getLearning() {
        return learning;
    }
    public long getNewVocab() {
        return newVocab;
    }

    public long getDailyNum() {
        return dailyNum;
    }
    public void setDailyNum(long daillyNum) {
        this.dailyNum = daillyNum;
    }

}
