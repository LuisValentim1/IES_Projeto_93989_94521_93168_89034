package pt.ua.Blossom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ua.Blossom.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
