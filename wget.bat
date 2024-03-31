:loop
 curl http://localhost:8081
 timeout /t 1
 curl http://localhost:8080
 goto loop