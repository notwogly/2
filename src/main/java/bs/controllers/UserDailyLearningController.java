package bs.controllers;

import bs.entities.*;
import bs.models.UserDailyLearning;
import bs.models.UserVocabType;
import bs.models.Vocab;
import bs.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/dailyLearning")
public class UserDailyLearningController {
    private final UserRepository userRepository;
    private final UserDailyLearningRepository userDailyLearningRepository;
    private final BookLearningRepository bookLearningRepository;

    @Autowired
    public UserDailyLearningController(UserRepository userRepository,
                                       UserDailyLearningRepository userDailyLearningRepository,
                                       BookLearningRepository bookLearningRepository){
        this.userRepository = userRepository;
        this.userDailyLearningRepository = userDailyLearningRepository;
        this.bookLearningRepository = bookLearningRepository;
    }

    public BookLearningEntity getUserBookLearning(Integer userId){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }catch (Exception e){}
        if(userEntity == null)
            return null;
        BookLearningEntity bookLearningEntity = null;
        try{
            bookLearningEntity = bookLearningRepository.findByUserAndLearned(userEntity,true);
        }catch (Exception e){}

        return bookLearningEntity;
    }

    //设置每天的学习单词的数量
    @PostMapping("/setDailyNum/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void setBookLearningDailyNum(@PathVariable Integer userId, @RequestBody long dailyNum){
        BookLearningEntity bookLearningEntity = getUserBookLearning(userId);
        if(bookLearningEntity == null)
             return;
        long totalNum = bookLearningEntity.getTotalNum();
        long newVocabs = bookLearningEntity.getNewVocab();
        long result = dailyNum;
        if(dailyNum > totalNum || dailyNum> newVocabs)
            result = (totalNum>newVocabs)?newVocabs:totalNum;

        bookLearningEntity.setDailyNum(result);
        bookLearningEntity.setNewVocab(result);
        bookLearningRepository.save(bookLearningEntity);
        Calendar now = Calendar.getInstance();
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return;
        List<UserDailyLearningEntity> userDailyLearningEntityList = null;
        try{
            userDailyLearningEntityList = userDailyLearningRepository.findByUser(userEntity);
        }catch (Exception e){}
        if(userDailyLearningEntityList == null)
            return;
        UserDailyLearningEntity userDailyLearningEntity = null;
        for(UserDailyLearningEntity userDailyLearningEntity1: userDailyLearningEntityList) {
            if ((now.get(Calendar.YEAR) == userDailyLearningEntity1.getToday().get(Calendar.YEAR)) && (now.get(Calendar.MONTH) == userDailyLearningEntity1.getToday().get(Calendar.MONTH))
                    && (now.get(Calendar.DAY_OF_MONTH) == userDailyLearningEntity1.getToday().get(Calendar.DAY_OF_MONTH))) {
                userDailyLearningEntity = userDailyLearningEntity1;
                break;
            }
        }
        if(userDailyLearningEntity == null)
            return;
        userDailyLearningEntity.setDailyNum(result);
        userDailyLearningRepository.save(userDailyLearningEntity);

        return;
    }

    @PostMapping("/setLearning/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDailyLearning setLearning(@PathVariable Integer userId, @RequestBody long newValue){
        Calendar now = Calendar.getInstance();
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;
        List<UserDailyLearningEntity> userDailyLearningEntityList = null;
        try{
            userDailyLearningEntityList = userDailyLearningRepository.findByUser(userEntity);
        }catch (Exception e){}
        if(userDailyLearningEntityList == null)
            return  null;
        UserDailyLearningEntity userDailyLearningEntity = null;
        for(UserDailyLearningEntity userDailyLearningEntity1: userDailyLearningEntityList) {
            if ((now.get(Calendar.YEAR) == userDailyLearningEntity1.getToday().get(Calendar.YEAR)) && (now.get(Calendar.MONTH) == userDailyLearningEntity1.getToday().get(Calendar.MONTH))
                    && (now.get(Calendar.DAY_OF_MONTH) == userDailyLearningEntity1.getToday().get(Calendar.DAY_OF_MONTH))) {
                userDailyLearningEntity = userDailyLearningEntity1;
                break;
            }
        }
        if(userDailyLearningEntity == null)
            return null;
        userDailyLearningEntity.setLearning(newValue);
        userDailyLearningRepository.save(userDailyLearningEntity);
        return new UserDailyLearning(userDailyLearningEntity);
    }

    @PostMapping("/setMastered/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDailyLearning setMastered(@PathVariable Integer userId, @RequestBody long newValue){
        Calendar now = Calendar.getInstance();
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;
        List<UserDailyLearningEntity> userDailyLearningEntityList = null;
        try{
            userDailyLearningEntityList = userDailyLearningRepository.findByUser(userEntity);
        }catch (Exception e){}
        if(userDailyLearningEntityList == null)
            return  null;
        UserDailyLearningEntity userDailyLearningEntity = null;
        for(UserDailyLearningEntity userDailyLearningEntity1: userDailyLearningEntityList) {
            if ((now.get(Calendar.YEAR) == userDailyLearningEntity1.getToday().get(Calendar.YEAR)) && (now.get(Calendar.MONTH) == userDailyLearningEntity1.getToday().get(Calendar.MONTH))
                    && (now.get(Calendar.DAY_OF_MONTH) == userDailyLearningEntity1.getToday().get(Calendar.DAY_OF_MONTH))) {
                userDailyLearningEntity = userDailyLearningEntity1;
                break;
            }
        }
        if(userDailyLearningEntity == null)
            return null;
        userDailyLearningEntity.setMastered(newValue);
        userDailyLearningRepository.save(userDailyLearningEntity);
        return new UserDailyLearning(userDailyLearningEntity);
    }

    @PostMapping("/setNewVocab/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDailyLearning setNewVocab(@PathVariable Integer userId, @RequestBody long newValue){
        Calendar now = Calendar.getInstance();
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;
        List<UserDailyLearningEntity> userDailyLearningEntityList = null;
        try{
            userDailyLearningEntityList = userDailyLearningRepository.findByUser(userEntity);
        }catch (Exception e){}
        if(userDailyLearningEntityList == null)
            return  null;
        UserDailyLearningEntity userDailyLearningEntity = null;
        for(UserDailyLearningEntity userDailyLearningEntity1: userDailyLearningEntityList) {
            if ((now.get(Calendar.YEAR) == userDailyLearningEntity1.getToday().get(Calendar.YEAR)) && (now.get(Calendar.MONTH) == userDailyLearningEntity1.getToday().get(Calendar.MONTH))
                    && (now.get(Calendar.DAY_OF_MONTH) == userDailyLearningEntity1.getToday().get(Calendar.DAY_OF_MONTH))) {
                userDailyLearningEntity = userDailyLearningEntity1;
                break;
            }
        }
        if(userDailyLearningEntity == null)
            return null;
        userDailyLearningEntity.setNewVocab(newValue);
        userDailyLearningRepository.save(userDailyLearningEntity);
        return new UserDailyLearning(userDailyLearningEntity);
    }

}
