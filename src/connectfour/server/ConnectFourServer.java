package connectfour.server;

import connectfour.ConnectFourException;
import connectfour.ConnectFourProtocol;
import connectfour.ConnectFour ;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The server waits for incoming client connections and pairs them off
 * to play the game.
 *
 * @author Kyle McCoy
 */
public class ConnectFourServer {

    /**
     * Starts a new sever.
     *
     * @param args Used to specify the port on which the server should listen
     *             for incoming client connections.
     *
     * @throws ConnectFourException If there is an error starting the server.
     */
    public static void main(String[] args) throws ConnectFourException {

        if (args.length != 1) {
            System.out.println("Usage: java ConnectFourServer port");
            System.exit(1);
        }
        try {
            int port = Integer.parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Waiting for players to connect...");

            Socket client1 = serverSocket.accept() ;
            Socket client2 = serverSocket.accept() ;

            Scanner client1networkin = new Scanner(client1.getInputStream()) ;
            PrintStream client1networkout = new PrintStream(client1.getOutputStream()) ;
            client1networkout.println(ConnectFourProtocol.CONNECT) ;

            Scanner client2networkin = new Scanner(client2.getInputStream()) ;
            PrintStream client2networkout = new PrintStream(client2.getOutputStream()) ;
            client2networkout.println(ConnectFourProtocol.CONNECT) ;

            System.out.println("Players Connected!") ;
            gameStart(client1networkin,client1networkout,client2networkin,client2networkout);

            client1.shutdownInput() ;
            client1.shutdownOutput() ;

            client2.shutdownInput() ;
            client2.shutdownOutput() ;

            serverSocket.close() ;
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /***
     * runs the game in a third party handshake fashion between the two clients
     * @param c1in scanner for player 1
     * @param c1out printstream for player 1
     * @param c2in scanner for player 2
     * @param c2out printstream for player 2
     */
    private static void gameStart(Scanner c1in, PrintStream c1out, Scanner c2in, PrintStream c2out){
        try {
            ConnectFour game = new ConnectFour();
            System.out.println(game);
            int turn = -1;
            while ((!game.hasWonGame()) && (!game.hasTiedGame())) {
                turn *= -1;
                if (turn == 1) {
                    System.out.println("Player 1's Turn!");
                    c1out.println(ConnectFourProtocol.MAKE_MOVE);
                    int number = Integer.parseInt(c1in.nextLine());
                    game.makeMove(number);
                    System.out.println("Move made at column " + number + "!");
                    c1out.println(ConnectFourProtocol.MOVE_MADE + " " + number);
                    c2out.println(ConnectFourProtocol.MOVE_MADE + " " + number);
                }
                if(turn == -1){
                    System.out.println("Player 2's Turn!");
                    c2out.println(ConnectFourProtocol.MAKE_MOVE) ;
                    int number = Integer.parseInt(c2in.nextLine()) ;
                    game.makeMove(number) ;
                    System.out.println("Move made at column " + number + "!");
                    c2out.println(ConnectFourProtocol.MOVE_MADE + " " + number);
                    c1out.println(ConnectFourProtocol.MOVE_MADE + " " + number);
                }
                System.out.println(game);
            }
            if(game.hasWonGame()){
                if(turn == 1){
                    System.out.println("Player 1 Won! Player 2 Lost!");
                    c1out.println(ConnectFourProtocol.GAME_WON) ;
                    c2out.println(ConnectFourProtocol.GAME_LOST) ;
                }else{
                    System.out.println("Player 2 Won! Player 1 Lost!");
                    c2out.println(ConnectFourProtocol.GAME_WON) ;
                    c1out.println(ConnectFourProtocol.GAME_LOST) ;
                }
            }else{
                System.out.println("Players Tied!");
                c1out.println(ConnectFourProtocol.GAME_TIED) ;
                c2out.println(ConnectFourProtocol.GAME_TIED) ;
            }
        }
        catch(ConnectFourException ex){
            c1out.println(ConnectFourProtocol.ERROR) ;
            c2out.println(ConnectFourProtocol.ERROR) ;
            System.out.println(ConnectFourProtocol.ERROR) ;
        }
        catch(NoSuchElementException ex){
            System.out.println("\n" + ConnectFourProtocol.ERROR + " : A Client has disconnected!") ;
        }
    }
}