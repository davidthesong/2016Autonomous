import processing.net.*; 

Client myClient; 
String dataIn; 
String[] receivedNumbers;
void setup() { 

  myClient = new Client(this, "10.2.95.2", 5800); 
} 
 
void draw() { 

  if(myClient.available() > 0)
  { 
      dataIn = myClient.readString();
  
      receivedNumbers = split(dataIn,";");
      for(int i = 0; i < receivedNumbers.length; i++){
        System.out.println(receivedNumbers[i]);
      }
  }
  
} 
void stop(){
 myClient.stop(); 
}