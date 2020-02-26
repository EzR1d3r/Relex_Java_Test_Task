import java.util.Scanner;

public class RecordPlayer
{
    private ReplayHandler replay_handler = null;
    private Scene scene = null;

    private Item[] CatcherItems = { new Item("[X]"), new Item("[X]"), new Item("[X]") };
    private Item RunnerItem = new Item("[*]");

    RecordPlayer( ReplayHandler replay_recorder )
    {
        this.replay_handler = replay_recorder;
    }

    private void apply_move( SingleMove move )
    {
        if (move.player == 'C' )
            CatcherItems[move.idx].set_pos(move.x, move.y);
        else
            RunnerItem.set_pos(move.x, move.y);
    }

    public void load_replay( String path )
    {
        this.replay_handler.load_replay(path);
        boolean[][] game_field = this.replay_handler.replay.game_field;
        this.scene = new Scene(game_field);

        scene.add_item( this.RunnerItem );
        for (Item item : this.CatcherItems)
            scene.add_item(item);

        CatcherItems[0].set_pos(0, 3);
        CatcherItems[1].set_pos(1, 4);
        CatcherItems[2].set_pos(2, 3);

        RunnerItem.set_pos(1, 2);
    }

    public void play()
    {
        System.out.println( "Recorded game: " + replay_handler.replay.catcher_name + "(catcher) vs " + replay_handler.replay.runner_name + "(runner)" );
        scene.render();
        Scanner scanner =  new Scanner (System.in);
        for (SingleMove move : replay_handler.replay.moves)
        {
            System.out.println("=========\n=========");
            System.out.println("Input any key.");
            scanner.next();
            this.apply_move(move);
            scene.render();
        }
        String winner_name = "";
        int last_move_idx = replay_handler.replay.moves.size() - 1;
        SingleMove last_move = replay_handler.replay.moves.get( last_move_idx );

        if (last_move.player == 'C')
            winner_name = replay_handler.replay.catcher_name + "(catcher)";
        else
            winner_name = replay_handler.replay.runner_name + "(runner)";

        System.out.println( winner_name + " wins on a " + last_move_idx + " move." );
        scanner.close();
    }
}
