package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.MyCart;
import com.spring.stockmanagement.entities.Product;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.CompanyRepository;
import com.spring.stockmanagement.repositories.MyCartRepository;
import com.spring.stockmanagement.repositories.ProductRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.ProductService;
import com.spring.stockmanagement.service.Interface.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MyCartRepository myCartRepository;

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void validateUserForUpdate(User user, int id, BindingResult bindingResult) {
        User existingUser = userRepository.findById(id).get();
        if (user != null) {

            if (StringUtils.isBlank(user.getEmail())) {
                bindingResult.addError(new FieldError("user", "email", "Email can not be blank"));
            }
            if (!existingUser.getEmail().equalsIgnoreCase(user.getEmail()) && userExistByEmail(user.getEmail())) {
                bindingResult.addError(new FieldError("user", "email", "Email already in use"));
            }
            if (StringUtils.isBlank(user.getAddress())) {
                bindingResult.addError(new FieldError("user", "address", "Address can not be blank"));
            }
            if (user.getAddress() != null && !user.getAddress().matches("^[0-9a-zA-Z\\s,-]+$")) {
                bindingResult.addError(new FieldError("user", "address", "Please write correct address"));
            }
            if (StringUtils.isBlank(user.getContact())) {
                bindingResult.addError(new FieldError("user", "contact", "Contact can not be blank"));
            }
            if (user.getContact() != null && !user.getContact().matches("^[0-9].{9}+$")) {
                bindingResult.addError(new FieldError("user", "contact", "Contact must be of 10 digits"));
            }
            if (!existingUser.getContact().equalsIgnoreCase(user.getContact()) && userExistByContact(user.getContact())) {
                bindingResult.addError(new FieldError("user", "contact", "Contact already in exist"));
            }
        }
    }

    @Override
    public void updateUser(User user, int id) {
        User existingUser = userRepository.findById(id).get();
        existingUser.setContact(user.getContact());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());
        userRepository.save(existingUser);
    }

    @Override
    public void setCompany(int id, Principal principal) {
        Company company = companyRepository.findById(id).get();
        String currentUserName = principal.getName();
        User CurrentUser = userRepository.findByName(currentUserName).get();
        CurrentUser.setCompany(company);
        userRepository.save(CurrentUser);
    }

    @Override
    public void removeFromCart(int id, Principal principal) {

        MyCart myCart = myCartRepository.findById(id).get();
        Product product = productRepository.findById(myCart.getProduct().getProductId()).get();
        product.setProductQuantity(product.getProductQuantity() + myCart.getProductCount());
        productRepository.save(product);
        myCartRepository.deleteById(id);

    }

    @Override
    public boolean resetPassword(int id, String password, String cpassword) {
        if(password.equals(cpassword)){
            User user=userRepository.findById(id).get();
            String encryptPassword=passwordEncoder.encode(password);
            user.setPassword(encryptPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public boolean userExists(String name) {
        Optional<User> byName = userRepository.findByName(name);
        return byName.isPresent();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean userExistByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public Optional<User> findByContact(String contact) {
        return userRepository.findByContact(contact);
    }

    @Override
    public boolean userExistByContact(String contact) {
        return userRepository.findByContact(contact).isPresent();
    }

    @Override
    public boolean userExistByName(String name) {
        return userRepository.findByName(name).isPresent();
    }

    @Override
    public void isUserValid(User user, BindingResult bindingResult) {
        if (user != null) {
            if (StringUtils.isBlank(user.getName())) {
                bindingResult.addError(new FieldError("user", "name", "Username can not be blank"));
            }
            if (user.getName() != null && !user.getName().matches("^[a-zA-Z0-9]+$")) {
                bindingResult.addError(new FieldError("user", "name", "Username can only have letters"));
            }
            if(user.getName().length()<4){
                bindingResult.addError(new FieldError("user", "name", "Username should contain atleast 4 characters"));
            }
            if (StringUtils.isBlank(user.getEmail())) {
                bindingResult.addError(new FieldError("user", "email", "Email can not be blank"));
            }
            if (StringUtils.isBlank(user.getContact())) {
                bindingResult.addError(new FieldError("user", "contact", "Contact can not be blank"));
            }
            if (user.getContact() != null && !user.getContact().matches("^[0-9].{9}+$")) {
                bindingResult.addError(new FieldError("user", "contact", "Contact must be of 10 digits"));
            }
            if (StringUtils.isBlank(user.getAddress())) {
                bindingResult.addError(new FieldError("user", "address", "Address can not be blank"));
            }
            if (user.getAddress() != null && !user.getAddress().matches("^[0-9a-zA-Z\\s,-]+$")) {
                bindingResult.addError(new FieldError("user", "address", "Please write correct address"));
            }
            if (StringUtils.isBlank(user.getPassword())) {
                bindingResult.addError(new FieldError("user", "password", "Password can not be blank"));
            }
            if (user.getPassword() != null && !user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$")) {
                bindingResult.addError(new FieldError("user", "password", "Password must contain atleast one number, one UpperCase letter, one LowerCase letter, one Special Character and password length must 8-15 character"));
            }
            if(userExistByName(user.getName())){
                bindingResult.addError(new FieldError("user", "name", "Username already in use"));
            }
            if (userExistByEmail(user.getEmail())) {
                bindingResult.addError(new FieldError("user", "email", "Email already in use"));
            }
            if (userExistByContact(user.getContact())) {
                bindingResult.addError(new FieldError("user", "contact", "Contact already in use"));
            }
        }
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}


