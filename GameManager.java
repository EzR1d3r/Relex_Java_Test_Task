import java.util.Scanner;

public class GameManager
{
    private boolean[][] game_field = { {false, true, true, true, false}, {true, true, true, true, true}, {false, true, true, true, false} };
    private Item[] CatcherItems = { new Item("[X]"), new Item("[X]"), new Item("[X]") };
    private Item RunnerItem = new Item("[*]");

    private Scene scene = null;

    GameManager()
    {
        this.scene = new Scene( game_field.length, game_field[0].length );
        scene.setMask(game_field);

        scene.addItem( this.RunnerItem );
        for (Item item : this.CatcherItems)
            scene.addItem(item);
    }

    public void reset()
    {
        CatcherItems[0].setPos(0, 3);
        CatcherItems[1].setPos(1, 4);
        CatcherItems[2].setPos(2, 3);

        RunnerItem.setPos(1, 2);
    }

    private boolean check_point_for_out_of_range(int x, int y)
    {
        //проверка доступности ячейки, исходя из формы и размера поля
        if ( x < 0 || x >= this.game_field.length ) return false;
        if ( y < 0 || y >= this.game_field[0].length ) return false;
        if (!this.game_field[x][y]) return false;

        return true;
    }

    private boolean check_point_for_free(int x, int y)
    {
        if ( RunnerItem.onPos(x, y) ) return false;
        for (Item item : CatcherItems)
        {
            if ( item.onPos(x, y) ) return false;
        }
        return true;
    }

    private boolean available_point(int x, int y)
    {
        return this.check_point_for_out_of_range(x, y) && this.check_point_for_free(x, y);
    }

    private void welcome_message()
    {
        System.out.println("Welcome to the game 'Catch me if you can!' ");
        System.out.println("Make your choice:");
        System.out.println("1. Play the game on this computer.");
        // System.out.println("2. Replay a recorded game.");
        // System.out.println("3. Play the multiplayer game.");
    }

    public void start_the_game()
    {
        this.reset();
        this.welcome_message();
        int choice = get_int_from_user(1,3);
        
        switch (choice)
        {
            case 1:
                System.out.println("You have chose 1.");
                this.local_game();
                break;
            case 2:
                System.out.println("You have chose 2.");
                break;
            case 3:
                System.out.println("You have chose 3.");
                break;
            default:
                break;
        }
    }

    private boolean checkCatcherWin()
    {
        boolean res = true;

        if (this.RunnerItem.onPos(1, 0))
        {
            res = res && !this.available_point(0, 1);
            res = res && !this.available_point(1, 1);
            res = res && !this.available_point(2, 1);
        }
        else
        {
            int x = this.RunnerItem.x();
            int y = this.RunnerItem.y();

            res = res && !this.available_point(x + 1, y);
            res = res && !this.available_point(x - 1, y);
            res = res && !this.available_point(x, y + 1);
            res = res && !this.available_point(x, y - 1);
        }

        return res;
    }

    private boolean CatcherTurn()
    {
        System.out.println("Catcher, it is a your turn!");
        scene.render();

        System.out.println("Choose a unit [1][2][3]!");
        int item_idx = 0;
        int x = -1;
        int y = -1;
        boolean success = false;
        while (!success)
        {
            item_idx = this.get_int_from_user(1, 3) - 1;
            Item unit = CatcherItems[item_idx];
            x = unit.x();
            y = unit.y() - 1;

            success = this.check_point_for_out_of_range( x, y );
            if (!success && item_idx != 1)
            {   
                x = 1;
                y = 0;
                success = this.check_point_for_free( x, y );
            }
            else
            {
                success = this.check_point_for_free(x, y);
            }
        }
        CatcherItems[item_idx].setPos(x, y);

        return this.checkCatcherWin();
    }

    private void RunnerTurn()
    {
        System.out.println("Runner, it is a your turn!");
        scene.render();
    }

    private int get_int_from_user(int min, int max)
    {
        Scanner scanner =  new Scanner (System.in);
        int res = 0;
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

        scanner.close();
        return res;
    }

    private void local_game()
    {
        boolean game_over = false;

        while (!game_over)
        {
            game_over = this.CatcherTurn();
        }
    }

}