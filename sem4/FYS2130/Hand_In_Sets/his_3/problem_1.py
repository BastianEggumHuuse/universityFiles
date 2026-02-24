import numpy as np
import matplotlib.pyplot as plt

# Function parameters
f_0     = 10
tau     = 0.3
T       = 3

# Defining the function
def x(t):
    return np.cos(2*np.pi**f_0*t) * np.exp(-abs(t)/tau)

# a)

# Defining sampling frequency
f_s     = 200

# this could have been a class probably
def fft_sequence()
    f_nyq   = f_s/2
    dt      = 1 / f_s
    print(f"\ndelta_time : {dt}s")

    T_smp   = np.arange(-T/2,T/2,dt)
    X_smp   = x(T_smp)

    # Computing the FFT
    X_fft   = np.fft.fft(X_smp)
    freqs   = np.fft.fftfreq(len(X_smp),d = dt)

    # plotting x(t)
    T_plt = np.linspace(-T/2,T/2,1000)
    #plt.plot(T_plt,x(T_plt))
    # plt.show()    # Doesn't work on linux (yet, maybe work on that)

    # Plotting X_fft(f)
    #plt.plot(freqs,np.abs(X_fft))
    #plt.axvline(f_nyq,color = "green")
    #plt.axvline(-f_nyq,color = "green")
    #plt.axvline(f_s,color = "red")
    #plt.axvline(-f_s,color = "red")
    #maybe some labels for those??
    # plt.show() 

# running for the entirity of a)
fft_sequence()

# b)

# Check the peak

# the accuracy of the frequency (the thinness of the peak) scales with T

# running again with different tau
tau = 0.6
fft_sequence()

# c)

# running again with lower sampling

f_s = 15
fft_sequence()

# point out aliasing.
