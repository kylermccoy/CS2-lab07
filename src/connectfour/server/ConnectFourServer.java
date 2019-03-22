package connectfour.server;

import connectfour.ConnectFourException;
import connectfour.ConnectFourProtocol;
import connectfour.ConnectFour ;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * The server waits for incoming client connections and pairs them off
 * to play the game.
 *
 * @author YOUR NAME HERE
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

            Socket client1 = serverSocket.accept() ;
            Socket client2 = serverSocket.accept() ;

            Scanner client1networkin = new Scanner(client1.getInputStream()) ;
            PrintStream client1networkout = new PrintStream(client1.getOutputStream()) ;
            client1networkout.println(ConnectFourProtocol.CONNECT) ;

            Scanner client2networkin = new Scanner(client2.getInputStream()) ;
            PrintStream client2networkout = new PrintStream(client2.getOutputStream()) ;
            client2networkout.println(ConnectFourProtocol.CONNECT) ;

            gameStart(client1networkin,client1networkout,client2networkin,client2networkout);
        }
        catch(IOException ex){
            System.out.println(ConnectFourProtocol.ERROR);
        }
    }

    public static void gameStart(Scanner c1in, PrintStream c1out, Scanner c2in, PrintStream c2out){
        try {
            ConnectFour game = new ConnectFour();
            int turn = 1;
            while ((!game.hasWonGame()) && (!game.hasTiedGame())) {
                if (turn == 1) {
                    c1out.println(ConnectFourProtocol.MAKE_MOVE);
                    int number = Integer.parseInt(c1in.nextLine());
                    game.makeMove(number);
                    c1out.println(ConnectFourProtocol.MOVE_MADE + " " + number);
                    c2out.println(ConnectFourProtocol.MOVE_MADE + " " + number);
                }
                if(turn == -1){
                    c1out.println(ConnectFourProtocol.MAKE_MOVE) ;
                    int number = Integer.parseInt(c2in.nextLine()) ;
                    game.makeMove(number) ;
                    c2out.println(ConnectFourProtocol.MOVE_MADE + " " + number);
                    c1out.println(ConnectFourProtocol.MOVE_MADE + " " + number);
                }
                turn *= -1;
            }
        }
        catch(ConnectFourException ex){
            c1out.println(ConnectFourProtocol.ERROR) ;
            c2out.println(ConnectFourProtocol.ERROR) ;
            System.out.println(ConnectFourProtocol.ERROR) ;
        }
    }
}