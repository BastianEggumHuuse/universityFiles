class Vanedannende extends Legemiddel
{

    public final int styrke;

    Vanedannende(String _navn, int _pris, double _mengdeVirkestoff,int _styrke)
    {
        super(_navn,_pris,_mengdeVirkestoff);
        styrke = _styrke;
    }

}