package com.project.diploma.data.repository;

import com.project.diploma.data.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByName(String name);

    boolean existsItemByName(String name);
}
