 # User roles
 curl "http://localhost:8080/veikkaus/userrole/create?roleName=ADMIN"
 
 # Users
 curl "http://localhost:8080/veikkaus/user/create?email=snail&name=turo&password=passu"
 curl "http://localhost:8080/veikkaus/user/update?id=11&email=snail2&name=turza&password=passu2"
 
 # Players
 curl "http://localhost:8080/veikkaus/player/create?firstName=Eric&lastName=Cantona"
