package com.demo.app.ncov2020.logic.Country;


public enum MedicineLevel {
    FIRST,
    SECOND,
    THIRD;

    public static MedicineLevel valueOf(int i)
    {
        switch (i)
        {
            case 0:
                return FIRST;

            case 1:
                return SECOND;

            case 2:
                return THIRD;

        }

        return null;
    }
}
