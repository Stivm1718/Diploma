package com.project.diploma.services.services;

import com.project.diploma.services.models.CreateItemServiceModel;
import com.project.diploma.web.models.ViewItemModel;

import java.util.List;

public interface ItemService {

    boolean create(CreateItemServiceModel model) throws Exception;

    List<ViewItemModel> takeAllItemsThatAreNotThere(String heroName);

    boolean addItemToHero(String heroName, String itemName) throws Exception;
}
