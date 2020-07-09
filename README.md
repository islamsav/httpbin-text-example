#### Запуск тестов:
- Если сборщик ***Maven*** установлен на локальной машине, то запустить тесты можно через консоль прописав команду 
`{директория с проектом}\httpbin-text-example mvn test -DthreadCount={количество потоков}`
- Иначе, в idea на нижней панели кликаем на ***Terminal*** и вводим команду `mvn test -DthreadCount={количество потоков}`
- В моем случае команда для запуска выглядит так `C:\Users\User\IdeaProjects\httpbin-text-example>mvn clean test -DthreadCount=10`
