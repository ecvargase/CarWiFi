/**
   GeekFactory - "Construye tu propia tecnologia"
   Distribucion de materiales para el desarrollo e innovacion tecnologica
   www.geekfactory.mx
 
   EJEMPLO SENSOR ULTRASONICO 1
 
   REALIZA LA MEDICION DE DISTANCIA CON UN SENSOR ULTRASÓNICO HC-SR04 CONECTADO
   AL ARDUINO. ESTE EJEMPLO NO UTILIZA LIBRERIAS ESPECIALES PARA EL SENSOR, SOLAMENTE
   SE UTILIZA LA FUNCION "PULSEIN" PARA MEDIR LA LONGITUD DEL PULSO DE ECO.
 
*/
/* DECLARACION DE VARIABLES PARA PINES*/
const int pinecho = 2;
const int pintrigger = 0;
const int pinled = 16;
const int pinledb = 9; 
const int pinledc = 10;

/*/ VARIABLES PARA CALCULOS*/
unsigned int tiempo, distancia;
 
void setup() {
  /*/ PREPARAR LA COMUNICACION SERIAL*/
  Serial.begin(115200);
  // CONFIGURAR PINES DE ENTRADA Y SALIDA
  pinMode(pinecho, INPUT);
  pinMode(pintrigger, OUTPUT);
  pinMode(pinled, OUTPUT);
  pinMode(pinledb, OUTPUT);
  pinMode(pinledc, OUTPUT);
}
 
void loop() {
  // ENVIAR PULSO DE DISPARO EN EL PIN "TRIGGER"
  digitalWrite(pintrigger, LOW);
  delayMicroseconds(2);
  digitalWrite(pintrigger, HIGH);
  // EL PULSO DURA AL MENOS 10 uS EN ESTADO ALTO
  delayMicroseconds(10);
  digitalWrite(pintrigger, LOW);
 
  // MEDIR EL TIEMPO EN ESTADO ALTO DEL PIN "ECHO" EL PULSO ES PROPORCIONAL A LA DISTANCIA MEDIDA
  tiempo = pulseIn(pinecho, HIGH);
 
  // LA VELOCIDAD DEL SONIDO ES DE 340 M/S O 29 MICROSEGUNDOS POR CENTIMETRO
  // DIVIDIMOS EL TIEMPO DEL PULSO ENTRE 58, TIEMPO QUE TARDA RECORRER IDA Y VUELTA UN CENTIMETRO LA ONDA SONORA
  distancia = tiempo / 58;
 
  // ENVIAR EL RESULTADO AL MONITOR SERIAL
  Serial.print(distancia);
  Serial.println(" cm");
  delay(200);
 
  // ENCENDER EL LED CUANDO SE CUMPLA CON CIERTA DISTANCIA
  if (distancia <= 15) {
    digitalWrite(pinled, HIGH);
    digitalWrite(pinledb, HIGH);
    digitalWrite(pinledc, HIGH);
    delay(500);
  } else {
    digitalWrite(pinled, LOW);
    digitalWrite(pinledb, LOW);
    digitalWrite(pinledc, LOW);

  }
}
