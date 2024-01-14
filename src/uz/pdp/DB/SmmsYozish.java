package uz.pdp.DB;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SmmsYozish implements Reposstory<SmsMesseg> {
    private List<SmsMesseg> smms;
    private static SmmsYozish singleton;
    private static final String PATH = "src/uz/pdp/DB/meseg_db.txt";

    public SmmsYozish(List<SmsMesseg> smms) {
        this.smms = smms;
    }

    public static SmmsYozish getInstance() {
        if (singleton == null) {
            singleton = new SmmsYozish(loatData());
        }
        return singleton;
    }

    public static List<SmsMesseg> loatData() {
        try (
                InputStream inputStream = new FileInputStream((PATH));
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ) {

          return  (List<SmsMesseg>) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }



    @Override
    public void save(SmsMesseg smsMesseg) {
        smms.add(smsMesseg);
        uploatData();
    }

    private void uploatData() {
        try (
                OutputStream outputStream = new FileOutputStream(PATH);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ) {
            objectOutputStream.writeObject(smms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<SmsMesseg> finAll() {
        return smms;
    }

    @Override
    public void delete(SmsMesseg smsMesseg) {
        smms.remove(smsMesseg);
        uploatData();
    }


}


