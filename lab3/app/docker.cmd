REM Собрать образ
docker build -t zotovkem86/car-otus:v5 .
REM Отправка образа в репозеторий
docker push zotovkem86/car-otus:v5
REM Сделать нагрузку на приложение
for(;;){ ab -n 50 -c 5 http://localhost/api/v1/cars/all; sleep 3;}
REM логин и пароль к графане user: admin pass: prom-operator
