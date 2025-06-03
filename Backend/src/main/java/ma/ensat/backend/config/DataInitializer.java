package ma.ensat.backend.config;

import ma.ensat.backend.entity.Item;
import ma.ensat.backend.entity.User;
import ma.ensat.backend.repository.ItemRepository;
import ma.ensat.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Créer des utilisateurs par défaut s'ils n'existent pas
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin user created: username=admin, password=admin123");
        }


        // Créer quelques articles d'exemple si la base est vide
        if (itemRepository.count() == 0) {
            Item item1 = new Item("Laptop Dell XPS 13", new BigDecimal("999.99"), "High-performance laptop with Intel i7 processor");
            Item item2 = new Item("iPhone 14", new BigDecimal("899.99"), "Latest iPhone with advanced camera system");
            Item item3 = new Item("Samsung Galaxy S23", new BigDecimal("799.99"), "Android smartphone with excellent display");
            Item item4 = new Item("MacBook Air M2", new BigDecimal("1199.99"), "Apple laptop with M2 chip");

            itemRepository.save(item1);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);

            System.out.println("Sample items created successfully");
        }
    }
}