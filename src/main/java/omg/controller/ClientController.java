package omg.controller;

import omg.model.Account;
import omg.model.Email;
import omg.model.Phone;
import omg.model.User;
import omg.repository.AccountRepository;
import omg.repository.EmailRepository;
import omg.repository.PhoneRepository;
import omg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/client")
public class ClientController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    @PostMapping(path="/addPhone")
    public @ResponseBody String addPhone (@RequestParam String login,
                                          @RequestParam String password,
                                          @RequestParam String phone){
        Optional<User> user = userRepository.findByLogin(login);
        if (user.get().getPassword().equals(password)) {
            Phone phoneSave = new Phone();
            phoneSave.setPhone(phone);
            phoneSave.setUser(user.get());
            phoneRepository.save(phoneSave);
            return "Saved phone";
        }
        return "ERROR Saved PHONE";
    }

    @PostMapping(path="/addEmail")
    public @ResponseBody String addEmail (@RequestParam String login,
                                          @RequestParam String password,
                                          @RequestParam String email){
        Optional<User> user = userRepository.findByLogin(login);
        if (user.get().getPassword().equals(password)) {
            Email EmailSave = new Email();
            EmailSave.setEmail(email);
            EmailSave.setUser(user.get());
            emailRepository.save(EmailSave);
            return "Email phone";
        }
        return "ERROR Saved EMAIL";
    }

    @PostMapping(path="/deletePhone")
    public @ResponseBody String deletePhone (@RequestParam String login,
                                          @RequestParam String password,
                                          @RequestParam String phone){
        Optional<User> user = userRepository.findByLogin(login);
        if (user.get().getPassword().equals(password)) {
            phoneRepository.deleteByPhone(phone);
            return "Deleted phone";
        }
        return "ERROR Delete PHONE";
    }

    @PostMapping(path="/deleteEmail")
    public @ResponseBody String deleteEmail (@RequestParam String login,
                                          @RequestParam String password,
                                          @RequestParam String email){
        Optional<User> user = userRepository.findByLogin(login);
        if (user.get().getPassword().equals(password)) {
            emailRepository.deleteByEmail(email);
            return "Deleted phone";
        }
        return "ERROR Deleted EMAIL";
    }

}
