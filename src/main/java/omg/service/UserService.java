package omg.service;

import omg.model.Account;
import omg.model.Email;
import omg.model.Phone;
import omg.model.User;
import omg.repository.AccountRepository;
import omg.repository.EmailRepository;
import omg.repository.PhoneRepository;
import omg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    public String addUser(String login, String password, String name,
                          String data, String emailNew, String phoneNew, Double sum){
        if (userRepository.existsByLogin(login))
            return "Login is busy";

        if (sum < 0)
            return "Not Valid Balance";

        User user = new User();
        Account account = new Account();
        Email email = new Email();
        Phone phone = new Phone();

        user.setLogin(login);
        user.setPassword(password);
        user.setFio(name);
        user.setDataBirth(data);

        account.setSum(sum);
        account.setUser(user);

        email.setEmail(emailNew);
        email.setUser(user);

        phone.setPhone(phoneNew);
        phone.setUser(user);

        userRepository.save(user);
        accountRepository.save(account);
        emailRepository.save(email);
        phoneRepository.save(phone);

        return "Saved";
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public ResponseEntity<Map<String, Object>> getAllUsers(int page, int size) {
        try {
            List<User> userList = new ArrayList<User>();
            Pageable paging = PageRequest.of(page, size);

            Page<User> pageTuts = userRepository.findAll(paging);

            userList = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("users", userList);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String pay(String login, String loginTo, Double sum){
        Optional<User> userFrom = userRepository.findByLogin(login);
        Optional<User> userTo = userRepository.findByLogin(loginTo);
        if (userFrom.isPresent() && userTo.isPresent()) {
            Optional<Account> accountFrom = accountRepository.findByUserId(userFrom.get().getId());
            Optional<Account> accountTo = accountRepository.findByUserId(userTo.get().getId());
            if (accountFrom.get().getSum() >= sum) {
                accountFrom.get().setSum(accountFrom.get().getSum() - sum);
                accountTo.get().setSum(accountTo.get().getSum() + sum);
                accountRepository.save(accountFrom.get());
                accountRepository.save(accountTo.get());
                return "pay";
            }
            return "no money";
        }
        return "no pay";
    }

}
