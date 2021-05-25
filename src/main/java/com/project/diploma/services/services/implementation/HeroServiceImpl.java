package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.*;
import com.project.diploma.data.repositories.HeroRepository;
import com.project.diploma.data.repositories.UserRepository;
import com.project.diploma.errors.HeroNotFoundException;
import com.project.diploma.services.models.CreateHeroServiceModel;
import com.project.diploma.services.services.HeroService;
import com.project.diploma.services.services.ValidationService;
import com.project.diploma.web.models.DetailsHeroModel;
import com.project.diploma.web.models.HeroModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ValidationService validationService;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository, UserRepository userRepository,  ModelMapper mapper, ValidationService validationService) {
        this.heroRepository = heroRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validationService = validationService;
    }

    @Override
    public boolean createHero(CreateHeroServiceModel model, String name) throws Exception {
        User user = userRepository.findUserByUsername(name);

        if (user == null) {
            throw new Exception("User does not exists");
        }
        if (validationService.isValidHeroName(model)){
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
                .collect(Collectors.toList());

        Hero hero = heroRepository.findHeroByName(heroName);
        Hero opponent = null;
        int level = Integer.MAX_VALUE;
        int currentPoints = Integer.MAX_VALUE;
        for (Hero h : heroes) {
            int diffLevelWithCurrentHero = Math.abs(hero.getLevel() - h.getLevel());
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
                }
            }
        }
        return opponent == null ? null : mapper.map(opponent, HeroModel.class);
    }

    @Override
    public HeroModel getHero(String name) {
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
