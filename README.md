* Прохождение тестов - https://www.youtube.com/watch?v=sdJ894pgn2U
* Инструкция для IntellijIdea (далее idea)
1. Переходим в репозиторий с тестами, кликаем на "Clone or download", копируем ссылку
2. Открываем idea
3. Кликаем на "Get from Version Control"
4. В idea в поле "URL" вводим url репозитория из п.1 и кликаем "Clone"
5. Ждем окончания загрузки проекта и библиотек из мавен репозитория

* Запуск тестов:
1. Если сборщик "Maven" установлен на локальной машине, то запустить тесты можно через консоль прописав команду "{директория с проектом}\httpbin-text-example mvn test -DthreadCount={количество потоков}"
2. Иначе, в idea на нижней панели кликаем на "Terminal" и вводим команду "mvn test -DthreadCount={количество потоков}"
* В моем случае команда для запуска выглядит так "C:\Users\User\IdeaProjects\httpbin-text-example>mvn clean test -DthreadCount=10"
