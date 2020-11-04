abstract class Form{
  public color couleur;
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
  
  abstract void update();
  abstract boolean over(Point p);
  
  int[] convert_couleur(String coul){
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
