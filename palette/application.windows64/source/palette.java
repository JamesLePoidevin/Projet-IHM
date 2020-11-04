import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import fr.dgac.ivy.*; 
import java.util.ArrayList; 
import java.util.List; 
import java.awt.Point; 

import fr.dgac.ivy.*; 
import fr.dgac.ivy.tools.*; 
import gnu.getopt.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class palette extends PApplet {







// data
Ivy bus;
String message= "";

String couleur = "";
String verbe = "";
String forme = "";
String pointage = "";
List<Form> liste;
boolean clicked = false;
float confiance;

boolean flag = false;

public void setup(){
  
  fill(255);
  background(255);
  liste = new ArrayList();

  try
  {
    bus = new Ivy("demo", " demo_processing is ready", null);
    bus.start("127.255.255.255:2010");

    bus.bindMsg("sra5 Parsed=(.*) Confidence=(.*) NP=.* Num_A=.*", new IvyMessageListener()
    {
      public void receive(IvyClient client, String[] args)
      {
        final String SEPARATEUR = " | ";
        final String SEPARATEURFLOAT = ",";
        message = args[0];
        
        String confianceString= args[1];
        String nombreString[] = confianceString.split(SEPARATEURFLOAT);
        confianceString = nombreString[0] + "." + nombreString[1];
        confiance = PApplet.parseFloat(confianceString);
        
        if(confiance > 0.60f){
          String mots[] = message.split(SEPARATEUR);
          verbe = mots[0];
          if(!(mots[2].equals("undefined") && flag)) forme = mots[2];
          couleur = mots[4];
          pointage = mots[6];
          if(!(verbe.equals("creer")) || (verbe.equals("creer") && !(forme.equals("undefined")))){
            switch(verbe){
              //Appelle la fonction déplacer qui permet de déplacer la forme au niveau de la souris.
              case "creer":
                form(forme);
              break;
              
              //Appelle la fonction déplacer qui permet de déplacer la forme au niveau de la souris.
              case "deplacer":
                deplacer();
              break;
              
              //Si l'utilisateur dit le mot supprimer. La forme qui ce trouve a l'endroit de la souris sera supprimer
              case "supprimer":
                for (int i=0;i<liste.size();i++){
                  if(liste.get(i).over(new Point(mouseX, mouseY))){
                    liste.remove(liste.get(i));
                  }
                }
              break;
              case "supprimerT":
                liste.clear();
              break; 
            }
          flag =false;
          }else{flag = true;}
        }
        try
        {
          bus.sendMsg("Demo_Processing Feedback=ok");
        }
        catch (IvyException ie) {
        }
      }
    }
    );
    
    //Gestion de l'arriver des données ICAR
    bus.bindMsg("ICAR Gesture=(.*)", new IvyMessageListener()
    {
      public void receive(IvyClient client, String[] args)
      {
        forme= args[0];
        println(forme);
        if(verbe.equals("creer") && !(forme.equals("undefined"))){
        switch(verbe){
          case "creer":
            form(forme);
          break;
          case "deplacer":
              deplacer();
          break;
          case "supprimer":
              for (int i=0;i<liste.size();i++){
                    if(liste.get(i).over(new Point(mouseX, mouseY))){
                      liste.remove(liste.get(i));
                    }
              }
          break;
          case "supprimerT":
              liste.clear();
          break; 
          }
        flag =false;
        }else{flag = true;}
      
    try{
      bus.sendMsg("Demo_Processing Feedback=ok");
    }catch (IvyException ie) {
    }
      }
    }
    );
  }
  catch (IvyException ie)
  {
  }
}

public void draw() {   
  background(255);
  /* afficher tous les objets */
  for (int i=0;i<liste.size();i++) // on affiche les objets de la liste
    (liste.get(i)).update();
}

public void deplacer(){
  if(pointage.equals("undefined")){
    for (int i=0;i<liste.size();i++){
      if(liste.get(i).over(new Point(mouseX, mouseY))){
        while(clicked == false){
          liste.get(i).p = new Point(mouseX, mouseY);
          draw();
        }
        clicked = false;
      }
    }
  } 
}

public void form(String forme){
  switch(forme){
    case "rectangle":
      if(pointage.equals("undefined")){
        Form f = new rectangle(couleur, new Point(50, 50));
        liste.add(f);
      }else if(pointage.equals("I")){
        Form f = new rectangle(couleur, new Point(mouseX, mouseY));
        liste.add(f);
      }else if(pointage.equals("D")){
        for (int i=0;i<liste.size();i++){
        if(liste.get(i).over(new Point(mouseX, mouseY))){
          println("a cote");
          Point p =new Point((int)liste.get(i).p.getX() + 70,(int)liste.get(i).p.getY());
          Form f = new rectangle(couleur, p);
          liste.add(f);
        }
      }
      
      }else if(pointage.equals("N")){
        for (int i=0;i<liste.size();i++){
        if(liste.get(i).over(new Point(mouseX, mouseY))){
          println("a cote");
          Point p =new Point((int)liste.get(i).p.getX(),(int)liste.get(i).p.getY()- 70);
          Form f = new rectangle(couleur, p);
          liste.add(f);
        }
      }
      
      }else if(pointage.equals("S")){
        for (int i=0;i<liste.size();i++){
          if(liste.get(i).over(new Point(mouseX, mouseY))){
            println("a cote");
            Point p =new Point((int)liste.get(i).p.getX() ,(int)liste.get(i).p.getY()+ 70);
            Form f = new rectangle(couleur, p);
            liste.add(f);
          }
        }
      }
    break;
    
    case "cercle":
      if(pointage.equals("undefined")){
        Form f = new cercle(couleur, new Point(50, 50));
        liste.add(f);
      }else if(pointage.equals("I")){
        Form f = new cercle(couleur, new Point(mouseX, mouseY));
        liste.add(f);
      }else if (pointage.equals("D")){
        for (int i=0;i<liste.size();i++){
         if(liste.get(i).over(new Point(mouseX, mouseY))){ 
           Point p =new Point((int)liste.get(i).p.getX() + 100,(int)liste.get(i).p.getY());
           Form f = new cercle(couleur, p);
           liste.add(f);
         }
         }
        }else if (pointage.equals("N")){
        for (int i=0;i<liste.size();i++){
         if(liste.get(i).over(new Point(mouseX, mouseY))){ 
           Point p =new Point((int)liste.get(i).p.getX(),(int)liste.get(i).p.getY()-100);
           Form f = new cercle(couleur, p);
           liste.add(f);
         }
         }
        }else if (pointage.equals("S")){
        for (int i=0;i<liste.size();i++){
         if(liste.get(i).over(new Point(mouseX, mouseY))){ 
           Point p =new Point((int)liste.get(i).p.getX(),(int)liste.get(i).p.getY()+100);
           Form f = new cercle(couleur, p);
           liste.add(f);
         }
         }
        }
      
        
    break;
    
    case "triangle":
      if(pointage.equals("undefined")){
        Form f = new triangle(couleur, new Point(50, 50));
        liste.add(f);
      }else if(pointage.equals("I")){
        Form f = new triangle(couleur, new Point(mouseX, mouseY));
        liste.add(f);
      }else if (pointage.equals("S")){
        for (int i=0;i<liste.size();i++){
         if(liste.get(i).over(new Point(mouseX, mouseY))){ 
           Point p =new Point((int)liste.get(i).p.getX(),(int)liste.get(i).p.getY()+100);
           Form f = new triangle(couleur, p);
           liste.add(f);
         }
         }
        }else if (pointage.equals("N")){
        for (int i=0;i<liste.size();i++){
         if(liste.get(i).over(new Point(mouseX, mouseY))){ 
           Point p =new Point((int)liste.get(i).p.getX(),(int)liste.get(i).p.getY()-100);
           Form f = new triangle(couleur, p);
           liste.add(f);
         }
         }
        }else if (pointage.equals("D")){
        for (int i=0;i<liste.size();i++){
         if(liste.get(i).over(new Point(mouseX, mouseY))){ 
           Point p =new Point((int)liste.get(i).p.getX()+100,(int)liste.get(i).p.getY());
           Form f = new triangle(couleur, p);
           liste.add(f);
         }
         }
        }
    break;
  }
}

  public void mouseClicked() {
    clicked = true;
  }
abstract class Form{
  public int couleur;
  public Point p;
  
  public Form(){
    int[] c = convert_couleur("noir");
    this.couleur = color(c[0],c[1],c[2]);
    this.p = new Point(50,50);
  }
  
  public Form(String coul){
    if(coul.equals("undefined")){
      int[] c = convert_couleur("noir");
      this.couleur = color(c[0],c[1],c[2]);
    }else{
      int[] c = convert_couleur(coul);
      this.couleur = color(c[0],c[1],c[2]);
    }
    p = new Point(50,50);
  }
  
  public Form(String coul,Point p){
    if(coul.equals("undefined")){
      int[] c = convert_couleur("noir");
      this.couleur = color(c[0],c[1],c[2]);
    }else{
      int[] c = convert_couleur(coul);
      this.couleur = color(c[0],c[1],c[2]);
    }
    this.p = p;
  }
  
    public Form(Point p){
      int[] c = convert_couleur("noir");
      this.couleur = color(c[0],c[1],c[2]);
      this.p = p;
  }
  
  public abstract void update();
  public abstract boolean over(Point p);
  
  public int[] convert_couleur(String coul){
    int r = 0 ;
    int b = 0 ;
    int g = 0 ;
    switch(coul){
      case "bleu":
        b=255;
      break;
      case "rouge":
        r=255;
      break;
      case "vert":
        g=255;
      break;
      default:
      break;
    }
    int[] resu = {r,g,b};
      return resu;
  }
}
public class cercle extends Form{
  public int rayon=40;
  
  public cercle(){
    super(); 
  }
  
  public cercle(String coul){
    super(coul,new Point(50,50));
  }
  
  public cercle(String coul,Point P){
    super(coul,P);
  }
  
  public cercle(Point P){
    super("noir",P);
  }
  
  public void update() {
    fill(this.couleur);
    circle((int) this.p.getX(),(int) this.p.getY(),this.rayon);
  }  
  
  public boolean over(Point p1){
        // vérifier que le cercle est cliqué
   PVector OM= new PVector( (int) (p1.getX() - this.p.getX()),(int) (p1.getY() - this.p.getY())); 
   if (OM.mag() <= this.rayon/2)
     return(true);
   else 
     return(false);
  }
}
public class rectangle extends Form{
  public int taillex = 50;
  public int tailley = 50;
  
  public rectangle(){
    super(); 
  }
  
  public rectangle(String coul){
    super(coul,new Point(50,50));
  }
  
  public rectangle(Point P){
    super("noir",P);
  }
  
  public rectangle(String coul,Point P){
    super(coul,P);
  }
  
  public void update() {
    fill(this.couleur);
    rect((int) this.p.getX(),(int) this.p.getY(),this.taillex,this.tailley);
  }  
  
   public boolean over(Point p1){
   int x= (int) p1.getX();
    int y= (int) p1.getY();
    int x0 = (int) this.p.getX();
    int y0 = (int) this.p.getY();
    
    // vérifier que le rectangle est cliqué
    if ((x>x0) && (x<x0+this.taillex) && (y>y0) && (y<y0+this.tailley))
      return(true);
    else  
      return(false);
      
   }
}
public class triangle extends Form{
  Point A, B,C;
  
  public triangle(){
    super();
    A = new Point();    
    A.setLocation(50,50);
    B = new Point();    
    B.setLocation(A);
    C = new Point();    
    C.setLocation(A);
    B.translate(40,60);
    C.translate(-40,60);
  }
  
  public triangle(String coul){
    super(coul,new Point(50,50));
    A = new Point();    
    A.setLocation(50,50);
    B = new Point();    
    B.setLocation(A);
    C = new Point();    
    C.setLocation(A);
    B.translate(40,60);
    C.translate(-40,60);
  }
  
  public triangle(String coul,Point p){
    super(coul,p);
    A = new Point();    
    A.setLocation(p);
    B = new Point();    
    B.setLocation(A);
    C = new Point();    
    C.setLocation(A);
    B.translate(40,60);
    C.translate(-40,60);
  }
  
  public triangle(Point p){
    super("noir",p);
    A = new Point();    
    A.setLocation(p);
    B = new Point();    
    B.setLocation(A);
    C = new Point();    
    C.setLocation(A);
    B.translate(40,60);
    C.translate(-40,60);
  }
  
  public void update() {
    fill(this.couleur);
    triangle((float) A.getX(), (float) A.getY(), (float) B.getX(), (float) B.getY(), (float) C.getX(), (float) C.getY());
  }  
  
  public boolean over(Point M){
    
    
    // vérifier que le triangle est cliqué
    
    PVector AB= new PVector( (int) (B.getX() - A.getX()),(int) (B.getY() - A.getY())); 
    PVector AC= new PVector( (int) (C.getX() - A.getX()),(int) (C.getY() - A.getY())); 
    PVector AM= new PVector( (int) (M.getX() - A.getX()),(int) (M.getY() - A.getY())); 
    
    PVector BA= new PVector( (int) (A.getX() - B.getX()),(int) (A.getY() - B.getY())); 
    PVector BC= new PVector( (int) (C.getX() - B.getX()),(int) (C.getY() - B.getY())); 
    PVector BM= new PVector( (int) (M.getX() - B.getX()),(int) (M.getY() - B.getY())); 
    
    PVector CA= new PVector( (int) (A.getX() - C.getX()),(int) (A.getY() - C.getY())); 
    PVector CB= new PVector( (int) (B.getX() - C.getX()),(int) (B.getY() - C.getY())); 
    PVector CM= new PVector( (int) (M.getX() - C.getX()),(int) (M.getY() - C.getY())); 
    
    if ( ((AB.cross(AM)).dot(AM.cross(AC)) >=0) && ((BA.cross(BM)).dot(BM.cross(BC)) >=0) && ((CA.cross(CM)).dot(CM.cross(CB)) >=0) ) { 
      return(true);
    }
    else
      return(false);
  }
}
  public void settings() {  size(1900, 950); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "palette" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
