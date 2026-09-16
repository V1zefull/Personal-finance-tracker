# pft

Spring Boot 4.1.0, Java 21, Gradle 8.14.5.

## Открытие в IntelliJ IDEA

Откройте `build.gradle.kts` через **File → Open → Open as Project**
и дождитесь синхронизации Gradle. В настройках **Build Tools → Gradle**
выберите **Distribution: Wrapper** и **Gradle JVM: JDK 21**.
Отдельно устанавливать Gradle не нужно: Wrapper загрузит его при первом запуске.
Версия JVM для Gradle также закреплена в `gradle/gradle-daemon-jvm.properties`:
используется установленный JDK 21. Java toolchain в `build.gradle.kts` задаёт
версию компилятора отдельно от JVM, на которой запускается Gradle.

## Сборка и запуск

Команды для PowerShell из корня проекта:

```powershell
.\gradlew.bat build
.\gradlew.bat bootRun
```

Для Linux/macOS используйте `sh ./gradlew build` и `sh ./gradlew bootRun`.

Приложению и тесту `contextLoads` нужен PostgreSQL. Задайте подключение
в `src/main/resources/application.properties` или через переменные окружения:

```powershell
$env:SPRING_DATASOURCE_URL = 'jdbc:postgresql://localhost:5433/dbName'
$env:SPRING_DATASOURCE_USERNAME = 'your_user'
$env:SPRING_DATASOURCE_PASSWORD = 'your_password'
.\gradlew.bat bootRun
```

Сборка JAR и компиляция тестов без подключения к базе:

```powershell
.\gradlew.bat assemble testClasses
```

Исполняемый JAR: `build/libs/pft-0.0.1-SNAPSHOT.jar`.
