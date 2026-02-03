import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 38 (Uke 5)"
# python "Forelesning 19.09.2024.py"
# --------------------- [ ------ ] ------------------------

# -- Basic array functionality --
r     = np.arange(0,21,1)   # same as range(), but returns a numpy array.
o_1   = np.zeros(100)       # returns 100 zeros.
o_2   = np.ones(100)        # returns 100 ones.
o_3   = np.ones(100) * 5    # returns 100 ones, then uses the arithmetic properties of numpy arrays to create 100 fives.
o_4   = np.append(o_1,o_2)  # appends all of list b to list a, then returns it.
o_1_1 = list(o_1)[:]        # Copying a list.
o_1_2 = o_1.copy()          # Copying an array.

# -- More advanced array functionality --
X   = np.linspace(0,1,100)     # returns an array with n spaced numbers between a and b.
Y   = X**2 + 4*X               # Some function math with X.
Y_2 = [x**2 + 4*x for x in X]  # The same math with a list. 

'''
with open("Yahoo.txt","w") as f:
    f.write(f"2{2:20}")
    '''