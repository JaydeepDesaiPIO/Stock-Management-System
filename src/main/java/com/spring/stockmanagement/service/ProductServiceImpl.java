package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.*;
import com.spring.stockmanagement.repositories.MyCartRepository;
import com.spring.stockmanagement.repositories.ProductRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyCartRepository myCartRepository;

    @Override
    public Product addProduct(Product product, Principal principal) {
        String currentUserName=principal.getName();
        User CurrentUser=userRepository.findByName(currentUserName).get();
        product.setCompany(CurrentUser.getCompany());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCompany(Principal principal) {
        String currentUserName=principal.getName();
        User CurrentUser=userRepository.findByName(currentUserName).get();
        Company company=CurrentUser.getCompany();
        return productRepository.findProductByCompany(company);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Optional<Product> isProductExits(String productName) {
        return productRepository.findProductByProductName(productName);
    }

    @Override
    public Product findProductByName(String name) {
        return productRepository.findByProductName(name);
    }

    @Override
    public void saveOrderItem(Product product, OrderItem orderItem, BindingResult bindingResult) {
        if(productRepository.existsById(product.getProductId())){
            Product product1=productRepository.findById(product.getProductId()).get();
            if(orderItem.getQuantity()>product1.getProductQuantity() || (product1.getProductQuantity()<=0)) {
                bindingResult.addError(new FieldError("orderItem", "quantity", "only " + product1.getProductQuantity() + " are available"));
            }
            else {
                product1.setProductQuantity(product1.getProductQuantity()-orderItem.getQuantity());
                orderItem.setProduct(product1);
                orderItem.setPrice(product1.getProductPrice());
                productRepository.save(product1);
            }
        }
    }

    @Override
    public void addProductToCart(Product product, MyCart myCart, Principal principal, HttpSession session, BindingResult bindingResult) {
        if(productRepository.findById(product.getProductId()).isPresent()) {
            Product product1 = productRepository.findById(product.getProductId()).get();
            if((product1.getProductQuantity()>0) && (product1.getProductQuantity()<myCart.getProductCount())) {

                bindingResult.addError(new FieldError("mycart", "productCount", "only " + product1.getProductQuantity() + " are available"));
            }
            else {
                product1.setProductQuantity(product1.getProductQuantity() - myCart.getProductCount());
                myCart.setProduct(product1);
                productRepository.save(product1);
                String currentUserName=principal.getName();
                User currentUser= userRepository.findByName(currentUserName).get();
                myCart.setUser(currentUser);
                myCartRepository.save(myCart);
            }
        }
    }

    @Override
    public void validateProduct(Product product, BindingResult bindingResult,Principal principal) {
        if(product!=null)
        {
            if (StringUtils.isBlank(product.getProductName())) {
                bindingResult.addError(new FieldError("product", "productName", "Product name cannot be blank"));
            }
            for(Product productInList: getProductByCompany(principal))
            {
                if(product.getProductName().equalsIgnoreCase(productInList.getProductName()))
                {
                    bindingResult.addError(new FieldError("product", "productName", "Product already exist"));
                }
            }
        }
    }

    @Override
    public void validateUpdatedProduct(int id, Product product, BindingResult bindingResult, Principal principal) {
        Product existingProduct=getProductById(id);
        if(product!=null)
        {
            if (StringUtils.isBlank(product.getProductName())) {
                bindingResult.addError(new FieldError("product", "productName", "Product name cannot be blank"));
            }
            for(Product productInList: getProductByCompany(principal))
            {
                if(!(existingProduct.getProductName().equalsIgnoreCase(product.getProductName())) && product.getProductName().equalsIgnoreCase(productInList.getProductName()))
                {
                    bindingResult.addError(new FieldError("product", "productName", "Product already exist"));
                }
            }
        }
    }

    @Override
    public void updateProduct(int id, Product product) {
        Product existingProduct=getProductById(id);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductQuantity(product.getProductQuantity());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setDescription(product.getDescription());
        productRepository.save(existingProduct);
    }

    @Override
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }
}
