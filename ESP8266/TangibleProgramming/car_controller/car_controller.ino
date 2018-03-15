#include <stdio.h>
#include <ESP8266WebServer.h>
#include <ArduinoJson.h>

#define HTTP_REST_PORT 80
#define WIFI_RETRY_DELAY 500
#define MAX_WIFI_INIT_RETRY 50

struct Led {
    byte id;
    byte gpio;
    byte status;
} led_resource;


struct Car {
    byte id;
    byte gpio_right_1;
    byte gpio_right_2;    
    byte gpio_left_1;
    byte gpio_left_2;
    byte status;
} car_resource;

const char* wifi_ssid = "CarController";
const char* wifi_passwd = "12345678";

String sessionID = "";

ESP8266WebServer http_rest_server(HTTP_REST_PORT);

void init_led_resource()
{
    led_resource.id = 0;
    led_resource.gpio = 5;
    led_resource.status = LOW;
    pinMode(5, OUTPUT);
    
}

void init_car_resource()
{
    car_resource.id = 1;
    car_resource.gpio_right_1 = 12;
    car_resource.gpio_right_2 = 14;
    car_resource.gpio_left_1 = 13;
    car_resource.gpio_left_2 = 15;    
    car_resource.status = 0;
    pinMode(12, OUTPUT);
    pinMode(13, OUTPUT);
    pinMode(14, OUTPUT);
    pinMode(15, OUTPUT);    
}

int init_wifi() {
    int retries = 0;

    Serial.println("Connecting to WiFi AP..........");

    WiFi.mode(WIFI_AP);
    WiFi.softAP(wifi_ssid, wifi_passwd);
    IPAddress myIP = WiFi.softAPIP(); //Get IP address
    Serial.print("HotSpt IP:");
    Serial.println(myIP);
    return WiFi.status(); // return the WiFi connection status
}


void post_instructionsSet() {
    StaticJsonBuffer<500> jsonBuffer;
    String post_body = http_rest_server.arg("plain");
    Serial.println(post_body);

    JsonObject& jsonBody = jsonBuffer.parseObject(http_rest_server.arg("plain"));

    Serial.print("HTTP Method: ");
    Serial.println(http_rest_server.method());
    
    if (!jsonBody.success()) {
        Serial.println("error in parsin json body");
        http_rest_server.send(400);
    }
    else {   
        if (http_rest_server.method() == HTTP_POST) {
            if ((jsonBody["sessionID"] == sessionID)) {
                Serial.println("sessionID: ");
                String a = jsonBody["sessionID"];
                Serial.println(a);
                JsonArray& intructions= jsonBody["instructions"];
                int arraySize =  intructions.size();
                Serial.println("Instructions: ");
                  for (int i = 0; i< arraySize; i++){
                 
                  String singleInstruction=intructions[i];
                  Serial.println(singleInstruction);
                  if(singleInstruction == "ON"){
                    led_on();
                    }else if(singleInstruction == "OFF"){
                      led_off();
                      }else if(singleInstruction == "UP"){
                      car_forward();
                      }else if(singleInstruction == "DOWN"){
                      car_backward();
                      }else if(singleInstruction == "LEFT"){
                      car_turn_left();
                      }else if(singleInstruction == "RIGHT"){
                      car_turn_right();
                      }
                 
                }
                http_rest_server.send(200);
            }else{
              http_rest_server.send(401);
              }
        }
        else
              http_rest_server.send(404);
        }
}

void led_on() {
  digitalWrite(led_resource.gpio, 1);
  delay(5000);
  }
void led_off() {
  digitalWrite(led_resource.gpio, 0);
  delay(5000);
  }  
void car_forward() {
  digitalWrite(car_resource.gpio_right_1, 1);
  digitalWrite(car_resource.gpio_right_2, 0);  
  digitalWrite(car_resource.gpio_left_1, 1);
  digitalWrite(car_resource.gpio_left_2, 0);   
  delay(3000);
  digitalWrite(car_resource.gpio_right_1, 0);
  digitalWrite(car_resource.gpio_right_2, 0);  
  digitalWrite(car_resource.gpio_left_1, 0);
  digitalWrite(car_resource.gpio_left_2, 0);     
  }  
void car_backward() {
  digitalWrite(car_resource.gpio_right_1, 0);
  digitalWrite(car_resource.gpio_right_2, 1);  
  digitalWrite(car_resource.gpio_left_1, 0);
  digitalWrite(car_resource.gpio_left_2, 1);   
  delay(3000);
  digitalWrite(car_resource.gpio_right_1, 0);
  digitalWrite(car_resource.gpio_right_2, 0);  
  digitalWrite(car_resource.gpio_left_1, 0);
  digitalWrite(car_resource.gpio_left_2, 0);   
  }  
void car_turn_left() {
  digitalWrite(car_resource.gpio_right_1, 1);
  digitalWrite(car_resource.gpio_right_2, 0);  
  digitalWrite(car_resource.gpio_left_1, 0);
  digitalWrite(car_resource.gpio_left_2, 0);   
  delay(3000);
  digitalWrite(car_resource.gpio_right_1, 0);
  digitalWrite(car_resource.gpio_right_2, 0);  
  digitalWrite(car_resource.gpio_left_1, 0);
  digitalWrite(car_resource.gpio_left_2, 0);   
  }    
void car_turn_right() {
  digitalWrite(car_resource.gpio_right_1, 0);
  digitalWrite(car_resource.gpio_right_2, 0);  
  digitalWrite(car_resource.gpio_left_1, 1);
  digitalWrite(car_resource.gpio_left_2, 0);   
  delay(3000);
  digitalWrite(car_resource.gpio_right_1, 0);
  digitalWrite(car_resource.gpio_right_2, 0);  
  digitalWrite(car_resource.gpio_left_1, 0);
  digitalWrite(car_resource.gpio_left_2, 0);   
  }    

void config_rest_server_routing() {
    http_rest_server.on("/", HTTP_GET, []() {
        http_rest_server.send(200, "text/html",
            "Welcome to the ESP8266 REST Web Server");
    });
    http_rest_server.on("/api/v1/instructionsSet", HTTP_POST, post_instructionsSet);
}

void setup(void) {
    Serial.begin(115200);

    init_led_resource();
    init_car_resource();
    if (init_wifi() == WL_CONNECTED) {
        Serial.print("Connetted to ");
        Serial.print(wifi_ssid);
        Serial.print("--- IP: ");
        Serial.println(WiFi.localIP());
    }
    else {
        //Serial.print("Error connecting to: ");
        //Serial.println(wifi_ssid);
    }

    config_rest_server_routing();

    http_rest_server.begin();
    Serial.println("HTTP REST Server Started");
}

void loop(void) {
    http_rest_server.handleClient();
}
