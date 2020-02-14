import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
class SingleMove
{
    public char player = 'C';
    public int idx = 0;
    public int x = 0;
    public int y = 0;

    SingleMove( char player, int idx, int x, int y )
    {
        this.player = player;
        this.idx = idx;
        this.x = x;
        this.y = y;
    }

    SingleMove(){};
}

@JsonAutoDetect
class Replay
{
    public String catcher_name = "";
    public String runner_name  = "";

    public boolean[][] game_field = {};

    public ArrayList<SingleMove> moves = new ArrayList<>();

    // Replay(){};
}

public class ReplayHandler
{
    public Replay replay = new Replay();
    
    public void start_new_record( String catcher_name, String runner_name, boolean[][] game_field )
    {
        this.replay.catcher_name = catcher_name;
        this.replay.runner_name = runner_name;
        this.replay.game_field = game_field;
        this.replay.moves.clear();
    }

    public void record_move( SingleMove move )
    {
        this.replay.moves.add( move );
    }

    public void save_replay( String path )
    {
        String replay_name =  path + "/" + replay.catcher_name + "_" + replay.runner_name + "_" + System.currentTimeMillis() / 1000 + ".rep";

        try
        {
            File file = new File ( replay_name );
            file.createNewFile();
    
            PrintWriter pw = new PrintWriter(file);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(pw, this.replay);

            pw.close();
            
        } catch (Exception e)
        {
            System.out.println("Failed to save replay.");
            System.out.println(e);
        }
    }
    
    public void load_replay( String path )
    {
        try
        {
            File file = new File ( path );
            FileReader reader = new FileReader(file);
            
            ObjectMapper mapper = new ObjectMapper();
            this.replay = mapper.readValue(reader, Replay.class);
            
            reader.close();
            
        } catch (Exception e)
        {
            System.out.println("Failed to load replay.");
            System.out.println(e);
        }
    }
}