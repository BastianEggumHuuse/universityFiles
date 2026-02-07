class Forelesning_20251014
{
    /*

    Vi skal lære to algoritmer for å finne korteste sti i en vektet graf.
    Disse fungerer for både rettede og urettede grafer.

    Dijkstra:
    En algoritme som sprer seg. Vi starter i en node, også sprer vi oss ut til vi har nådd alle.

    Input er en graf og en startnode.
    Vi traverserer grafen med en prioritetskø som ordner noder etter avstand fra startnoden.

    1) Vi setter dv_1 = 0, og vi setter dv_i = inf (alle andre noder)

    2) Vi besøker første node v_1, og v_1s naboer i køen. (dette ordner nodene etter vekt)

    3) node v_i sin avstand blir lik v_(i-1) sin avstand pluss egen kobling sin vekt.
    


    Bellman Ford:
    Dersom en graf har negative vekter, eller en negativ sykel (skummelt), kan vi ikke bruke Dijkstra, siden denne
    ikke oppdaterer noder den har vært på før. Bellman ford finner korteste sti dersom grafen har negative vekter,
    og vil oppdage negative sykler dersom de finnes.

    En g
    */

    public static void Main(String[] Args)
    {

    }
}