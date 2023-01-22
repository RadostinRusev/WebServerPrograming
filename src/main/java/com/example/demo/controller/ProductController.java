package com.example.demo.controller;

import com.example.demo.car.CarDTO;
import com.example.demo.car.CarService;
import com.example.demo.car.Product;
import com.example.demo.car.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private CarService carService;

    public static boolean logged;
    @GetMapping("/products")
    public String getAll(@RequestParam(value = "page",defaultValue = "1")Integer page,Search search,Model model){
        if(!logged){
            return "index";
        }
        List<Product> allCars = new ArrayList<>();
        if(search!=null){
           // Search search = (Search) model.getAttribute("search");
            if(search.getDropdown()!=null || search.getId()!=null){
                allCars  = carService.getAllCars(search.getDropdown(),search.getId());
            }else {
                allCars = carService.getAllCars();
            }

        }else {
            allCars = carService.getAllCars();
        }
        List<Integer> integer = deduceNumberofPages(allCars);
        allCars= sort(allCars,page);
        model.addAttribute("pages",integer);
        model.addAttribute("products",allCars);
        model.addAttribute("visible",allCars.size()>0);
        return  "products";
    }

    private List<Integer> deduceNumberofPages(List<Product> allCars) {
        int max;
        if(allCars.size()<=5){
            max=1;
        }
        else {
            max = allCars.size() / 5 + 1;
        }
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            pages.add(i);
        }
        return pages;
    }

    @PostMapping("/products/create")
    public String createProduct(@RequestParam(value = "page",defaultValue = "1",required = false)Integer page, CarDTO carDTO, Model model){
        if(!logged){
            return "index";
        }
        if(carDTO.getCarid()==null)
        carDTO.setCarid(UUID.randomUUID());
        carService.updateCar(carDTO,"0");
        List<Product> allCars = carService.getAllCars();
        List<Integer> integers = deduceNumberofPages(allCars);
       allCars= sort(allCars,page);
        model.addAttribute("products",allCars);
        model.addAttribute("search",new Search());
        model.addAttribute("visible",allCars.size()>0);

        model.addAttribute("pages",integers);
        return  "products";
    }
    @GetMapping({"/products/create/{id}","products/create"})
    public String createProductForm(Model model,@PathVariable(required = false) String id){
//        Product product = new Product();
//        product.setProduct_id(UUID.randomUUID());
//        product.setCategory(carDTO.getCategory());
//        product.setDescription(carDTO.getDescription());
//        product.setCategory(carDTO.getCategory());
//        product.setPriceToBuy(carDTO.getPriceToBuy());
//        product.setPriceToSell(carDTO.getPriceToSell());
//        carService.updateCar(carDTO,"0");

            if (id != null) {
                Product carById = carService.getCarById(UUID.fromString(id));
                CarDTO carDTO = new CarDTO();
                carDTO.setCarid(carById.getProduct_id());
                carDTO.setCategory(carById.getCategory());
                carDTO.setPriceToBuy(carById.getPriceToBuy());
                carDTO.setPriceToSell(carById.getPriceToSell());
                carDTO.setDescription(carById.getDescription());
                carDTO.setName(carById.getName());
                carDTO.setNumber(carById.getNumber());
                model.addAttribute("car", carDTO);
                model.addAttribute("product", carDTO);

            }
            else {
                model.addAttribute("product",new CarDTO());
            }
        return  "createProduct";
    }
    @GetMapping("/products/{id}")
    public String findCarByID(@PathVariable(required = true)String id , Model model){
        Product carById = carService.getCarById(UUID.fromString(id));
        CarDTO carDTO = new CarDTO();
        carDTO.setCarid(carById.getProduct_id());
        carDTO.setCategory(carById.getCategory());
        carDTO.setPriceToBuy(carById.getPriceToBuy());
        carDTO.setPriceToSell(carById.getPriceToSell());
        carDTO.setDescription(carById.getDescription());
        model.addAttribute("car",carDTO);

        return  "products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteById(@PathVariable(required = true)String id  ){
      carService.deleteCar(UUID.fromString(id));
        return  "products";
    }
    @GetMapping("/products/delete/{id}")
    public String delById(@PathVariable(required = true)String id ,Model model ){
        carService.deleteCar(UUID.fromString(id));
        List<Product> allCars = carService.getAllCars();
        allCars= sort(allCars,1);
        model.addAttribute("products",allCars);
        model.addAttribute("search",new Search());
        model.addAttribute("visible",allCars.size()>0);
        List<Integer> integers = deduceNumberofPages(allCars);
        model.addAttribute("pages",integers);
        return  "products";
    }

    public List<Product> sort(List<Product> products,Integer page){
        List<Product> collect = products.stream().sorted().collect(Collectors.toList());
        if(page!=null) {
          if (collect.size() > ((page-1) * 5) + 5)
              return collect.subList(((page-1) * 5), ((page-1) * 5) + 5);
          else if(collect.size()>0) {
                return collect.subList(((page - 1) * 5), collect.size());
            }
          else   return collect.subList(((page - 1) * 5), 0);
      }
      return collect;
    }

    public static void setLogged(boolean login){
        logged= login;
    }

}
