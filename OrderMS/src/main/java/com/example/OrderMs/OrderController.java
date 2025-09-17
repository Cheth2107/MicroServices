package com.example.OrderMs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
    private RestTemplate restTemplate;
 
    
    @GetMapping("/order/product-info")
   public String getProductInfo()
   {
    	String productServiceURL = "http://ProductService/products";
    	
    	return restTemplate.getForObject(productServiceURL, String.class);
   }
}
