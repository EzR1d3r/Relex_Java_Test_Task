package ru.relex.vita.task;

public class Item
{
    private String _sign;
    private int _x = 0;
    private int _y = 0;
    
    Item( String sign )
    {
        this._sign = sign; // можно добавить проверку, что отображение составляет 3 символа
    }

    public void set_pos(int x, int y)
    {
        this._x = x;
        this._y = y;
    }

    public boolean is_on_pos(int x, int y)
    {
        return this._x == x && this._y == y;
    }

    public int x() { return this._x; }
    public int y() { return this._y; }
    public String design(){ return this._sign; }
}