package com.example.demo.controller;

import com.example.demo.car.CarService;
import com.example.demo.car.Product;
import com.example.demo.car.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BaseController {

    @Autowired
    private CarService carService;

    @GetMapping("/")
    public String index(Model model) {
        if(ProductController.logged){
            List<Product> allCars = carService.getAllCars();
            allCars= carService.sort(allCars,1);
            model.addAttribute("products",allCars);
            model.addAttribute("search",new Search());
            model.addAttribute("visible",allCars.size()>0);
            List<Integer> integers = carService.deduceNumberofPages(allCars);
            model.addAttribute("pages",integers);
            return "products";
        }
        return "index";
    }
}
