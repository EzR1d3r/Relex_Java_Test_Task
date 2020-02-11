import java.util.Arrays;
import java.util.ArrayList;

class Item
{
    public String design;
    public int x = 0;
    public int y = 0;
    
    Item( String design )
    {
        this.design = design; // можно добавить проверка, что отображение составляет 3 символа
    }
}

class Scene 
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
            render_arr[item.x][item.y] = item.design;
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
            System.out.print("\n");
        }
    }
}

class Main {
    public static void main (String[] args)
    {
        Scene scene = new Scene(3, 5);
        boolean[][] mask = { {false, true, true, true, false}, {true, true, true, true, true}, {false, true, true, true, false} };
        scene.setMask(mask);
        Item item = new Item("[*]");
        item.x = 1;
        item.y = 2;
        scene.addItem(item);
        scene.render();
    }
}