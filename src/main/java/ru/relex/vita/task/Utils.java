package ru.relex.vita.task;

import java.util.Scanner;

public class Utils
{
    static public String default_replays_folder = "replays/";

    static public int get_int_from_user(int min, int max)
    {
        int res = 0;
        Scanner scanner =  new Scanner (System.in);
        do 
        {
            while (!scanner.hasNextInt())
            {
                System.out.println("Try again!");
                scanner.next();
            }
            res = scanner.nextInt();
        }
        while (res < min || res > max);

        // scanner.close();
        return res;
    }

    public static boolean[][] get_default_field()
    {
        boolean[][] game_field = { {false, true, true, true, false}, {true, true, true, true, true}, {false, true, true, true, false} };
        return game_field;
    }
}