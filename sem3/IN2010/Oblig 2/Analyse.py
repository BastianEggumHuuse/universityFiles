import matplotlib.pyplot as plt

Directory = "Outputs"

FileName = "nearly_sorted_10000_results.csv"

Dict = {}

with open(Directory + "/" + FileName) as File:

    line1 = File.readline()
    keys = line1.strip("\n").replace(" ","").split(",")
    for k in keys:
        Dict[k] = []

    for l in File:
        values = l.strip("\n").replace(" ","").split(",")
        for i in range(len(keys)):
            Dict[keys[i]].append(int(values[i]))

# Runtime over n
plt.plot(Dict["n"],Dict["insertion_time"],label = "Insertion sort")
plt.plot(Dict["n"],Dict["quick_time"],label = "Quick sort")
plt.plot(Dict["n"],Dict["merge_time"],label = "Merge sort")
plt.plot(Dict["n"],Dict["heap_time"],label = "Heap sort")
plt.legend()

plt.title("Runtime of all algorithms for nearly sorted 10000")
plt.xlabel("size of n")
plt.ylabel("Runtime in microseconds")
plt.show()

# Runtime over smaller n

N = 100

plt.plot(Dict["n"][:N],Dict["insertion_time"][:N],label = "Insertion sort")
plt.plot(Dict["n"][:N],Dict["quick_time"][:N],label = "Quick sort")
plt.plot(Dict["n"][:N],Dict["merge_time"][:N],label = "Merge sort")
plt.plot(Dict["n"][:N],Dict["heap_time"][:N],label = "Heap sort")
plt.legend()

plt.title("Runtime of all algorithms for random 10000, with N <= 100")
plt.xlabel("size of n")
plt.ylabel("Runtime in microseconds")
plt.show()

# Runtime over swaps
fig, (ax1,ax2,ax3,ax4) = plt.subplots(4,1)

#plt.title("Number of runtime over swaps,comparisons of all algorithms for random 10000")
ax1.plot(Dict["insertion_time"],Dict["insertion_swaps"],label = "Insertion swaps",color = "orangered")
ax1.plot(Dict["insertion_time"],Dict["insertion_cmp"],label = "Insertion cmps",color = "red")
ax1.legend()

ax2.plot(Dict["quick_time"],Dict["quick_swaps"],label = "Quick swaps",color = "limegreen")
ax2.plot(Dict["quick_time"],Dict["quick_cmp"],label = "Quick cmps", color = "lime")
ax2.legend()

ax3.plot(Dict["merge_time"],Dict["merge_swaps"],label = "Merge swaps",color = "khaki")
ax3.plot(Dict["merge_time"],Dict["merge_cmp"],label = "Merge cmps", color = "gold")
ax3.legend()

ax4.plot(Dict["heap_time"],Dict["heap_swaps"],label = "Heap swaps", color = "dodgerblue")
ax4.plot(Dict["heap_time"],Dict["heap_cmp"],label = "Heap cmps", color = "royalblue")
ax4.legend()


plt.xlabel("Runtime in microseconds")
plt.ylabel("Number of swaps/comparisons")
plt.show()
