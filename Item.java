public class Item
{
    private String _design;
    private int _x = 0;
    private int _y = 0;
    
    Item( String design )
    {
        this._design = design; // можно добавить проверка, что отображение составляет 3 символа
    }

    public void setPos(int x, int y)
    {
        this._x = x;
        this._y = y;
    }

    public boolean onPos(int x, int y)
    {
        return this._x == x && this._y == y;
    }

    public int x() { return this._x; }
    public int y() { return this._y; }
    public String design(){ return this._design; }
}