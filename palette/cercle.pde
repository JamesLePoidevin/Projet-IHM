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
