package stepdefinitions;

import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.Тогда;
import cucumber.api.java.ru.Когда;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;

public class SendMailStepDefinition {

    @Дано("^пользоватлель открывает страницу в браузере$")
    public void openBrowser() {
        open("http://www.yandex.ru/");
    }

    @Дано("^пользоватлель авторизуется логином \"([^\"]*)\" и паролем \"([^\"]*)\"")
    public void userAuthorization(String username, String password) throws InterruptedException {

        $(By.linkText("Войти в почту")).click();

        if($(By.xpath("//*[text()='Другой аккаунт']")).isDisplayed())
            $(By.xpath("//*[text()='Другой аккаунт']")).click();

        $(By.name("login")).setValue(username).pressEnter();
        $(By.name("passwd")).setValue(password).pressEnter();

        if($(By.xpath("//*[contains(text(), 'сейчас')]/ancestor::button")).isDisplayed()) {
            $(By.xpath("//*[contains(text(), 'сейчас')]/ancestor::button")).waitUntil(visible, 1000).click();
        }

    }

    @Тогда("^пользователь создает письмо$")
    public void userClickCreateMail() {
        $(By.linkText("Написать")).should(visible).click();
    }

    @Когда("^пользователь указывает получателя \"([^\"]*)\"$")
    public void userInputTo(String mailTo) throws InterruptedException {
        $(By.name("to")).setValue(mailTo).pressEnter();
    }

    @Когда("^пользователь указывает тему письма \"([^\"]*)\"$")
    public void userInputSubject(String subject) {
        $(By.xpath("//input[contains(@name, 'subj-')]")).setValue(subject).pressEnter();
    }

    @Когда("^пользователь заполняет текст письма \"([^\"]*)\"$")
    public void userInputText(String text) {
        $(By.xpath("//*[@role='textbox']")).setValue(text).pressEnter();
    }

    @Когда("^пользователь отправляет письмо$")
    public void userClickSendMail() {
        $(By.xpath("//button[contains(@title, 'Отправить письмо')]")).click();
    }

    @Тогда("^пользователь проверяет, что письмо отправлено$")
    public void userCheckSendMail() {
        $(By.xpath("//div[contains(@class, 'mail-Done-Title')]")).shouldHave(text("Письмо отправлено."));
    }

    @Когда("^пользователь перекладывает письмо с темой \"([^\"]*)\" в папку \"([^\"]*)\"$")
    public void userMoveMail(String subject, String folder) throws InterruptedException {

        String xpathMail = "//span[@title='" + subject + "']";
        String xpathFolder = "//div[@title='" + folder + "']";
        $(By.xpath(xpathMail)).contextClick();
        $(By.xpath("//div[text()='Переложить в папку']")).waitUntil(visible, 1000).click();
        $(By.xpath(xpathFolder)).waitUntil(visible, 1000).click();

    }

    @Тогда("^пользователь переходит в папку \"([^\"]*)\"$")
    public void userChangeFolder(String folder) {

        String xpathFolder = "//a[contains(@title, '" + folder + "')]";
        $(By.xpath(xpathFolder)).click();

    }

    @Когда("^пользователь для письма с темой \"([^\"]*)\" ставит метку \"([^\"]*)\"$")
    public void userMarkMail(String subject, String mark) throws InterruptedException {

        String xpathMail = "//span[@title='" + subject + "']";
        String xpathMark = "//div[@title='" + mark + "']";
        $(By.xpath(xpathMail)).shouldBe(visible).contextClick();
        $(By.xpath("//div[text()='Поставить метку']")).waitUntil(visible, 1000).click();
        $(By.xpath(xpathMark)).waitUntil(visible, 1000).click();

    }

    @Тогда("^пользователь выходит из яндекс сервисов$")
    public void userExitFromMail() throws InterruptedException {

        $(By.className("mail-User-Name")).shouldBe(visible).click();
        $(By.xpath("//*[text()='Выйти из сервисов Яндекса']")).waitUntil(visible, 1000).click();

    }

    @Тогда("^пользователь проверяет наличие письма с темой \"([^\"]*)\"$")
    public void userCheckMailExist(String subject) throws InterruptedException {

        String xpathMail = "//span[@title='" + subject + "']";
        $(By.xpath(xpathMail)).isDisplayed();

    }

    @Когда("^пользователь переходит к письмам с меткой \"([^\"]*)\"$")
    public void userMoveToMark(String mark) throws InterruptedException {

        String xpathMark = "//a[@data-title='" + mark + "']";
        $(By.xpath(xpathMark)).waitUntil(visible, 1000).click();

    }

    @Тогда("^пользователь удалеят письмо с темой \"([^\"]*)\"$")
    public void userDeleteMail(String subject) throws InterruptedException {

        String xpathMail = "//span[@title='" + subject + "']";
        $(By.xpath(xpathMail)).waitUntil(visible, 1000).contextClick();
        $(By.xpath("//span[text()='Удалить']")).waitUntil(visible, 1000).click();

    }

    @Тогда("^пользователь проверяет отсутствие письма с темой \"([^\"]*)\"$")
    public void userCheckMailNotExist(String subject) throws InterruptedException {

        String xpathMail = "//span[@title='" + subject + "']";
        $(By.xpath(xpathMail)).shouldNotBe(exist);

    }
}