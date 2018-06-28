package bs.controllers;

import bs.PasswordHash;
import bs.entities.*;
import bs.models.*;
import bs.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final VocabRepository vocabRepository;
    private final UserVocabsRepository userVocabsRepository;
    private final UserDailyLearningRepository userDailyLearningRepository;
    private final BookLearningRepository bookLearningRepository;

    @Autowired
    public UserController(UserRepository userRepository, BookRepository bookRepository,
                          VocabRepository vocabRepository, UserVocabsRepository userVocabsRepository,
                          UserDailyLearningRepository userDailyLearningRepository, BookLearningRepository bookLearningRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.vocabRepository = vocabRepository;
        this.userVocabsRepository = userVocabsRepository;
        this.userDailyLearningRepository = userDailyLearningRepository;
        this.bookLearningRepository = bookLearningRepository;
    }

    public int userVocabInType(Integer userId, Integer vocabId, int type){
        UserEntity userEntity = null;
        try{
            userEntity = userRepository.findById(userId).get();
        }catch (Exception e){}
        if(userEntity == null)
            return -1;
        VocabEntity vocabEntity = null;
        try {
            vocabEntity = vocabRepository.findById(vocabId).get();
        }catch (Exception e){}
        if(vocabEntity == null)
            return -1;
        UserVocabsEntity userVocabsEntity = null;
        try{
                userVocabsEntity = userVocabsRepository.findByTypeAndUser(type,userEntity);
        }catch (Exception e){}
        if(userVocabsEntity == null)
            return -1;
        List<VocabEntity> vocabEntityList = userVocabsEntity.getVocabs();
        if(vocabEntityList.indexOf(vocabEntity)>=0)
            return type;

        return -1;
    }

    //用户注册
    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public boolean userRegister(@RequestBody UserRegistered  u){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(u.getEmail());
        }catch (Exception e){}
        if(userEntity != null)
            return false;

        PasswordHash p = new PasswordHash();
        String salt = p.getSalt();
        String hashedPwd = p.getHashedPassword(u.getPwd(),salt);
        userEntity = new UserEntity(u.getEmail(), hashedPwd, salt);
        userRepository.save(userEntity);

        return true;
    }

    //用户登陆
    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User userLogin(@RequestBody UserRegistered  u){
//        System.out.println(u.getEmail());
//        System.out.println(u.getPwd());
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(u.getEmail());
        }catch (Exception e){}
        if(userEntity == null)
            return null;
        PasswordHash p = new PasswordHash();
        String salt = userEntity.getSalt();
        String hashedPwd = p.getHashedPassword(u.getPwd(),salt);
        userBeginLearning(userEntity.getId());
        if(hashedPwd.equals(userEntity.getHashedPassword()))
            return new User(userEntity);
        else
            return null;
    }

    //获取用户信息
    @GetMapping("/username/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUserByUsername(@PathVariable String username){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUsername(username);
        }
        catch (Exception e){}
        if(userEntity != null)
            return new User(userEntity);
        else
            return null;
    }

    @GetMapping("/email/{email}")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUserByEmail(@PathVariable String email){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(email);
        }
        catch (Exception e){}
        if(userEntity != null)
            return new User(userEntity);
        else
            return null;
    }

    //修改用户信息
    @PostMapping("/{userId}/userInfo")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User modifyInfo(@PathVariable Integer userId, @RequestBody User user){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;
        UserEntity userEntity2 = null;
        try {
            userEntity2 = userRepository.findByUsername(user.getUsername());
        }
        catch (Exception e){}
        if(userEntity2 != null && userEntity2.getId() != userEntity.getId())
            return null;
        userEntity.setIntro(user.getIntro());
        userEntity.setGender(user.getGender());
        userEntity.setUsername(user.getUsername());
        userRepository.save(userEntity);
        return new User(userEntity);
    }

    //修改打卡日期
    @PostMapping("/{userId}/recordDay")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User modifyRecordDay(@PathVariable Integer userId, @RequestBody int recordDay){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;
        userEntity.setRecordDay(recordDay);
        userRepository.save(userEntity);
        return new User(userEntity);
    }

    //修改打卡日期
    @PostMapping("/{userId}/addRecordDay")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User addRecordDay(@PathVariable Integer userId){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;
        int recordDay = userEntity.getRecordDay();
        recordDay++;
        userEntity.setRecordDay(recordDay);
        userRepository.save(userEntity);
        return new User(userEntity);
    }

    //修改密码
    @PostMapping("/{userId}/password")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User modifyPassword(@PathVariable Integer userId, @RequestBody String newPwd){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;
        PasswordHash p = new PasswordHash();
        String salt = userEntity.getSalt();
        String hashedPwd = p.getHashedPassword(newPwd,salt);
        userEntity.setHashedPassword(hashedPwd);
        userRepository.save(userEntity);
        User a =  new User(userEntity);
        return a;
    }

    //用户开始本日学习
    @PostMapping("/begin/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDailyLearning userBeginLearning(@PathVariable Integer userId){
        Calendar now = Calendar.getInstance();
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }catch (Exception e){}
        if(userEntity == null)
            return null;
        BookLearning bookLearning = getBook(userId);
        if(bookLearning == null)
            return null;
        Calendar date = Calendar.getInstance();
        List<UserDailyLearningEntity> userDailyLearningEntityList = null;
        try{
            userDailyLearningEntityList = userDailyLearningRepository.findByUser(userEntity);
        }catch (Exception e){}
        if(userDailyLearningEntityList == null)
            return  null;
        for(UserDailyLearningEntity userDailyLearningEntity: userDailyLearningEntityList) {
            if ((now.get(Calendar.YEAR) == userDailyLearningEntity.getToday().get(Calendar.YEAR)) && (now.get(Calendar.MONTH) == userDailyLearningEntity.getToday().get(Calendar.MONTH))
                    && (now.get(Calendar.DAY_OF_MONTH) == userDailyLearningEntity.getToday().get(Calendar.DAY_OF_MONTH))) {
                return new UserDailyLearning(userDailyLearningEntity);
            }
        }
        //不存在今天的学习情况记录
        //System.out.println("不存在今天的学习情况记录");
        userEntity.addRecordDay();
        userRepository.save(userEntity);
        UserDailyLearningEntity userDailyLearningEntity = new UserDailyLearningEntity(userEntity,bookLearning.getDailyNum(),date);
        userDailyLearningRepository.save(userDailyLearningEntity);
        long newVocab = userDailyLearningEntity.getNewVocab();
