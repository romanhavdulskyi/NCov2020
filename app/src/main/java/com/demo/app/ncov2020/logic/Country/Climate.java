package com.demo.app.ncov2020.logic.Country;

public enum Climate implements Cloneable{
    HOT,
    NORMAL,
    COLD;

    public static Climate valueOf(int i)
    {
        switch (i)
        {
            case 0:
                return HOT;

            case 1:
                return NORMAL;

            case 2:
                return COLD;

        }

        return null;
    }

}
