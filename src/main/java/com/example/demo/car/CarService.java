package com.example.demo.car;

import com.example.demo.repository.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Product> getAllCars(){
       return carRepository.findAll();
    }
    public List<Product> getAllCars(String category,String id){
        if(category.equals("Building materials"))
        {
            category="lding";
        }

        if(!id.isEmpty()){
            return carRepository.findAll().stream()
                    .filter(product -> product.getProduct_id().toString().contains(id))
                    .collect(Collectors.toList());
        }
        if("All".equals(category)) {
            return carRepository.findAll().stream()
                    .filter(product -> product.getProduct_id().toString().contains(id))
                    .collect(Collectors.toList());
        }
        String finalCategory = category;
        return carRepository.findAll().stream()
                .filter(product -> {

                   return product.getCategory().contains(finalCategory);
                })
                .collect(Collectors.toList());
    }
    public Product updateCar(CarDTO carDTO, String Id){
        Product product = new Product();
        product.setCategory(carDTO.getCategory());
        product.setProduct_id(carDTO.getCarid());
        product.setNumber(carDTO.getNumber());
        product.setPriceToBuy(carDTO.getPriceToBuy());
        product.setPriceToSell(carDTO.getPriceToSell());
        product.setName(carDTO.getName());
        product.setDescription(carDTO.getDescription());
        return  carRepository.save(product);
    }

    public void deleteCar(UUID id){
        carRepository.deleteById(id);
    }

    public List<Integer> deduceNumberofPages(List<Product> allCars) {
        int max = allCars.size() / 5 + 1;
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            pages.add(i);
        }
        return pages;
    }
    public List<Product> sort(List<Product> products,Integer page){
        List<Product> collect = products.stream().sorted().collect(Collectors.toList());
        if(page!=null) {
            if (collect.size() > ((page-1) * 5) + 5)
                return collect.subList(((page-1) * 5), ((page-1) * 5) + 5);
            else
                return  collect.subList(((page-1) * 5), products.size()-1);
        }
        return collect;
    }
    public Product getCarById(UUID id){
     return    carRepository.getById(id);
    }
}
