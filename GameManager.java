public class GameManager
{
    private GameEngine engine = new GameEngine();
    
    private void welcome_message()
    {
        System.out.println("Welcome to the game 'Catch me if you can!' ");
        System.out.println("Make your choice:");
        System.out.println("1. Play the game on this computer.");
        // System.out.println("2. Replay a recorded game.");
        // System.out.println("3. Play the multiplayer game.");
    }

    public void start_the_game()
    {
        this.welcome_message();
        int choice = Utils.get_int_from_user(1,3);
        
        switch (choice)
        {
            case 1:
                System.out.println("You have chose 1.");
                this.engine.local_game();
                break;
            case 2:
                System.out.println("You have chose 2.");
                break;
            case 3:
                System.out.println("You have chose 3.");
                break;
            default:
                break;
        }
    }
}