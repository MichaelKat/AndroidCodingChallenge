# AndroidCodingChallenge
An Android app that solves a statistical challenge!

I was given a challenge in which I was required to find the expected number of different colours (and 99% confidence interval) when selecting a given quantity of colour lightbulbs at random from an inventory of colour lightbulbs, all within an Android app. Well, the statistical side of things wasn't too complicated, so I dove into developing an Android app!

First things first, per the challenge requirements, I had to collect user input as five values:
1. The total number of lightbulbs in the inventory;
2. The total number of different colours in the inventory;
3. The number of lighbulbs of each colour (every colour is equally represented in the inventory);
4. The amount of lightbulbs we will be randomly picking from the inventory;
5. And, lastly, how many times we wish to simulate the experiment (more simulations yield more accurate results).

Lastly, I wanted to add a "Run" button to start the simulations, and an output box to show the result. And thus, here is what the app looks like:

![App](https://github.com/MichaelKat/AndroidCodingChallenge/blob/master/Images/Portrait.png)


I spent quite some time working on the triangles so that they scale properly on different screen sizes, but I got it figured out and am very satisfied with the way they work, and especially the way they look!

Now, you might be wondering, aren't user inputs 1, 2, and 3 somewhat redundant? If so, you are absolutely correct, but alas I was not the one who designed the challenge requirements. Additionally, this redunant setup requires additional error checking, as entering a mismatched set of numbers would make no sense, and would prevent us from accurately performing the calculations. Here is how I handled a user entering mismatched amounts:

![Mismatched Inventory Numbers Error](https://github.com/MichaelKat/AndroidCodingChallenge/blob/master/Images/Inventory_Numbers_Error.png)


Another possible user error is trying to randomly pick a number of lightbulbs greater than the number of lightbulbs available in inventory, since that is impossible! Here is how I handled it:

![Quantity to Pick too Great](https://github.com/MichaelKat/AndroidCodingChallenge/blob/master/Images/Picking_Error.png)


To continue, a user might start the calculations without filling in some values. I simply decided to handle those cases by treating empty values as the number 0. This is what it looks like - as you can see, the app sees that the quantity of lightbulbs to pick is 0, therefore there are 0 possible different colours:

![Empty String Handling](https://github.com/MichaelKat/AndroidCodingChallenge/blob/master/Images/Empty_String_Error.png)


Lastly, a more funky error to look out for is if the user decides to run the calculations with 0 simulations - 0 simulations is equivalent to not calculating anything! This is how I handled those cases:

![Zero Simulations Error](https://github.com/MichaelKat/AndroidCodingChallenge/blob/master/Images/Simulation_Error.png)


And that's the gist of it! Let me know if you're interested in an APK to see it in action! :)


P.S. It is possible to make a separate version for landscape orientation, by changing some guidelines and some font sizes, to achieve something like this:

![Landscape Orientation](https://github.com/MichaelKat/AndroidCodingChallenge/blob/master/Images/Landscape.png)

I personally decided against it because I like how it looks in portrait mode, and I enforced the portrait only mode in the AndroidManifest for the same reason, but it's a possibility!
