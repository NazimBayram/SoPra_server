package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to the user.
 * The controller will receive the request and delegate the execution to the UserService and finally return the result.
 */
@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }

    @PostMapping("/users/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create user in database
        User createdUser = userService.createUser(userInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
    }

    @PostMapping("/users/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {

        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        //System.out.println("Login user-------"+userInput);

        // check if user exists
        User loginUser = userService.checkForLogin(userInput);
        //System.out.println("Login user-------"+loginUser);

        // convert internal representation of user back to API
        System.out.println(DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser));
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser);
    }

    //@PostMapping("/user")
    @GetMapping ("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUserByID(@PathVariable Long userId) {

        // convert API user to internal representation
      //  User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        //System.out.println(userPostDTO.toString());

        // check if user exists
      //  User loginUser = userService.getUser(userInput.getId());
        User loginUser = userService.getUser(userId);
        // User loginUser = userService.getUser(userInput.getId());

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser);

    }

    @PostMapping("/userbyusername")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUserProfile(@RequestBody UserPostDTO userPostDTO) {
    //public UserGetDTO getUserProfile(@PathVariable String userName) {

        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // check if user exists
        User loginUser = userService.getUserbyUsername(userInput.getUsername());
        //User loginUser = userService.getUserbyUsername(userName);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser);
    }

    @PostMapping("/updateUser")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO saveUsername(@RequestBody UserPostDTO userPostDTO) {

        // convert API user to internal representation
        //System.out.println(userPostDTO.toString());
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // check if user exists
        //User loginUser = userService.getUserbyUsername(userInput.getUsername());
         User loginUser = userService.getUser(userInput.getId());

        loginUser.setUsername(userPostDTO.getUsername());
        loginUser.setBirthday(userPostDTO.getBirthday());
        //System.out.println(loginUser.getUsername());

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.updateUser(loginUser));
    }

    /**
     @PostMapping("/birthdayUpdate")
     @ResponseStatus(HttpStatus.OK)
     @ResponseBody
     public UserGetDTO savebirtday(@RequestBody UserPostDTO userPostDTO) {

     // convert API user to internal representation
     User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
     //System.out.println(userPostDTO.toString());

     // check if user exists
     User loginUser = userService.getUser(userInput.getId());
     // User loginUser = userService.getUser(userInput.getId());

     loginUser.setBirthday(userPostDTO.getBirthday());


     // convert internal representation of user back to API
     return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.updateUser(loginUser));

     }*/

    /**
     * Put & Post both can be used, but put is more use for updating and post for creating.
     * PUT: Create or update based on existence of the resource.
     */


}
