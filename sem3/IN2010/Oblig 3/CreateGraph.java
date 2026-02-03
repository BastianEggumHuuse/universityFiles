// Reading
import java.io.File;                 
import java.io.FileNotFoundException; 
import java.util.Scanner;    

// Data structures
import java.util.ArrayList;

public class CreateGraph
{

    public static void main(String[] args) 
    {
        // Defining filepaths
        String filePath = "Inputs/";
        String moviePath = filePath + "movies.tsv";
        String actorPath = filePath + "actors.tsv";

        // Reading movies
        File movieFile = new File(moviePath);
        ArrayList<String> movieLines = new ArrayList<>();

        try (Scanner reader = new Scanner(movieFile)) 
        {
            while (reader.hasNextLine()) 
            {
                movieLines.add(reader.nextLine());
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Reading actors
        File actorFile = new File(actorPath);
        ArrayList<String> actorLines = new ArrayList<>();

        try (Scanner reader = new Scanner(actorFile)) 
        {
            while (reader.hasNextLine()) 
            {
                actorLines.add(reader.nextLine());
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Creating our graph
        Graph graph = new Graph(movieLines,actorLines,false,false);

        // Calculating number of nodes, number of edges, and number of components of given sizes
        Graph.WidthFirstResult result = graph.WidthFirstFull();

        // Printing number of nodes and number of edges
        System.out.println("\nOppgave 1\n");
        System.out.println("Number of nodes: " + result.nodeCount);
        System.out.println("Number of edges: " + result.edgeCount);
        
        // Printing the sizes of all components
        System.out.println("\nOppgave 2\n");
        for(Integer currentActorCount : result.componentActorCount.keySet())
        {
            Integer currentComponentCount = result.componentActorCount.get(currentActorCount);
            System.out.println("There are " + currentComponentCount + " components of size " + currentActorCount);
        }

        // Defining start- and endpoints for our "six degrees of IMDB" tasks
        String[] inputIDs = new String[] {"nm2255973","nm0424060","nm4689420","nm0000288","nm0637259"};
        String[] outputIDs = new String[] {"nm0000460","nm8076281","nm0000365","nm2143282","nm0931324"};

        // Looping over IDs
        System.out.println("\nOppgave 3\n");
        for(int i = 0; i < inputIDs.length; i += 1)
        {
            // Calculating shortest path between two given Nodes
            ArrayList<String> path = graph.ShortestPath(inputIDs[i], outputIDs[i]);

            System.out.println("");

            // Printing.
            // Because of how we've structured our program, every other element in path is a movie.
            // We want to print these differently.
            int movieCounter = 1; // This is what movieCounter is for!
            for (String s : path)
            {
                // If movieCounter is even, we know that we are printing a movie.
                if (movieCounter % 2 == 0)
                {
                    System.out.print("====[" + s + "]===> ");
                }
                else
                {
                    System.out.print(s + "\n");
                }
                movieCounter += 1;
            }
        }

        // Looping over IDs
        System.out.println("\nOppgave 4\n");
        for(int i = 0; i < inputIDs.length; i += 1)
        {
            // Calculating "chillest" path between two Nodes
            ArrayList<String> path = graph.ChillestPath(inputIDs[i], outputIDs[i]);

            System.out.println("");

            // Same principle as above, we want to print movies differently.
            // This time we don't need a counter, since we have the iterator stringIndex!
            for (int stringIndex = 0; stringIndex < path.size() - 1; stringIndex += 1)
            {
                String s = path.get(stringIndex);

                // StringIndex begins at 0, while movieCounter begins at 1. 
                // We have to add 1 here for the logic to still work.
                if ((stringIndex + 1) % 2 == 0)
                {
                    System.out.print("====[" + s + "]===> ");
                }
                else
                {
                    System.out.print(s + "\n");
                }
            }

            // The final element in path contains the total weight for the path.
            System.out.println(path.getLast());

        }

        System.out.println("\nOppgave 5\n");
        System.out.println("Constructing graph using votes to calculate weights...\n");

        // Constructing a graph using votes as weights (instead of ratings)
        Graph voteGraph = new Graph(movieLines,actorLines,true,false);
        // Computing number of nodes and number of edges to check if the graph is constructed correctly
        result = voteGraph.WidthFirstFull();
        System.out.println("Number of nodes: " + result.nodeCount);
        System.out.println("Number of edges: " + result.edgeCount);

        // looping over input IDs
        for(int i = 0; i < inputIDs.length; i += 1)
        {
            // Finding chillest path (Now through votes instead of ratings)
            ArrayList<String> path = voteGraph.ChillestPath(inputIDs[i], outputIDs[i]);

            System.out.println("");

            // Exactly the same as Oppgave 4.
            for (int stringIndex = 0; stringIndex < path.size() - 1; stringIndex += 1)
            {
                String s = path.get(stringIndex);

                if ((stringIndex + 1) % 2 == 0)
                {
                    System.out.print("====[" + s + "]===> ");
                }
                else
                {
                    System.out.print(s + "\n");
                }
            }

            System.out.println(path.getLast());

        }
    }
}


/*
This program is run using the following command: java CreateGraph

This program produces the following output:

Oppgave 1

Number of nodes: 234094
Number of edges: 775288

Oppgave 2

There are 44 components of size 0
There are 4617 components of size 1
There are 335 components of size 2
There are 124 components of size 3
There are 46 components of size 4
There are 20 components of size 5
There are 12 components of size 6
There are 8 components of size 7
There are 4 components of size 8
There are 3 components of size 9
There are 1 components of size 10
There are 1 components of size 11
There are 1 components of size 15
There are 1 components of size 120030

Oppgave 3


Donald Glover
====[Lennon or McCartney]===> Robert De Niro
====[The Mission]===> Jeremy Irons

Scarlett Johansson
====[Rough Night]===> Kate McKinnon
====[Barbie]===> Emma Mackey

Carrie Coon
====[His Three Daughters]===> Elizabeth Olsen
====[Avengers: Age of Ultron]===> Julie Delpy

Christian Bale
====[Vice]===> Shea Whigham
====[Non-Stop]===> Lupita Nyong'o

Tuva Novotny
====[Eat Pray Love]===> Lidia Biondi
====[Miracle at St. Anna]===> Michael K. Williams

Oppgave 4


Donald Glover
====[The Martian (8.0)]===> Enzo Cilenti
====[The Man Who Knew Infinity (7.2)]===> Jeremy Irons
Total weight: 4.8

Scarlett Johansson
====[Avengers: Infinity War (8.4)]===> Ariana Greenblatt
====[Barbie (7.1)]===> Emma Mackey
Total weight: 4.5000005

Carrie Coon
====[His Three Daughters (8.5)]===> Elizabeth Olsen
====[Avengers: Age of Ultron (7.3)]===> Julie Delpy
Total weight: 4.2

Christian Bale
====[The Big Short (7.8)]===> Adepero Oduye
====[12 Years a Slave (8.1)]===> Lupita Nyong'o
Total weight: 4.0999994

Tuva Novotny
====[Borg McEnroe (6.9)]===> Demetri Goritsas
====[Saving Private Ryan (8.6)]===> Paul Giamatti
====[12 Years a Slave (8.1)]===> Michael K. Williams
Total weight: 6.399999

Oppgave 5

Constructing graph using votes to calculate weights...

Number of nodes: 234094
Number of edges: 775288

Donald Glover
====[The Martian (895022.0)]===> Enzo Cilenti
====[The Man Who Knew Infinity (60419.0)]===> Jeremy Irons
Total weight: 5244559.0

Scarlett Johansson
====[Avengers: Infinity War (1156287.0)]===> Ariana Greenblatt
====[Barbie (329659.0)]===> Emma Mackey
Total weight: 4714054.0

Carrie Coon
====[Avengers: Infinity War (1156287.0)]===> Cobie Smulders
====[Avengers: Age of Ultron (899995.0)]===> Julie Delpy
Total weight: 4143718.0

Christian Bale
====[The Dark Knight (2782625.0)]===> Michael Caine
====[Inception (2470153.0)]===> Joseph Gordon-Levitt
====[Star Wars: Episode VIII ? The Last Jedi (656606.0)]===> Lupita Nyong'o
Total weight: 3390616.0

Tuva Novotny
====[Eat Pray Love (103016.0)]===> Lidia Biondi
====[Miracle at St. Anna (19001.0)]===> Michael K. Williams
Total weight: 6077983.0
*/