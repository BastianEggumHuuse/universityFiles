import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math               as  mt
import  random             as  rd
import  csv                as  csv

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Eksperiment_1"
# python "Eksperiment_Still.py"
# --------------------- [ ------ ] ------------------------

Plot_Data_3 = {}
Count = 0

with open("Raw_Data_Still.csv") as Data:

    csv_Data = csv.reader(Data)
    List_Data = list(csv_Data)

    for i in range(1,len(List_Data)):
        Plot_Data_3[float(List_Data[i][0])] = float(List_Data[i][3])
        Count += float(List_Data[i][3])

Average = Count/len(Plot_Data_3)
Avvik = (max(Plot_Data_3.values()) - min(Plot_Data_3.values())) / 2
print(f"Gjennomsnittelig Akselerasjon : {Average:5.10f}, Standardavvik : {Avvik:5.3f}")

manager = plt.get_current_fig_manager()
manager.full_screen_toggle()
plt.plot(Plot_Data_3.keys(),Plot_Data_3.values())

plt.show()