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
