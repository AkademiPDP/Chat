package uz.pdp.mssegServis;

import uz.pdp.DB.SmmsYozish;
import uz.pdp.DB.SmsMesseg;
import uz.pdp.DB.UserRepo;
import uz.pdp.DB.entity.User;
import uz.pdp.util.Input;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessegServis {

    public static User CurrentUser = null; // Hozirgi foydalanuvchi

    static UserRepo userRepo = UserRepo.getInstance();
    static  SmmsYozish smmsYozish = SmmsYozish.getInstance();

    public static void regster() {
        User user = new User(
                Input.inputStr("Ismingizni kiriting"),
                Input.inputStr("Parolingizni kiriting "),
                createPassword(),
                chooseId()
        );
        userRepo.save(user);
        System.out.println("Muaffaqiyatli ro'yxatdan o'tdingiz!");
    }

private static String createPassword() {
    while (true) {
        String password = Input.inputStr("Parolingizni kiriting (kamida 8 belgi, 1 raqam va 1 katta harf kerak):");
        Pattern pattern = Pattern.compile("(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[0-9]+).{7,}");
        Matcher matcher = pattern.matcher(password);

        if (matcher.matches()) {
            return password;
        } else {
            System.out.println("Noto'g'ri parol formati. Iltimos, qaytadan kiriting.");
        }
    }
}


    private static ZoneId chooseId() {
        System.out.println("""
                1-Uzbekiston
                2-Japon
                3-Amerika
                                
                """);
        return switch (Input.inputNum("Tanlang")) {
            case 1 -> ZoneId.of("Asia/Tashkent");
            case 2 -> ZoneId.of("Asia/Tokyo");
            case 3 -> ZoneId.of("America/New_York");
            default -> ZoneId.systemDefault();
        };
    }

    public static void logIn() {
        String phoneNumber = Input.inputStr("Parolingizni kiriting:");
        String password = Input.inputStr("Parolingizni kiriting:");

        List<User> users = userRepo.finAll();

        for (User user : users) {
            if (user.getPhone().equals(phoneNumber) && user.getPassword().equals(password)) {
                CurrentUser= user;
                System.out.println("Tizimga kiritdingiz!");

                userTanlash();

                return;
            }
        }
        System.out.println("Noto'g'ri telefon raqami yoki parol. Iltimos, qaytadan urinib ko'ring.");
    }

private static void userTanlash() {
    List<User> all = userRepo.finAll();

    if (all.isEmpty()) {
        System.out.println("Hech qanday foydalanuvchi ro'yxatga olingan emas.");
        return;
    }

    System.out.println("Foydalanuvchilardan tanlang:");
    for (int i = 0; i < all.size(); i++) {
        User user = all.get(i);
        if(!CurrentUser.equals(user))
        System.out.println((i + 1) + ". " + user.getName() + " - " + user.getPhone());
    }

int choice = Input.inputNum("Tanlangan foydalanuvchi raqamini kiriting (0 - chiqish):");

    if (choice > 0 && choice <= all.size()) {
        User selectedUser = all.get(choice - 1);
        System.out.println("Tanlangan foydalanuvchi: " + selectedUser.getName());
        showMessages(selectedUser);
        messageSend(selectedUser);
    } else if (choice == 0) {
        System.out.println("Tanlash bekor qilindi.");
    } else {
        System.out.println("Noto'g'ri raqam kiritdingiz. Iltimos, qaytadan urinib ko'ring.");
    }


    if (Input.inputNum("0 to exit/1 to continue") == 0) {
        CurrentUser = null;
        return;
    }
}

private static void messageSend(User user1) {
    SmsMesseg newMessage = new SmsMesseg(
            CurrentUser.getId(),
            user1.getId(),
            Input.inputStr("Write a message"),
            ZonedDateTime.now());
    System.out.println("\t\t\t\t\t\t\t\t\t"+newMessage.getText()+" 🔃");
    System.out.println("Loading...");
    CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
        smmsYozish.save(newMessage);
    });
    completableFuture.thenRun(() -> {
        showMessages(user1);
    });
}
private static void showMessages(User selectedUser) {
    System.out.println("User: " + selectedUser.getName());
    List<SmsMesseg> smsMessages = smmsYozish.finAll();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    for (SmsMesseg smsMesseg : smsMessages) {
        String formattedTime = smsMesseg.getZonedDateTime().withZoneSameInstant(selectedUser.getZoneId()).format(formatter);
        String indent = "\t\t\t\t\t\t\t\t\t";
        if (smsMesseg.getFromId().equals(selectedUser.getId())&& smsMesseg.getToId().equals(CurrentUser.getId()) ||
        smsMesseg.getFromId().equals(CurrentUser.getId()) && smsMesseg.getToId().equals(selectedUser.getId())){

              boolean trueOrFalse = smsMesseg.getFromId().equals(selectedUser.getId());

        if (trueOrFalse) {
            System.out.println(selectedUser.getName() + ": " + smsMesseg.getText());
            System.out.println(formattedTime);
        } else {
            System.out.println(indent+smsMesseg.getText()+" "+selectedUser.getName());
            System.out.println(indent+formattedTime);
        }
    }
        }


}
}


/*showMessage(user1);
    messageSend(user1);
    if (Input.inputInt("0 to exit/1 to continue") == 0) {
        userjon = null;
        return;
    }
}

private static void messageSend(User user1) {
    Message newMessage = new Message(
            userjon.getId(),
            user1.getId(),
            Input.inputStr("Write a message"),
            ZonedDateTime.now());

    System.out.println("Loading...");

    CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
        messageRepo.save(newMessage);
    });
    completableFuture.thenRun(() -> {
        showMessage(userjon);
    });
}

private static void showMessage(User currentUser) {
    System.out.println("User: " + currentUser.getName());
    List<Message> messages = messageRepo.findAll();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    for (Message message : messages) {
        String formattedTime = message.getZonedDateTime().withZoneSameInstant(currentUser.getZoneId()).format(formatter);

        if (message.getFrom().equals(currentUser.getId()) || message.getTo().equals(currentUser.getId())) {
            Optional<User> otherUserOptional = (message.getFrom().equals(currentUser.getId())) ? userRepo.findById(message.getTo()) : userRepo.findById(message.getFrom());

            if (otherUserOptional.isPresent()) {
                User otherUser = otherUserOptional.get();
                String indent = (message.getFrom().equals(currentUser.getId())) ? "" : "\t\t\t\t\t\t\t\t\t";

                System.out.println(indent + otherUser.getName() + ": " + message.getText());
                System.out.println(indent + formattedTime);
            } else {
                System.out.println("Other user not found for message");
            }
        }
    }
}*/
