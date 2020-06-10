package ru.javawebinar.topjava.dao;

public class MealsDAOFabric {
    private static MealsDAO dao = new MealsInMemory();

    public static MealsDAO getMealsDAO(){
        return dao;
    }
}
