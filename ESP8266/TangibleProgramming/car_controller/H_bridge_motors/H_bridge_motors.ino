//Code by Reichenstein7 (thejamerson.com)

//Keyboard Controls:
//
// 1 -Motor 1 Left
// 2 -Motor 1 Stop
// 3 -Motor 1 Right
//
// 4 -Motor 2 Left
// 5 -Motor 2 Stop
// 6 -Motor 2 Right

// Declare L298N Dual H-Bridge Motor Controller directly since there is not a library to load.

// Motor 1
const int dir1PinA = 13;
const int dir2PinA = 15;

// Motor 2
const int dir1PinB = 14;
const int dir2PinB = 12;

void setup() {   
pinMode(dir1PinB,OUTPUT);
pinMode(dir2PinB,OUTPUT);  
pinMode(dir1PinA,OUTPUT);
pinMode(dir2PinA,OUTPUT);
  // Setup runs once per reset
// initialize serial communication @ 115200 baud:
Serial.begin(115200);
delay(500);
//Define L298N Dual H-Bridge Motor Controller Pins
Serial.println("Define L298N Dual H-Bridge Motor Controller Pins");


}

void loop() {
// Initialize the Serial interface:

if (Serial.available() > 0) {
  Serial.println("Serial.read()");

int inByte = Serial.read();

switch (inByte) {

//______________Motor 1______________

case '1': // Motor 1 Forward
digitalWrite(dir1PinA, LOW);
digitalWrite(dir2PinA, HIGH);
Serial.println("Motor 1 Forward"); // Prints out “Motor 1 Forward” on the serial monitor
Serial.println("..."); // Creates a blank line printed on the serial monitor
break;

case '2': // Motor 1 Stop (Freespin)
digitalWrite(dir1PinA, LOW);
digitalWrite(dir2PinA, LOW);
Serial.println("Motor 1 Stop");
Serial.println("..."); // Creates a blank line printed on the serial monitor
break;

case '3': // Motor 1 Reverse
digitalWrite(dir1PinA, HIGH);
digitalWrite(dir2PinA, LOW);
Serial.println("Motor 1 Reverse");
Serial.println("..."); // Creates a blank line printed on the serial monitor
break;

//______________Motor 2______________

case '4': // Motor 2 Forward
digitalWrite(dir1PinB, LOW);
digitalWrite(dir2PinB, HIGH);
Serial.println("Motor 2 Forward");
Serial.println("..."); // Creates a blank line printed on the serial monitor
break;

case '5': // Motor 1 Stop (Freespin)
digitalWrite(dir1PinB, LOW);
digitalWrite(dir2PinB, LOW);
Serial.println("Motor 2 Stop");
Serial.println("..."); // Creates a blank line printed on the serial monitor
break;

case '6': // Motor 2 Reverse
digitalWrite(dir1PinB, HIGH);
digitalWrite(dir2PinB, LOW);
Serial.println("Motor 2 Reverse");
Serial.println("..."); // Creates a blank line printed on the serial monitor
break;

default:
// turn all the connections off if an unmapped key is pressed:
digitalWrite(dir1PinA, LOW);
digitalWrite(dir2PinA, LOW);
digitalWrite(dir1PinB, LOW);
digitalWrite(dir2PinB, LOW);
  }
    }
      }
