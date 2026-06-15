package ui_tests;

import data_providers.UserDataProvider;
import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestNGListener;

import java.util.Random;

import static utils.UserFactory.positiveUser;
@Listeners(TestNGListener.class)

public class RegistrationTests extends AppManager {
    LoginPage loginPage;

    @BeforeMethod
    public void goToLoginRegistrationPage() {
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void registrationPositiveTest() {
        int i = new Random().nextInt(1000);
        User user = User.builder()
                .username("uioyptnh" + i + "@vbt.op")
                .password("Atyur123!")
                .build();
        System.out.println(user);
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .validateTextInNoContactMessage("No Contacts here!"));
    }

    @Test
    public void registrationPositiveTestWithFaker() {
        User user = positiveUser();
        System.out.println(user);
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .validateTextInNoContactMessage("No Contacts here!"));
    }

    @Test
    public void registrationEmptyAllFieldsNegativeTest() {
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test
    public void registrationEmptyFieldEmailNegativeTest() {
        User user = positiveUser();
        user.setUsername("");
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test
    public void registrationEmptyFieldPasswordNegativeTest() {
        User user = positiveUser();
        user.setPassword("");
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password format"));
    }

    @Test(dataProvider = "dataProviderWrongEmailUserFromFile",
    dataProviderClass = UserDataProvider.class)
    public void registrationWrongFieldEmailOrPasswordNegativeTest(User user) {
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlert()
                .contains("Wrong email or password format"));
    }
}
