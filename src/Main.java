import uz.pdp.DB.UserRepo;
import uz.pdp.DB.entity.User;
import uz.pdp.mssegServis.MessegServis;
import uz.pdp.service.UserService;
import uz.pdp.util.Input;


public class Main {
    public static void main(String[] args) {
        System.out.println("Salom");
        while (true) {
            displeMenu();
            switch (Input.inputNum("CHOOSE")) {
                case 1 -> MessegServis.regster();
                case 2 -> MessegServis.logIn();
                case 3 -> System.exit(0);
            }
            if(MessegServis.CurrentUser==null){
                continue;
            }
        }
    }

    private static void displeMenu() {
        System.out.println("""
                    1-Register
                    2-LogIn
                    3-chqish
                """);
    }
}
















/*
        while (true){
            UserService.printAll();
            System.out.println("""
                    1-add
                    2-delet
                    3-updet
                    """);
            switch (Input.inputNum("choos")){
                case 1->UserService.add();
                case 2->UserService.delet();
                case 3->UserService.updet();

            }
        }
    }
*/


