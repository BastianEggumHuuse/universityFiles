
class Box
{
    public String Contents;

    Box(String c)
    {
        Contents = c;
    }
}

interface Lockable
{
    String Unlock(String k);
}

class LockedBox extends Box implements Lockable
{
    String Key = "";

    LockedBox(String c,String k)
    {
        super(c);
        Key = k;
    }

    @Override
    public String Unlock(String k)
    {
        if(k == Key)
        {
            return(Contents);
        }

        return(null);
    }

}

class MainClass
{

    public static void main(String[] Args)
    {
        LockedBox b = new LockedBox("Juicy Loot","Dungeon Key");

        String key_1 = "Slimy Key";
        String key_2 = "Dungeon Key";

        System.out.println(b.Unlock(key_1));
        System.out.println(b.Unlock(key_2));
    }

}
