# springboot-fcm-xmpp
Send Notification using Firebase Cloud Messaging with help of XMPP

Get the senderId and server key from Firebase console .               
https://console.firebase.google.com/
Choose the desired Project.                                                                           
Project Overview -> Project Setting -> Cloud Messaging .                                                                                 
You can find the senderId and serverkey .                                                    
Replace senderId and serverKey in application.properties .                                     
senderid -> server_username                                  
serverKey -> server_key                                      

serverusername should be <b>senderid@gcm.googleapis.com</b>

