package com.haktanozgur.PsychologistApp.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.haktanozgur.PsychologistApp.Entity.*;


public interface UserRepository extends JpaRepository<User, Long>{

	Optional <User> findByUsername(String userName);
}
