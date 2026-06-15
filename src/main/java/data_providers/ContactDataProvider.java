package data_providers;

import dto.Contact;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactDataProvider {
    @DataProvider
    public Iterator<Contact> dataProviderWrongEmailContactFromFile() {
        List<Contact> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader
                (new FileReader
                        ("src/test/resources/wrong_email.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitLine = line.split(",");
                list.add(Contact.builder()
                        .name(splitLine[0])
                        .lastName(splitLine[1])
                        .phone(splitLine[2])
                        .email(splitLine[3])
                        .address(splitLine[4])
                        .description(splitLine[5])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("created exception");
        }
        return list.listIterator();
    }
}
