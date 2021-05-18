package com.project.diploma.service.services.implementation;

import com.project.diploma.data.models.*;
import com.project.diploma.data.repository.HeroRepository;
import com.project.diploma.data.repository.UserRepository;
import com.project.diploma.error.HeroNotFoundException;
import com.project.diploma.service.models.CreateHeroServiceModel;
import com.project.diploma.service.services.HeroService;
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

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository, UserRepository userRepository, ModelMapper mapper) {
        this.heroRepository = heroRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void createHero(CreateHeroServiceModel model, String name) throws Exception {
        User user = userRepository.findUserByUsername(name);

        if (user == null){
            throw new Exception("User does not exists");
        }

        Hero hero = this.mapper.map(model, Hero.class);

        hero.setGender(model.getGender());
        hero.setAttack(Point.ATTACK.getValue());
        hero.setDefence(Point.DEFENCE.getValue());
        hero.setLevel(Point.GOLD.getValue());
        hero.setStamina(Point.STAMINA.getValue());
        hero.setStrength(Point.STRENGTH.getValue());
        hero.setCurrentPoints(Point.STRENGTH.getValue());
        hero.setMaxPoints(Point.MAX_POINTS.getValue());
        hero.setUser(user);
        user.getHeroes().add(hero);
        userRepository.saveAndFlush(user);
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
    public Hero selectOpponent(String username, String heroName) {
        List<Hero> heroes = heroRepository.findAll().stream()
                .filter(e -> !e.getUser().getUsername().equals(username)).collect(Collectors.toList());

        Hero hero = heroRepository.findHeroByName(heroName);
        Hero opponent = null;
        int level = Integer.MAX_VALUE;
        int currentPoints = Integer.MAX_VALUE;
        for(Hero h : heroes){
            int diffLevelWithCurrentHero = Math.abs(hero.getLevel() - h.getLevel());
            int diffLevelWithOpponentHero = Math.min(diffLevelWithCurrentHero, level);
            if (diffLevelWithOpponentHero < level){
                level = diffLevelWithCurrentHero;
                opponent = h;
            } else if (diffLevelWithCurrentHero == level){
                int diffCurrentPointsCurrentHero = Math.abs(hero.getCurrentPoints() - h.getCurrentPoints());
                int diffCurrentPointsOpponentHero = Math.min(diffCurrentPointsCurrentHero, currentPoints);
                if (diffCurrentPointsOpponentHero < currentPoints){
                    currentPoints = diffCurrentPointsOpponentHero;
                    opponent = h;
                }
            }
        }
        return opponent;
    }

//    @Override
//    public DetailsHeroServiceModel findHero(String heroName) {
//        Hero hero = heroRepository.findHeroByName(heroName).orElseThrow(() -> new HeroNotFoundException("No such found"));
//
//        if (hero == null) {
//            throw new HeroNotFoundException("No such found");
//        }
//
//        DetailsHeroServiceModel detail = this.mapper.map(hero, DetailsHeroServiceModel.class);
//        detail.setGauntlets(getItemBySlot(hero.getItems(), Slot.GAUNTLETS));
//        detail.setHelmet(getItemBySlot(hero.getItems(), Slot.HELMET));
//        detail.setPads(getItemBySlot(hero.getItems(), Slot.PADS));
//        detail.setWeapon(getItemBySlot(hero.getItems(), Slot.WEAPON));
//        detail.setPauldron(getItemBySlot(hero.getItems(), Slot.PAULDRON));
//
//        return detail;
//    }

//    private Item getItemBySlot(List<Item> items, Slot weapon) {
//        return items.stream().filter(a -> a.getSlot().name().equals(weapon.name())).findFirst().orElse(null);
//    }

//    @Override
//    public List<DetailsHeroServiceModel> getAllWithoutCurrent(String heroName) {
//        return heroRepository.findAll().stream()
//                .filter(n -> !n.getName().equals(heroName))
//                .map(m -> mapper.map(m, DetailsHeroServiceModel.class))
//                .collect(Collectors.toList());
//    }

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

//    @Override
//    public HeroModel findNameAndGenderHero(String name) {
//        return mapper.map(heroRepository.findHeroByName(name), HeroModel.class);
//    }
//
//    @Override
//    public DetailsHeroServiceModel getByUsername(String name) {
//        Optional<Hero> hero = heroRepository.findHeroByName(name);
//
//        if (hero.isEmpty()) {
//            throw new HeroNotFoundException("No such found");
//        }
//
//        return mapper.map(hero.get(), DetailsHeroServiceModel.class);
//    }
//
//    @Override
//    public String getHeroNameByUsername(String username){
//        User user = userRepository.findUserByUsername(username);
//        return heroRepository.findHeroByName(user.getHero().getName()).get().getName();
//    }

//    private void setNewValueHero(Hero hero) {
//        hero.setLevel(hero.getLevel() + 1);
//        hero.setStrength(hero.getStrength() + 5);
//        hero.setStamina(hero.getStamina() + 5);
//        heroRepository.saveAndFlush(hero);
//    }

    @ExceptionHandler(HeroNotFoundException.class)
    public ModelAndView heroException(HeroNotFoundException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("message", ex.getMessage());
        return model;
    }
}
