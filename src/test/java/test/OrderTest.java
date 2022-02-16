package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.DebitPage;
import page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static data.DBHelper.deleteAllDB;
import static data.DataHelper.AuthInfo.*;
import static data.DataHelper.getDeclinedCard;
import static data.DataHelper.getUnknownCard;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private static final int amount = 45_000_00;

    @BeforeAll
    static void addListenerAndHeadless() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @AfterAll
    static void removeListener() {
        SelenideLogger.removeListener("AllureSelenide");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
        new HomePage().openPage();
    }
    @AfterEach
    void setDown() {
        deleteAllDB();
    }

    // DEBIT CARD

    @Test
    @DisplayName("Должен успешно оплатить с одобренной дебетовой картой в форме оплаты")
    void shouldSuccessPayWithApprovedDebitCard() {
        successDebitPage().enterValidUserWithApprovedCard();
        assertEquals(amount, DBHelper.getAmountDebitCard());
        assertEquals("APPROVED", DBHelper.getStatusDebitCard());
        assertNotNull(DBHelper.getPaymentId());
        assertNotNull(DBHelper.getTransactionIdDebitCard());
        assertEquals(DBHelper.getPaymentId(), DBHelper.getTransactionIdDebitCard());
        assertNull(DBHelper.getCreditId());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с отклоненной дебетовой картой в форме оплаты")
    void shouldReturnFailWithDeclinedDebitCard() {
        successDebitPage()
                .enterValidUserWithIncorrectCard(validUser(getDeclinedCard()));
        assertEquals("DECLINED", DBHelper.getStatusDebitCard());
        assertNull(DBHelper.getPaymentId());
        assertNull(DBHelper.getTransactionIdDebitCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с неизвестной дебетовой картой в форме оплаты")
    void shouldReturnFailWithUnknownDebitCard() {
        successDebitPage()
                .enterValidUserWithIncorrectCard(validUser(getUnknownCard()));
        assertNull(DBHelper.getPaymentId());
        assertNull(DBHelper.getTransactionIdDebitCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустой дебетовой картой в форме оплаты")
    void shouldReturnErrorWithEmptyDebitCard() {
        successDebitPage()
                .enterIncorrectCardInput(emptyCardUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем месяца в форме оплаты")
    void shouldReturnErrorWithEmptyMonthDebit() {
        successDebitPage()
                .enterIncorrectMonthInput(emptyMonthUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем года в форме оплаты")
    void shouldReturnErrorWithEmptyYearDebit() {
        successDebitPage()
                .enterIncorrectYearInput(emptyYearUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем имени в форме оплаты")
    void shouldReturnErrorWithEmptyNameDebit() {
        successDebitPage()
                .enterIncorrectNameInput(emptyNameUser(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем CVC в форме оплаты")
    void shouldReturnErrorWithEmptyCodeDebit() {
        successDebitPage()
                .enterIncorrectCodeInput(emptyCodeUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми пустыми полями в форме оплаты")
    void shouldReturnErrorsWithEmptyAllDebit() {
        successDebitPage()
                .enterInputs(emptyUser());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Поле обязательно для заполнения",
                "Неверный формат"
        );
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем карты в форме оплаты")
    void shouldReturnErrorWithInvalidDebitCard() {
        successDebitPage()
                .enterIncorrectCardInput(invalidCardUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем месяца в форме оплаты")
    void shouldReturnErrorWithInvalidMonthDebit() {
        successDebitPage()
                .enterIncorrectMonthInput(invalidMonthUser(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем года в форме оплаты")
    void shouldReturnErrorWithInvalidYearDebit() {
        successDebitPage()
                .enterIncorrectYearInput(invalidYearUser(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем имени в форме оплаты")
    void shouldReturnErrorWithInvalidNameDebit() {
        successDebitPage()
                .enterIncorrectNameInput(invalidNameUser(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем CVC в форме оплаты")
    void shouldReturnErrorWithInvalidCodeDebit() {
        successDebitPage()
                .enterIncorrectCodeInput(invalidCodeUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми некорректными полями в форме оплаты")
    void shouldReturnErrorsWithInvalidAllDebit() {
        successDebitPage()
                .enterInputs(invalidUser());
        errorsDisplay(
                "Неверный формат",
                "Неверно указан срок действия карты",
                "Истёк срок действия карты",
                "Неверный формат",
                "Неверный формат"
        );
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми полями со значением '0' в форме оплаты")
    void shouldReturnErrorsWithAllZeroInputsDebit() {
        successDebitPage()
                .enterInputs(userWithAllZero());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Истёк срок действия карты",
                "Неверный формат",
                "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми полями до лимита в форме оплаты")
    void shouldReturnErrorsWithUnderLimitsDebit() {
        successDebitPage()
                .enterInputs(userWithUnderLimits());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми полями сверх лимита в форме оплаты")
    void shouldReturnErrorsWithAfterLimitsDebit() {
        successDebitPage()
                .enterInputs(userWithAfterLimits());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку в полях с некорректными символами в форме оплаты")
    void shouldReturnFailWithIncorrectSymbolsDebit() {
        successDebitPage()
                .enterInputs(userWithIncorrectSymbols());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат"
        );
    }

    @Test
    @DisplayName("Должен вернуть ошибку в полях с символьными значениями в форме оплаты")
    void shouldReturnFailWithSymbolicValuesDebit() {
        successDebitPage()
                .enterInputs(userWithSymbolicValues());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Поле обязательно для заполнения",
                "Неверный формат"
        );
    }

    // CREDIT CARD

    @Test
    @DisplayName("Должен успешно оплатить с одобренной кредитной картой в форме кредита")
    void shouldSuccessPayWithApprovedCreditCard() {
        successCreditPage()
                .enterValidUserWithApprovedCard();
        assertEquals("APPROVED", DBHelper.getStatusCreditCard());
        assertNotNull(DBHelper.getBankIdCreditCard());
        assertNotNull(DBHelper.getCreditId());
        assertNull(DBHelper.getPaymentId());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с отклоненной кредитной картой в форме кредита")
    void shouldReturnFailWithDeclinedCreditCard() {
        successCreditPage()
                .enterValidUserWithIncorrectCard(validUser(getDeclinedCard()));
        assertEquals("DECLINED", DBHelper.getStatusCreditCard());
        assertNull(DBHelper.getBankIdCreditCard());
        assertNull(DBHelper.getCreditId());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с неизвестной кредитной картой в форме кредита")
    void shouldReturnFailWithUnknownCreditCard() {
        successCreditPage()
                .enterValidUserWithIncorrectCard(validUser(getUnknownCard()));
        assertNull(DBHelper.getBankIdCreditCard());
        assertNull(DBHelper.getCreditId());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустой кредитной картой в форме кредита")
    void shouldReturnErrorWithEmptyCreditCard() {
        successCreditPage()
                .enterIncorrectCardInput(emptyCardUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем месяца в форме кредита")
    void shouldReturnErrorWithEmptyMonthCredit() {
        successCreditPage()
                .enterIncorrectMonthInput(emptyMonthUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем года в форме кредита")
    void shouldReturnErrorWithEmptyYearCredit() {
        successCreditPage()
                .enterIncorrectYearInput(emptyYearUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем имени в форме кредита")
    void shouldReturnErrorWithEmptyNameCredit() {
        successCreditPage()
                .enterIncorrectNameInput(emptyNameUser(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем CVC в форме кредита")
    void shouldReturnErrorWithEmptyCodeCredit() {
        successCreditPage()
                .enterIncorrectCodeInput(emptyCodeUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми пустыми полями в форме кредита")
    void shouldReturnErrorsWithEmptyAllCredit() {
        successCreditPage()
                .enterInputs(emptyUser());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Поле обязательно для заполнения",
                "Неверный формат"
        );
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем карты в форме кредита")
    void shouldReturnErrorWithInvalidCreditCard() {
        successCreditPage()
                .enterIncorrectCardInput(invalidCardUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем месяца в форме кредита")
    void shouldReturnErrorWithInvalidMonthCredit() {
        successCreditPage()
                .enterIncorrectMonthInput(invalidMonthUser(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем года в форме кредита")
    void shouldReturnErrorWithInvalidYearCredit() {
        successCreditPage()
                .enterIncorrectYearInput(invalidYearUser(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем имени в форме кредита")
    void shouldReturnErrorWithInvalidNameCredit() {
        successCreditPage()
                .enterIncorrectNameInput(invalidNameUser(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем CVC в форме кредита")
    void shouldReturnErrorWithInvalidCodeCredit() {
        successCreditPage()
                .enterIncorrectCodeInput(invalidCodeUser(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми некорректными полями в форме кредита")
    void shouldReturnErrorsWithInvalidAllCredit() {
        successCreditPage()
                .enterInputs(invalidUser());
        errorsDisplay(
                "Неверный формат",
                "Неверно указан срок действия карты",
                "Истёк срок действия карты",
                "Неверный формат",
                "Неверный формат"
        );
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми полями со значением '0' в форме кредита")
    void shouldReturnErrorsWithAllZeroInputsCredit() {
        successCreditPage()
                .enterInputs(userWithAllZero());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Истёк срок действия карты",
                "Неверный формат",
                "Неверный формат"
        );
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми полями до лимита в форме кредита")
    void shouldReturnErrorsWithUnderLimitsCredit() {
        successCreditPage()
                .enterInputs(userWithUnderLimits());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат"
        );
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми полями сверх лимита в форме кредита")
    void shouldReturnErrorsWithAfterLimitsCredit() {
        successCreditPage().enterInputs(userWithAfterLimits());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат"
        );
    }

    @Test
    @DisplayName("Должен вернуть ошибку в полях с некорректными символами в форме кредита")
    void shouldReturnFailWithIncorrectSymbolsCredit() {
        successCreditPage().enterInputs(userWithIncorrectSymbols());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Неверный формат"
        );
    }

    @Test
    @DisplayName("Должен вернуть ошибку в полях с символьными значениями в форме кредита")
    void shouldReturnFailWithSymbolicValuesCredit() {
        successCreditPage().enterInputs(userWithSymbolicValues());
        errorsDisplay(
                "Неверный формат",
                "Неверный формат",
                "Неверный формат",
                "Поле обязательно для заполнения",
                "Неверный формат"
        );
    }

    // Helper methods

    private DebitPage successDebitPage() {
        new HomePage().openDebitForm().successOpenPage();
        return new DebitPage();
    }

    private CreditPage successCreditPage() {
        new HomePage().openCreditForm().successOpenPage();
        return new CreditPage();
    }

    private void errorsDisplay(String errorCard, String errorMonth, String errorYear, String errorName, String errorCode) {
        new DebitPage()
                .errorsDisplay(errorCard, errorMonth, errorYear, errorName, errorCode);
    }
}