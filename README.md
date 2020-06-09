* Прохождение тестов - https://www.youtube.com/watch?v=sdJ894pgn2U
* Инструкция для IntellijIdea (далее idea)
1. Переходим в репозиторий с тестами, кликаем на "Clone or download", копируем ссылку
2. Открываем idea
3. Кликаем на "Get from Version Control"
4. В idea в поле "URL" вводим url репозитория из п.1 (остальные поля можно не трогать) и кликаем "Clone"
5. Ждем окончания загрузки
6. idea может предложить загрузить из Maven-репозитория недостающие библиотеки находящиеся в "pom.xml", соглашаемся кликая на "Enable auto import"

* Запустить тесты можно следующим способом:
1. Если сборщик "Maven" установлен на локальной машине, то запустить тесты можно через консоль прописав команду "{директория с проектом}\httpbin-text-example mvn test -DthreadCount={количество потоков}"
2. Иначе, в idea на нижней панели кликаем на "Terminal" и вводим команду "mvn test -DthreadCount={количество потоков}"
* В моем случае команда для запуска выглядит так "C:\Users\User\IdeaProjects\httpbin-text-example>mvn clean test -DthreadCount=10"
* Ждем окончания тестов, в консоли появится строка "Results"
* [INFO] Tests run: 24, Failures: 0, Errors: 0, Skipped: 0
* Если локально установлен allure то можно посмотреть аллюр-отчет, набрав команду в командной строке "allure serve {путь до папки targer/allure-results}", ждем 3-5 сек, сгенерируется html-страница с результатами тестов
