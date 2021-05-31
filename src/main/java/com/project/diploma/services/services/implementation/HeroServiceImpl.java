package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.*;
import com.project.diploma.data.repositories.HeroRepository;
import com.project.diploma.data.repositories.ItemRepository;
import com.project.diploma.data.repositories.UserRepository;
import com.project.diploma.services.models.CreateHeroServiceModel;
import com.project.diploma.services.services.HeroService;
import com.project.diploma.services.services.ValidationService;
import com.project.diploma.web.models.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class HeroServiceImpl implements HeroService {

    private static final String ATTACK = "attack";
    private static final String DEFENCE = "defence";
    private static final String STAMINA = "stamina";
    private static final String STRENGTH = "strength";
    private static final String WIN = "win";
    private static final String DRAW = "draw";
    private static final String DEFEAT = "defeat";

    private final HeroRepository heroRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ValidationService validationService;
    private final ItemRepository itemRepository;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository,
                           UserRepository userRepository,
                           ModelMapper mapper,
                           ValidationService validationService,
                           ItemRepository itemRepository) {
        this.heroRepository = heroRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validationService = validationService;
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean createHero(CreateHeroServiceModel model, String name) throws Exception {
        User user = userRepository.findUserByUsername(name);

        if (validationService.isValidHeroName(model)) {
            return false;
        }

        Hero hero = this.mapper.map(model, Hero.class);

        hero.setGender(model.getGender());
        hero.setAttack(Point.ATTACK.getValue());
        hero.setDefence(Point.DEFENCE.getValue());
        hero.setLevel(Point.LEVEL.getValue());
        hero.setStamina(Point.STAMINA.getValue());
        hero.setStrength(Point.STRENGTH.getValue());
        hero.setCurrentPoints(Point.CURRENT_POINTS.getValue());
        hero.setMaxPoints(Point.MAX_POINTS.getValue());
        hero.setBattles(Point.BATTLES.getValue());
        hero.setWins(Point.WINS.getValue());
        hero.setUser(user);
        user.getHeroes().add(hero);
        userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public DetailsHeroModel detailsHero(String heroName) {
        Hero hero = heroRepository.findHeroByName(heroName);

        DetailsHeroModel details = this.mapper.map(hero, DetailsHeroModel.class);
        for (Item i : hero.getItems()) {
            Slot slot = i.getSlot();
            switch (slot) {
                case GAUNTLET:
                    details.getGauntlets().add(mapper.map(i, DetailsItemModel.class));
                    break;
                case HELMET:
                    details.getHelmets().add(mapper.map(i, DetailsItemModel.class));
                    break;
                case PADS:
                    details.getPads().add(mapper.map(i, DetailsItemModel.class));
                    break;
                case PAULDRON:
                    details.getPauldrons().add(mapper.map(i, DetailsItemModel.class));
                    break;
                case WEAPON:
                    details.getWeapons().add(mapper.map(i, DetailsItemModel.class));
                    break;
            }
        }
        return details;
    }

    @Override
    public int getCountOfHeroes(String username) {
        return userRepository.findUserByUsername(username).getHeroes().size();
    }

    @Override
    public List<HeroPictureModel> getAllUserHeroes(String username) {
        return heroRepository
                .findAll()
                .stream()
                .filter(e -> e.getUser().getUsername().equals(username))
                .map(h -> mapper.map(h, HeroPictureModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public HeroPictureModel selectOpponent(String username, String heroName) {
        Hero hero = heroRepository.findHeroByName(heroName);

        List<Hero> heroes = heroRepository
                .findAll()
                .stream()
                .filter(u -> !u.getUser().getUsername().equals(username))
                .filter(h -> (h.getLevel() >= hero.getLevel() - 5) || (h.getLevel() <= hero.getLevel() + 5))
                .collect(Collectors.toList());

        if (heroes.size() == 0) {
            return null;
        }

        Random random = new Random();
        Hero opponent = heroes.get(random.nextInt(heroes.size()));
        return mapper.map(opponent, HeroPictureModel.class);
    }

    @Override
    public HeroPictureModel getMyHero(String name) {
        Hero hero = heroRepository.findHeroByName(name);

        return mapper.map(hero, HeroPictureModel.class);
    }

    @Override
    public BattleModel fight(HeroPictureModel myHero,
                             HeroPictureModel opponent,
                             SelectItemsModel myItems,
                             SelectItemsModel opponentItems) {
        BattleModel model = new BattleModel();

        int attachMyHero = getPower(myItems, ATTACK);
        int defenceMyHero = getPower(myItems, DEFENCE);
        int staminaMyHero = getPower(myItems, STAMINA);
        int strengthMyHero = getPower(myItems, STRENGTH);

        Hero hero = heroRepository.findHeroByName(myHero.getName());

        attachMyHero += hero.getAttack();
        model.setMyAttack(attachMyHero);
        defenceMyHero += hero.getDefence();
        model.setMyDefence(defenceMyHero);
        staminaMyHero += hero.getStamina();
        model.setMyStamina(staminaMyHero);
        strengthMyHero += hero.getStrength();
        model.setMyStrength(strengthMyHero);

        int attachMyOpponent = getPower(opponentItems, ATTACK);
        int defenceMyOpponent = getPower(opponentItems, DEFENCE);
        int staminaMyOpponent = getPower(opponentItems, STAMINA);
        int strengthMyOpponent = getPower(opponentItems, STRENGTH);

        Hero myOpponentHero = heroRepository.findHeroByName(opponent.getName());

        attachMyOpponent += myOpponentHero.getAttack();
        model.setOpponentAttack(attachMyOpponent);
        defenceMyOpponent += myOpponentHero.getDefence();
        model.setOpponentDefence(defenceMyOpponent);
        staminaMyOpponent += myOpponentHero.getStamina();
        model.setOpponentStamina(staminaMyOpponent);
        strengthMyOpponent += myOpponentHero.getStrength();
        model.setOpponentStrength(strengthMyOpponent);

        int damageMyHero = calculateDamageHero(attachMyHero,
                strengthMyHero,
                defenceMyOpponent,
                staminaMyOpponent);
        model.setMyResult(damageMyHero);

        int damageOpponentHero = calculateDamageHero(attachMyOpponent,
                strengthMyOpponent,
                defenceMyHero,
                staminaMyHero);
        model.setOpponentResult(damageOpponentHero);

        if (damageMyHero > damageOpponentHero) {
            setResultAfterBattle(model, WIN);
            increasePowerOfHeroAndBattles(hero);

            setPowersOfBattleModel(hero, model);

            User userOfMyHero = hero.getUser();

            increasePointsAndGoldOfMyHeroInWin(hero, model, userOfMyHero);

            userRepository.saveAndFlush(userOfMyHero);

            increaseLevelOfMyHero(hero, model);
            heroRepository.saveAndFlush(hero);

            User userOfOpponentHero = myOpponentHero.getUser();
            userOfOpponentHero.setGold(userOfOpponentHero.getGold() + myOpponentHero.getLevel());
            userRepository.saveAndFlush(userOfOpponentHero);

            setBattlesAndPointsOfDefeatedHero(myOpponentHero);

            increaseLevelOfMyOpponentHero(myOpponentHero);
            heroRepository.saveAndFlush(myOpponentHero);
        } else if (damageMyHero < damageOpponentHero) {
            setResultAfterBattle(model, DEFEAT);

            increasePowerOfHeroAndBattles(myOpponentHero);

            User userOfOpponentHero = myOpponentHero.getUser();

            increasePointsAndGoldOfMyOpponentInWin(myOpponentHero, userOfOpponentHero);

            userRepository.saveAndFlush(userOfOpponentHero);

            increaseLevelOfMyOpponentHero(myOpponentHero);

            heroRepository.saveAndFlush(myOpponentHero);

            User userOfMyHero = hero.getUser();
            userOfMyHero.setGold(userOfMyHero.getGold() + hero.getLevel());
            userRepository.saveAndFlush(userOfMyHero);

            model.setGold(hero.getLevel());

            setBattlesAndPointsOfDefeatedHero(hero);
            model.setPointsEarned(hero.getLevel());

            increaseLevelOfMyHero(hero, model);
            heroRepository.saveAndFlush(hero);
        } else {
            setResultAfterBattle(model, DRAW);
            hero.setBattles(hero.getBattles() + 1);

            User userOfMyHero = hero.getUser();
            increaseMyPointsAndGoldOfMyHeroInDraw(hero, model, userOfMyHero);
            userRepository.saveAndFlush(userOfMyHero);

            increaseLevelOfMyHero(hero, model);
            heroRepository.saveAndFlush(hero);

            myOpponentHero.setBattles(myOpponentHero.getBattles() + 1);

            User userOfOpponentHero = myOpponentHero.getUser();
            increasePointsAndGoldOfMyOpponentInDraw(myOpponentHero, userOfOpponentHero);

            userRepository.saveAndFlush(userOfOpponentHero);

            increaseLevelOfMyOpponentHero(myOpponentHero);
            heroRepository.saveAndFlush(myOpponentHero);
        }
        return model;
    }

    @Override
    public List<RankingModel> getSortedHeroes() {
        return heroRepository
                .findAll()
                .stream()
                .sorted((a, b) -> {
                    int diff = b.getLevel() - a.getLevel();
                    if (diff == 0){
                        diff = b.getCurrentPoints() - a.getCurrentPoints();
                        if (diff == 0){
                            diff = a.getName().compareTo(b.getName());
                        }
                    }
                    return diff;
                })
                .map(h -> mapper.map(h, RankingModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public int sellItem(String nameHero, String nameItem) {
        Hero hero = heroRepository.findHeroByName(nameHero);
        Item item = itemRepository.getItemByName(nameItem);
        User user = userRepository.findUserByUsername(hero.getUser().getUsername());

        hero.getItems().remove(item);
        item.getHeroes().remove(hero);

        int gold = 0;
        if (item.getPriceInGold() != null){
            int itemGold = item.getPriceInGold() / 4;
            int userGold = user.getGold();
            user.setGold(userGold + itemGold);
            gold = user.getGold();
            userRepository.saveAndFlush(user);
        }

        heroRepository.saveAndFlush(hero);
        itemRepository.saveAndFlush(item);
        return gold;
    }

    private void setPowersOfBattleModel(Hero hero, BattleModel model) {
        model.setAttack(hero.getLevel());
        model.setStamina(hero.getLevel());
        model.setStrength(hero.getLevel());
        model.setDefence(hero.getLevel());
    }

    private int calculateDamageHero(int attack,
                                    int strength,
                                    int defence,
                                    int stamina) {
        return attack + (strength * 4) -
                (defence + (stamina * 2));
    }

    private void setBattlesAndPointsOfDefeatedHero(Hero hero) {
        hero.setBattles(hero.getBattles() + 1);
        hero.setCurrentPoints(hero.getCurrentPoints() + hero.getLevel());
    }

    private void increasePointsAndGoldOfMyOpponentInWin(Hero myOpponentHero, User userOfOpponentHero) {
        if (myOpponentHero.getLevel() < 9) {
            myOpponentHero.setCurrentPoints(myOpponentHero.getCurrentPoints() +
                    10 + myOpponentHero.getLevel());
            int goldForMyUser = userOfOpponentHero.getGold() + ((10 + myOpponentHero.getLevel()) / 2);
            userOfOpponentHero.setGold(goldForMyUser);
        } else {
            myOpponentHero.setCurrentPoints(myOpponentHero.getCurrentPoints() + (2 * myOpponentHero.getLevel()));
            userOfOpponentHero.setGold(userOfOpponentHero.getGold() + myOpponentHero.getLevel());
        }
    }

    private void increasePointsAndGoldOfMyOpponentInDraw(Hero myOpponentHero, User userOfOpponentHero) {
        if (myOpponentHero.getLevel() < 9) {
            myOpponentHero.setCurrentPoints(myOpponentHero.getCurrentPoints() +
                    5 + myOpponentHero.getLevel());
            int goldForMyUser = userOfOpponentHero.getGold() + ((5 + myOpponentHero.getLevel()) / 2);
            userOfOpponentHero.setGold(goldForMyUser);
        } else {
            myOpponentHero.setCurrentPoints(myOpponentHero.getCurrentPoints() + myOpponentHero.getLevel());
            int goldForMyUser = myOpponentHero.getLevel() / 2;
            userOfOpponentHero.setGold(userOfOpponentHero.getGold() + goldForMyUser);
        }
    }

    private void increaseMyPointsAndGoldOfMyHeroInDraw(Hero hero, BattleModel model, User userOfMyHero) {
        if (hero.getLevel() < 9) {
            int points = 5 + hero.getLevel();
            model.setPointsEarned(points);
            hero.setCurrentPoints(hero.getCurrentPoints() + points);
            int gold = points / 2;
            userOfMyHero.setGold(userOfMyHero.getGold() + gold);
            model.setGold(gold);
        } else {
            model.setPointsEarned(hero.getLevel());
            hero.setCurrentPoints(hero.getCurrentPoints() + hero.getLevel());
            int gold = hero.getLevel() / 2;
            userOfMyHero.setGold(userOfMyHero.getGold() + gold);
            model.setGold(gold);
        }
    }

    private void increasePointsAndGoldOfMyHeroInWin(Hero hero, BattleModel model, User userOfMyHero) {
        if (hero.getLevel() < 9) {
            int points = 10 + hero.getLevel();
            model.setPointsEarned(points);
            hero.setCurrentPoints(hero.getCurrentPoints() + points);
            int gold = points / 2;
            userOfMyHero.setGold(userOfMyHero.getGold() + gold);
            model.setGold(gold);
        } else {
            int points = 2 * hero.getLevel();
            model.setPointsEarned(points);
            hero.setCurrentPoints(hero.getCurrentPoints() + points);
            userOfMyHero.setGold(userOfMyHero.getGold() + hero.getLevel());
            model.setGold(hero.getLevel());
        }
    }

    private void increaseLevelOfMyOpponentHero(Hero myOpponentHero) {
        if (myOpponentHero.getCurrentPoints().equals(myOpponentHero.getMaxPoints())) {
            myOpponentHero.setCurrentPoints(0);
            myOpponentHero.setMaxPoints(myOpponentHero.getMaxPoints() + 100);
            myOpponentHero.setLevel(myOpponentHero.getLevel() + 1);
        } else if (myOpponentHero.getCurrentPoints() > myOpponentHero.getMaxPoints()) {
            myOpponentHero.setCurrentPoints(myOpponentHero.getCurrentPoints() - myOpponentHero.getMaxPoints());
            myOpponentHero.setMaxPoints(myOpponentHero.getMaxPoints() + 100);
            myOpponentHero.setLevel(myOpponentHero.getLevel() + 1);
        }
    }

    private void increaseLevelOfMyHero(Hero hero, BattleModel model) {
        if (hero.getCurrentPoints().equals(hero.getMaxPoints())) {
            model.setIsRaisedLevel(true);
            hero.setCurrentPoints(0);
            hero.setMaxPoints(hero.getMaxPoints() + 100);
            hero.setLevel(hero.getLevel() + 1);
            model.setLevel(hero.getLevel());
        } else if (hero.getCurrentPoints() > hero.getMaxPoints()) {
            model.setIsRaisedLevel(true);
            hero.setCurrentPoints(hero.getCurrentPoints() - hero.getMaxPoints());
            hero.setMaxPoints(hero.getMaxPoints() + 100);
            hero.setLevel(hero.getLevel() + 1);
            model.setLevel(hero.getLevel());
        }
    }

    private void increasePowerOfHeroAndBattles(Hero hero) {
        hero.setAttack(hero.getAttack() + hero.getLevel());
        hero.setStamina(hero.getStamina() + hero.getLevel());
        hero.setStrength(hero.getStrength() + hero.getLevel());
        hero.setDefence(hero.getDefence() + hero.getLevel());
        hero.setBattles(hero.getBattles() + 1);
        hero.setWins(hero.getWins() + 1);
    }

    private void setResultAfterBattle(BattleModel model, String result) {
        model.setResult(result);
    }

    private int getPower(SelectItemsModel items, String power) {
        int sum = 0;
        sum = checkIfItIsNotNull(power, sum, items.getHelmet());
        sum = checkIfItIsNotNull(power, sum, items.getGauntlets());
        sum = checkIfItIsNotNull(power, sum, items.getPads());
        sum = checkIfItIsNotNull(power, sum, items.getPauldron());
        sum = checkIfItIsNotNull(power, sum, items.getWeapon());
        return sum;
    }

    private int checkIfItIsNotNull(String power, int sum, String slot) {
        if (slot != null) {
            sum = getSum(power, sum, slot);
        }
        return sum;
    }

    private int getSum(String slot, int sum, String name) {
        switch (slot) {
            case ATTACK:
                sum += itemRepository.getItemByName(name).getAttack();
                break;
            case DEFENCE:
                sum += itemRepository.getItemByName(name).getDefence();
                break;
            case STAMINA:
                sum += itemRepository.getItemByName(name).getStamina();
                break;
            case STRENGTH:
                sum += itemRepository.getItemByName(name).getStrength();
                break;
        }
        return sum;
    }
}
