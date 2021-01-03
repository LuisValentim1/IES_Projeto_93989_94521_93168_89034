package com.ies.blossom.repositorys;

import java.util.List;

import com.ies.blossom.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    // TODO posso ter que usar limit
    @Query(value = "SELECT * FROM users WHERE role <> 'admin' ORDER BY entry_date DESC", nativeQuery=true)
    public List<User> findAllUsersNotAdmin();
}
