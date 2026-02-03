import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Eksperiment_2"
# python "Eksperiment 22.10.2024.py"
# --------------------- [ ------ ] ------------------------

Data_Pos = [0]
Data_Rel_Pos = [0]
Data_Velocity = [0]

with open("Datasett_ball.txt") as file:
    file.readline()

    i = 0
    for l in file:
        values = l.split(",")
        Data_Rel_Pos.append(float(values[0]) / 100)
        Data_Pos.append(float(values[1]) / 100)

        v = (Data_Rel_Pos[-1]) * 30
        Data_Velocity.append(v)

        if i >= 1:
            pass
        i += 1

    print(Data_Pos)

Pos = [0]
Velocity = [0]
Time = [0]

# I believe that the numbers might be wrong here? 
# The graph flattens out way faster than i thought it would
g = 9.81
m = 0.0027
k = 0.08
dt = 1e-4


while Time[-1] < (27 / 30):

    a = ((m*9.81) - k*(Velocity[-1]**2))/m
    Velocity.append(Velocity[-1] + a * dt)
    Pos.append(Pos[-1] + Velocity[-1] * dt)

    Time.append(Time[-1] + dt)

plt.subplot(2,1,1)
plt.plot(Time,Pos,c="k")
plt.plot(np.linspace(0,27 / 30,27),Data_Pos,"ro")
plt.ylabel("Absolutt strekning i m")

plt.subplot(2,1,2)
plt.plot(Time,Velocity,c="k")
plt.plot(np.linspace(0,27 / 30,27),Data_Velocity,"go")
plt.ylabel("Absolutt hastighet i m/s")
plt.xlabel("Tid i s")

plt.show()