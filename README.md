# Запуск программы:

    mvn -X clean install - подготовка перед первым запуском (загрузка зависимостей и компиляция проекта)

    mvn "-Dpath=F:\1С\TEMP\SMS\файлСДанными.txt" exec:java - запуск выполнения программы. в переменной "path" указывается путь к текстовому файлу с номером телефона и текстом сообщения (тот файл, что будет из 1С сохраняться)
    
    Чтобы выполнить команду сборщика maven необходимо находиться в корневом каталоге программы.
    В 1С, если для запуска используется cmd, предлагаю построить команду запуска следующим образом: F: && cd F:\DEV\sms_sender_am && mvn "-Dpath=F:\1С\TEMP\SMS\файлСДанными.txt" exec:java