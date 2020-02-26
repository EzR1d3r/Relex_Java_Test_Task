import java.util.ArrayList;

public class Scene 
{
    private int size_x;
    private int size_y;

    private boolean game_field[][];
    private String filler = "[ ]";
    private String blank_filler = "   ";

    private ArrayList<Item> objects = new ArrayList<>(4);

    Scene( boolean game_field[][] )
    {
        this.game_field = game_field;
        this.size_x = game_field.length;
        this.size_y = game_field[0].length; //игнорируем возможную ошибку пустого пустого поля
    }

    private String[][] fill_render_arr()
    {
        String[][] render_arr= new String[this.size_x][this.size_y];
        for (int x = 0; x < this.size_x; x++)
        {
            for (int y = 0; y < this.size_y; y++)
            {
                if (game_field[x][y])
                    render_arr[x][y] = this.filler;
                else
                    render_arr[x][y] = this.blank_filler;
            }
        }

        for (Item item : this.objects)
        {
            render_arr[item.x()][item.y()] = item.design();
        }

        return render_arr;
    }

    public void add_item( Item item )
    {
        this.objects.add(item);
    }

    public void render()
    {
        String[][] render_arr = this.fill_render_arr();

        for (int y = 0; y < this.size_y; y++)
        {
            for (int x = 0; x < this.size_x; x++)
            {
                System.out.print( render_arr[x][y] );
            }
            System.out.println();
        }
    }
}