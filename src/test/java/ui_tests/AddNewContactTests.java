package ui_tests;

import data_providers.ContactDataProvider;
import dto.Contact;
import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddPage;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestNGListener;

import static utils.ContactFactory.positiveContact;
import static utils.PropertiesReader.getProperty;

@Listeners(TestNGListener.class)

public class AddNewContactTests extends AppManager {
    ContactsPage contactsPage;
    AddPage addPage;
    int countOfContacts;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void login() {
        User user = User.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();
        new HomePage(getDriver()).clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        contactsPage = new ContactsPage(getDriver());
        countOfContacts = contactsPage.countOfContacts(); // 20
        contactsPage.clickBtnAdd();
    }

    @Test
    public void addNewContactPositiveTest() {
        addPage = new AddPage(getDriver());
        addPage.typeAddNewContactForm(positiveContact());
        addPage.clickBtnSave();
        int countOfContactsAfterAdd = contactsPage
                .countOfContacts(); // 21
        Assert.assertEquals(countOfContactsAfterAdd,
                countOfContacts + 1);
//        Assert.assertEquals(countOfContacts,
//                countOfContactsAfterAdd - 1);
    }

    @Test
    public void checkLastContactPositiveTest() {
        addPage = new AddPage(getDriver());
        Contact contact = positiveContact();
        addPage.typeAddNewContactForm(contact);
        addPage.clickBtnSave();
        System.out.println(contact);
        contactsPage.scrollToLastContact();
        contactsPage.clickLastContact();
        String text = contactsPage.getTextFromItemCard();
        System.out.println(text);
        softAssert.assertTrue(text.contains(contact.getName()),
                "validate Name in LastContactItemCard");
        softAssert.assertTrue(text.contains(contact.getPhone()),
                "validate Phone in LastContactItemCard");
        softAssert.assertTrue(text.contains(contact.getEmail()),
                "validate Email in LastContactItemCard");
        softAssert.assertAll();
    }

    @Test
    public void checkLastContactWithScrollPositiveTest() {
        addPage = new AddPage(getDriver());
        Contact contact = positiveContact();
        addPage.typeAddNewContactForm(contact);
        addPage.clickBtnSave();
        System.out.println(contact);
        contactsPage.scrollListToEnd();
        Assert.assertTrue(contactsPage.isContactPresent(contact));
    }

    @Test
    public void addNewContactAllFieldsEmptyNegativeTest() {
        addPage = new AddPage(getDriver());
        addPage.clickBtnSave();
        Assert.assertTrue(addPage.closeAlert().contains("Phone not valid: Phone " +
                "number must contain only digits!"));
    }

    @Test
    public void addNewContactEmptyFieldNameNegativeTest() {
        addPage = new AddPage(getDriver());
        Contact contact = positiveContact();
        contact.setName("");
        addPage.typeAddNewContactForm(contact);
        addPage.clickBtnSave();
        //Assert.assertTrue(addPage.isBtnSaveNotClickable());
        //Assert.assertFalse(addPage.isBtnSaveDisabled());
        //Assert.assertFalse(addPage.isBtnSaveClickable());
        Assert.assertTrue(addPage.isURLContainsText("add"));
    }

    @Test
    public void addContactExistsNegativeTest() {
        addPage = new AddPage(getDriver());
        Contact contact = positiveContact();
        System.out.println(contact);
        addPage.typeAddNewContactForm(contact);
        addPage.clickBtnSave();
        contactsPage.clickBtnAdd();
        contact.setName("Sveta");
        contact.setLastName("Svetlaia");
        //System.out.println(contact);
        addPage.typeAddNewContactForm(contact);
        addPage.clickBtnSave();
        Assert.assertTrue(addPage.closeAlert().contains("Email not valid: "));
    }

    @Test(dataProvider = "dataProviderWrongEmailContactFromFile",
            dataProviderClass = ContactDataProvider.class)
    public void addContactWrongEmailNegativeTest(Contact contact) {
        addPage = new AddPage(getDriver());
        addPage.typeAddNewContactForm(contact);
        addPage.clickBtnSave();
        Assert.assertTrue(addPage.closeAlert().contains("Email not valid: "));
    }
}
