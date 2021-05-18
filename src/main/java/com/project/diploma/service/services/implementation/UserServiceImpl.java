package com.project.diploma.service.services.implementation;

import com.project.diploma.data.models.Point;
import com.project.diploma.data.models.User;
import com.project.diploma.data.repository.RoleRepository;
import com.project.diploma.data.repository.UserRepository;
import com.project.diploma.service.models.LoginUserServiceModel;
import com.project.diploma.service.models.RegisterUserServiceModel;
import com.project.diploma.service.services.HashingService;
import com.project.diploma.service.services.RoleService;
import com.project.diploma.service.services.UserService;
import com.project.diploma.service.services.ValidationService;
import com.project.diploma.web.models.LoggedUserFilterModel;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, ValidationService validationService, HashingService hashingService, RoleRepository roleRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validationService = validationService;
        this.hashingService = hashingService;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

//    @Override
//    public ProfileServiceModel profile(String model) {
//        User user = userRepository.findByUsername(model);
//
////        if (user == null){
////            throw new Exception("User does not exists");
////        }
//
//        return this.mapper.map(user, ProfileServiceModel.class);
//    }

    @Override
    public boolean register(RegisterUserServiceModel model) {
        if (!validationService.isValid(model)) {
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
}
