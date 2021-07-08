# Схема решения

![image-20200512154011363](./README.assets/image-20200512154011363.png)

###### Установка
namespace default
Деплой приложения аутентификации skaffold run auth/skaffold.yaml
Деплой приложения skaffold run app/skaffold.yaml
Тесты для newman nginx_forward_auth_postman_collection.json

###### Решение Forward-auth

1) Пользователь логинится по логину и паролю в приложении auth получая куку сессии
2) При последующих запросах на закрытые url nginx-ingress форвардит запрос с кукой в приложение auth. Если куки нет на метод signin
3) Приложение auth обогащает запрос заголовками и возвращает в nginx-ingress
4) nginx-ingress проверяет наличие прописанных заголовков и форвардит запросы или в auth (если их еще нет) или на защищенный url (если они уже получены)
