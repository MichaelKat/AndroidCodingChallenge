package katilevsky.michael.androidcodingchallenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void runSimulation(View view) {

        // User data we will be retrieving
        int numLightsTotal, numColoursTotal, numColour, numPick, numSimulations;

        // Get the output text box
        TextView output = findViewById(R.id.output);

        // NOTE: We add "0+" to every 'value' below so that we do can convert empty strings to the
        // value '0'

        // Get number of lightbulbs available
        EditText textField = findViewById(R.id.numLightsTotal);
        String value = textField.getText().toString();
        numLightsTotal = Integer.parseInt(0 + value);

        // Get number of lightbulb colours
        textField = findViewById(R.id.numColoursTotal);
        value = textField.getText().toString();
        numColoursTotal = Integer.parseInt(0 + value);

        // Get number of each lightbulb colour
        textField = findViewById(R.id.numColour);
        value = textField.getText().toString();
        numColour = Integer.parseInt(0 + value);

        // Get number of lightbulbs to randomly pick
        textField = findViewById(R.id.numPick);
        value = textField.getText().toString();
        numPick = Integer.parseInt(0 + value);

        // Get number of times to run the simulation
        textField = findViewById(R.id.numSimulations);
        value = textField.getText().toString();
        numSimulations = Integer.parseInt(0 + value);


        // Check whether the retrieved values are valid, return if they're invalid
        if (check_values(numLightsTotal, numColoursTotal, numColour, numPick, numSimulations, output)) {
            return;
        }


        // Create array that stores the colour counts from all the simulations
        int[] colourCounts = new int[numSimulations];

        // Aggregates the number of colours selected throughout all simulations
        int sum = 0;

        // Run 'numSimulations' simulations and store the counts in colourCounts[]
        for (int i = 0; i < numSimulations; i++) {
            int count = countColours(numLightsTotal, numColoursTotal, numColour, numPick);
            colourCounts[i] = count;
            sum += count;
        }


        // Find the average number of colours selected per simulation
        float avg = sum / (float) numSimulations;


        // Create array that stores the squared  differences of the individual average from the end average
        float[] differences = new float[numSimulations];

        // Error value to find our confidence interval
        for(int i = 0; i < numSimulations; i++) {
            differences[i] = (float) Math.pow(((double) avg - colourCounts[i]), 2);
        }

        int confidenceSum = 0;

        for(int i = 0; i < numSimulations; i++) {
            confidenceSum += differences[i];
        }

        float error = confidenceSum/(float) numSimulations;


            // Format the avg to at most 2 decimal places and output the result, alongside the confidence interval
        DecimalFormat df = new DecimalFormat("#.##");
        output.setText("Expected Number of Colours: " + df.format(avg) +
                "\n99% Confidence Interval: " + df.format(avg) + " +/- " + df.format(error));

    }


    /* Helper Functions Below */


    // This function verifies whether all user-entered values are valid, where:
    // i = number of lightbulbs
    // j = number of colours
    // k = quantity of each lightbulb colour
    // m = the number of lightbulbs to randomly pick
    // n = number of times to run the simulation
    // Returns true if an error is detected
    private boolean check_values(int i, int j, int k, int m, int n, TextView out) {

        // Don't have to do anything if we run the simulation 0 times...
        if(n == 0) {
            out.setText("I cannot give you any results without running the simulation even once!");
            return true;
        }
        // Check that all values entered are non-negative
        // NOTE: Technically, this is never supposed to happen because I configured the EditText
        // views for unsigned integers, but it is always good to double check!
        if(i < 0 || j < 0 || k < 0 || m < 0 || n < 0) {
            out.setText("Sorry! Values cannot be negative! :/");
            return true;
        }

        // Check that the numbers of colours and lightbulbs are coherent
        if(i != j*k) {
            out.setText("Hmm... Something does not add up! Please check that you've entered the " +
                    "correct number of lightbulbs and colours!");
            return true;
        }

        // Check that the user hasn't requested to pick too many lightbulbs
        if(m > i) {
            out.setText("Careful! You've set the value of lightbulbs to randomly pick greater " +
                    "than the number of lightbulbs available... That is impossible!");
            return true;
        }

        return false;
    }


    // This function selects 'numPick' lightbulbs at random and then computes the number of colours selected
    private int countColours(int numLightsTotal, int numColoursTotal, int numColour, int numPick) {

        // Declare array of all lightbulbs, whose colour is represented by an int
        int[] lightbulbs = new int[numLightsTotal];

        // Initialize array of lightbulbs
        for (int i = 0; i < numColoursTotal; i++) {
            for (int j = 0; j < numColour; j++) {
                lightbulbs[i * numColour + j] = i;
            }
        }


        // An array to keep track of the colours already selected
        boolean[] selected = new boolean[numColoursTotal];

        // Initialize array to all false, no colours selected
        for(int i = 0; i < numColoursTotal; i++) {
            selected[i] = false;
        }


        int countColours = 0;

        // Used to keep track of the array range in which there are lightbulbs we have not yet selected
        int size = lightbulbs.length;


        Random rand = new Random();

        // Pick 'numPick' lightbulbs at random and
        for(int i = 0; i < numPick; i++) {

            // Get a random index within the range of lightbulbs we have not yet selected
            int j = rand.nextInt(size);

            // Mark that the lightbulb colour at index j has been selected;
            selected[lightbulbs[j]] = true;

            // Replace lightbulb we have just selected by a new lightbulb
            lightbulbs[j] = lightbulbs[size-1];

            // Reduce our range by one element, as we're selecting lightbulbs without replacement
            size--;
        }


        // Count how many of the colours have been selected
        for (boolean select : selected) {
            if (select) { // if colour i has been selected...
                countColours++; // increase the count
            }
        }

        return countColours;
    }

}
