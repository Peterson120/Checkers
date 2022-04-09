import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Piece here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Piece extends Actor
{
    private GreenfootImage image;
    public final int PIECE_SIZE = GameWorld.WORLD_SIZE / 10;
    public Color color;
    
    private MouseInfo mouse;
    private boolean hovering = false;
    private boolean clicked = false;
    
    private ArrayList<Tile> availableTiles;
    
    public Piece(Color color){
        this.color = color;
        image = new GreenfootImage(PIECE_SIZE + 1, PIECE_SIZE + 1);
        drawPiece(color);
    }
    
    public Color getColor() {return color;}
    
    /**
     * Act - do whatever the Pieces wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        mouse = Greenfoot.getMouseInfo();
        // try
        // {
            // System.out.println(mouse.getX() + " " + mouse.getY()); // debug
        // }catch(Exception NullPointerException){}
        Tile tile = (Tile)getOneIntersectingObject(Tile.class);
        if(Greenfoot.mouseClicked(this)){
            hovering = false;
            clicked = true;
            tile.drawTile(tile.getColor(), clicked);
            availableTiles = new ArrayList<Tile>();
            check();
        }
        else if(clicked){
            for(Tile availableTile: availableTiles){
                if(Greenfoot.mouseClicked(availableTile)){
                    this.setLocation(availableTile.getX(), availableTile.getY());
                    clicked = false;
                    createBoard();
                }
                else if(Greenfoot.mouseClicked(this)) clicked = false;
            }
        }
        else if(!clicked){
            if(Greenfoot.mouseMoved(this)){
                hovering = true;
                // if(!soundPlayed){ //adds sound
                    // moveSound.play();
                    // soundPlayed = true;
                // }
                
                tile.drawTile(tile.getColor(), hovering);
            }
            if(Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)){
                hovering = false;
                // soundPlayed = false;
                
                tile.drawTile(tile.getColor(), hovering);
            }
        }
    }
    
    private void drawPiece(Color color){
        image.clear();
        image.setColor(color);
        image.fillOval(1, 1, PIECE_SIZE, PIECE_SIZE);
        setImage(image);
    }
    
    private void check(){
        char[][] middle = createBoard();
        printBoard(middle);
        int newX = getX()/400/8;
        int newY = getY()/400/8;
        if(GameWorld.game.check(middle,newX,newY,newX-1,newY-1)) {
            List tilesPresent = getWorld().getObjectsAt(getX() - GameWorld.TILE_SIZE, getY() - GameWorld.TILE_SIZE, Tile.class);
            availableTiles.add((Tile)tilesPresent.get(0));
        }
        if(GameWorld.game.check(middle,newX,newY,newX+1,newY-1))
        {
            List tilesPresent = getWorld().getObjectsAt(getX() + GameWorld.TILE_SIZE, getY() - GameWorld.TILE_SIZE, Tile.class);
            availableTiles.add((Tile)tilesPresent.get(0));
        }
        // if(getY() - GameWorld.TILE_SIZE > 0){
            // if(getX() - GameWorld.TILE_SIZE > 0){
                // List piecePresent = getWorld().getObjectsAt(getX() - GameWorld.TILE_SIZE, getY() - GameWorld.TILE_SIZE, Piece.class);
                // if(piecePresent.size() == 0){
                    // List tilesPresent = getWorld().getObjectsAt(getX() - GameWorld.TILE_SIZE, getY() - GameWorld.TILE_SIZE, Tile.class);
                    // availableTiles.add((Tile)tilesPresent.get(0));
                // }
                // else if(((Piece)(piecePresent.get(0))).getColor() != color)
                // {
                    // if(getX() - 2 * GameWorld.TILE_SIZE > 0){
                        // if(getY() - 2 * GameWorld.TILE_SIZE > 0){
                            // piecePresent = getWorld().getObjectsAt(getX() - 2 * GameWorld.TILE_SIZE, getY() - 2 * GameWorld.TILE_SIZE, Piece.class);
                            // if(piecePresent.get(0) == null){
                                // List tilesPresent = getWorld().getObjectsAt(getX() - 2 * GameWorld.TILE_SIZE, getY() - 2 * GameWorld.TILE_SIZE, Tile.class);
                                // availableTiles.add((Tile)tilesPresent.get(0));
                            // }
                        // }
                    // }
                // }
            // }
            
            // if(getX() + GameWorld.TILE_SIZE < GameWorld.WORLD_SIZE){
                
            // }
        // }
        for(Tile tile: availableTiles){
            tile.drawTile(tile.getColor(), true);
        }
    }
    
    private void printBoard(char[][] board) //debugging
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
    
    private char[][] createBoard() //issue in this function
    {
        char[][] board = GameWorld.game.getBoard();
        for(int col = 0; col < 7; col++)
        {
            for(int row = 0; row < 7; row++)
            {
                List pieceAt = getWorld().getObjectsAt(50 * (row) + 25,50 * (col) + 25, Piece.class);
                System.out.println(50 * (row + 1) + 25);
                System.out.println(50 * (col + 1) + 25);
                if(pieceAt.size() == 0)
                {
                    board[row][col] = 'n';
                }
                else if(((Piece)pieceAt.get(0)).getColor().equals(GameWorld.pieceColor1))
                {
                    board[row][col] = 'w';
                }
                else if(((Piece)pieceAt.get(0)).getColor().equals(GameWorld.pieceColor2))
                {
                    board[row][col] = 'r';
                }
            }
        }
        return board;
    }
}
