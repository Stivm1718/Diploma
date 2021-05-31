package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.*;
import com.project.diploma.data.repositories.HeroRepository;
import com.project.diploma.data.repositories.ItemRepository;
import com.project.diploma.data.repositories.UserRepository;
import com.project.diploma.services.models.CreateItemServiceModel;
import com.project.diploma.services.services.HeroService;
import com.project.diploma.services.services.ItemService;
import com.project.diploma.services.services.ValidationService;
import com.project.diploma.web.models.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ModelMapper mapper;
    private final ItemRepository itemRepository;
    private final HeroRepository heroRepository;
    private final ValidationService validationService;
    private final HeroService heroService;
    private final UserRepository userRepository;

    @Autowired
    public ItemServiceImpl(ModelMapper mapper, ItemRepository itemRepository, HeroRepository heroRepository, ValidationService validationService, HeroService heroService, UserRepository userRepository) {
        this.mapper = mapper;
        this.itemRepository = itemRepository;
        this.heroRepository = heroRepository;
        this.validationService = validationService;
        this.heroService = heroService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean create(CreateItemServiceModel model) throws Exception {
        if (validationService.isValidItemName(model)) {
            return false;
        }

        if ((model.getPriceInGold() == null && model.getPriceInMoney() == null) ||
                (model.getPriceInGold() != null && model.getPriceInMoney() != null)) {
            return false;
        }

        itemRepository.saveAndFlush(mapper.map(model, Item.class));
        return true;
    }

    @Override
    public List<ViewItemModel> takeAllItemsThatAreNotThere(String heroName) {
        return this.itemRepository.findAll()
                .stream()
                .filter(i -> !i.getHeroes().stream()
                        .map(Hero::getName)
                        .collect(Collectors.toList()).contains(heroName))
                .map(u -> this.mapper.map(u, ViewItemModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addHeroItemForAdmin(String heroName, String itemName) {
        Hero hero = heroRepository.findHeroByName(heroName);
        Item item = itemRepository.getItemByName(itemName);
        insertItemAndHeroInDatabase(hero, item);
    }

    @Override
    public ShowItemsHero getItemsOfHero(String heroName) {
        DetailsHeroModel details = heroService.detailsHero(heroName);
        return mapper.map(details, ShowItemsHero.class);
    }

    @Override
    public List<ViewItemModelWithTypePay> takeItemWithGoldForPay(String heroName) {
        return itemRepository
                .findAll()
                .stream()
                .filter(i -> i.getPay().equals(Pay.GOLD))
                .filter(i -> !i.getHeroes().stream()
                        .map(Hero::getName)
                        .collect(Collectors.toList()).contains(heroName))
                .sorted(Comparator.comparingInt(Item::getLevel))
                .map(u -> this.mapper.map(u, ViewItemModelWithTypePay.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewItemModelWithTypePay> takeItemWithMoneyForPay(String heroName) {
        return itemRepository
                .findAll()
                .stream()
                .filter(i -> i.getPay().equals(Pay.MONEY))
                .filter(i -> !i.getHeroes().stream()
                        .map(Hero::getName)
                        .collect(Collectors.toList()).contains(heroName))
                .sorted(Comparator.comparingInt(Item::getLevel))
                .map(u -> this.mapper.map(u, ViewItemModelWithTypePay.class))
                .collect(Collectors.toList());
    }

    @Override
    public Pay getWayToPay(String name) {
        return itemRepository.getItemByName(name).getPay();
    }

    @Override
    public boolean buyItemWithGold(String heroName, String itemName) {
        Hero hero = heroRepository.findHeroByName(heroName);
        User user = hero.getUser();
        Item item = itemRepository.getItemByName(itemName);

        if (item.getPriceInGold() <= user.getGold()) {
            insertItemAndHeroInDatabase(hero, item);
            user.setGold(user.getGold() - item.getPriceInGold());
            userRepository.saveAndFlush(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean existItem(String name) {
        return itemRepository.existsItemByName(name);
    }

    @Override
    public SelectItemsModel getTheBestItemsOfOpponent(String nameHero) {
        List<Item> items = heroRepository.findHeroByName(nameHero).getItems();

        String weaponName = selectTheBestWeapon(items, Slot.WEAPON);
        String helmetName = selectTheBestWeapon(items, Slot.HELMET);
        String pauldronName = selectTheBestWeapon(items, Slot.PAULDRON);
        String padsName = selectTheBestWeapon(items, Slot.PADS);
        String gauntletName = selectTheBestWeapon(items, Slot.GAUNTLET);

        SelectItemsModel model = new SelectItemsModel();
        model.setGauntlets(gauntletName);
        getNamePictureGauntlets(model, gauntletName);

        model.setHelmet(helmetName);
        getNamePictureHelmet(model, helmetName);

        model.setPads(padsName);
        getNamePicturePads(model, padsName);

        model.setPauldron(pauldronName);
        getNamePicturePauldron(model, pauldronName);

        model.setWeapon(weaponName);
        getNamePictureWeapon(model, weaponName);

        return model;
    }

    @Override
    public void getNamesPictureItems(SelectItemsModel model) {
        getNamePictureHelmet(model, model.getHelmet());

        getNamePictureWeapon(model, model.getWeapon());

        getNamePicturePads(model, model.getPads());

        getNamePicturePauldron(model, model.getPauldron());

        getNamePictureGauntlets(model, model.getGauntlets());
    }

    private void getNamePictureGauntlets(SelectItemsModel model, String gauntlets) {
        if (gauntlets != null) {
            model.setItemPictureGauntlets(itemRepository.getItemByName(gauntlets).getItemPicture());
        }
    }

    private void getNamePicturePauldron(SelectItemsModel model, String pauldron) {
        if (pauldron != null) {
            model.setItemPicturePauldron(itemRepository.getItemByName(pauldron).getItemPicture());
        }
    }

    private void getNamePicturePads(SelectItemsModel model, String pads) {
        if (pads != null) {
            model.setItemPicturePads(itemRepository.getItemByName(pads).getItemPicture());
        }
    }

    private void getNamePictureWeapon(SelectItemsModel model, String weapon) {
        if (weapon != null) {
            model.setItemPictureWeapon(itemRepository.getItemByName(weapon).getItemPicture());
        }
    }

    private void getNamePictureHelmet(SelectItemsModel model, String helmet) {
        if (helmet != null) {
            model.setItemPictureHelmet(itemRepository.getItemByName(helmet).getItemPicture());
        }
    }

    private String selectTheBestWeapon(List<Item> items, Slot slot) {
        List<Item> result = items
                .stream()
                .filter(i -> i.getSlot().equals(slot))
                .sorted((a, b) -> {
                    int powerA = a.getStamina() + a.getAttack() + a.getStrength() + a.getDefence();
                    int powerB = b.getStamina() + b.getAttack() + b.getStrength() + b.getDefence();
                    return powerB - powerA;
                })
                .collect(Collectors.toList());

        return result.size() == 0 ? null : result.get(0).getName();
    }

    private void insertItemAndHeroInDatabase(Hero hero, Item item) {
        hero.getItems().add(item);
        item.getHeroes().add(hero);
        heroRepository.saveAndFlush(hero);
        itemRepository.saveAndFlush(item);
    }
}
