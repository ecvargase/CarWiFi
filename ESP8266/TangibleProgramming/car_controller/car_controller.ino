#include <stdio.h>
#include <ESP8266WebServer.h>
#include <ArduinoJson.h>
#include <DHT.h>

#define HTTP_REST_PORT 80
#define WIFI_RETRY_DELAY 500
#define MAX_WIFI_INIT_RETRY 50

const char* wifi_ssid = "CarController";
const char* wifi_passwd = "12345678";
String sessionID = "";
ESP8266WebServer http_rest_server(HTTP_REST_PORT);

// Definimos el pin digital donde se conecta el sensor
#define DHTPIN 5
// Dependiendo del tipo de sensor
#define DHTTYPE DHT11
// Inicializamos el sensor DHT11
DHT dht(DHTPIN, DHTTYPE);

#define Motion_Led_Pin 16                // choose the pin for the LED
#define PIR_Sensor_Pin 4                // choose the input pin (for PIR sensor)
boolean Motion_Already_Detected = false;// we start, assuming that no motion is detected
int PIR_Sensor_Status = 0;              // variable for reading the current PIR_Sensor_Status
unsigned long Duration_Of_Motion;
String Message;
boolean real_motion_detected = false;
    
struct Led {
    byte id;
    byte gpio;
    byte status;
} led_resource,led_resource_2;

struct Car {
    byte id;
    byte gpio_right_1;
    byte gpio_right_2;    
    byte gpio_left_1;
    byte gpio_left_2;
    byte status;
} car_resource;

struct Proximity_Ultrasonic_Sensor {
    byte id;
    byte gpio_pinecho;
    byte gpio_pintrigger;    
    byte status;
} proximity_sensor;


