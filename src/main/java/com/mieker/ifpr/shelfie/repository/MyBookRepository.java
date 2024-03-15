package com.mieker.ifpr.shelfie.repository;

import com.mieker.ifpr.shelfie.entity.MyBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MyBookRepository extends JpaRepository<MyBooks, UUID> {

}
