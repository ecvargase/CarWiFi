#include <DHT.h>
 
// Definimos el pin digital donde se conecta el sensor
#define DHTPIN 5
// Dependiendo del tipo de sensor
#define DHTTYPE DHT11
 int led_status = 0;
// Inicializamos el sensor DHT11
DHT dht(DHTPIN, DHTTYPE);


void setup() {
  // Inicializamos comunicación serie
  Serial.begin(115200);
  pinMode(16, OUTPUT);
  // Comenzamos el sensor DHT
  dht.begin();
}

void loop() {
    // Esperamos 5 segundos entre medidas
  delay(5000);
  if(led_status == 0){
    led_status=1;
    }else {
      led_status=0;
      }
  digitalWrite(16, led_status);
 
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
 
}
