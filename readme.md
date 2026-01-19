## Технологии
<ul>
  <li>Java 17, Maven</li>
  <li>Spring boot 3</li>
  <li>Spring security</li>
  <li>Hibernate</li>
  <li>H2 database</li>
  <li>Lombok</li>
  <li>Mapstract</li>
</ul>

<hr>

# REST

## Добавление пользователя в базу, доступно без авторизации
POST http://localhost:8080/users  
Content-Type: application/json  

{  
  "login": "root",  
  "password": "root",  
  "role": "ADMIN"  
}  

## Авторизация (не может быть одинаковых login)
POST http://localhost:8080/login  
Content-Type: application/x-www-form-urlencoded  

username=root&password=root  

## Logout
POST http://localhost:8080/logout  

## Получение списка пользователей, отфильтрованного по ролям (ADMIN, USER)
GET http://localhost:8080/users?role=ADMIN  
Если роли нет, возвращается полный список пользователей

## Обновление профиля пользователя
PUT http://localhost:8080/users/{id}  
Content-Type: application/json  

{  
  "login": "dododo",  
  "password": "4321",  
  "role": "USER"  
}  

## Получение конкретного профиля
GET http://localhost:8080/users/{id}  

## Удаление пользователя (невозможно, если текущий пользователь не имеет роли ADMIN)
DELETE http://localhost:8080/users/{id}
