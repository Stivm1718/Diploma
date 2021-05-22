package com.project.diploma.data.repositories;

import com.project.diploma.data.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByName(String name);

    boolean existsItemByName(String name);
}
