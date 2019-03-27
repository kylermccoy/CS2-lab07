package connectfour.client;

import connectfour.ConnectFour ;
import connectfour.ConnectFourException;
import connectfour.ConnectFourProtocol;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents the client side of a Connect Four game. Establishes a connection
 * with the server and then responds to requests from the server (often by
 * prompting the real user).
 *
 * @author YOUR NAME HERE
 */
public class ConnectFourClient {

    /**
     * Starts a new {@link ConnectFourClient}.
     *
     * @param args Used to specify the hostname and port of the Connect Four
     *             server through which the client will play.
     * @throws ConnectFourException If there is a problem creating the client
     *                              or connecting to the server.
     */
    public static void main(String[] args) throws ConnectFourException {
        if (args.length != 2) {
            System.out.println(
                    "Usage: java ConnectFourClient hostname port");
            System.exit(1);
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        try{
            Socket socket = new Socket(hostname,port) ;
            Scanner networkin = new Scanner(socket.getInputStream()) ;
            PrintStream networkout = new PrintStream(socket.getOutputStream()) ;

            String response = networkin.nextLine() ;
            if(response.equals(ConnectFourProtocol.CONNECT)){
                System.out.println("Connected!") ;
                gameStart(networkin,networkout) ;
            }else{
                System.out.println(ConnectFourProtocol.ERROR) ;
            }
            socket.shutdownInput() ;
            socket.shutdownOutput() ;
            socket.close() ;
        }
        catch(IOException ex){
            System.out.println("Server invalid or unavailable!");
        }
    }

    /***
     * runs a local version of the game and communicates with server
     * @param networkin scanner for server
     * @param networkout printstream for server
     */
    private static void gameStart(Scanner networkin, PrintStream networkout){
        try{
            Scanner prompt = new Scanner(System.in) ;
            ConnectFour game = new ConnectFour() ;
            System.out.println(game) ;
            while(true){
                try {
                    String response = networkin.nextLine();
                    if(response.equals(ConnectFourProtocol.MAKE_MOVE)){
                        System.out.print("Your turn! Enter a column: ") ;
                        int number = prompt.nextInt() ;
                        networkout.println(number) ;
                    }
                    if(response.contains(ConnectFourProtocol.MOVE_MADE)){
                        int number = Integer.parseInt(String.valueOf(response.charAt(response.length()-1))) ;
                        System.out.println("A move has been made in column " + number + "!") ;
                        game.makeMove(number) ;
                        System.out.println(game) ;
                    }
                    if(response.equals(ConnectFourProtocol.GAME_WON)){
                        System.out.println("You win! Yay!") ;
                        break ;
                    }
                    if(response.equals(ConnectFourProtocol.GAME_LOST)){
                        System.out.println("You lose! Nice try!") ;
                        break ;
                    }
                    if(response.equals(ConnectFourProtocol.GAME_TIED)){
                        System.out.println("You tied!");
                        break ;
                    }
                    if(response.equals(ConnectFourProtocol.ERROR)){
                        System.out.println(ConnectFourProtocol.ERROR) ;
                        break ;
                    }
                }
                catch(NoSuchElementException ex){
                    System.out.println("The other player has disconnected!\nYou win! Yay!") ;
                    break ;
                }
            }
        }
        catch(ConnectFourException ex){
            System.out.println(ConnectFourProtocol.ERROR) ;
        }
    }
}
