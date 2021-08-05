### Сервис Аутентификации

Логин игрока
POST /api/v1/login :
body: { "login": "login",
"pass":"password",
"email":"qwe@aasd.ru"
}


Подтверждение почты
POST /api/v1/email/verify :
body: { "login": "login",
"code":"1234",
"email":"qwe@aasd.ru"
}

### Сервис управления профилем игрока
Получить информацию об игроке 
GET /api/v1/profile/dashboard

### Сервис управления парком авто
Получить список авто
GET /api/v1/cars/all

Получить список купленных авто
GET /api/v1/cars/bought

Выбрать машину для заезда
PUT /api/v1/cars/current/change?carId=1

### Сервис заездов
Стартовать гонку
POST /api/v1/race/start?raceId=1

Завершить гонку
PUT /api/v1/race/finish?raceId=1

### Сервис Магазин
Пополнить кошелек 
POST /api/v1/money/add
body{ 
"ticket":"sdfsadfsadgkjdfhbsakjdf",
"type": MONEY,
"total":1
}
