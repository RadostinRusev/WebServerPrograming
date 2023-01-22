package com.example.demo.controller;

import com.example.demo.car.CarService;
import com.example.demo.car.Product;
import com.example.demo.car.Search;
import com.example.demo.user.User;
import com.example.demo.user.UserDTO;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @GetMapping(value = "/login")
    public String login(
            RegisterRequest registerRequest, Model model
    ){
//        User first = userService.getUsers().stream().filter(user -> user.getEmail()
//                .equals(registerRequest.getEmail()) && user.getPassword().equals(registerRequest.getPassword()))
//                .findFirst().orElse(null);
//
//        model.addAttribute(first);

        return  "login";

    }
    @GetMapping(value = "/register")
    public String register(Model model, UserDTO userDTO){
//        Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
//        if(registerRequest.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,20}$")
//        && registerRequest.getPassword().matches(String.valueOf(regex))
//        && registerRequest.getName().matches("(?=.*[a-z_[A-Z]]).{5,15}"))
//        userService.updateUser("",registerRequest);
        model.addAttribute("userDTO", userDTO);
        return "register";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String register(@ModelAttribute("userDTO") UserDTO registerRequest, Model model) {
        Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");

        if(isValid(registerRequest.getPassword())
                && registerRequest.getEmail().matches(String.valueOf(regex))
                && registerRequest.getName().matches("(?=.*[a-z_[A-Z]]).{5,15}")){
            userService.updateUser("",registerRequest);
        return "index";

            }

        else return "register";
    }

    public boolean isValid(String password){
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginPost(
            RegisterRequest registerRequest, Model model
    ){
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User first = userService.getUsers().stream().filter(user -> user.getEmail()
                .equals(registerRequest.getEmail()) &&
                user.getPassword()
                        .equals(registerRequest.getPassword()))
                .findFirst().orElse(null);



        if(first==null){
            model.addAttribute("error","Incorrect Email or Password");
           return "login";
        }
        model.addAttribute(first);
        ProductController.logged=true;
        List<Product> allCars = carService.getAllCars();
        allCars= carService.sort(allCars,1);
        model.addAttribute("products",allCars);
        model.addAttribute("search",new Search());
        model.addAttribute("visible",allCars.size()>0);
        List<Integer> integers = carService.deduceNumberofPages(allCars);
        model.addAttribute("pages",integers);
        return  "products";

    }
    @GetMapping("/logout")
    public String logOut(){
        ProductController.logged=false;
        return "index";
    }
}
