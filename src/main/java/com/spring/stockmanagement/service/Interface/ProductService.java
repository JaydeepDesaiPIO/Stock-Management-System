package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.MyCart;
import com.spring.stockmanagement.entities.OrderItem;
import com.spring.stockmanagement.entities.Product;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product addProduct(Product product, Principal principal);

    List<Product> getAllProduct();

    List<Product> getProductByCompany(Principal principal);

    Product getProductById(int id);

    Optional<Product> isProductExits(String productName);

    Product findProductByName(String name);

    void saveOrderItem(Product product, OrderItem orderItem, BindingResult bindingResult);

    void addProductToCart(Product product, MyCart myCart, Principal principal, HttpSession session, BindingResult bindingResult);

    void validateProduct(Product product, BindingResult bindingResult, Principal principal);

    void validateUpdatedProduct(int id, Product product, BindingResult bindingResult, Principal principal);

    void updateProduct(int id, Product product);

    void deleteProductById(int id);
}
