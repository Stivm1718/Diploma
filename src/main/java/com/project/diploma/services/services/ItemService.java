package com.project.diploma.services.services;

import com.project.diploma.data.models.Pay;
import com.project.diploma.services.models.CreateItemServiceModel;
import com.project.diploma.web.models.ShowItemsHero;
import com.project.diploma.web.models.ViewItemModel;
import com.project.diploma.web.models.ViewItemModelWithTypePay;

import java.util.List;

public interface ItemService {

    boolean create(CreateItemServiceModel model) throws Exception;

    List<ViewItemModel> takeAllItemsThatAreNotThere(String heroName);

    void addHeroItemForAdmin(String heroName, String itemName) throws Exception;

    ShowItemsHero getItemsOfHero(String heroName);

    List<ViewItemModelWithTypePay> takeItemWithGoldForPay(String heroName);

    List<ViewItemModelWithTypePay> takeItemWithMoneyForPay(String heroName);

    Pay getWayToPay(String name);

    boolean buyItemWithGold(String heroName, String name);

    boolean existItem(String name);
}
