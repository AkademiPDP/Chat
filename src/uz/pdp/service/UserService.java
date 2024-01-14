package uz.pdp.service;

import uz.pdp.DB.UserRepo;
import uz.pdp.DB.entity.User;
import uz.pdp.util.Input;

import java.util.List;
import java.util.Optional;

public class UserService {
private static final UserRepo USER_REPO=UserRepo.getInstance();
    public static void printAll() {
        List<User>all=USER_REPO.finAll();
        for(User user:all){
            System.out.println(user);
        }
        System.out.println("==========================");
    }
    public static void add() {
        User user=new User(
                Input.inputStr("Ismini kriting"),
                Input.inputNum("Yoshni kriting")
        );
           USER_REPO.save(user);
    }
    public static void delet() {
        User user=chooseUser();
        USER_REPO.delete(user);
    }

    private static User chooseUser() {
        printAll();
        int id =Input.inputNum("choose by id:");
        Optional<User>userOpt=findBy(id);
        return userOpt.orElseThrow(()->new RuntimeException("user toplmadi"));
    }


    public static Optional<User> findBy(Integer id) {
    List<User>users=USER_REPO.finAll();
        for(User user:users){
            if(user.getId().equals(id)){
                return Optional.of( user);
            }
        }
        return Optional.empty();
    }

}
