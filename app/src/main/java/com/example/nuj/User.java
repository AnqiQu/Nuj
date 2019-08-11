package com.example.nuj;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class User {

    private String name;
    private Date birthday;
    private Date joinedDate;

    private List<Goal> allGoals;
    private List<Goal> completedGoals;
    private List<Goal> ongoingGoals;

    private double average;
    private double deviation;
    private double recentAverage;

    // Constructor method
    public User(String name, Date birthday, Date joinedDate, List<Goal> allGoals, List<Goal> completedGoals, List<Goal> ongoingGoals){
        this.name = name;
        this.birthday = birthday;
        this.joinedDate = joinedDate;
        this.allGoals = allGoals;
        this.completedGoals = completedGoals;
        this.ongoingGoals = ongoingGoals;
        average = calculateAverage(ongoingGoals);

        calculateDeviation(ongoingGoals);

        List<Goal> recentGoals = new ArrayList<>();
        for (int i = ongoingGoals.size() - 1; i < ongoingGoals.size() - 5; i--){
            recentGoals.add(ongoingGoals.get(i));
        }
        recentAverage = calculateAverage(recentGoals);
    }

    // Method to calculate user's average
    private double calculateAverage(List<Goal> goalList){
        List<Integer> daysTaken = new ArrayList<>();

        for(Goal i : goalList){
            daysTaken.add(daysBetween(i.getStart(), i.getEnd()));
        }

        double total = 0;

        for (Integer i : daysTaken){
            total += i;
        }

        return total / daysTaken.size();
    }

    // Method to calculate user's deviation from their average
    private void calculateDeviation(List<Goal> goalList){
        double sum = 0.0, standardDeviation = 0.0;

        List<Integer> daysTaken = new ArrayList<>();

        for(Goal i : goalList){
            daysTaken.add(daysBetween(i.getStart(), i.getEnd()));
        }

        int length = daysTaken.size();

        for(double num : daysTaken) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: daysTaken) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        deviation = Math.sqrt(standardDeviation/length);
    }

    //Calculates the number of days between 2 dates
    private static int daysBetween(Date start, Date end) {
        int difference = (int) (start.getTime()-end.getTime())/86400000;
        return Math.abs(difference);
    }

    //Getters for the fields
    public String getName() {
        return name;
    }

    public double getAverage() {
        return average;
    }

    public double getDeviation() {
        return deviation;
    }

    public double getRecentAverage() {
        return recentAverage;
    }
}


