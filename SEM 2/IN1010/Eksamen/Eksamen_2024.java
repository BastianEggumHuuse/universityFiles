// Oppgave 2:
interface Motordrevet {
    
    int trekkKraft();
}

abstract class Fly implements Motordrevet {

    public final int MTOW, motorAntall, kraft;
    public String id;
    
    public Fly Neste;
    
    public Fly(String nId, int nMTOW,int nMotorAntall,int nTtrekkKraft){
        
        id = nId;
        MTOW = nMTOW;
        motorAntall = nMotorAntall;
        kraft = nTrekkKraft;
    }
    
    public int trekkKraft()
    {
        return(kraft)
    }
    
    public String hentID()
    {
        return id;
    }
    
    public int hentMotorAntall()
    {
        return motorAntall;
    }
    
    public int hentMTOW()
    {
        return MTOW;
    }
    
}

class SeilFly extends Fly{
    
    public final int minSynk;
    
    public SeilFly(String nId, int nMTOW,int nMotorAntall,int nTrekkKraft, int nMinSynk){
        
        super(nId,nMTOW,nMotorAntall,nTrekkKraft);
        minSynk = nMinSynk;
    }
}

abstract class MotorFly extends Fly{
    
    public MotorFly(String nId, int nMTOW,int nMotorAntall,int nTrekkKraft){
        
        super(nId,nMTOW,nMotorAntall,nTrekkKraft);
    }
}

class LasteFly extends MotorFly{
    
    public final int maxVekt;
    
    public LasteFly(String nId, int nMTOW,int nMotorAntall,int nTrekkKraft, int nMaxVekt){
        
        super(nId,nMTOW,nMotorAntall,nTrekkKraft);
        maxVekt = nMaxVekt;
    }
}

class PassasjerFly extends MotorFly{
    
    public final int maxPass;
    
    public LasteFly(String nId, int nMTOW,int nMotorAntall,int nTrekkKraft, int nMaxPass){
        
        super(nId,nMTOW,nMotorAntall,nTrekkKraft);
        maxPass = nMaxPass;
    }
}

// Oppgave 3
class Flyformasjon{
    
    // 3a
    public final Fly foerste = null;
    
    public Fyformasjon()
    {
        
    }
    
    // 3b
    public void leggtil(Fly nFly)
    {
        nFly.neste = foerste;
        foerste = nFly
    }
    
    // 3c
    public boolean erMed(String id)
    {
        private Fly iter = foerste;
        while(iter != null)
        {
            if(iter.hentID().equals(id)):
            {
                return true;
            }
            iter = iter.neste;
        }
        
        return false;
    }
    
    // 3d
    public Fly taUt(String id)
    {
        private Fly iter = foerste;
        
        // Edge-case der det første elementet er den vi vil ha
        if(iter.hentID().equals(id)):
        {
            foerste = iter.neste;
            return iter;
        }
        
        
        while(iter.neste != null)
        {
            if(iter.neste.hentID().equals(id)):
            {
                Fly temp = iter.neste;
                iter.neste = iter.neste.neste;
                
                return temp;
            }
            
            iter = iter.neste;
        }
        
        return null;
    }
    
    // 3e
    
    
}