import java.util.Collections;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Graph
{
    class Node
    {
        // We're only interested in ID's and Names
        // This goes for both movies and actors.
        // We can therefore use this class for both types of node
        public String ID;
        public String name;
        // We use this variable to separate movies from actors.
        //(This could have been done through checking the IDs, since the start with different letters, but we found this to be cleaner)
        public boolean isMovie; 

        // We also keep track of all edges connected to this node.
        // This way, we only have to loop through relevant edges later.
        ArrayList<Edge> localEdges;

        public Node(String newID, String newName,boolean newIsMovie)
        {
            ID = newID;
            name = newName;
            isMovie = newIsMovie;

            localEdges = new ArrayList<>();
        }

        public String GetID()
        {
            return(ID);
        }

        public String GetName()
        {
            return(name);
        }

        public void AddEdge(Edge newEdge)
        {
            localEdges.add(newEdge);
        }
    }

    class Edge
    {
        // This class represents an edge between two nodes.

        public Node node1;
        public Node node2;
        public float weight;

        public Edge(Node newNode1, Node newNode2, float newWeight)
        {
            node1 = newNode1;
            node2 = newNode2;
            weight = newWeight;
        }

        // This method simpy checks if this edge contains a given node
        public boolean Contains(Node checkNode)
        {
            return (node1 == checkNode || node2 == checkNode);
        }

        public Node OtherNode(Node checkNode)
        {
            // This method assumes that checkNode is either node1 or node2
            // It then returns the other node.
            if(checkNode == node1)
            {
                return node2;
            }

            return node1;
        }
    }

    // We store our nodes in a hashmap, which makes it quick and easy to find nodes by ID
    public HashMap<String,Node> V;
    public ArrayList<Edge> E;
    // We have two different possible functions to calculate the edge weights
    // we use this variable to store what the highest possible weight is.
    float baseWeight;
    boolean printer;

    public Graph(ArrayList<String> movieLines,ArrayList<String> actorLines,boolean altWeight,boolean newPrinter)
    {
        //V = new ArrayList<>();
        V = new HashMap<>();
        E = new ArrayList<>();
        printer = newPrinter;

        // The default weight function calculates weight by taking 10 - rating, where rating is between 0 and 10
        baseWeight = 10f;
        if(altWeight)
        {
            // The alternative weight function calculates weight by taking highestVotes - votes,'
            // where votes is amount of people that have voted for a given movie.
            // A quick google search revealed that the movie with the highest votes (The Shawshank Redemption)
            // has 3.1 million votes. This is our baseline!
            baseWeight = 3100000;
        }

        // We want to keep movies and actors separate during construction.
        // The keys are movie and actor IDs, and the values are the nodes themselves.
        HashMap<String,Node> movies = new HashMap<>();
        HashMap<String,Node> actors = new HashMap<>();

        // These aren't saved after construction, and ara only used to calculate weights
        HashMap<String,Float> ratings = new HashMap<>();
        HashMap<String,Integer> votes = new HashMap<>();

        if(printer)
        {
            System.out.println("Beginning construction of graph...");
        }

        // Looping through and creating movie nodes
        for(String line : movieLines)
        {
            // The parameter movieLines is expected to be a list containing all lines
            // from the input file. We know these are separated by tabulator:
            String[] words = line.split("\t");

            // Creating a new node for the current movie.
            Node newNode = new Node(words[0],words[1],true);
            movies.put(newNode.ID,newNode);

            // Storing ratings and votes for the current movie.
            ratings.put(newNode.ID,Float.valueOf(words[2]));
            votes.put(newNode.ID,Integer.valueOf(words[3]));
        }

        if(printer)
        {
            System.out.println("All movies turned into Nodes.");
        }

        // Looping through and creating actor nodes
        for(String line : actorLines)
        {
            // Same as with movies.
            String[] words = line.split("\t");

            Node newNode = new Node(words[0],words[1],false);
            actors.put(newNode.ID,newNode);

            // The rest of the words (from index 2) are all movies that this actor should be connected to.
            // We loop through these.
            for(int i = 2; i < words.length; i += 1)
            {
                String currentID = words[i];
            
                // If the movie hashmap doesn't contain this ID, it doesn't exist in the dataset.
                if(!movies.containsKey(currentID))
                {
                    continue;
                }

                // Getting the corresponding movie node.
                Node movieNode = movies.get(currentID);

                // Creating a new Edge between this movie and this actor,
                // using the movies rating (or number of votes) to calculate edge weight
                Edge newEdge = new Edge(newNode,movieNode,WeightFunction(ratings.get(currentID)));
                if(altWeight)
                {
                    newEdge = new Edge(newNode,movieNode,WeightFunction(votes.get(currentID)));
                }

                // Adding this edge to both connected nodes
                newNode.AddEdge(newEdge);
                movieNode.AddEdge(newEdge);

                // Adding this edge to the list of total edges
                E.add(newEdge);
            }
        }

        if(printer)
        {
            System.out.println("All actors turned into Nodes.");
            System.out.println("All nodes connected via Edges.");
        }

        // We have now constructed all nodes and edges. We only need to concatenate the lists:
        V.putAll(movies);
        V.putAll(actors);

        // Graph constructed! :)
    }

    public float WeightFunction(float rating)
    {
        return (baseWeight - rating);
    }

    // These can all be called individually to get only one piece of information
    // With the large dataset this takes some time, and it is usually more efficient 
    // to simply get the entire WidthFirstResult instance and to get the data out from that.
    public int NumNodes()
    {
        return WidthFirstFull().nodeCount;
    }

    public int NumEdges()
    {
        return WidthFirstFull().edgeCount;
    }

    public HashMap<Integer,Integer> size()
    {
        return WidthFirstFull().componentActorCount;
    }

    // This class is only used to store the results of the breadth first search.
    // We wish to return multiple datatypes (two integers and a hashmap),
    // which is impossible to do within java. We do a workaround!
    public class WidthFirstResult
    {
        public int nodeCount;
        public int edgeCount;
        public HashMap<Integer,Integer> componentActorCount;

        public WidthFirstResult()
        {
            nodeCount = 0;
            edgeCount = 0;
            componentActorCount = new HashMap<>();
        }

        public void Update(int nodeAdd, int edgeAdd, Integer newComponentActorCount)
        {
            // We want to get total amount of nodes and edges, so we simply sum these.
            nodeCount += nodeAdd;
            edgeCount += edgeAdd;

            // The third parameter is the number of actors in the newly traversed component.
            // We use actor count as keys, and number of components with this actor count as values
            // We must therefore check if we already have an entry for this count in the map.
            if(componentActorCount.containsKey(newComponentActorCount))
            {
                // if we do, we add 1 to it.
                Integer currentCount = componentActorCount.get(newComponentActorCount);
                componentActorCount.put(newComponentActorCount, currentCount + 1);
            }
            else
            {
                // if we don't, we create a new entry with value 1.
                componentActorCount.put(newComponentActorCount, 1);
            }
        }
    }

    public WidthFirstResult WidthFirstFull()
    {
        // This method performs an entire breadth first search.
        // The original algorithm (implemented in the method WidthFirstSearch)
        // only searches one component of the graph, so this method runs the search
        // on all components.

        // Instantiating a result instance
        WidthFirstResult result = new WidthFirstResult();

        // We want to keep track of the nodes we've already visited
        ArrayList<Node> visitedNodes_old = new ArrayList<>();
        HashSet<Node> visitedNodes = new HashSet<>();

        // looping through all nodes
        for(Node v : V.values())
        {
            if(!visitedNodes.contains(v))
            {
                // Running breadth first search.
                int[] currentResult = WidthFirstSearch(v, visitedNodes);

                // Updating results.
                result.Update(currentResult[0], currentResult[1], currentResult[2]);
            }
        }

        return result;
    }

    private int[] WidthFirstSearch(Node s,HashSet<Node> visitedNodes)
    {
        // We want to count nodes, edges, and actors
        int nodeCount = 0;
        int edgeCount = 0;
        int actorCount = 0;

        // Edges are connected to both nodes, so we would count them more than once.
        // Therefore we keep track of the ones we've counted already.
        ArrayList<Edge> visitedEdges_old = new ArrayList<>();
        HashSet<Edge> visitedEdges = new HashSet<>();
        // The queue central to the algorithm.
        ArrayDeque<Node> nodeQueue = new ArrayDeque<>();

        // Adding the first node to visited
        visitedNodes.add(s);
        // Adding the first node to the queue
        nodeQueue.addLast(s);

        // This algorithm is implemented almost directly the way we were told to during lectures
        while(!nodeQueue.isEmpty())
        {
            // Getting the first element in the queue
            Node u = nodeQueue.removeFirst();
            nodeCount += 1;

            // Checking if the node is a movie and incrementing if not
            if(!u.isMovie)
            {
                actorCount += 1;
            }

            if(nodeCount % 10000 == 0 && printer){
                System.out.println(nodeCount + " Nodes counted for current component");
            }

            // Looping through the edges connected to u
            for(Edge e : u.localEdges)
            {
                // Getting the node on the other side of the edge
                Node v = e.OtherNode(u);

                // Checking if edge is visited and incrementing edgecount (if not visited)
                if(!visitedEdges.contains(e))
                {
                    visitedEdges.add(e);
                    edgeCount += 1;
                }

                // Checking if node is visited and adding it to the queue (if not visited)
                if(!visitedNodes.contains(v))
                {
                    visitedNodes.add(v);
                    nodeQueue.addLast(v);
                }
            }
        }

        // Creating an array containing the results, which we return.
        int[] results = new int[3];
        results[0] = nodeCount;
        results[1] = edgeCount;
        results[2] = actorCount;
        return results;
    }

    // This method is different enough that we decided to give it it's own implementation.
    // Note that it could be implemented together with the Breadth-first-search implemented above.
    public ArrayList<String> ShortestPath(String startID, String endID)
    {
        // Finding start and end node by IDs
        Node start = V.get(startID);
        Node end = V.get(endID);

        // Instead of keeping track of nodes we've been to, we keep track of each node's "parent"
        // A parent is a node that leads to the current node.
        // The keys are given nodes, and the values are the parents of said nodes
        HashMap<Node,Node> parents = new HashMap<>();
        ArrayDeque<Node> nodeQueue = new ArrayDeque<>();

        // Adding the first (it has no parent)
        parents.put(start,null);
        // Adding the first node to the queue
        nodeQueue.addLast(start);

        while(!nodeQueue.isEmpty())
        {
            // Getting the first element in the queue
            Node u = nodeQueue.removeFirst();

            // The same principle as before
            for(Edge e : u.localEdges)
            {
                Node v = e.OtherNode(u);
                
                if(!parents.containsKey(v))
                {
                    // Adding this node as a key, and the node we came from (u) as it's parent.
                    parents.put(v,u);
                    nodeQueue.addLast(v);

                    // Because of how Breadth first searches work, we know that we've found the shortest path
                    // the second we find the ending node. Therefore, we can terminate the algorithm, 
                    // saving us some runtime.
                    if(v == end)
                    {
                        // Clearing the queue and breaking out of loop
                        nodeQueue.clear();
                        break;
                    }
                }
            }
        }

        // At this point, we either have all the edges that connect end to start,
        // or they aren't in the same component. We simply need to traverse this span-tree back up to the root
        // (this root will always be start, since this is where we started).

        // This list keeps track of the path taken to end up at the end node
        // Every element is the name of the actor or movie at that step in the path.
        ArrayList<String> path = new ArrayList<>();

        Node currentNode = end;

        // If we haven't found a parent corresponding to the end node,
        // the start and end nodes don't exist in the same component
        if(!parents.containsKey(end))
        {
            // We return an empty list.
            return path;
        }

        // Traversing up the span-tree, and adding names to the path list
        while(currentNode != null)
        {
            path.add(currentNode.GetName());
            currentNode = parents.get(currentNode);
        }

        // We reverse the list before returning, so we begin the list at the start node.
        Collections.reverse(path);
        return path;
    }


    // Small datastructure we use to populate our queue.
    // (again, since java doesn't have tuples, we improvise)
    // also we want to be able to implement a comparison method
    class DijkstraPair implements Comparable<DijkstraPair>
    {
        float dist;
        Node n;

        public DijkstraPair(float newDist, Node newN)
        {
            dist = newDist;
            n = newN;
        }

        @Override
        public int compareTo(DijkstraPair other)
        {
            // The previous variant of this method was quite over-engineered, (and at times wrong)
            // This variant is longer in terms of lines, but way better :)

            if(dist < other.dist)
            {
                return -1;
            }
            if(dist > other.dist)
            {
                return 1;
            }
            return 0;
        }
    }

    public ArrayList<String> ChillestPath(String startID, String endID)
    {
        // Finding start and end.
        Node start = V.get(startID);
        Node end = V.get(endID);

        // For this algorithm we use a priorityqueue instead of a queue,
        // so that the "cheapest" node gets sorted to the front.
        PriorityQueue<DijkstraPair> nodeQueue = new PriorityQueue<>();
        nodeQueue.add(new DijkstraPair(0,start));

        // In the lecture notes, this algorithm sets the distance of every node to inf at the beginning.
        // We have chosen to not define all the node distances instead, and then 
        // assume that their distance is infinite when we encounter them for the first time.
        HashMap<Node,Float> distances = new HashMap<>();
        distances.put(start,0f);

        // We want to keep track of the cost of moving to a given node, for printing purposes later.
        HashMap<Node,Float> weights = new HashMap<>();
        weights.put(start,0f);

        // This algorithm is almost identical to ShortestPath, so we also need to keep track of parents
        HashMap<Node,Node> parents = new HashMap<>();
        parents.put(start,null);


        while(!nodeQueue.isEmpty())
        {
            // Getting the current pair
            DijkstraPair currentPair = nodeQueue.poll();
            Node u = currentPair.n;
            float cost = currentPair.dist;

            if(cost != distances.get(u))
            {
                continue;
            }

            for(Edge e : u.localEdges)
            {
                Node v = e.OtherNode(u);

                // Computing the cost of moving to the new node v
                float c = cost + e.weight;

                // this is instead of having inf as our default.
                // We check if the distances map contains this node, and if not, we assume it has a distance of inf
                // (this means that the new distance c is always less than the current distance inf)
                if(!distances.containsKey(v))
                {
                    distances.put(v,c);
                    nodeQueue.add(new DijkstraPair(c,v));
                    parents.put(v, u);
                    weights.put(v,e.weight);
                }
                // If not, we perform the algorithm normally
                else if(c < distances.get(v))
                {
                    // saving new distance
                    distances.put(v,c);
                    // Adding node to the queue
                    nodeQueue.add(new DijkstraPair(c,v));
                    // Saving parent (to find the path)
                    parents.put(v, u);
                    // Saving the weight (for printing)
                    weights.put(v,e.weight);
                }
            }
        }

        ArrayList<String> path = new ArrayList<>();
        Node currentNode = end;

        if(!parents.containsKey(end))
        {
            return path;
        }

        // This part of the method works exactly like ShortestPath, but this time we
        // also want to add the weight of the movie (the cost of moving to and from said movie)
        float totalWeight = 0;
        while(currentNode != null)
        {
            if(currentNode.isMovie)
            {
                path.add(currentNode.GetName() + " (" + (baseWeight - weights.get(currentNode)) + ")");
                totalWeight += weights.get(currentNode);
            }
            else
            {
                path.add(currentNode.GetName());
            }

            currentNode = parents.get(currentNode);
        }

        // We reverse the list before returning, so we begin the list at the start node.
        Collections.reverse(path);
        path.add("Total weight: " + totalWeight);
        return path;
    }
}