void setup(void) {
    Serial.begin(115200);
    init_car_resource();
    init_proximity_sensor();
    init_led_resources();
    pinMode(PIR_Sensor_Pin, INPUT);
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
void init_led_resources()
{
    led_resource.id = 0;
    led_resource.gpio = 16;
    led_resource.status = 0;
    pinMode(led_resource.gpio, OUTPUT);
    led_resource_2.id = 1;
    led_resource_2.gpio = 10;
    led_resource_2.status = 0;
    pinMode(led_resource_2.gpio, OUTPUT);  
    pinMode(9, OUTPUT);    
    Serial.println("led_resource_2..........");
    Serial.println(led_resource_2.gpio);
}

void init_car_resource()
{
    car_resource.id = 0;
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

void init_proximity_sensor()
{
    proximity_sensor.id = 0;
    proximity_sensor.gpio_pinecho = 2;
    proximity_sensor.gpio_pintrigger = 0;    
    proximity_sensor.status = 0;
    pinMode(proximity_sensor.gpio_pinecho, INPUT);
    pinMode(proximity_sensor.gpio_pintrigger, OUTPUT);    

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
                    led2_on();
                    }else if(singleInstruction == "OFF"){
                      led_off();
                      led2_off();
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
  digitalWrite(led_resource.gpio, HIGH);
  delay(5000);
  }
void led_off() {
  digitalWrite(led_resource.gpio, LOW); 
  delay(5000);
  }  
  
void led2_on() {
  digitalWrite(led_resource_2.gpio, HIGH);
  digitalWrite(9, HIGH);
  delay(5000);
  }
void led2_off() {
  digitalWrite(led_resource_2.gpio, LOW);
  digitalWrite(9, LOW); 
  delay(5000);
  }  
void car_forward() {
  digitalWrite(car_resource.gpio_right_1, LOW);
  digitalWrite(car_resource.gpio_right_2, HIGH);  
  digitalWrite(car_resource.gpio_left_1, LOW);
  digitalWrite(car_resource.gpio_left_2, HIGH);   
  delay(3000);
  digitalWrite(car_resource.gpio_right_1, LOW);
  digitalWrite(car_resource.gpio_right_2, LOW);  
  digitalWrite(car_resource.gpio_left_1, LOW);
  digitalWrite(car_resource.gpio_left_2, LOW);     
  }  
void car_backward() {
  digitalWrite(car_resource.gpio_right_1, HIGH);
  digitalWrite(car_resource.gpio_right_2, LOW);  
  digitalWrite(car_resource.gpio_left_1, HIGH);
  digitalWrite(car_resource.gpio_left_2, LOW);   
  delay(3000);
  digitalWrite(car_resource.gpio_right_1, LOW);
  digitalWrite(car_resource.gpio_right_2, LOW);  
  digitalWrite(car_resource.gpio_left_1, LOW);
  digitalWrite(car_resource.gpio_left_2, LOW);  
  }  
void car_turn_left() {
  digitalWrite(car_resource.gpio_right_1, HIGH);
  digitalWrite(car_resource.gpio_right_2, LOW);  
  digitalWrite(car_resource.gpio_left_1, LOW);
  digitalWrite(car_resource.gpio_left_2, LOW);   
  delay(3000);
  digitalWrite(car_resource.gpio_right_1, LOW);
  digitalWrite(car_resource.gpio_right_2, LOW);  
  digitalWrite(car_resource.gpio_left_1, LOW);
  digitalWrite(car_resource.gpio_left_2, LOW);   
  }    
void car_turn_right() {
  digitalWrite(car_resource.gpio_right_1, LOW);
  digitalWrite(car_resource.gpio_right_2, LOW);  
  digitalWrite(car_resource.gpio_left_1, HIGH);
  digitalWrite(car_resource.gpio_left_2, LOW);   
  delay(3000);
  digitalWrite(car_resource.gpio_right_1, LOW);
  digitalWrite(car_resource.gpio_right_2, LOW);  
  digitalWrite(car_resource.gpio_left_1, LOW);
  digitalWrite(car_resource.gpio_left_2, LOW);  
  }    
void read_proximity_value() {
  unsigned int tiempo, distancia;
  digitalWrite(proximity_sensor.gpio_pintrigger, LOW);
  delayMicroseconds(2);
  digitalWrite(proximity_sensor.gpio_pintrigger, HIGH);
  // EL PULSO DURA AL MENOS 10 uS EN ESTADO ALTO
  delayMicroseconds(10);
  digitalWrite(proximity_sensor.gpio_pintrigger, LOW);
 
  // MEDIR EL TIEMPO EN ESTADO ALTO DEL PIN "ECHO" EL PULSO ES PROPORCIONAL A LA DISTANCIA MEDIDA
  tiempo = pulseIn(proximity_sensor.gpio_pinecho, HIGH);
 
  // LA VELOCIDAD DEL SONIDO ES DE 340 M/S O 29 MICROSEGUNDOS POR CENTIMETRO
  // DIVIDIMOS EL TIEMPO DEL PULSO ENTRE 58, TIEMPO QUE TARDA RECORRER IDA Y VUELTA UN CENTIMETRO LA ONDA SONORA
  distancia = tiempo / 58;
 
  // ENVIAR EL RESULTADO AL MONITOR SERIAL
  Serial.print(distancia);
  Serial.println(" cm");
 
  // ENCENDER EL LED CUANDO SE CUMPLA CON CIERTA DISTANCIA
    if (distancia <= 15) {
      digitalWrite(led_resource_2.gpio, HIGH);
    } else {
      digitalWrite(led_resource_2.gpio, LOW);
    }
    String response_init = "{";
    String distance_init= "\"distance\": ";
    String response_unit = "\"unit\":\"cm\",";
    String response_end = "}";
    String response_all = response_init+response_unit+distance_init+distancia+response_end;
   http_rest_server.send(200, "application/json",response_all);    
  }    
void read_temp_hum() {
    // Leemos la humedad relativa
      float h = dht.readHumidity();
      // Leemos la temperatura en grados centígrados (por defecto)
      float t = dht.readTemperature();
      // Leemos la temperatura en grados Fahrenheit
      float f = dht.readTemperature(true);
     
      // Comprobamos si ha habido algún error en la lectura
      if (isnan(h) || isnan(t) || isnan(f)) {
        Serial.println("Error obteniendo los datos del sensor DHT11");
        return;
      }
     
      // Calcular el índice de calor en Fahrenheit
      float hif = dht.computeHeatIndex(f, h);
      // Calcular el índice de calor en grados centígrados
      float hic = dht.computeHeatIndex(t, h, false);
     
      Serial.print("Humedad: ");
      Serial.print(h);
      Serial.print(" %\t");
      Serial.print("Temperatura: ");
      Serial.print(t);
      Serial.print(" *C ");
      Serial.print(f);
      Serial.print(" *F\t");
      Serial.print("Índice de calor: ");
      Serial.print(hic);
      Serial.print(" *C ");
      Serial.print(hif);
      Serial.println(" *F");  
      String response_init = "{";
      String comma = ",";
      String response_temp= "\"temperature\": ";
      String response_humidity = "\"humidity\": ";
      String response_end = "}";
      String response_all = response_init+response_temp+t+comma+response_humidity+h+response_end;
      http_rest_server.send(200, "application/json",response_all);    

  }  
void motion_detection() {
    while(real_motion_detected != true){
      PIR_Sensor_Status = digitalRead(PIR_Sensor_Pin);  // read PIR Sensor status
   
    if (PIR_Sensor_Status == HIGH) {                     // if the PIR Sensor is HIGH
   
      digitalWrite(Motion_Led_Pin, HIGH);  // turn LED ON
      if (Motion_Already_Detected  == false) { //If we did not have motion before -> then change the current motion state to true
        Serial.println("Posible Motion detected! [start]");
        Motion_Already_Detected  = true;
        Duration_Of_Motion = millis();
      }
   
    } else {                     // if the PIR Sensor status is LOW
   
      digitalWrite(Motion_Led_Pin, LOW); // turn LED OFF
      if (Motion_Already_Detected  == true){ //We had motion before so -> change the current motion state to false
        Duration_Of_Motion = millis() - Duration_Of_Motion;
        if(Duration_Of_Motion>1000){
        Message = "Real motion detected! Motion duration :  ";
        Message += Duration_Of_Motion;
        Message += " ms";
        Serial.println(Message);
        real_motion_detected= true;
        http_rest_server.send(200, "text/html",Message);
        }
        Motion_Already_Detected = false;
      }
    }
      delay(200);
    }
  }  
void config_rest_server_routing() {
    http_rest_server.on("/", HTTP_GET, []() {
        http_rest_server.send(200, "text/html",
            "Welcome to the ESP8266 REST Web Server");
    });
    http_rest_server.on("/api/v1/instructionsSet", HTTP_POST, post_instructionsSet);
    http_rest_server.on("/api/v1/proximity", HTTP_GET, read_proximity_value);
    http_rest_server.on("/api/v1/temperature", HTTP_GET, read_temp_hum);
    http_rest_server.on("/api/v1/motion", HTTP_GET, motion_detection);

}
