package com.project.diploma.service.services;

import com.project.diploma.service.models.CreateItemServiceModel;
import com.project.diploma.web.models.ViewItemModel;

import java.util.List;

public interface ItemService {

    boolean create(CreateItemServiceModel model) throws Exception;

    List<ViewItemModel> takeAllItemsThatAreNotThere(String heroName);

    //void addItem(String heroName, String itemName) throws Exception;
}
