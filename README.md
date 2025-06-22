# 🌦️ Weather Android App

Приложение погоды, разработанное на **Kotlin + Jetpack Compose**, с кэшированием, загрузкой фоновых изображений по погодным условиям и безопасным подключением к серверу через **HTTPS**.

## 📱 Особенности

- Отображение текущей погоды и прогноза.
- Поддержка нескольких городов.
- Кэширование погоды с проверкой срока годности (`TTL`).
- Автоматическое восстановление UI из кэша при запуске.
- Загрузка фонового изображения с сервера в зависимости от погодного состояния.
- Хранение фонового изображения в `DataStore` (в формате `Base64`).
- Безопасное соединение с сервером через HTTPS.
- ViewModel и Repository архитектура.

## 🔗 Backend-сервер

Для работы приложение подключается к серверу на Spring Boot:

👉 [Репозиторий сервера (Spring Boot)](https://github.com/Surft14/weatherserver.git)

Сервер предоставляет:
- JSON-ответы по текущей погоде.
- Фоновые изображения (`.png`) по погодному коду.
- Кросс-доменные HTTPS-запросы.
- Простое RESTful API.

## 🛠️ Стек технологий

- Kotlin
- Jetpack Compose
- ViewModel / State management
- Volley / Coil
- Android DataStore
- Base64, Bitmap
- HTTPS (с сертифицированным IP)

## 🚀 Запуск

1. Убедитесь, что сервер запущен и доступен.
2. Клонируйте этот репозиторий.
3. Откройте проект в Android Studio.
4. Запустите сборку и установите приложение на устройство.

## 📂 Структура проекта
```
.
├── app
|    ├── data
|    |    ├── const
|    |    ├── model
|    |    ├── DataStoreExtensions.kt
|    ├── logic
|    |    ├── cache
|    |    ├── geo
|    |    ├── image
|    |    ├── network
|    |    ├── repository
|    ├── ui
|    |    ├── screens
|    |    ├── theme
|    |    ├── viewmodel
|    ├── utils
|    |    ├── GeolocationUtils
|    |    ├── sha256.kt
|    |    ├── isNetworlAvailable.kt
|    ├── MainACtivity
|    ├── MyApp
|    ├── res
|    |    ├── drawable
|    |    ├── mipmap
|    |    ├── raw
|    |    ├── values
|    |    ├── xml    
├── build.gradle

```

## 👨‍💻 Автор

Разработка: [Surft14]  
Контакты: [Email: mr.darck31@gmail.com/TG: @Surft14]
    
