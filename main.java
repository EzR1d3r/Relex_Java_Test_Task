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
    private int size_x = 3;
    private int size_y = 5;

    private boolean mask[][] = new boolean[this.size_x][this.size_y];
    private String filler = "[ ]";
    private String blank_filler = "   ";

    private ArrayList<Item> objects = new ArrayList<>(4);

    Scene()
    {
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

class QuickStart {
    public static void main (String[] args)
    {
        // System.out.print("Hello, World.");
        // boolean mask[] = new boolean[4];
        // Arrays.fill(mask, true);
        // for (int i = 0; i < 4; ++i)
        //     System.out.print( mask.length );

        Scene scene = new Scene();
        scene.render();
    }
}