REM Собрать образ
docker build -t zotovkem86/car-otus:v4 .
REM Отправка образа в репозеторий
docker push zotovkem86/car-otus:v4
