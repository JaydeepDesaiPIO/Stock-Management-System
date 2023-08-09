package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.MyCart;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.MyCartRepository;
import com.spring.stockmanagement.repositories.ProductRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.MyCartService;
import com.spring.stockmanagement.service.Interface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class MyCartServiceImpl implements MyCartService {

    @Autowired
    private MyCartRepository myCartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<MyCart> findByUser(User user) {
        return myCartRepository.getCartByUser(user);
    }

    @Override
    public void addProduct(Product product, MyCart myCart, Principal principal) {
        Product product1=productService.getProductById(product.getProductId());
        int count = 0;
        product1.setProductQuantity(product1.getProductQuantity() - myCart.getProductCount());
        productRepository.save(product1);
        String currentUserName = principal.getName();
        User currentUser = userRepository.findByName(currentUserName).get();
        myCart.setUser(currentUser);
        List<MyCart> myCartList = myCartRepository.getCartByUser(currentUser);
        for (MyCart myCart1 : myCartList) {
            if (myCart1.getProduct().equals(product1)) {
                myCart1.setProductCount(myCart1.getProductCount() + myCart.getProductCount());
                myCartRepository.save(myCart1);
                count++;
            }
        }
        if (count == 0) {
            myCart.setProduct(product1);
            myCartRepository.save(myCart);
        }
    }
}
