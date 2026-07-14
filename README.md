# User Authentication Service

1. Front end login to the Auth Service:  
   curl -X POST http://localhost:8080/api/v1/auth/login \  
     -H "Content-Type: application/json" \  
     -d '{"username":"admin", "password":"adminpass"}'  
     
   Response: You receive an access_token string signed using the Private Key.


2. The Frontend passes that token to the Resource Service:  
   curl http://localhost:8081/api/v1/business/user/data \  
     -H "Authorization: Bearer <PASTE_YOUR_ACCESS_TOKEN_HERE>"  

   Response:  
    {  
      "status": "Success",  
      "user": "admin",  
      "message": "Data fetched from stateless business pod!",  
      "tokenIssuer": "http://auth-service-pod"  
    }
