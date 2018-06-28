package bs.repositories;

import bs.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//CrudRespository定义了增删改查方法
//数据访问层接口
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}
