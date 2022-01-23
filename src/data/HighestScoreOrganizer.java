package ooga.data;

import java.util.Comparator;

import ooga.data.firebase.playerprofiles.Profile;

/**
 * HighestScoreOrganizer implements the Comparator<Profile> class
 * and is used in sorting lists of profile by their highestScores
 * from least to greatest
 */
public class HighestScoreOrganizer implements Comparator<Profile> {

    public HighestScoreOrganizer() {
    }

    /**
     * Compares two profiles in a list based on their highest scores
     *
     * @param left  the left Profile in the list operation
     * @param right the right profile in the list operation
     * @return returns a negative number if the profiles should be
     * swapped in the list
     */
    @Override
    public int compare(Profile left, Profile right) {
        if (right != null) {
            return -left.getHighestScoreEver().compareTo(right.getHighestScoreEver());
        } else {
            return 1;
        }

  }

}
