package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestNGListener;

import static utils.PropertiesReader.getProperty;
@Listeners(TestNGListener.class)

public class LoginTests extends AppManager {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void loginPositiveTest() {
        User user = User.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();
        new HomePage(getDriver()).clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        softAssert.assertTrue(new ContactsPage(getDriver())
                .validateTextInBtnAdd(), "validate text in element add");
        softAssert.assertTrue(new ContactsPage(getDriver())
                .isURLContainsText("contacts"), "validate url");
        softAssert.assertTrue(new ContactsPage(getDriver())
                .isBtnSignOutDisplayed(), "validate btn signOut displayed");
        softAssert.assertAll();
    }

    @Test
    public void loginNegative_AllFieldsEmpty_Test(){
        new HomePage(getDriver()).clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(), "Wrong email or password");
    }

    @Test
    public void loginNegative_EmptyFieldEmail_Test(){
        User user = User.builder()
                .username("")
                .password(getProperty("base.properties", "password"))
                .build();
        new HomePage(getDriver()).clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(), "Wrong email or password");
    }

    @Test
    public void loginNegative_EmptyFieldPassword_Test(){
        User user = User.builder()
                .username(getProperty("base.properties", "email"))
                .password("")
                .build();
        new HomePage(getDriver()).clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(), "Wrong email or password");
    }

    @Test
    public void loginNegative_WrongEmail_Test(){
        User user = User.builder()
                .username("asdert123@rty.com")
                .password(getProperty("base.properties", "password"))
                .build();
        new HomePage(getDriver()).clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(), "Wrong email or password");
    }

    @Test
    public void loginNegative_WrongPassword_Test(){
        User user = User.builder()
                .username(getProperty("base.properties", "email"))
                .password("A123vbgt!")
                .build();
        new HomePage(getDriver()).clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationForm(user);
        loginPage.clickBtnLogin();
        Assert.assertEquals(loginPage.closeAlert(), "Wrong email or password");
    }
//    @Test
//    public void testMethod(){
//        new HomePage(getDriver()).method();
//    }
//
//    @Test
//    public void testAjaxMethod(){
//        new HomePage(getDriver()).ajaxMethod();
//    }
}
