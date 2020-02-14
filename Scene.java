import java.util.Arrays;
import java.util.ArrayList;

public class Scene 
{
    private int size_x;
    private int size_y;

    private boolean mask[][];
    private String filler = "[ ]";
    private String blank_filler = "   ";

    private ArrayList<Item> objects = new ArrayList<>(4);

    Scene( int size_x, int size_y )
    {
        this.size_x = size_x;
        this.size_y = size_y;
        this.mask = new boolean[this.size_x][this.size_y];

        for (int i = 0; i < mask.length; i++)
            Arrays.fill( this.mask[i], true );
    }

    private String[][] fill_render_arr()
    {
        String[][] render_arr= new String[this.size_x][this.size_y];
        for (int x = 0; x < this.size_x; x++)
        {
            for (int y = 0; y < this.size_y; y++)
            {
                if (mask[x][y])
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

    public void addItem( Item item )
    {
        this.objects.add(item);
    }

    public void setMask( boolean[][] mask )
    {
        this.mask = mask;
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