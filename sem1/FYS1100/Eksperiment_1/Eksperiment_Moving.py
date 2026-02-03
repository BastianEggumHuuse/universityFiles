import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math               as  mt
import  random             as  rd
import  csv                as  csv

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Eksperiment_1"
# python "Eksperiment_Moving.py"
# --------------------- [ ------ ] ------------------------

Plot_Data_7 = {}
V_List = [0]
S_List = [0]
S_Flat = 0

with open("Raw_Data_Moving.csv") as Data:

    csv_Data = csv.reader(Data)
    List_Data = list(csv_Data)

    Average = 0

    for i in range(1,len(List_Data)):
        Plot_Data_7[float(List_Data[i][0])] = float(List_Data[i][2])
        Average += float(List_Data[i][2])

    Average = Average/len(Plot_Data_7)

    for i in range(2,len(List_Data)):
        V_List.append(V_List[-1] + (float(List_Data[i][0]) - float(List_Data[i-1][0])) * (float(List_Data[i][2]) - Average))
        S_List.append(S_List[-1] + (float(List_Data[i][0]) - float(List_Data[i-1][0])) * V_List[-1])
        if (float(List_Data[i][0]) > 35) and S_Flat == 0:
            S_Flat = S_List[-1]



plt.subplot(3,1,1)
plt.plot(Plot_Data_7.keys(),Plot_Data_7.values(),c = "red",label = "Akselerasjon")
plt.ylabel("Akselerasjon i m/s^2")
plt.title("Bevegelse i Heis")
plt.legend()

plt.subplot(3,1,2)
plt.plot(Plot_Data_7.keys(),V_List,c = "orange",label = "Fart")
plt.ylabel("Hastighet i m/s")
plt.legend()

plt.subplot(3,1,3)
plt.plot(Plot_Data_7.keys(),S_List,c = "coral",label = "Posisjon")
plt.axhline(S_Flat,c="Firebrick",label = "Posisjon etter 35s")
plt.text(34,S_Flat - 2,f"s = {S_Flat:2.3f}",c="Firebrick")
plt.xlabel("Tid i sekunder")
plt.ylabel("Posisjon over start i meter")
plt.legend()

manager = plt.get_current_fig_manager()
manager.full_screen_toggle()
plt.show()
