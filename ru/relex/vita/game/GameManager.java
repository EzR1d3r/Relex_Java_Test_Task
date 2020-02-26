package ru.relex.vita.game;

import java.io.File;

public class GameManager
{
    private GameEngine engine = new GameEngine( Utils.get_default_field() );
    private RecordPlayer player = new RecordPlayer( new ReplayHandler() );

    
    private void welcome_message()
    {
        System.out.println("Welcome to the game 'Catch me if you can!' ");
        System.out.println("Make your choice:");
        System.out.println("1. Play the game on this computer.");
        System.out.println("2. Replay a recorded game.");
        // System.out.println("3. Play the multiplayer game.");
    }

    private void watch_replay()
    {
        File folder = new File( Utils.default_replays_folder );
        String[] replays_names = folder.list();

        System.out.println("Please choose a replay.");

        if (replays_names.length == 0)
        {
            System.out.println("There is no replays.");
            return;
        }

        for (int i = 1; i <= replays_names.length; i++)
        {
            System.out.println( i + ". " + replays_names[i - 1] );
        }

        int idx = Utils.get_int_from_user(1, replays_names.length);
        player.load_replay( Utils.default_replays_folder + replays_names[idx - 1] );
        player.play();
    }
    
    public void start_the_game()
    {
        this.welcome_message();
        String catcher_name = "Winston";
        String runner_name  = "Tracer";
        int choice = Utils.get_int_from_user(1,2);
        
        switch (choice)
        {
            case 1:
                System.out.println("You have chose 1.");
                this.engine.local_game( catcher_name, runner_name ); //Здесь должен быть выбор имен игроков
                break;
            case 2:
                System.out.println("You have chose 2.");
                this.watch_replay();
                break;
            case 3:
                System.out.println("You have chose 3.");
                break;
            default:
                break;
        }
    }
}