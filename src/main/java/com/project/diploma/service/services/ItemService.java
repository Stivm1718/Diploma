package com.project.diploma.service.services;

import com.project.diploma.service.models.CreateItemServiceModel;

import java.util.List;

public interface ItemService {

    void create(CreateItemServiceModel model) throws Exception;

    List<CreateItemServiceModel> getByHeroName(String heroName);

    void addItem(String heroName, String itemName) throws Exception;
}
