package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.*;
import com.project.diploma.data.repositories.HeroRepository;
import com.project.diploma.data.repositories.ItemRepository;
import com.project.diploma.data.repositories.UserRepository;
import com.project.diploma.errors.HeroNotFoundException;
import com.project.diploma.services.models.CreateHeroServiceModel;
import com.project.diploma.services.services.HeroService;
import com.project.diploma.services.services.ValidationService;
import com.project.diploma.web.models.BattleModel;
import com.project.diploma.web.models.DetailsHeroModel;
import com.project.diploma.web.models.HeroModel;
import com.project.diploma.web.models.SelectItemsModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
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
    public HeroServiceImpl(HeroRepository heroRepository, UserRepository userRepository, ModelMapper mapper, ValidationService validationService, ItemRepository itemRepository) {
        this.heroRepository = heroRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validationService = validationService;
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean createHero(CreateHeroServiceModel model, String name) throws Exception {
        User user = userRepository.findUserByUsername(name);

        if (user == null) {
            throw new Exception("User does not exists");
        }
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
    public long getCountOfHeroes(String username) {
        List<Hero> heroes = heroRepository.findAll();

        return heroes.stream().filter(e -> e.getUser().getUsername().equals(username)).count();
    }

    @Override
    public List<Hero> getAllUserHeroes(String username) {
        return heroRepository.findAll().stream()
                .filter(e -> e.getUser().getUsername().equals(username)).collect(Collectors.toList());
    }

    @Override
    public HeroModel selectOpponent(String username, String heroName) {
        List<Hero> heroes = heroRepository.findAll().stream()
                .filter(e -> !e.getUser().getUsername().equals(username))
                .sorted(Comparator.comparingInt(Hero::getLevel).thenComparingInt(Hero::getCurrentPoints))
                .collect(Collectors.toList());

        if (heroes.size() == 0) {
            return null;
        }

        Hero hero = heroRepository.findHeroByName(heroName);
        Hero opponent = heroes.get(0);
        int level = opponent.getLevel();
        int currentPoints = opponent.getCurrentPoints();
        for (int i = 1; i < heroes.size(); i++) {
            Hero h = heroes.get(i);
            int diffLevelWithCurrentHero = Math.abs(hero.getLevel() - h.getLevel()) + 1;
            int diffLevelWithOpponentHero = Math.min(diffLevelWithCurrentHero, level);
            if (diffLevelWithOpponentHero < level) {
                level = diffLevelWithCurrentHero;
                opponent = h;
            } else if (diffLevelWithCurrentHero == level) {
                int diffCurrentPointsCurrentHero = Math.abs(hero.getCurrentPoints() - h.getCurrentPoints());
                int diffCurrentPointsOpponentHero = Math.min(diffCurrentPointsCurrentHero, currentPoints);
                if (diffCurrentPointsOpponentHero < currentPoints) {
                    currentPoints = diffCurrentPointsOpponentHero;
                    opponent = h;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return mapper.map(opponent, HeroModel.class);
    }

    @Override
    public HeroModel getMyHero(String name) {
        Hero hero = heroRepository.findHeroByName(name);

        return mapper.map(hero, HeroModel.class);
    }


    @Override
    public DetailsHeroModel detailsHero(String heroName) {
        Hero hero = heroRepository.findHeroByName(heroName);

        if (hero == null) {
            throw new HeroNotFoundException("No such found");
        }

        DetailsHeroModel detail = this.mapper.map(hero, DetailsHeroModel.class);
        List<Item> items = hero.getItems();
        for (Item i : items) {
            Slot slot = i.getSlot();
            switch (slot) {
                case GAUNTLET:
                    List<Item> gauntlets = detail.getGauntlets();
                    gauntlets.add(i);
                    break;
                case HELMET:
                    List<Item> helmets = detail.getHelmets();
                    helmets.add(i);
                    break;
                case PADS:
                    List<Item> pads = detail.getPads();
                    pads.add(i);
                    break;
                case PAULDRON:
                    List<Item> pauldrons = detail.getPauldrons();
                    pauldrons.add(i);
                    break;
                case WEAPON:
                    List<Item> weapons = detail.getWeapons();
                    weapons.add(i);
                    break;
            }
        }
        return detail;
    }

    @Override
    public BattleModel fight(HeroModel myHero,
                             HeroModel opponent,
                             SelectItemsModel myItems,
                             SelectItemsModel opponentItems) {
        int attachMyHero = getPower(myItems, ATTACK);
        int defenceMyHero = getPower(myItems, DEFENCE);
        int staminaMyHero = getPower(myItems, STAMINA);
        int strengthMyHero = getPower(myItems, STRENGTH);

        Hero hero = heroRepository.findHeroByName(myHero.getName());

        attachMyHero += hero.getAttack();
        defenceMyHero += hero.getDefence();
        staminaMyHero += hero.getStamina();
        strengthMyHero += hero.getStrength();

        int attachMyOpponent = getPower(opponentItems, ATTACK);
        int defenceMyOpponent = getPower(opponentItems, DEFENCE);
        int staminaMyOpponent = getPower(opponentItems, STAMINA);
        int strengthMyOpponent = getPower(opponentItems, STRENGTH);

        Hero myOpponentHero = heroRepository.findHeroByName(opponent.getName());

        attachMyOpponent += myOpponentHero.getAttack();
        defenceMyOpponent += myOpponentHero.getDefence();
        staminaMyOpponent += myOpponentHero.getStamina();
        strengthMyOpponent += myOpponentHero.getStrength();

        int damageMyHero = calculateDamageHero(attachMyHero,
                strengthMyHero,
                defenceMyOpponent,
                staminaMyOpponent);

        int damageOpponentHero = calculateDamageHero(attachMyOpponent,
                strengthMyOpponent,
                defenceMyHero,
                staminaMyHero);

        BattleModel model = new BattleModel();

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

    private void setPowersOfBattleModel(Hero hero, BattleModel model) {
        model.setAttack(hero.getLevel());
        model.setStamina(hero.getLevel());
        model.setStrength(hero.getLevel());
        model.setDefence(hero.getLevel());
    }

    private int calculateDamageHero(int attachMyHero, int strengthMyHero, int defenceMyOpponent, int staminaMyOpponent) {
        return attachMyHero + (strengthMyHero * 4) -
                (defenceMyOpponent + (staminaMyOpponent * 2));
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
            int goldForMyUser = points / 2;
            userOfMyHero.setGold(userOfMyHero.getGold() + goldForMyUser);
            model.setGold(goldForMyUser);
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
        if (items.getHelmet() != null) {
            sum = getSum(power, sum, items.getHelmet());
        }
        if (items.getGauntlets() != null) {
            sum = getSum(power, sum, items.getGauntlets());
        }
        if (items.getPads() != null) {
            sum = getSum(power, sum, items.getPads());
        }
        if (items.getPauldron() != null) {
            sum = getSum(power, sum, items.getPauldron());
        }
        if (items.getWeapon() != null) {
            sum = getSum(power, sum, items.getWeapon());
        }
        return sum;
    }

    private int getSum(String slot, int sum, String name) {
        switch (slot) {
            case ATTACK:
                sum += itemRepository.findByName(name).getAttack();
                break;
            case DEFENCE:
                sum += itemRepository.findByName(name).getDefence();
                break;
            case STAMINA:
                sum += itemRepository.findByName(name).getStamina();
                break;
            case STRENGTH:
                sum += itemRepository.findByName(name).getStrength();
                break;
        }
        return sum;
    }

//    @Override
//    public String fight(String heroName, String guest) {
//
//        Hero homeHero = heroRepository.findHeroByName(heroName).get();
//        Hero guestHero = heroRepository.findHeroByName(guest).get();
//
//        int damageHeroHome = homeHero.getAttack() + (homeHero.getStrength() * 4) -
//                (guestHero.getDefence() + (guestHero.getStamina() * 2));
//
//        int damageHeroGuest = guestHero.getAttack() + (guestHero.getStrength() * 4) -
//                homeHero.getDefence() + (homeHero.getStamina() * 2);
//
//        if (damageHeroHome > damageHeroGuest) {
//            setNewValueHero(homeHero);
//            return homeHero.getName();
//        } else if (damageHeroGuest > damageHeroHome){
//            setNewValueHero(guestHero);
//            return guestHero.getName();
//        } else {
//            return "draw";
//        }
//    }

    @ExceptionHandler(HeroNotFoundException.class)
    public ModelAndView heroException(HeroNotFoundException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("message", ex.getMessage());
        return model;
    }
}
