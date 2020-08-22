package com.example.tinylibrary.repository;

import com.example.tinylibrary.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
  List<Record> findAllByUserId(Long userId);
}
