package data;

import com.github.javafaker.Faker;
import lombok.Value;

@Value
public class DataHelper {

    private static final Faker faker = new Faker();

    private static final String approvedCard = "4444 4444 4444 4441";
    private static final String declinedCard = "4444 4444 4444 4442";
    private static final String unknownCard = "4444 4444 4444 4444";

    @Value
    public static class AuthInfo {
        String number;
        String year;
        String month;
        String holder;
        String cvc;

        public static AuthInfo validUser(String cardType) {
            return new AuthInfo(
                    cardType,
                    String.valueOf(faker.number().numberBetween(22, 28)), // Год с 22 по 28.
                    String.valueOf(faker.number().numberBetween(10, 12)), // Месяц с 10 по 12.
                    faker.name().firstName() + " " + faker.name().lastName(), // Полное имя владельца карты.
                    String.valueOf(faker.number().numberBetween(100, 999)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo userWithAllZero() {
            return new AuthInfo(
                    "0000 0000 0000 0000",
                    "00",
                    "00",
                    "000 000",
                    "000"
            );
        }

        public static AuthInfo userWithUnderLimits() {
            return new AuthInfo(
                    "4444 4444 4444 444",
                    String.valueOf(faker.number().numberBetween(1, 9)),
                    String.valueOf(faker.number().numberBetween(1, 9)),
                    "f",
                    String.valueOf(faker.number().numberBetween(1, 99))
            );
        }

        public static AuthInfo userWithAfterLimits() {
            return new AuthInfo(
                    "44444444444444444",
                    "234",
                    "123",
                    "SAJDKFJDSFHSDKASDJNSAJDJSANDJAKNDJANDJKADNJANDJKANDJADNKJAJDNAJKDNSJJSJ",
                    "1234"
            );
        }

        public static AuthInfo userWithIncorrectSymbols() {
            return new AuthInfo(
                    "abcd fcds",
                    "ab",
                    "ab",
                    "123456 1234",
                    "abc"
            );
        }

        public static AuthInfo userWithSymbolicValues() {
            return new AuthInfo(
                    "%&$# &@^!",
                    "*&",
                    "?@",
                    ")*^%@ @!#*(",
                    "*^$"
            );
        }

        public static AuthInfo invalidUser() {
            return new AuthInfo(
                    "4444",
                    String.valueOf(faker.number().numberBetween(10, 20)),
                    String.valueOf(faker.number().numberBetween(13, 99)),
                    "ваня ваньков",
                    String.valueOf(faker.number().numberBetween(0, 99))
            );
        }

        public static AuthInfo invalidCardUser() {
            return new AuthInfo(
                    "4444",
                    String.valueOf(faker.number().numberBetween(22, 28)), // Год с 22 по 28.
                    String.valueOf(faker.number().numberBetween(10, 12)), // Месяц с 10 по 12.
                    faker.name().firstName() + " " + faker.name().lastName(), // Полное имя владельца карты.
                    String.valueOf(faker.number().numberBetween(100, 999)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo invalidYearUser() {
            return new AuthInfo(
                    getApprovedCard(),
                    String.valueOf(faker.number().numberBetween(10, 20)), // Год с 22 по 28.
                    String.valueOf(faker.number().numberBetween(10, 12)), // Месяц с 10 по 12.
                    faker.name().firstName() + " " + faker.name().lastName(), // Полное имя владельца карты.
                    String.valueOf(faker.number().numberBetween(100, 999)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo invalidMonthUser() {
            return new AuthInfo(
                    getApprovedCard(),
                    String.valueOf(faker.number().numberBetween(22, 28)), // Год с 22 по 28.
                    String.valueOf(faker.number().numberBetween(13, 99)), // Месяц с 10 по 12.
                    faker.name().firstName() + " " + faker.name().lastName(), // Полное имя владельца карты.
                    String.valueOf(faker.number().numberBetween(100, 999)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo invalidNameUser() {
            return new AuthInfo(
                    getApprovedCard(),
                    String.valueOf(faker.number().numberBetween(22, 28)), // Год с 22 по 28.
                    String.valueOf(faker.number().numberBetween(10, 12)), // Месяц с 10 по 12.
                    "ваня ваньков", // Полное имя владельца карты.
                    String.valueOf(faker.number().numberBetween(100, 999)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo invalidCodeUser() {
            return new AuthInfo(
                    getApprovedCard(),
                    String.valueOf(faker.number().numberBetween(22, 28)), // Год с 22 по 28.
                    String.valueOf(faker.number().numberBetween(10, 12)), // Месяц с 10 по 12.
                    faker.name().firstName() + " " + faker.name().lastName(), // Полное имя владельца карты.
                    String.valueOf(faker.number().numberBetween(0, 99)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo emptyUser() {
            return new AuthInfo(
                    "",
                    "",
                    "",
                    "",
                    ""
            );
        }

        public static AuthInfo emptyCardUser() {
            return new AuthInfo(
                    "",
                    String.valueOf(faker.number().numberBetween(22, 28)), // Год с 22 по 28.
                    String.valueOf(faker.number().numberBetween(10, 12)), // Месяц с 10 по 12.
                    faker.name().firstName() + " " + faker.name().lastName(), // Полное имя владельца карты.
                    String.valueOf(faker.number().numberBetween(100, 999)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo emptyYearUser() {
            return new AuthInfo(
                    getApprovedCard(),
                    "",
                    String.valueOf(faker.number().numberBetween(10, 12)), // Месяц с 10 по 12.
                    faker.name().firstName() + " " + faker.name().lastName(), // Полное имя владельца карты.
                    String.valueOf(faker.number().numberBetween(100, 999)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo emptyMonthUser() {
            return new AuthInfo(
                    getApprovedCard(),
                    String.valueOf(faker.number().numberBetween(22, 28)), // Год с 22 по 28.
                    "",
                    faker.name().firstName() + " " + faker.name().lastName(), // Полное имя владельца карты.
                    String.valueOf(faker.number().numberBetween(100, 999)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo emptyNameUser() {
            return new AuthInfo(
                    getApprovedCard(),
                    String.valueOf(faker.number().numberBetween(22, 28)), // Год с 22 по 28.
                    String.valueOf(faker.number().numberBetween(10, 12)), // Месяц с 10 по 12.
                    "",
                    String.valueOf(faker.number().numberBetween(100, 999)) // Любой трехзначный int-код.
            );
        }

        public static AuthInfo emptyCodeUser() {
            return new AuthInfo(
                    getApprovedCard(),
                    String.valueOf(faker.number().numberBetween(22, 28)), // Год с 22 по 28.
                    String.valueOf(faker.number().numberBetween(10, 12)), // Месяц с 10 по 12.
                    faker.name().firstName() + " " + faker.name().lastName(), // Полное имя владельца карты.
                    ""
            );
        }
    }

    public static String getApprovedCard() {
        return approvedCard;
    }

    public static String getDeclinedCard() {
        return declinedCard;
    }

    public static String getUnknownCard() {
        return unknownCard;
    }
}