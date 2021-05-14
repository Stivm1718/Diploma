package com.project.diploma.service.services.implementation;

import com.project.diploma.data.models.*;
import com.project.diploma.data.repository.HeroRepository;
import com.project.diploma.data.repository.UserRepository;
import com.project.diploma.error.HeroNotFoundException;
import com.project.diploma.service.models.CreateHeroServiceModel;
import com.project.diploma.service.models.DetailsHeroServiceModel;
import com.project.diploma.service.services.HeroService;
import com.project.diploma.web.models.HeroModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public HeroServiceImpl(HeroRepository heroRepository, UserRepository userRepository, ModelMapper mapper) {
        this.heroRepository = heroRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void createHero(CreateHeroServiceModel model, String name) throws Exception {
        User user = userRepository.findByUsername(name);

        if (user == null){
            throw new Exception("User does not exists");
        }

        if (user.getHero() != null){
            throw new Exception("User already has a hero");
        }

        Hero hero = this.mapper.map(model, Hero.class);

        hero.setGender(Gender.valueOf(model.getGender().toUpperCase()));
        hero.setAttack(1);
        hero.setDefence(1);
        hero.setLevel(1);
        hero.setStamina(1);
        hero.setStrength(1);
//        User user = userRepository.findByUsername(name);
        hero.setUser(user);
        user.setHero(hero);
        userRepository.saveAndFlush(user);
//        heroRepository.saveAndFlush(hero);
    }

    @Override
    public DetailsHeroServiceModel findHero(String heroName) {
        Hero hero = heroRepository.findByName(heroName).orElseThrow(() -> new HeroNotFoundException("No such found"));

        DetailsHeroServiceModel detail = this.mapper.map(hero, DetailsHeroServiceModel.class);
        detail.setGauntlets(getItemBySlot(hero.getItems(), Slot.GAUNTLETS));
        detail.setHelmet(getItemBySlot(hero.getItems(), Slot.HELMET));
        detail.setPads(getItemBySlot(hero.getItems(), Slot.PADS));
        detail.setWeapon(getItemBySlot(hero.getItems(), Slot.WEAPON));
        detail.setPauldron(getItemBySlot(hero.getItems(), Slot.PAULDRON));

        return detail;
    }

    private Item getItemBySlot(List<Item> items, Slot weapon) {
        return items.stream().filter(a -> a.getSlot().name().equals(weapon.name())).findFirst().orElse(null);
    }

    @Override
    public List<DetailsHeroServiceModel> getAllWithoutCurrent(String heroName) {
        return heroRepository.findAll().stream()
                .filter(n -> !n.getName().equals(heroName))
                .map(m -> mapper.map(m, DetailsHeroServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public String fight(String heroName, String guest) {

        Hero homeHero = heroRepository.findByName(heroName).get();
        Hero guestHero = heroRepository.findByName(guest).get();

        int damageHeroHome = homeHero.getAttack() + (homeHero.getStrength() * 4) -
                (guestHero.getDefence() + (guestHero.getStamina() * 2));

        int damageHeroGuest = guestHero.getAttack() + (guestHero.getStrength() * 4) -
                homeHero.getDefence() + (homeHero.getStamina() * 2);

        if (damageHeroHome > damageHeroGuest) {
            setNewValueHero(homeHero);
            return homeHero.getName();
        } else if (damageHeroGuest > damageHeroHome){
            setNewValueHero(guestHero);
            return guestHero.getName();
        } else {
            return "draw";
        }
    }

    @Override
    public HeroModel findNameAndGenderHero(String name) {
        return mapper.map(heroRepository.findByName(name).get(), HeroModel.class);
    }

    @Override
    public DetailsHeroServiceModel getByUsername(String username) {
        Optional<Hero> user = heroRepository.findByName(username);

        if (user.isEmpty()) {
            throw new HeroNotFoundException("No such found");
        }

        return mapper.map(user, DetailsHeroServiceModel.class);
    }

    private void setNewValueHero(Hero hero) {
        hero.setLevel(hero.getLevel() + 1);
        hero.setStrength(hero.getStrength() + 5);
        hero.setStamina(hero.getStamina() + 5);
        heroRepository.saveAndFlush(hero);
    }

    @ExceptionHandler(HeroNotFoundException.class)
    public ModelAndView heroException(HeroNotFoundException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("message", ex.getMessage());
        return model;
    }
}
