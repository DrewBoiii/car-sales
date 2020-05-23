package example.drew.carsales.controller;

import example.drew.carsales.persistence.dto.user.UserCriteriaDto;
import example.drew.carsales.persistence.dto.user.UserProfileDto;
import example.drew.carsales.persistence.dto.user.UserRolesUpdateDto;
import example.drew.carsales.service.RoleService;
import example.drew.carsales.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@Controller
public class UserController {

    public static final int ITEMS_PER_PAGE = 5;

    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('user')")
    public String getProfilePage(Model model, @AuthenticationPrincipal User authUser) {
        example.drew.carsales.persistence.entity.User user = userService.getByUsername(authUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("user_dto", new UserProfileDto());
        return "private_profile";
    }

    @PostMapping("/profile/update")
    @PreAuthorize("hasAuthority('user')")
    public String updateProfile(@ModelAttribute("user_dto") UserProfileDto userProfileDto) throws IOException {
        userService.updateAndSendMail(userProfileDto);
        return "redirect:/profile";
    }

    @GetMapping("/home/{username}")
    public String getPublicProfile(Model model,
                                   @PathVariable("username") String username,
                                   @AuthenticationPrincipal User authUser) {
        example.drew.carsales.persistence.entity.User publicUser = userService.getByUsername(username);

        if(authUser != null) {
            example.drew.carsales.persistence.entity.User user = userService.getByUsername(authUser.getUsername());
            boolean isLiked = false;
            if (publicUser.getLikes().contains(user)) {
                isLiked = true;
            }
            model.addAttribute("isLiked", isLiked);
        }

        model.addAttribute("user", publicUser);
        model.addAttribute("roles", new ArrayList<>(roleService.getAll()));
        model.addAttribute("role_dto", new UserRolesUpdateDto());
        return "public_profile";
    }

    @PostMapping("/users/{username}/fav")
    @PreAuthorize(value = "hasAuthority('user')")
    public String addToFavorites(@PathVariable("username") String username,
                                 @AuthenticationPrincipal User authUser) {
        example.drew.carsales.persistence.entity.User publicUser = userService.getByUsername(username);
        example.drew.carsales.persistence.entity.User user = userService.getByUsername(authUser.getUsername());

        Set<example.drew.carsales.persistence.entity.User> publicUserLikes = publicUser.getLikes();
        boolean isContains = publicUserLikes.contains(user);

        if(isContains) {
            publicUserLikes.remove(user);
        } else {
            publicUserLikes.add(user);
        }
        userService.update(publicUser);

        return "redirect:/home/{username}";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public String getUsersPage(Model model,
                               @ModelAttribute("criteria") UserCriteriaDto criteria,
                               @PageableDefault(value = ITEMS_PER_PAGE) Pageable pageable) {
        Sort sort = Sort.by(criteria.getSort()).descending();
        if(criteria.getOrder().equals("asc")) {
            sort = Sort.by(criteria.getSort()).ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        model.addAttribute("page", userService.getAll(criteria, pageRequest));
        return "users";
    }

    @PostMapping("/users/roles/update")
    @PreAuthorize("hasAuthority('admin')")
    public String updateUserRoles(@ModelAttribute("role_dto") UserRolesUpdateDto userRolesUpdateDto) {
        userService.updateAndSendMail(userRolesUpdateDto);
        return "redirect:/users";
    }

}