//        if( newVocab == 0)
//            return null;
        UserVocabsEntity userVocabsEntity = null;
        try{
            userVocabsEntity = userVocabsRepository.findByTypeAndUser(2,userEntity);
        }catch (Exception e){}
        UserVocabsEntity userVocabsEntity1 = null;
        try{
            userVocabsEntity1 = userVocabsRepository.findByTypeAndUser(1,userEntity);
        }catch (Exception e){}
        List<VocabEntity> vocabEntityList = userVocabsEntity.getVocabs();
        userVocabsEntity1.getVocabs().clear();
        if(vocabEntityList.size() == 0)
            return null;
        List<VocabEntity> vocabEntityList1 = userVocabsEntity1.getVocabs();
        vocabEntityList1.clear();
        if(newVocab > vocabEntityList.size())
            newVocab = vocabEntityList.size();
        for(int i = 0; i< newVocab; i++)
        {
            VocabEntity vocabEntity = vocabEntityList.get(i);
            vocabEntityList1.add(vocabEntity);
        }
        userVocabsRepository.save(userVocabsEntity);
        userVocabsRepository.save(userVocabsEntity1);

        return new UserDailyLearning(userDailyLearningEntity);
    }

    //用户获取本日的学习情况
    @GetMapping("/getDailyLearning/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDailyLearning getDailyLearning(@PathVariable Integer userId){
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
            return new UserDailyLearning();
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

        return new UserDailyLearning(userDailyLearningEntity);
    }

    //用户增加在学习的书籍
    @PostMapping("/addBook/{userId}/{bookId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Book userAddBook(@PathVariable Integer userId, @PathVariable Integer bookId){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }catch (Exception e){}
        if(userEntity == null)
            return null;
        BookEntity bookEntity = null;
        try{
            bookEntity = bookRepository.findById(bookId).get();
        }catch (Exception e){}
        if(bookEntity == null)
            return  null;
        //将书的所有单词添加到用户的newVocabs单词库中
        //清空用户的type = 2的单词库
        UserVocabsEntity userVocabsEntity = null;
        try{
            userVocabsEntity = userVocabsRepository.findByTypeAndUser(2,userEntity);
        }catch (Exception e){}
        if(userVocabsEntity == null)
            return null;
        userVocabsEntity.getVocabs().clear();
        List<VocabEntity> userVocabEntityList = userVocabsEntity.getVocabs();
        List<VocabEntity> bookVocabEntityList = bookEntity.getVocabs();
        for(VocabEntity vocabEntity: bookVocabEntityList)
        {
            int exit = userVocabInType(userId,vocabEntity.getId(),3);
            int exit2 = userVocabInType(userId,vocabEntity.getId(),4);
            if((exit<0)&&(exit2<0))
                userVocabEntityList.add(vocabEntity);
        }
        userVocabsRepository.save(userVocabsEntity);
        //删除当前的本日的学习记录并添加新的
        Calendar now = Calendar.getInstance();
        List<UserDailyLearningEntity> userDailyLearningEntityList = null;
        try{
            userDailyLearningEntityList = userDailyLearningRepository.findByUser(userEntity);
        }catch (Exception e){}
        if(userDailyLearningEntityList == null)
            return null;
        UserDailyLearningEntity userDailyLearningEntity = null;
        for(UserDailyLearningEntity userDailyLearningEntity1: userDailyLearningEntityList) {
            if ((now.get(Calendar.YEAR) == userDailyLearningEntity1.getToday().get(Calendar.YEAR)) && (now.get(Calendar.MONTH) == userDailyLearningEntity1.getToday().get(Calendar.MONTH))
                    && (now.get(Calendar.DAY_OF_MONTH) == userDailyLearningEntity1.getToday().get(Calendar.DAY_OF_MONTH))) {
                userDailyLearningEntity = userDailyLearningEntity1;
                break;
            }
        }
        if(userDailyLearningEntity != null)
        {
            try{
                userDailyLearningRepository.deleteById(userDailyLearningEntity.getId());
            }catch (Exception e){}
            userEntity.sebRecordDay();
            userRepository.save(userEntity);
        }
        //是否已经添加在list中但是不是当前正在学习
        List<BookLearningEntity> bookLearningEntityList = null;
        try{
            bookLearningEntityList = bookLearningRepository.findByUser(userEntity);
        }catch (Exception e){}
        boolean flag = false;
        for(BookLearningEntity bookLearningEntity: bookLearningEntityList)
        {
            if(bookLearningEntity.getBookEntity().getId() == bookEntity.getId())
            {
                bookLearningEntity.setLearned(true);
                bookLearningEntity.setNewVocab((long)userVocabEntityList.size());
                bookLearningEntity.updateDailyNum();
                flag = true;
                bookLearningRepository.save(bookLearningEntity);
            }else if(bookLearningEntity.isLearned() == true && bookLearningEntity.getBookEntity().getId() != bookEntity.getId())
            {
                bookLearningEntity.setLearned(false);
                bookLearningRepository.save(bookLearningEntity);
            }
        }
        if(flag == false)
        {
            BookLearningEntity bookLearningEntity = new BookLearningEntity(userEntity,bookEntity,(long)userVocabEntityList.size());
            bookLearningRepository.save(bookLearningEntity);
        }
        if(userDailyLearningEntity!= null)
        {
            userBeginLearning(userId);
        }

        return new Book(bookEntity);
    }

    //用户删除在学习的书籍
    @DeleteMapping("/deleteBook/{userId}/{bookId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void userDeleteBook(@PathVariable Integer userId, @PathVariable Integer bookId){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }catch (Exception e){}
        if(userEntity == null)
            return;
        BookEntity bookEntity = null;
        try{
            bookEntity = bookRepository.findById(bookId).get();
        }catch (Exception e){}
        if(bookEntity == null)
            return;
        BookLearningEntity bookLearningEntity = null;
        try{
            bookLearningEntity = bookLearningRepository.findByUserAndBook(userEntity,bookEntity);
            Integer id = bookLearningEntity.getId();
            bookLearningRepository.deleteById(id);
        }catch (Exception e){}
//        UserVocabsEntity userVocabsEntity = null;
//        try{
//            userVocabsEntity = userVocabsRepository.findByTypeAndUser(2,userEntity);
//            Integer id = userVocabsEntity.getId();
//            userVocabsRepository.deleteById(id);
//        }catch (Exception e){}

        return;
    }

    //用户获取当前在学习的书本的信息
    @GetMapping("/getBook/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public BookLearning getBook(@PathVariable Integer userId){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;
        BookLearningEntity bookLearningEntity = null;
        try{
            bookLearningEntity = bookLearningRepository.findByUserAndLearned(userEntity,true);
        }catch (Exception e){}
        if(bookLearningEntity == null)
            return new BookLearning();

        return new BookLearning(bookLearningEntity);
    }

    //用户获取词库中的单词
    @GetMapping("/getVocab/{userId}/{type}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Vocab> getVocabs(@PathVariable Integer userId,@PathVariable int type ){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;

        UserVocabsEntity userVocabsEntity = null;
        try{
            userVocabsEntity = userVocabsRepository.findByTypeAndUser(type,userEntity);
        }catch (Exception e){}
        if(userVocabsEntity == null && type == 2)
            return new ArrayList<>();
        else if(userVocabsEntity == null)
            return null;

        List<VocabEntity> vocabEntityList = userVocabsEntity.getVocabs();
        if(vocabEntityList == null)
            return null;

        List<Vocab> vocabList = new ArrayList<Vocab>();
        for(VocabEntity vocabEntity:vocabEntityList)
        {
            Vocab vocab = new Vocab(vocabEntity);
            vocabList.add(vocab);
        }

        return vocabList;
    }

    //清空用户的单词库
    @DeleteMapping("/clearVocabs/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void getVocabs(@PathVariable Integer userId){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return;
        BookLearningEntity bookLearningEntity = null;
        try{
            bookLearningEntity = bookLearningRepository.findByUserAndLearned(userEntity,true);
        }catch (Exception e){}
        if(bookLearningEntity != null){
            long total = bookLearningEntity.getTotalNum();
            bookLearningEntity.setNewVocab(total);
            bookLearningEntity.setLearning(0);
            bookLearningEntity.setMastered(0);
        }
        List<UserVocabsEntity> userVocabsEntityList = null;
        try{
            userVocabsEntityList = userVocabsRepository.findByUser(userEntity);
        }catch (Exception e){}
        if(userVocabsEntityList == null )
            return;
        for(UserVocabsEntity userVocabsEntity: userVocabsEntityList){
            List<VocabEntity> vocabEntityList = userVocabsEntity.getVocabs();
            vocabEntityList.clear();
            userVocabsRepository.save(userVocabsEntity);
        }

        return;
    }

    //用户从newVocab单词库中随机获取一个获取单词的细节
    @GetMapping("/getVocabDetail/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Vocab getVocabDetail(@PathVariable Integer userId){
        UserDailyLearning userDailyLearning = getDailyLearning(userId);
        if(userDailyLearning == null)
            return null;
        else if(userDailyLearning.getNewVocab() == 0)
            return null;

        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return null;
        UserVocabsEntity userVocabsEntity = null;
        try{
            userVocabsEntity = userVocabsRepository.findByTypeAndUser(2,userEntity);
        }catch (Exception e){}
        List<VocabEntity> vocabEntityList = userVocabsEntity.getVocabs();
        if(vocabEntityList.size() == 0)
            return null;
        VocabEntity vocabEntity = vocabEntityList.get(0);

        return new Vocab(vocabEntity);
    }

    //将单词添加进用户的单词库
    @PostMapping("/addVocab/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public boolean userAddVocab(@PathVariable Integer userId, @RequestBody UserVocabType userVocabType){
        UserEntity userEntity = null;
        try{
            userEntity = userRepository.findById(userId).get();
        }catch (Exception e){}
        if(userEntity == null)
            //return  null;
            return false;
        VocabEntity vocabEntity = null;
        try {
            vocabEntity = vocabRepository.findById(userVocabType.getVocabId()).get();
        }catch (Exception e){}
        if(vocabEntity == null)
            return false;
        UserVocabsEntity userVocabsEntity = null;
        try{
            userVocabsEntity = userVocabsRepository.findByTypeAndUser(userVocabType.getType(),userEntity);
        }catch (Exception e){}
        if(userVocabsEntity == null)
            return false;
        List<VocabEntity> vocabEntityList = userVocabsEntity.getVocabs();
        for(VocabEntity vocabEntity1: vocabEntityList)
        {
            if(vocabEntity1.getId() == vocabEntity.getId())
                return false;//return "单词已经存在";
        }

        userVocabsEntity.addVocabs(vocabEntity);
        userVocabsEntity.setVocabs(vocabEntityList);
        userVocabsRepository.save(userVocabsEntity);

        return true;//return "更新成功";
    }

    //将单词从用户的单词库中删除
    @DeleteMapping("/deleteVocab/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public boolean userDeleteVocab(@PathVariable Integer userId, @RequestBody UserVocabType userVocabType){
        UserEntity userEntity = null;
        try{
            userEntity = userRepository.findById(userId).get();
        }catch (Exception e){}
        if(userEntity == null)
            //return  null;
            return false;//return "无该用户";
        VocabEntity vocabEntity = null;
        try {
            vocabEntity = vocabRepository.findById(userVocabType.getVocabId()).get();
        }catch (Exception e){}
        if(vocabEntity == null)
            return false;//return "无该单词";
        UserVocabsEntity userVocabsEntity = null;
        try{
            userVocabsEntity = userVocabsRepository.findByTypeAndUser(userVocabType.getType(),userEntity);
        }catch (Exception e){}
        if(userVocabsEntity == null)
            return false;//return "类型错误";

        List<VocabEntity> vocabEntityList = userVocabsEntity.getVocabs();
        VocabEntity vocabEntity2 = null;
        for(VocabEntity vocabEntity1: vocabEntityList)
        {
            if(vocabEntity.getId() == vocabEntity.getId())
            {
                vocabEntity2 = vocabEntity1;
                break;
            }
        }
        if(vocabEntity2 == null)
            return false;//return "该类型中无此单词";
        userVocabsEntity.deleteVocab(vocabEntity2);
        userVocabsRepository.save(userVocabsEntity);

        return true;//return "删除成功";
    }

    //修改用户的词库
    @PostMapping("/changeVocabs/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public boolean userChangeVocab(@PathVariable Integer userId, @RequestBody UserChangeVocabType userChangeVocabType){
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findById(userId).get();
        }
        catch (Exception e){}
        if(userEntity == null)
            return false;
        BookLearningEntity bookLearningEntity = null;
        try{
            bookLearningEntity = bookLearningRepository.findByUserAndLearned(userEntity,true);
        }catch (Exception e){}
        if(bookLearningEntity == null)
            return false;
        int oldType = -1;
        for(int i = 2; i<5; i++)
        {
            int re = userVocabInType(userId,userChangeVocabType.getVocabId(),i);
            if(re>0)
            {
                oldType = i;
                break;
            }
        }
        if(oldType<0)
            return false;
        if(oldType != userChangeVocabType.getOldType())
            return false;
        int type = userChangeVocabType.getType();
        UserVocabType newUserVocabType = new UserVocabType(userChangeVocabType.getVocabId(),type);
        UserVocabType oldUserVocabType = new UserVocabType(userChangeVocabType.getVocabId(),oldType);
        UserDailyLearningController userDailyLearningController = new UserDailyLearningController(userRepository,userDailyLearningRepository,bookLearningRepository);
        UserDailyLearning userDailyLearning = getDailyLearning(userId);
        if(userDeleteVocab(userId,oldUserVocabType) == true)
        {
            if(oldType == 2)
            {
                bookLearningEntity.subNewVocab();
                userDailyLearningController.setNewVocab(userId,userDailyLearning.getNewVocab()-1);
            }
            else if(oldType == 3)
            {
                bookLearningEntity.subLearning();
                userDailyLearningController.setLearning(userId,userDailyLearning.getLearning()-1);
            }
            else if(oldType == 4)
            {
                bookLearningEntity.subMastered();
                userDailyLearningController.setMastered(userId,userDailyLearning.getMastered()-1);
            }
            else
                return false;
        }else
            return false;
        if(userAddVocab(userId,newUserVocabType))
        {
            if(type == 2)
            {
                bookLearningEntity.addNewVocab();
                userDailyLearningController.setNewVocab(userId,userDailyLearning.getNewVocab()+1);
            }
            else if(type == 3)
            {
                bookLearningEntity.addLearning();
                userDailyLearningController.setLearning(userId,userDailyLearning.getLearning()+1);
            }
            else if(type == 4)
            {
                bookLearningEntity.addMastered();
                userDailyLearningController.setMastered(userId,userDailyLearning.getMastered()+1);
            }
            else
                return false;
        }else
            return false;
        return true;
    }

}
