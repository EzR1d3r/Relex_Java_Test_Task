public class GameEngine
{
    private boolean[][] game_field;
    private Item[] CatcherItems = { new Item("[X]"), new Item("[X]"), new Item("[X]") };
    private Item RunnerItem = new Item("[*]");

    private Scene scene = null;
    private ReplayHandler replay_handler = new ReplayHandler();

    GameEngine( boolean[][] game_field )
    {
        this.game_field = game_field;

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

    private boolean checkRunnerWin()
    {
        boolean res = true;
        
        res = res && this.RunnerItem.y() > this.CatcherItems[0].y();
        res = res && this.RunnerItem.y() > this.CatcherItems[1].y();
        res = res && this.RunnerItem.y() > this.CatcherItems[2].y();
        
        return res;
    }

    private boolean CatcherTurn()
    {
        System.out.println("============================\n============================");
        System.out.println("Catcher, it is a your turn!");
        scene.render();

        System.out.println("Choose a unit [1][2][3]!");
        int item_idx = 0;
        int x = -1;
        int y = -1;
        boolean success = false;
        while (!success)
        {
            item_idx = Utils.get_int_from_user(1, 3) - 1;
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
        this.replay_handler.record_move( new SingleMove('C', item_idx, x, y) );

        boolean win = this.checkCatcherWin();
        
        if (win)
        {
            System.out.println("Catcher wins!");
        }
        return win;
    }

    private boolean RunnerTurn()
    {
        System.out.println("============================\n============================");
        System.out.println("Runner, it is a your turn!");
        scene.render();
        
        System.out.println("Choose a correct direction: 1.Up 2.Left 3.Right 4.Down!");
        int direction = -1;
        int x = -1;
        int y = -1;
        boolean success = false;

        while (!success)
        {
            direction = Utils.get_int_from_user(1, 4);
            switch (direction)
            {
                case 1: // Up
                    x = this.RunnerItem.x();
                    y = this.RunnerItem.y() - 1;
                    break;
                case 2: // Left
                    x = this.RunnerItem.x() - 1;
                    y = RunnerItem.onPos(1, 0) ? 1 : RunnerItem.y();
                    break;
                case 3: // Right
                    x = this.RunnerItem.x() + 1;
                    y = RunnerItem.onPos(1, 0) ? 1 : RunnerItem.y();
                    break;
                case 4: // Down
                    x = this.RunnerItem.x();
                    y = this.RunnerItem.y() + 1;
                    break;
                default:
                    break;
            }

            success = this.available_point(x, y);
        }

        this.RunnerItem.setPos(x, y);
        this.replay_handler.record_move( new SingleMove('R', 0, x, y) );

        boolean win = this.checkRunnerWin();
        if (win)
        {
            System.out.println("Runner wins!");
        }
        return win;
    }

    public void local_game( String catcher_name, String runner_name )
    {
        this.reset();
        this.replay_handler.start_new_record(catcher_name, runner_name, this.game_field);

        boolean game_over = false;
        while (!game_over) 
        {
            game_over = this.CatcherTurn() || this.RunnerTurn();
        }
        scene.render();

        this.replay_handler.save_replay("replays/");
        System.out.println("GAME OVER");
    }

}