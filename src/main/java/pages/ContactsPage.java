package pages;

import dto.Contact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class ContactsPage extends BasePage {
    public ContactsPage(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory
                (driver, 10), this);
    }

    @FindBy(xpath = "//a[@href='/add']")
    WebElement btnAdd;
    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOut;
    @FindBy(xpath = "//h1[text()=' No Contacts here!']")
    WebElement noContactMessage;
    @FindBy(className = "contact-item_card__2SOIM")
    List<WebElement> listContacts;
    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM'][last()]")
    WebElement lastContact;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    WebElement itemCard;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']/div")
    WebElement divContactList;

    public int countOfContacts() {
        return listContacts.size();
    }

    public String getTextFromItemCard() {
        return itemCard.getText();
    }

    public void clickLastContact() {
        lastContact.click();
    }

    public void clickBtnAdd() {
        clickWait(btnAdd);
    }

    public boolean validateTextInBtnAdd() {
        return isTextInElementPresent(btnAdd, "ADD");
    }

    public boolean isBtnSignOutDisplayed() {
        return btnSignOut.isDisplayed();
    }

    public boolean validateTextInNoContactMessage(String text) {
        return isTextInElementPresent(noContactMessage, text);
    }

    public void scrollToLastContact() {
        Actions actions = new Actions(driver);
        actions.scrollToElement(lastContact).perform();
    }

    public void scrollListToEnd() {
        Actions actions = new Actions(driver);
        int deltaY = divContactList.getSize().getHeight();
        System.out.println("Height " + deltaY);
        WheelInput.ScrollOrigin scrollOrigin = WheelInput
                .ScrollOrigin.fromElement(listContacts.get(0));
        actions.scrollFromOrigin(scrollOrigin, 0, deltaY).perform();
    }

    public boolean isContactPresent(Contact contact){
        for (WebElement el: listContacts){
            if(el.getText().contains(contact.getName())
            && el.getText().contains(contact.getPhone())){
                return true;
            }
        }
        return false;
    }
}
