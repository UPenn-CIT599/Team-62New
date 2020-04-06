import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * This class represents the user interface container (JPanel) that is placed inside the application window. 
 * @author gracepark
 *
 */
public class UserInterface extends JPanel {
    JPanel graphPanel;
    DefaultListModel<String> tickerListModel;
    JList<String> tickerList;
    JScrollPane tickerListScroller;
    boolean tickerSelectedFlag;
    DefaultListModel<String> timeListModel;
    JList<String> timeSeriesList;
    JScrollPane timeListScroller;
    boolean timeSelectedFlag;
    JButton okButton;


    public UserInterface() {
        //Create JPanel container for chart to be placed in
        graphPanel = new JPanel();
        graphPanel.setPreferredSize(new Dimension(800, 500));
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        graphPanel.setBorder(blackLine);
        
        
        //Create ticker list and put it in a scroll pane 
        tickerListModel = new DefaultListModel<String>();
        String tickers[] = {"VOO", "MSFT", "AAPL", "AMZN", "FB", "GOOGL"};
        for (int i = 0; i < tickers.length; i++) 
            tickerListModel.addElement(tickers[i]);
        tickerList = new JList<String>(tickerListModel);
        tickerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tickerList.setVisibleRowCount(-1);
        
        tickerListScroller = new JScrollPane(tickerList);
        tickerListScroller.setPreferredSize(new Dimension(250, 80));
        tickerList.addListSelectionListener(new tickerListHandler());
        tickerSelectedFlag = false;
        
        
        //Create time series list and put it in a scroll pane
        timeListModel = new DefaultListModel<String>();
        String timeSeries[] = {"Day", "Week", "Month", "Year"};
        for (int i = 0; i < timeSeries.length; i++)
            timeListModel.addElement(timeSeries[i]);
        timeSeriesList = new JList<String>(timeListModel);
        timeSeriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        timeSeriesList.setVisibleRowCount(-1);
        
        timeListScroller = new JScrollPane(timeSeriesList);
        timeSeriesList.setPreferredSize(new Dimension(250, 80));
        timeSeriesList.addListSelectionListener(new timeListHandler());
        timeSelectedFlag = false;
        

        //Create OK Button and set up handler
        okButton = new JButton("OK");
        okButton.addActionListener(new OkButtonHandler());
        okButton.setEnabled(false);

        
        //Customize layout 
        setLayout(new BorderLayout(10, 10));
        add(graphPanel, BorderLayout.PAGE_START);
        add(tickerListScroller, BorderLayout.LINE_START);
        add(timeListScroller, BorderLayout.LINE_END);
        add(okButton, BorderLayout.PAGE_END);
    }
    
    
    /**
     * This class sets the "tickerSelectedFlag" to indicate that the user has selected a stock ticker, and then calls setButtonState() method. 
     * @author gracepark
     *
     */
    class tickerListHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            tickerSelectedFlag = true;
            setButtonState();
        } 
    }
    
    /**
     * This inner class sets the "timeSelectedFlag" to indicate that the user has selected a time series, and then calls setButtonState() method. 
     * @author gracepark
     *
     */
    class timeListHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            timeSelectedFlag = true;
            setButtonState();
        } 
    }
    
    /**
     * This method enables the "OK" button if the user has selected both a stock ticker and time series.
     */
    private void setButtonState() {
        if (tickerSelectedFlag && timeSelectedFlag) 
            okButton.setEnabled(true);
    }
   
    
    /**
     * This inner class handles the "OK" button. It utilizes the other two classes. 
     * @author gracepark
     *
     */
    class OkButtonHandler implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String ticker = (String) tickerList.getSelectedValue();
            String timeSeries = (String) timeSeriesList.getSelectedValue();
            int timeSeriesInt = convertTimeSeriesToInt(timeSeries);
            
            //Creates an instance of StockDataReader and uses its fetchData() method to get stock data stored in a HashMap 
            StockDataReader stock = new StockDataReader(ticker, timeSeriesInt);
            HashMap<Integer, StockData> stockData = stock.fetchData(); 
            
            //Creates an instance of DataConverter and uses its methods to convert HashMap data to stock time and stock value arrays
            DataConverter dataConvert = new DataConverter(stockData);
            String[] stockTimes = dataConvert.getStockTimes();
            double[] stockValues = dataConvert.getStockValues();
            
            //Creates an instance of GraphConstructor and adds the returned graph to graphPanel 
            GraphConstructor gc = new GraphConstructor();
            gc.setStockTimes(stockTimes); 
            gc.setStockValues(stockValues);
            graphPanel.add(gc);
           
        }
        
        /**
         * This method converts the timeSeries variable from String format to integer format. 
         * @param timeSeries
         * @return
         */
        int convertTimeSeriesToInt(String timeSeries) {
            return 0;
        }
    }
    
    
    
    /**
     * This method creates and displays the application window. 
     * It also places an instance of the UserInterface JPanel inside the window.  
     */
    private static void createAndShowGUI() {
        //Create and set up the window
        JFrame frame = new JFrame("Stock Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane
        UserInterface newContentPane = new UserInterface();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        
        //Display the window
        frame.pack();
        frame.setVisible(true);
    }
    
    
    
    /**
     * This is the main method for the UserInterface class and the entire program. 
     * @param args
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
