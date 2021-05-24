package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.Point;
import com.project.diploma.data.models.User;
import com.project.diploma.data.repositories.HeroRepository;
import com.project.diploma.data.repositories.RoleRepository;
import com.project.diploma.data.repositories.UserRepository;
import com.project.diploma.services.models.LoginUserServiceModel;
import com.project.diploma.services.models.RegisterUserServiceModel;
import com.project.diploma.services.services.HashingService;
import com.project.diploma.services.services.RoleService;
import com.project.diploma.services.services.UserService;
import com.project.diploma.services.services.ValidationService;
import com.project.diploma.web.models.LoggedUserFilterModel;
import com.project.diploma.web.models.ProfileUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ValidationService validationService;
    private final HashingService hashingService;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final HeroRepository heroRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, ValidationService validationService, HashingService hashingService, RoleRepository roleRepository, RoleService roleService, HeroRepository heroRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validationService = validationService;
        this.hashingService = hashingService;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.heroRepository = heroRepository;
    }

    @Override
    public boolean register(RegisterUserServiceModel model) {
        if (!validationService.isValidUser(model)) {
            return false;
        }

        User user = this.mapper.map(model, User.class);
        user.setPassword(hashingService.hashPassword(user.getPassword()));
        user.setGold(Point.GOLD.getValue());

        if (userRepository.count() == 0) {
            roleService.seedRolesInDb();
            user.setAuthorities(new HashSet<>(roleRepository.findAll()));
        } else {
            user.setAuthorities(new HashSet<>(Set.of(roleRepository.findByAuthority("User"))));
        }

        userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public boolean login(LoginUserServiceModel user) {
        String username = user.getUsername();
        String password = hashingService.hashPassword(user.getPassword());

        return userRepository.existsByUsernameAndPassword(username, password);
    }

    @Override
    public LoggedUserFilterModel findLogUser(String username) {
        User user = userRepository.findUserByUsername(username);

        return mapper.map(user, LoggedUserFilterModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    @Override
    public ProfileUserModel getDetailsForUser(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User does not exists");
        }
        ProfileUserModel model = mapper.map(user, ProfileUserModel.class);

        final int[] totalWinsOfHero = {0};
        final int[] totalBattlesOfHero = {0};

        heroRepository.findAll().stream()
                .filter(e -> e.getUser().getUsername().equals(username))
                .forEach(e -> {
                    totalWinsOfHero[0] += e.getWins();
                    totalBattlesOfHero[0] += e.getBattles();
                });

        model.setTotalWins(totalWinsOfHero[0]);
        model.setTotalBattles(totalBattlesOfHero[0]);
        return model;
    }

    @Override
    public int takeGoldFromUser(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getGold();
    }
}
