# PatchesOfGrass

This is a project to benchmark and visualize algorithms that find the maximum area submatrix consisting of all 1's in a binary matrix. We call this the patch of grass problem for the following reason. Imagine you are trying to take a nap in a large field of grass where some blades of grass are sufficient for laying on and some are not. Our algorithm will find the largest area rectangle consiting of suitable blades of grass so you can slumber soundly. Check out PatchesofGrassPoster.pdf to see the poster we presented on this work. 

We implement algorithms using two different approaches to this problem: Brute Force and Dynamic programming. Both algorithms were not
designed by us but are also not credited to anyone.
PatchOGrass contains our algorithms and methods to benchmark them.
The rest of the classes in the project are used for the visualization of the algorithms.

Note that the findMaxAreaSubmatrix methods in the Field class were tweaked to provide for a clearer visualization so are not identical to the corresponding methods in the PatchOGrass class but return the same results and have the same running time.

This program requires Java8, ACM.Jar, and the JUnit testing framework to run.
To run the visualizations, simply run the RunPatchOfGrass class. It is possible that the histogram label for the dynamic programming algorithm will not be lined up correctly as we have found it lines up differently on Mac's vs. Pc's. To run our benchmarking of the algorithms, simply run the PatchOGrass class.

Matt Hagen & Steven Roach


