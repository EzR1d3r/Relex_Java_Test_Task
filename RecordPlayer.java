import java.util.Scanner;

public class RecordPlayer
{
    private ReplayHandler replay_recorder = null;
    private Scene scene = new Scene( 0, 0 );

    private Item[] CatcherItems = { new Item("[X]"), new Item("[X]"), new Item("[X]") };
    private Item RunnerItem = new Item("[*]");

    RecordPlayer( ReplayHandler replay_recorder )
    {
        this.replay_recorder = replay_recorder;
    }

    private void applyMove( SingleMove move )
    {
        if (move.player == 'C' )
            CatcherItems[move.idx].setPos(move.x, move.y);
        else
            RunnerItem.setPos(move.x, move.y);
    }

    public void load_replay( String path )
    {
        this.replay_recorder.load_replay(path);
        boolean[][] game_field = Utils.get_default_field(); // информация об игровом поле должна браться из реплея, пока берем стандартный
        this.scene = new Scene(game_field.length, game_field[0].length);
        this.scene.setMask(game_field);

        scene.addItem( this.RunnerItem );
        for (Item item : this.CatcherItems)
            scene.addItem(item);

        CatcherItems[0].setPos(0, 3);
        CatcherItems[1].setPos(1, 4);
        CatcherItems[2].setPos(2, 3);

        RunnerItem.setPos(1, 2);
    }

    public void play()
    {
        System.out.println( "Recorded game: " + replay_recorder.replay.catcher_name + "(catcher) vs " + replay_recorder.replay.runner_name + "(runner)" );
        scene.render();
        Scanner scanner =  new Scanner (System.in);
        for (SingleMove move : replay_recorder.replay.moves)
        {
            System.out.println("=========\n=========");
            this.applyMove(move);
            scene.render();
            System.out.println("Input any key.");
            scanner.next();
        }
        String winner_name = "";
        int last_move_idx = replay_recorder.replay.moves.size() - 1;
        SingleMove last_move = replay_recorder.replay.moves.get( last_move_idx );

        if (last_move.player == 'C')
            winner_name = replay_recorder.replay.catcher_name + "(catcher)";
        else
            winner_name = replay_recorder.replay.runner_name + "(runner)";

        System.out.println( winner_name + " wins on a " + last_move_idx + " move." );
        scanner.close();
    }
}
