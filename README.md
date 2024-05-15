# Тестовый проект для обучения автоматизации тестирования
Проект автоматизации тестирования REST API, Web UI и базы данных.

## Содержание
- [Технологии](#технологии)
- [Тестирование](#тестирование)
- [Обучение](#обучение)

## Технологии
- [Java](https://www.java.com/ru/)
- [Gradle](https://gradle.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Selenide](https://ru.selenide.org/)
- [REST-assured](https://rest-assured.io/)
- [JDBC](https://docs.oracle.com/en/database/oracle/oracle-database/21/jjdbc/introducing-JDBC.html)
- [Allure Report](https://allurereport.org/)
- [Allure TestOps](https://qameta.io/)
- [Selenoid](https://aerokube.com/selenoid/latest/)

## Тестирование

Запуск тестов:
```sh
./gradlew test
```

Открытие Allure отчёта в браузере:
```sh
./gradlew allureServe
```
Для тестирования БД нужно: 
1. [Развернуть](https://www.asozykin.ru/posts/demo_database_sql_foundation) локально PostgreSQL
2. Создать тенстовую БД по [инструкции](https://www.asozykin.ru/posts/demo_database_sql_foundation#rec267589724) 
3. В классе **DbData** (java/ru/iaygi/db/data/DbData.java) в переменной **dbPassword** прописать свой пароль для соединения с PostgreSQL  

## Обучение

План  обучения:

1. Написание тестов для API
2. Написание тестов для UI
3. Настройка отчётов в Allure Report
4. Настройка Selenoid
5. Интеграция с Allure TestOps
6. Интеграция c CI/CD
7. Тестирование БД (JDBC)