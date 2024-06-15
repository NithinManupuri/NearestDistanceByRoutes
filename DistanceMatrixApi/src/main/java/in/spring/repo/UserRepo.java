package in.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.spring.entity.User;

public interface UserRepo extends JpaRepository<User,Integer>{

}
