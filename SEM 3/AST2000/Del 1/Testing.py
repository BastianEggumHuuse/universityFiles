import numpy as np
import time
import random
import ast2000tools.constants as const

def GetArrays(Size = 10):
    # Array of ten objects
    rng = np.random.default_rng()
    arr = rng.uniform(-2,2,Size) #(values between -2 and 2)
    arr_velocity = np.ones(Size) * 5

    # The limit we search for
    limit = 1.0 # Values outside of 1 or -1 are false.
    limit_arr = np.zeros(Size)
    limit_arr[0:10] = limit

    return arr,arr_velocity,limit,limit_arr




def MathMethod():
    # Getting our arrays
    arr,arr_velocity,limit,limit_arr = GetArrays(10**5)

    #print(f"Array state 1 : {arr}\n")

    # Creating a copy of our array
    arr2 = arr.copy()
    # We don't care about negative numbers
    arr2 = abs(arr2)

    #print(f"Array state 2 : {arr2}\n")

    arr2 -= limit_arr

    #print(f"Array state 3 : {arr2}\n")

    arr2 = arr2.clip(0,limit)

    #print(f"Array state 4 : {arr2}\n")

    arr_div = arr2.copy()
    arr_div[arr_div == 0] = 1
    arr2 = arr2 / arr_div
    arr2[arr2 == 0] = -1

    #print(f"Array state 5 : {arr2}\n")

    #print(f"Velocity Array State Before : {arr_velocity}\n")

    arr_velocity = arr_velocity * (arr2 * -1)

    #print(f"Velocity Array State After : {arr_velocity}\n")

def SlicingMethod():
    arr,arr_velocity,limit,limit_arr = GetArrays(10**5)

    Copy = abs(arr.copy())
    Copy[Copy > limit] = -1
    Copy[Copy != -1] = 1

    arr_velocity = arr_velocity * Copy

def SlicingThreeDim():

    Size = 5
    rng = np.random.default_rng()
    arr = rng.uniform(-2,2,(Size,3)) #(values between -2 and 2, in three dimentions
    arr_velocity = np.ones((Size,3)) * 5

    # The limit we search for
    limit = 1.0 # Values outside of 1 or -1 are false.

    Copy = abs(arr)
    Indexes = np.where(Copy > limit)

    print(arr_velocity)
    arr_velocity[Indexes] *= -1

    print(arr)
    print(arr_velocity)



def timer(method,reps = 10000):
    t0 = time.time()
    for i in range(reps): method()
    t1 = time.time()

    print(f"Total time elapsed after {reps} repetitions : {t1 - t0}\n")

#print(f"Method of math")
#timer(MathMethod,300)

#print(f"Method of slicing")
#timer(SlicingThreeDim,100)

Size = 10
rng = np.random.default_rng()
arr = rng.uniform(-2,2,(Size,3)) #(values between -2 and 2, in three dimentions

x_indexes = np.where(arr[:,0] > 1)
y_indexes = np.where(abs(arr[:,1]) < 1.5)
z_indexes = np.where(abs(arr[:,2]) < 1.5)

all_indexes = np.intersect1d(np.intersect1d(x_indexes,y_indexes),z_indexes)

Temp = 3*10**3
ParticleMass = const.m_H2
sigma = ((Temp*const.k_B)/ParticleMass)**(1/2)
MaxwellBoltzmann = lambda : np.random.normal(loc = 0, scale = sigma) 

SlicingThreeDim()