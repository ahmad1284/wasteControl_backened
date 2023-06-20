package com.example.wasteControl.repo;

import com.example.wasteControl.model.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplainRepo  extends JpaRepository<Complain, Integer> {
}
