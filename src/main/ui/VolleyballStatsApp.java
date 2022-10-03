package ui;

import model.Event;
import model.EventLog;
import model.Team;
import model.VolleyballPlayer;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;


public class VolleyballStatsApp extends JFrame {

    private static final String JSON_STORE = "./data/team.json";
    private JButton addPlayer;
    private JButton viewRoster;
    private JButton saveTeam;
    private JButton loadTeam;
    private JButton startGame;
    private JButton setTeamNamePanel;
    private JTextField teamName;
    private JTextField numberTextField;
    private JTextField nameTextField;
    private JTextField editNumberField;
    private JTextField newNumber;
    private JTextField newName;
    private JTextField getNumField;
    private Team team;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;


    // MODIFIES: this
    VolleyballStatsApp() {
        team = new Team();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        super.setTitle("Volleyball Statistics Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        setSize(900,700);
        ImageIcon img = new ImageIcon("./src/main/model/volleyball.png");
        setIconImage(img.getImage());
        add(mainMenu(), BorderLayout.NORTH);
        add(sideButtons(), BorderLayout.EAST);
        setTeamNamePanel();
        addButtonListeners();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                printEventLog(EventLog.getInstance());
            }
        });
    }

    // EFFECTS: prints out an event log of all user actions
    // MODIFIES:
    public void printEventLog(EventLog el) {
        for (Event x : el) {
            System.out.println(x);
        }
    }

    // EFFECTS: creates the introduction panel in the application, filling it with text
    private JPanel mainMenu() {
        JPanel organizer = new JPanel();
        organizer.setLayout(new BoxLayout(organizer, BoxLayout.Y_AXIS));
        organizer.setBorder(BorderFactory.createTitledBorder("Introduction"));
        String introduction = "Hello! Welcome to the volleyball statistics application program."
                + " This program is designed to help coaches, and players determine what";
        String introduction2 = "skill needs the most improvement in their volleyball career";
        JLabel applicationIntro = new JLabel(introduction);
        JLabel applicationIntro2 = new JLabel(introduction2);
        String instructions = "To start, add your players to your roster using the add button on the side.";
        JLabel applicationInstructions = new JLabel(instructions);
        organizer.add(applicationIntro);
        organizer.add(applicationIntro2);
        organizer.add(applicationInstructions);

        return organizer;
    }

    // EFFECTS: creates the side button panel seen on the right hand side of the application
    private JPanel sideButtons() {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Options"));
        organizer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 0.25;
        c.insets = new Insets(15,0,15,0);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        addPlayer = new JButton("Add Player");
        viewRoster = new JButton("View Roster");
        saveTeam = new JButton("Save Current Team");
        loadTeam = new JButton("Load Team");
        startGame = new JButton("Start the Game");
        setTeamNamePanel = new JButton("Set Team Name");
        setButtonStyle();
        organizer.add(setTeamNamePanel, c);
        organizer.add(addPlayer, c);
        organizer.add(viewRoster, c);
        organizer.add(saveTeam, c);
        organizer.add(loadTeam, c);
        organizer.add(startGame, c);
        return organizer;
    }

    // EFFECTS: sets buttons to certain colors
    private void setButtonStyle() {
        addPlayer.setBackground(Color.WHITE);
        viewRoster.setBackground(Color.WHITE);
        saveTeam.setBackground(Color.WHITE);
        loadTeam.setBackground(Color.WHITE);
        startGame.setBackground(Color.WHITE);
        setTeamNamePanel.setBackground(Color.WHITE);
    }

    // REQUIRES:
    // EFFECTS: adds action listeners to side panel buttons
    // MODIFIES:
    private void addButtonListeners() {
        setTeamNamePanel.addActionListener(e -> setTeamNamePanel());
        addPlayer.addActionListener(e -> addPlayerPanel(0));
        viewRoster.addActionListener(e -> viewRosterPanel(0));
        saveTeam.addActionListener(e -> saveTeam());
        loadTeam.addActionListener(e -> loadTeam());
        startGame.addActionListener(e -> startGame(0));
        startGame.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                startGame.setBackground(Color.GREEN);
            }

            public void mouseExited(MouseEvent evt) {
                startGame.setBackground(Color.WHITE);
            }
        });
    }


    // REQUIRES:
    // EFFECTS: refreshes the panel to view an option to set a team name
    // MODIFIES:
    private void setTeamNamePanel() {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Set Team Name"));
        JPanel organizer2 = new JPanel();
        JLabel setTeamName = new JLabel("Enter your team name here: ");
        teamName = new JTextField(20);
        JButton setTeamNameButton = new JButton("Set Team Name");
        setTeamNameButton.setBackground(Color.white);
        teamNameButtonListener(setTeamNameButton);

        JLabel userMessage = new JLabel("Please note - if no team is entered then a default name will be set");
        organizer2.add(setTeamName);
        organizer2.add(teamName);
        organizer2.add(setTeamNameButton);
        organizer.add(organizer2, BorderLayout.NORTH);
        organizer.add(userMessage, BorderLayout.SOUTH);
        refreshGui(organizer);
        addButtonListeners();
    }

    // REQUIRES:
    // EFFECTS: adds a button listener to the set team name button
    // MODIFIES:
    private void teamNameButtonListener(JButton setTeamNameButton) {
        setTeamNameButton.addActionListener(e -> setTeamName());
    }

    // EFFECTS: performs the act of setting the name of the team
    private void setTeamName() {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Team Name"));
        if (teamName.getText().equals("''")) {
            team.setTeamName("Default Name");
        } else {
            team.setTeamName(teamName.getText());
        }
        
        String message = "Team name has been set to '" + team.getTeamName() + "'";
        JLabel output = new JLabel(message);
        organizer.add(output);
        refreshGui(organizer);
        addButtonListeners();

    }

    // EFFECTS: refreshes the JFrame to show a Panel that contains a team roster
    private void viewRosterPanel(int state) {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Roster"));
        organizer.add(constructRoster());
        organizer.add(editPlayer());
        if (state == -1) {
            JLabel errorMessage = new JLabel("Error: No Player found");
            errorMessage.setForeground(Color.RED);
            organizer.add(errorMessage);
        }
        refreshGui(organizer);
        addButtonListeners();
    }

    // EFFECTS: constructs the team roster table
    private JTable constructRoster() {
        String[] col = {"Number", "Name", "Digs", "Kills", "Assists", "Blocks", "Aces"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableModel.addRow(col);
        for (int i = 0; i < team.numPlayers(); i++) {
            int number = team.getTeam().get(i).getPlayerNumber();
            String name = team.getTeam().get(i).getPlayerName();
            int digs = team.getTeam().get(i).getPlayerStats().getDigs();
            int kills = team.getTeam().get(i).getPlayerStats().getKills();
            int assists = team.getTeam().get(i).getPlayerStats().getAssists();
            int blocks = team.getTeam().get(i).getPlayerStats().getBlocks();
            int aces = team.getTeam().get(i).getPlayerStats().getAces();
            Object[] columnNames = {number, name, digs, kills, assists, blocks, aces};
            tableModel.addRow(columnNames);
        }
        return table;
    }

    // REQUIRES:
    // EFFECTS: creates a JPanel that lets the user edit a player
    private JPanel editPlayer() {
        JPanel organizer = new JPanel();
        JLabel message = new JLabel("Enter the player Number:");
        editNumberField = new JTextField(4);
        JButton editPlayer = new JButton("Edit Player");
        editPlayer.addActionListener(e -> editPlayerPanel());
        editPlayer.setBackground(Color.WHITE);
        organizer.add(message);
        organizer.add(editNumberField);
        organizer.add(editPlayer);
        return organizer;
    }

    // EFFECTS: checks to see if the inputted player is on the team before editing a player
    private void editPlayerPanel() {
        int numField = 0;
        if (editNumberField.getText().equals("")) {
            viewRosterPanel(-1);
        }
        try {
            numField = Integer.parseInt(editNumberField.getText());
        } catch (NumberFormatException e) {
            viewRosterPanel(-1);
        }
        
        boolean containsPlayer = checkTeam(numField);
        if (!containsPlayer) {
            viewRosterPanel(-1);
        } else {
            editPlayerSelection(numField);
        }

    }

    // REQUIRES:
    // EFFECTS:
    // MODIFIES:
    private boolean checkTeam(int numField) {
        for (int i = 0; i < team.numPlayers(); i++) {
            if (numField == team.getTeam().get(i).getPlayerNumber()) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: creates a new JPanel that lets the user select what they would like to edit about the player
    private void editPlayerSelection(int numField) {
        JPanel organizer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        organizer.setBorder(BorderFactory.createTitledBorder("Edit Player"));
        JLabel message = new JLabel("Selected: #" + team.getTeam().get(getPlayerPosition(numField)).getPlayerNumber()
                                    + " " + team.getTeam().get(getPlayerPosition(numField)).getPlayerName());
        organizer.add(message);
        getContentPane().removeAll();
        JPanel buttonOrganizer = new JPanel();
        JButton editPlayerName = new JButton("Edit Name");
        JButton editPlayerNumber = new JButton("Edit Number");
        JButton deletePlayer = new JButton("Delete Player");
        deletePlayer.setBackground(Color.RED);
        buttonOrganizer.add(editPlayerName);
        buttonOrganizer.add(editPlayerNumber);
        buttonOrganizer.add(deletePlayer);
        organizer.add(buttonOrganizer);
        addEditPlayerButtonListeners(editPlayerName, editPlayerNumber, deletePlayer, getPlayerPosition(numField));
        getContentPane().removeAll();
        refreshGui(organizer);
        addButtonListeners();
    }

    // EFFECTS: adds a button listener to edit player name button, edit player number button, delete player button
    private void addEditPlayerButtonListeners(JButton editPlayerName, JButton editPlayerNumber,
                                              JButton deletePlayer, int playerPos) {
        editPlayerName.addActionListener(e -> editNamePanel(playerPos));
        editPlayerNumber.addActionListener(e -> editNumberPanel(playerPos));
        deletePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePlayerPanel(playerPos);
                EventLog.getInstance().logEvent(new Event(team.getTeam().get(playerPos).getPlayerName()
                                        + " has been deleted"));
            }
        });
    }

    // EFFECTS: creates a JPanel that lets the user change a players name
    private void editNamePanel(int playerPos) {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Edit Player Name"));
        JLabel message = new JLabel("Enter new Name: ");
        newName = new JTextField(20);
        JButton newNameButton = new JButton("Edit Name");
        organizer.add(message);
        organizer.add(newName);
        organizer.add(newNameButton);
        refreshGui(organizer);
        newNameButton.addActionListener(e -> nameEditedPanel(playerPos));
        addButtonListeners();
    }

    // EFFECTS: creates a JPanel informing the user what the name has been changed to
    private void nameEditedPanel(int playerPos) {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Edit Player Name"));
        VolleyballPlayer player = team.getTeam().get(playerPos);
        String oldName = player.getPlayerName();
        team.getTeam().get(playerPos).setPlayerName(newName.getText());
        JLabel message = new JLabel(oldName + "'s name has been changed to "
                + team.getTeam().get(playerPos).getPlayerName());

        organizer.add(message);
        refreshGui(organizer);
        addButtonListeners();
    }

    // EFFECTS: creates a JPanel that lets the user edit a players number
    private void editNumberPanel(int playerPos) {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Edit Player Number"));
        JLabel message = new JLabel("Enter new Number: ");
        newNumber = new JTextField(4);
        JButton newNumberButton = new JButton("Edit Number");
        organizer.add(message);
        organizer.add(newNumber);
        organizer.add(newNumberButton);
        refreshGui(organizer);
        newNumberButton.addActionListener(e -> numberEditedPanel(playerPos));
    }

    // EFFECTS: creates a JPanel that informs the user what the selected player's number has been changes to
    private void numberEditedPanel(int playerPos) {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Edit Player Number"));
        int updatedNumber = Integer.parseInt(newNumber.getText());
        team.getTeam().get(playerPos).setPlayerNumber(updatedNumber);
        JLabel message = new JLabel("Number for " + team.getTeam().get(playerPos).getPlayerName() + " has been updated"
                + " to " + team.getTeam().get(playerPos).getPlayerNumber());
        organizer.add(message);
        refreshGui(organizer);
        addButtonListeners();
    }

    // EFFECTS: informs the user if a player has been successfully deleted
    private void deletePlayerPanel(int playerPos) {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Delete Player"));
        JLabel message;
        VolleyballPlayer player = team.getTeam().get(playerPos);
        team.getTeam().remove(playerPos);
        if (team.getTeam().contains(player)) {
            message = new JLabel("Error: Delete player has failed");
            message.setForeground(Color.RED);
        } else {
            message = new JLabel(player.getPlayerName() + " has been deleted");
        }
        organizer.add(message);
        refreshGui(organizer);
        addButtonListeners();
    }

    // EFFECTS: returns the players position on a list
    private int getPlayerPosition(int numField) {
        for (int i = 0; i < team.numPlayers(); i++) {
            if (team.getTeam().get(i).getPlayerNumber() == numField) {
                return i;
            }
        }
        return -1;
    }

    // EFFECTS: creates a JPanel that allows users to add a player
    private void addPlayerPanel(int status) {
        JPanel organizer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        organizer.setBorder(BorderFactory.createTitledBorder("Add Player: "));
        JPanel nameInfo = new JPanel();
        JLabel nameLabel = new JLabel("Input your name here");
        nameTextField = new JTextField(20);
        nameInfo.add(nameLabel);
        nameInfo.add(nameTextField);
        JLabel numberLabel = new JLabel("Input your player's number here: ");
        numberTextField = new JTextField(14);
        JPanel numberInfo = new JPanel();
        numberInfo.add(numberLabel);
        numberInfo.add(numberTextField);
        organizer.add(nameInfo);
        organizer.add(numberInfo);
        JButton addButton = new JButton("Add Player");
        addButton.setBackground(Color.WHITE);
        organizer.add(addButton);
        if (status == -1) {
            JLabel errorMessage = new JLabel("Error: Player Number Taken");
            errorMessage.setForeground(Color.RED);
            organizer.add(errorMessage);
        }

        addPlayerPanelExtended(organizer, addButton);

    }

    // EFFECTS: helps create a Jpanel that allows users to add a player
    private void addPlayerPanelExtended(JPanel organizer, JButton addButton) {
        refreshGui(organizer);
        addPlayerButtonListener(addButton);
        addButtonListeners();
    }

    // EFFECTS: adds a button listener to the add player button
    private void addPlayerButtonListener(JButton addButton) {
        addButton.addActionListener(e -> playerSetPanel());
    }

    // EFFECTS: creates a panel that lets the user add a player
    private void playerSetPanel() {
        JPanel organizer = new JPanel();
        boolean containsNumber = false;
        int playerNumber = Integer.parseInt(numberTextField.getText());
        organizer.setBorder(BorderFactory.createTitledBorder("Add Player"));
        for (int i = 0; i < team.numPlayers(); i++) {
            if (playerNumber == team.getTeam().get(i).getPlayerNumber()) {
                containsNumber = true;
            }
        }
        playerSetPanelExtended(containsNumber, playerNumber, organizer);
    }

    // EFFECTS: creates a panel that lets the user add a player
    private void playerSetPanelExtended(boolean containsNumber, int playerNumber, JPanel organizer) {
        if (!containsNumber) {
            String playerName = nameTextField.getText();
            team.addPlayer(new VolleyballPlayer(playerNumber, playerName));
            String output = playerName + " with the number " + playerNumber + " has been added to the Roster";
            JLabel message = new JLabel(output);
            organizer.add(message);
            refreshGui(organizer);
            addButtonListeners();
        } else {
            addPlayerPanel(-1);
        }
    }

    // EFFECTS: Saves team as a JSON file
    private void saveTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(team);
            jsonWriter.close();
            String message = "\nSaved " + team.getTeamName() + " to " + JSON_STORE;
            JPanel organizer = new JPanel();
            organizer.setBorder(BorderFactory.createTitledBorder("Save Status"));
            organizer.add(new JLabel(message));
            refreshGui(organizer);
            addButtonListeners();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: loads team from a JSON file
    private void loadTeam() {
        try {
            team = jsonReader.read();
            String message = "Loaded " + team.getTeamName() + " from " + JSON_STORE;
            JPanel organizer = new JPanel();
            organizer.setBorder(BorderFactory.createTitledBorder("Load Status"));
            organizer.add(new JLabel(message));
            refreshGui(organizer);
            addButtonListeners();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: creates a user interface where users can see and record stats
    private void startGame(int status) {
        JPanel organizer = new JPanel();
        organizer.setBorder(BorderFactory.createTitledBorder("Game in Session"));
        organizer.add(constructRoster());
        JLabel message = new JLabel("Enter the player number who you would like to record stats for:");
        getNumField = new JTextField(4);
        JButton editPlayer = new JButton("Record Stats");
        JPanel organizer1 = new JPanel();
        organizer1.add(message);
        organizer1.add(getNumField);
        organizer1.add(editPlayer);
        organizer.add(organizer1);
        organizer.add(startGameOptions(status));
        startGameExtended(organizer, editPlayer);
    }

    // EFFECTS: creates a user interface where users can see and record stats
    private void startGameExtended(JPanel organizer, JButton editPlayer) {
        refreshGui(organizer);
        addButtonListeners();
        editPlayer.addActionListener(e -> recordStats());
    }

    // EFFECTS: indicates whether or not a stat was added
    @SuppressWarnings("methodlength")
    private JLabel startGameOptions(int status) {
        JLabel message;
        switch (status) {
            case -1:
                message = new JLabel("Invalid number entered");
                System.out.print("Hello");
                message.setForeground(Color.RED);
                break;
            case 1:
                message = new JLabel("Dig Added");
                message.setForeground(Color.GREEN);
                break;
            case 2:
                message = new JLabel("Kill Added");
                message.setForeground(Color.GREEN);
                break;
            case 3:
                message = new JLabel("Assist Added");
                message.setForeground(Color.GREEN);
                break;
            case 4:
                message = new JLabel("Block Added");
                message.setForeground(Color.GREEN);
                break;
            case 5:
                message = new JLabel("Ace Added");
                message.setForeground(Color.GREEN);
                break;
            default:
                message = new JLabel("");
                break;
        }
        return message;
    }

    // EFFECTS: creates a JPanel that selects what stat should be added
    private void recordStats() {
        JPanel organizer = new JPanel();
        int playerPos = Integer.parseInt(getNumField.getText());
        int position = getPlayerPosition(playerPos);
        if (position == -1 || getNumField.getText().equals("")) {
            startGame(-1);
        } else {
            organizer.setBorder(BorderFactory.createTitledBorder(team.getTeam().get(position).getPlayerName()
                    + "'s Stats"));
            JButton addDig = new JButton("Add Dig");
            JButton addKill = new JButton("Add Kill");
            JButton addAssist = new JButton("Add Assist");
            JButton addBlock = new JButton("Add Block");
            JButton addAce = new JButton("Add Ace");
            recordStatsExtended(organizer, addDig, addKill, addAssist, addBlock, addAce);
        }
    }

    // EFFECTS: creates a JPanel that selects what stat should be added
    private void recordStatsExtended(JPanel organizer, JButton addDig, JButton addKill,
                                     JButton addAssist, JButton addBlock, JButton addAce) {
        organizer.add(addDig);
        organizer.add(addKill);
        organizer.add(addAssist);
        organizer.add(addBlock);
        organizer.add(addAce);
        refreshGui(organizer);
        int pos = Integer.parseInt(getNumField.getText());
        addSkillListeners(addDig, addKill, addAssist, pos);
        addSkillListeners2(addBlock, addAce, pos);
    }

    // EFFECTS: adds button listeners to add dig, add kill, add assist buttons
    private void addSkillListeners(JButton addDig, JButton addKill, JButton addAssist, int pos) {
        addDig.addActionListener(e -> {
            team.getTeam().get(getPlayerPosition(pos)).addDig();
            startGame(1);
        });
        addKill.addActionListener(e -> {
            team.getTeam().get(getPlayerPosition(pos)).addKill();
            startGame(2);
        });
        addAssist.addActionListener(e -> {
            team.getTeam().get(getPlayerPosition(pos)).addAssist();
            startGame(3);
        });
    }

    // EFFECTS: Adds button listener to addBlock, addAce buttons
    private void addSkillListeners2(JButton addBlock, JButton addAce, int pos) {
        addBlock.addActionListener(e -> {
            team.getTeam().get(getPlayerPosition(pos)).addBlock();
            startGame(4);
        });
        addAce.addActionListener(e -> {
            team.getTeam().get(getPlayerPosition(pos)).addAce();
            startGame(5);
        });
    }

    // EFFECTS: refreshes gui JFrame
    // MODIFIES: this
    public void refreshGui(JPanel organizer) {
        getContentPane().removeAll();
        super.add(sideButtons(), BorderLayout.EAST);
        super.add(mainMenu(), BorderLayout.NORTH);
        super.add(organizer, BorderLayout.CENTER);
        invalidate();
        validate();
    }

}



