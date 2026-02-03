import numpy as np
import matplotlib.pyplot as plt

class Track:

    def __init__(self,Name,Album,Artist,Length,Emotion,Crying,Tags):
        self.Name = Name
        self.Album = Album
        self.Artist = Artist
        self.Length = Length
        self.Emotion = Emotion
        self.Crying = Crying
        self.Tags = Tags

    def Point(self):
        return self.Emotion,self.Crying
    
class Collection:

    def __init__(self,Tracks):
        self.Tracks = Tracks

    def Plot(self,color = "k"):

        Xs = []
        Ys = []

        for t in self.Tracks:
            Emotion,Crying = t.Point()
            Xs.append(Emotion)
            Ys.append(Crying)

        # Setting Axis Style
        plt.xlim(-12,12)
        plt.ylim(-1,11)
        plt.plot((0,0),(-0.5,10),"k")
        plt.plot((-10,10),(0,0),"k")

        plt.plot(Xs,Ys,".",color = color)
        plt.show()

# Defining tags
Tags = {
    
}

# Defining tracks
Tracks = [
    Track(Name = "party at club bug!",Album= "party at club bug!",Artist = "spellcasting",Length = 2*60 + 36,Emotion = 7, Crying = 1, Tags = []),
    Track(Name = "Aria Math", Album= "Minecraft - Volume Beta",Artist = "C418",Length = 5*60 + 10,Emotion = -7, Crying = 6, Tags = []),
    Track(Name = "Can I Call You Tonight?", Album= "Fuzzybrain",Artist = "Dayglow",Length = 4*60 + 39,Emotion = -5, Crying = 8, Tags = []),
]

c = Collection(Tracks=Tracks)
c.Plot("red")