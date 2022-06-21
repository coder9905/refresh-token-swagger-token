package uz.zako.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.zako.entity.Admin;
import uz.zako.entity.Role;
import uz.zako.repository.AdminRepository;
import uz.zako.repository.RoleRepository;
import uz.zako.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;


    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String init;

    public DataLoader(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, AdminRepository adminRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("keldi");

        try {
            if (init.equalsIgnoreCase("create")) {
                Role roleUser=new Role();

//                User user=new User();

                roleUser.setName("ROLE_USER");
                roleUser.setId(1L);
                Role roleAdmin = new Role();
                roleAdmin.setId(2L);
                roleAdmin.setName("ROLE_ADMIN");
                List<Role> roleList = new ArrayList<>(Arrays.asList(roleUser, roleAdmin));
                roleRepository.saveAll(roleList);

                Admin admin = new Admin();
                admin.setIsAdmin(true);
                admin.setFullName("admin");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setSocial("t.me/test");
                admin.setRoles(roleList);
                adminRepository.save(admin);

            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }
}
