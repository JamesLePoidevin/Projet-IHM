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
