package com.project.diploma.data.repositories;

import com.project.diploma.data.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsItemByName(String name);

    Item getItemByName(String name);
}
