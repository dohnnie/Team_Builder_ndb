/**
 * GUI for the program
 */
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class TeamInterface extends JFrame implements ActionListener{

    //For frame size
    private final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    private JButton infoButton;

    //Components for the JPanel partyPanel
    //Pokemon names
    private JTextField[] pNames = new JTextField[6];
    //Type array where each row is a separate pokemon, and each column is a separate type
    private JTextField[][] pTypes = new JTextField[6][2];
    //Moveset array where each row is a separate pokemon, and each column is a separate move
    private JTextField[][] pMoveset = new JTextField[6][4];

    //Labels for the JPanel infoPanel
    private JLabel[] pNamesLabel = new JLabel[6];
    private JLabel[] pResistance = new JLabel[6];
    private JLabel[] pWeakness = new JLabel[6];
    private JLabel[] pImmunity = new JLabel[6];
    private JLabel[] pTypeCover = new JLabel[6];
    private JLabel[] pNoTypeCover = new JLabel[6];

    //Interface Constructor
    TeamInterface() {
        setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
        setTitle("Team Builder: NDb");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        //Center the frame
        int frmXPos = (SCREEN_WIDTH / 2) - (SCREEN_WIDTH / 4);
        int frmYPos = (SCREEN_HEIGHT / 2) - (SCREEN_HEIGHT / 4);
        setLocation(frmXPos, frmYPos);

        //Panel that will hold all the containers the user will input information into
        JPanel partyPanel = new JPanel(new GridLayout(3,2, 20,20)); 

        //Panel that will hold all the output from the program
        JPanel infoPanel = new JPanel(new GridLayout(6,1,0,5)); // panel1
                                                                                    // panel2
                                                                                    // panel3
                                                                                    // panel4
                                                                                    // panel5
                                                                                    // panel6
        /*Pokemons panel will hold 3 JPanels
            1) Pokemon Names
            2) Pokemon Types
            3) Pokemon Moveset
        */
        JPanel[] pokemons = new JPanel[6];
        JPanel[] pNamesPanel = new JPanel[6];
        JPanel[] pTypesPanel = new JPanel[6];
        JPanel[] pMovesetPanel = new JPanel[6];
        JPanel[] pMovesetLayout = new JPanel[6];

        JPanel[] pTypeInfo = new JPanel[6];
        //Initialize all the JPanels and components needed and sets the layout
        for(int i = 0; i < pokemons.length; i++) {
            pokemons[i] = new JPanel(new BorderLayout());

            //Holds labels
            pTypeInfo[i] = new JPanel(new GridLayout(6,1)); // panel1
                                                                      // panel2
                                                                      // panel3
                                                                      // panel4
                                                                      // panel5
                                                                      // panel6

            pNamesPanel[i] = new JPanel(new GridLayout(1,2)); // panel1 panel2

            pTypesPanel[i] = new JPanel(new GridLayout(1,3));   // panel1 panel2 panel3

            pMovesetPanel[i] = new JPanel(new GridLayout(2,1)); // panel1 
                                                                          // panel2

            pMovesetLayout[i] = new JPanel(new GridLayout(2,2)); //panel1 panel2
                                                                           //panel3 panel4

            //initializes pNames
            pNames[i] = new JTextField(20);
            //initializes all of pTypes
            for(int j = 0; j < 2; j++) {
                pTypes[i][j] = new JTextField(10);
            }
            //initializes all of pMoveset
            for(int j = 0; j < 4; j++) {
                pMoveset[i][j] = new JTextField(10);
                pMovesetLayout[i].add(pMoveset[i][j]);
            }

            //initializing labels and adds to the pTypesInfo panel
            pNamesLabel[i] = new JLabel("Pokemon: ");
            pTypeInfo[i].add(pNamesLabel[i]);
            pResistance[i] = new JLabel("Resists: ");
            pTypeInfo[i].add(pResistance[i]);
            pWeakness[i] = new JLabel("Weak: ");
            pTypeInfo[i].add(pWeakness[i]);
            pImmunity[i] = new JLabel("Immune: ");
            pTypeInfo[i].add(pImmunity[i]);
            pTypeCover[i] = new JLabel("Type Coverage: ");
            pTypeInfo[i].add(pTypeCover[i]);
            pNoTypeCover[i] = new JLabel("No Coverage: ");
            pTypeInfo[i].add(pNoTypeCover[i]);
        }

        //Add components to pNamesPanel, pTypesPanel, and pMovesetPanel
        JLabel[] pNamesLabel = new JLabel[6];
        JLabel[] pTypesLabel = new JLabel[6];
        JLabel[] pMovesetsLabel = new JLabel[6];
        for(int i = 0; i < pokemons.length; i++) {
            pNamesLabel[i] = new JLabel("Name: ");
            pTypesLabel[i] = new JLabel("Type: ");
            pMovesetsLabel[i] = new JLabel("Moves");

            pNamesPanel[i].add(pNamesLabel[i]);
            pTypesPanel[i].add(pTypesLabel[i]);
            pMovesetPanel[i].add(pMovesetsLabel[i]);
            
            //Add name JTextField to pNamesPanel
            pNamesPanel[i].add(pNames[i]);
            //Add type JTextField to pTypesPanel
            for(int j = 0; j < 2; j++) {
                pTypesPanel[i].add(pTypes[i][j]);
            }
            pMovesetPanel[i].add(pMovesetLayout[i]);

        }

        //Adds JPanel components to each box of the partyPanel
        for(int i = 0; i < 6; i++) {
            pokemons[i].add(pNamesPanel[i], BorderLayout.NORTH);
            pokemons[i].add(pTypesPanel[i], BorderLayout.CENTER);
            pokemons[i].add(pMovesetPanel[i],BorderLayout.SOUTH);

            partyPanel.add(pokemons[i]);
        }

        for(JPanel p : pTypeInfo) {
            infoPanel.add(p);
        }
        

        //Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        infoButton = new JButton("Get Type Coverage");
        infoButton.addActionListener(this);
        buttonPanel.add(infoButton);

        partyPanel.setPreferredSize(new Dimension(frmXPos,500));
        infoPanel.setPreferredSize(new Dimension(frmXPos,500));
        buttonPanel.setPreferredSize(new Dimension(frmXPos,50));

        add(partyPanel,BorderLayout.WEST);
        add(infoPanel,BorderLayout.EAST);
        add(buttonPanel,BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    /*
     * Displays the program's interface
     */
    public static void showTeamInterface() {
        new TeamInterface();
    }

     public static void main(String[] args) {
        //Run the window through the event dispatching thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showTeamInterface();
            }
        });
     }

     public void actionPerformed(ActionEvent e) {
        String[] pokemon = new String[6], pType1 = new String[6], pType2 = new String[6],
         mType1 = new String[6], mType2 = new String[6], mType3 = new String[6], mType4 = new String[6];

        int partySize = 0;

        if(e.getSource() == infoButton) {
            //Gets the values for ALL textfields regardless if they're empty or not
            for(int i = 0; i < 6; i++) {
                pokemon[i] = pNames[i].getText();
                pType1[i] = pTypes[i][0].getText();
                pType2[i] = pTypes[i][1].getText();
                mType1[i] = pMoveset[i][0].getText();
                mType2[i] = pMoveset[i][1].getText();
                mType3[i] = pMoveset[i][2].getText();
                mType4[i] = pMoveset[i][3].getText();

                //pNamesLabel[i].setText(pokemon[i]);
                partySize += !(pokemon[i].equals("")) ? 1 : 0;
            }

            //Shifting the data as far left in the array as possible
            //Just in case the user decides to use random boxes to input data
            if(partySize < 6) {
                int index = 0;
                for(int i = 0; i < 6; i++) {
                    if(!(pokemon[i].equals(""))) {
                        pokemon[index] = pokemon[i];
                        pType1[index] = pType1[i];
                        pType2[index] = pType2[i];
                        mType1[index] = mType1[i];
                        mType2[index] = mType2[i];
                        mType3[index] = mType3[i];
                        mType4[index] = mType4[i];
    
                        index++;
                    }
                }
            }

            //Rows = pokemon, columns = types
            String[][] pTypeCombo = new String[partySize][2];
            String[][] mTypes = new String[partySize][4];

            //Moving all the data again and also capitalizing the name of each pokemon
            for(int i = 0; i < partySize; i++) {
                pokemon[i] = capitalizeName(pokemon[i]);

                pTypeCombo[i][0] = pType1[i];
                pTypeCombo[i][1] = pType2[i];

                mTypes[i][0] = mType1[i];
                mTypes[i][1] = mType2[i];
                mTypes[i][2] = mType3[i];
                mTypes[i][3] = mType4[i];
            }

            UserParty partyManager = new UserParty(partySize,pokemon,pTypeCombo,mTypes);

            //Sets the JLabels after getting all the data needed from the user
            for(int i = 0; i < partySize; i++) {
                pNamesLabel[i].setText(pokemon[i]);
                pResistance[i].setText("Resists: " + partyManager.getPokemonResists(i));
                pWeakness[i].setText("Weakness: " + partyManager.getPokemonWeakness(i));
                pImmunity[i].setText("Immune: " + partyManager.getPokemonImmunity(i));
                pTypeCover[i].setText("Coverage for: " + partyManager.getPokemonCoverage(i));
                pNoTypeCover[i].setText("No Coverage for: " + partyManager.getPokemonCoverageNeeded(i));
            }
        }
    }
    public String capitalizeName(String name) {
        char firstLetter = name.charAt(0);

        //in case name is already capitalized
        if(firstLetter >= 'A' && firstLetter <= 'Z')
            return name;

        firstLetter -= 32;

        String result = "" + firstLetter;
        for(int i = 1; i < name.length(); i++) {
            result +=name.charAt(i);
        }

        return result;
    }
}
