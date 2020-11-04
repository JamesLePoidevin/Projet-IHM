
import fr.dgac.ivy.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

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

void setup(){
  size(1900, 950);
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
        confiance = float(confianceString);
        
        if(confiance > 0.60){
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

void draw() {   
  background(255);
  /* afficher tous les objets */
  for (int i=0;i<liste.size();i++) // on affiche les objets de la liste
    (liste.get(i)).update();
}

void deplacer(){
  //if(pointage.equals("undefined")){
    for (int i=0;i<liste.size();i++){
      if(liste.get(i).over(new Point(mouseX, mouseY))){
        while(clicked == false){
          liste.get(i).p = new Point(mouseX, mouseY);
        }
        draw();
        clicked = false;
      }
    }
  //} 
}

void form(String forme){
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
  couleur = "undefined";
  verbe = "undefined";
  forme = "undefined";
  pointage = "undefined";
  
}

  void mouseClicked() {
    clicked = true;
  }
