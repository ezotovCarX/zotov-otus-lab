REM Собрать образ
docker build -t zotovkem86/car-otus:v5 .
REM Отправка образа в репозеторий
docker push zotovkem86/car-otus:v5
REM Посмотреть контейнеры в докере
docker -H tcp://172.24.55.163:2376 ps
REM на локальном
docker ps
REM Сделать нагрузку на приложение
for(;;){ ab -n 50 -c 5 http://localhost/api/v1/cars/all; sleep 3;}
REM логин и пароль к графане user: admin pass: prom-operator

REM Старт миникуба
minikube start --cpus=4 --memory=8g --vm-driver=hyperv --addons default-storageclass --addons storage-provisioner

REM Чтобы заиспользовать демон докера локальной машины нужно добавить в виндовую переменную окружения ip и порт из команды minikube ip
REM Прогнать тесты postman newman
newman run OtusLab7.postman_collection.json
REM Запустить скрипт установки приложения  skaffold
skaffold run
REM Удалить приложение через skaffold
skaffold delete
REM Посмотреть собранный helm chart
helm install myapp ./hello-chart --dry-run
REM Скачать зависимости хелма
helm dependency update ./carracing_chart

REM Для мониторинга отключаем 1 ингрес плагин 2 ставим прометей 3 ставим ингес контролер 4 форвардим порты
REM Включить ингресс
minikube addons enable ingress
REM Установить прометей
helm install prom prometheus-community/kube-prometheus-stack -f prometheus/prometheus.yaml --atomic
REM Установка ингресс
helm install nginx ingress-nginx/ingress-nginx -f prometheus/nginx-ingress.yaml --atomic
REM Пробросить порт сервиса
kubectl port-forward service/prom-grafana 9000:80
