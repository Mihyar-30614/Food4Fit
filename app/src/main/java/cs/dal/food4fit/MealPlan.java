package cs.dal.food4fit;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by graceliu on 2017-11-21.
 */

public class MealPlan {



    private ArrayList<ImageItem> Breakfast;
    private ArrayList<ImageItem> Lunch;
    private ArrayList<ImageItem> Dinner;

    public MealPlan(ArrayList<ImageItem> Breakfast, ArrayList<ImageItem> Lunch, ArrayList<ImageItem> Dinner) {

        this.Breakfast = Breakfast;
        this.Lunch = Lunch;
        this.Dinner = Dinner;

    }

    public ArrayList<ImageItem> getBreakfastList() {
        return Breakfast;
    }

    public ArrayList<ImageItem> getLunchList() {
        return Lunch;
    }

    public ArrayList<ImageItem> getDinnerList() {
        return Dinner;
    }

}
