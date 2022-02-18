<div align="center">

# Сервис заказа путешествия

</div>

<div align="center">

### [Дипломный проект профессии «Тестировщик»](https://github.com/netology-code/qa-diploma/tree/2ccafd34b6f9eb3a66dd7a11a0b8b1ba3f266e50#дипломный-проект-профессии-тестировщик)

</div>

___
## Описание приложения

### Бизнес часть

Приложение представляет из себя веб-сервис.

Приложение предлагает купить тур по определённой цене с помощью двух способов:
1. Обычная оплата по дебетовой карте.
1. Выдача кредита по данным банковской карты.

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей (далее - Payment Gate)
* кредитному сервису (далее - Credit Gate)

Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).

### Техническая часть

Само приложение расположено в файле [`aqa-shop.jar`](artifacts/aqa-shop.jar) и запускается на порту `8080`.

В файле [`application.properties`](application.properties) приведён ряд типовых настроек:
* учётные данные и url для подключения к СУБД
* url-адреса банковских сервисов

### СУБД

Заявлена поддержка двух СУБД:
* MySQL
* PostgreSQL

Учётные данные и url для подключения задаются в файле [`application.properties`](application.properties).

### Банковские сервисы

Разработчики подготовили симулятор банковских сервисов, который может принимать запросы в нужном формате и генерировать ответы.

Симулятор расположен в каталоге [`gate-simulator`](gate-simulator). Для запуска необходимо перейти в этот каталог.

Запускается симулятор командой `npm start` на порту 9999.

Симулятор позволяет для заданного набора карт генерировать предопределённые ответы.

Набор карт представлен в формате JSON в файле [`data.json`](gate-simulator/data.json).

Сервис симулирует и Payment Gate и Credit Gate.
___
## Начало работы

### Предусловия

На ПК необходимо установить:
`IntelliJ IDEA`, `Google Chrome`.

### Установка и запуск

_Примечание: по-умолчанию `SUT` работает с `PostgreSQL`._

1. Скачать код репозитория любым способом.
2. Выполнить команду в консоли:
```
docker-compose up -d
```
3. После развертывания контейнера для запуска `SUT` в зависимости от выбранной для работы `СУБД` выполнить команду в консоли:

| _PostgreSQL_                         |                                           _MySQL_                                           |
|--------------------------------------|:-------------------------------------------------------------------------------------------:|
| `java -jar .\artifacts\aqa-shop.jar` | `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/db` |
4. Запустить тесты командой в консоли:

| _PostgreSQL_                       |                                _MySQL_                                 |
|------------------------------------|:----------------------------------------------------------------------:|
| `./gradlew clean test allureServe` | `./gradlew clean test allureServe -Ddb=jdbc:mysql://localhost:3306/db` |
___

### Дополнительные сведения:

Для просмотра отчета `Allure Report` после выполнения тестов ввести в консоли:
```
./gradlew allureServe
```
___
Установка `headless`-режима `Selenide` осуществляется через изменение
свойств `gradle.properties`:
```
systemProp.selenide.headless=true
```
или
```
systemProp.selenide.headless=false
```
___
#### После окончания работы:
1. Завершить работу `SUT` сочетанием клавиш `CTRL + C`.
2. Завершить работу контейнеров командой в консоли:
```
docker-compose down
```
___

[![Issues](https://img.shields.io/github/issues-raw/aremarss/qa-project?color=800000&style=for-the-badge)](https://github.com/aremarss/qa-project/issues) &nbsp;
[<img src="https://aremarss.github.io/qa-project-allure/favicon.ico?v=2"/>][Allure]
[**ALLURE REPORT**](https://aremarss.github.io/qa-project-allure/) &nbsp;
[![Java CI with Gradle](https://github.com/aremarss/qa-project/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/aremarss/qa-project/actions/workflows/gradle.yml) &nbsp;

[Allure]: https://aremarss.github.io/qa-project-allure/