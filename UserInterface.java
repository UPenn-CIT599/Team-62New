import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



/**
 * This class creates the user interface container (JPanel) that is placed inside the application window, and runs the application window.
 * @author gracepark
 *
 */
@SuppressWarnings("serial")
public class UserInterface extends JPanel implements ItemListener {
    private JFrame frame;
    private JPanel graphPanel;
    private TickerMap tickerMap;
    private JList<String> tickerList;
    private boolean tickerSelectedFlag;
    private JList<String> timeSeriesList;
    private boolean timeSelectedFlag;
    private boolean trendLineFlag;
    private JButton okButton;
    private Timer timer;
    private JLabel timerLabel;
    private boolean timerFlag;
   
    

    public UserInterface(JFrame frame) {
        this.frame = frame;
        
        //Create JPanel container for chart to be placed in
        graphPanel = new JPanel();
        graphPanel.setPreferredSize(new Dimension(800, 500));
        graphPanel.setLayout(new GridLayout(1,1));
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        graphPanel.setBorder(blackLine);
        
        //Create ticker list, put it in a scroll pane, and add listener 
        tickerMap = new TickerMap();
        String tickers[] = tickerMap.getTickerArray();
        DefaultListModel<String> tickerListModel = new DefaultListModel<String>();
        for (int i = 0; i < tickers.length; i++) 
            tickerListModel.addElement(tickers[i]);
        tickerList = new JList<String>(tickerListModel);
        tickerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tickerList.setVisibleRowCount(-1);
        JScrollPane tickerListScroller = new JScrollPane(tickerList);
        tickerListScroller.setPreferredSize(new Dimension(250, 80));
        tickerList.addListSelectionListener(new TickerListHandler());
        tickerList.addMouseMotionListener(new TickerMouseListener());
        tickerSelectedFlag = false;
        ToolTipManager.sharedInstance().setInitialDelay(0);
        
        //Create time series list, put it in a scroll pane, and add listener
        DefaultListModel<String> timeListModel = new DefaultListModel<String>();
        String timeSeries[] = {"Day", "Week", "Month", "Year"};
        for (int i = 0; i < timeSeries.length; i++)
            timeListModel.addElement(timeSeries[i]);
        timeSeriesList = new JList<String>(timeListModel);
        timeSeriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        timeSeriesList.setVisibleRowCount(-1);
        JScrollPane timeListScroller = new JScrollPane(timeSeriesList);
        timeSeriesList.setPreferredSize(new Dimension(250, 80));
        timeSeriesList.addListSelectionListener(new TimeListHandler());
        timeSelectedFlag = false; 
        
        //Create trendline checkbox and add listener 
        JCheckBox trendBox = new JCheckBox("Trendline");
        trendBox.setHorizontalAlignment(SwingConstants.CENTER);
        trendBox.addItemListener(this);
        trendLineFlag = false;

        //Create timer label
        timerLabel = new JLabel("Wait Time: 0 sec", JLabel.CENTER);
        timerFlag = false;

        //Create OK Button and add listener
        okButton = new JButton("OK");
        okButton.addActionListener(new OkButtonHandler());
        okButton.setEnabled(false);
        okButton.setPreferredSize(new Dimension(10, 40));
         
        //Customize layout of UserInterface JPanel 
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(0,1));
        bottomPanel.add(timerLabel);
        bottomPanel.add(okButton);
        setLayout(new BorderLayout(10, 10));
        add(graphPanel, BorderLayout.PAGE_START);
        add(tickerListScroller, BorderLayout.LINE_START);
        add(timeListScroller, BorderLayout.LINE_END);
        add(trendBox, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
    }
    
    
    /**
     * This inner class is the listener that's notified when a ticker symbol list selection value changes. 
     * It sets the "tickerSelectedFlag" to indicate that the user has selected a stock ticker, and enables the okButton button if appropriate.
     * It implements Java Swing's ListSelectionListener interface to override the valueChanged() method. 
     * @author gracepark
     *
     */
    private class TickerListHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            tickerSelectedFlag = true;
            if (tickerSelectedFlag && timeSelectedFlag && !timerFlag) 
                okButton.setEnabled(true);
        } 
    }
    
    
    /**
     * This event handling class sets the tooltip texts of the ticker selection list.
     * The tooltip text displays the company name of a specific ticker symbol when the user's mouse hovers over the ticker symbol. 
     * This class extends Java AWT's abstract MouseMotionAdapter class, which implements the MouseMotionListener interface, to only override the mouseEntered method. 
     * @author gracepark
     *
     */
    private class TickerMouseListener extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            @SuppressWarnings("unchecked")
            JList<String> list = (JList<String>) e.getSource();
            DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
            int index = list.locationToIndex(e.getPoint());
            if (index >= 0) {
                String temp = model.getElementAt(index);
                String companyName = tickerMap.getNameOfTicker(temp);
                list.setToolTipText(companyName);
            } 
        }
    }
 
    
    /**
     * This inner class is the listener that's notified when a time duration list selection value changes. 
     * It sets the "timeSelectedFlag" to indicate that the user has selected a time series, and enables the okButton button if appropriate.
     * It implements Java Swing's ListSelectionListener interface to override the valueChanged() method.  
     * @author gracepark
     *
     */
    private class TimeListHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            timeSelectedFlag = true;
            if (tickerSelectedFlag && timeSelectedFlag && !timerFlag) 
                okButton.setEnabled(true);
        } 
    }
    
    
    /**
     * This event handling method overrides the ItemListener interface method to handle the trendline checkbox.
     */
    @Override 
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == 1) {
            trendLineFlag = true;
        } else {
            trendLineFlag = false;
        }
    }
   
    
    /**
     * This event handling class sets the events that occur after a user presses the "OK" button. 
     * It creates objects of the StockDataReader and GraphConstructor classes. 
     * It catches exceptions thrown from StockDataReader class.
     * A NullPointerException indicates that the user exceeded the API call limit, and starts a timer.
     * @author gracepark
     *
     */
    private class OkButtonHandler implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            String ticker = (String) tickerList.getSelectedValue();
            String timeSeries = (String) timeSeriesList.getSelectedValue();
            int timeSeriesInt = convertTimeSeriesToInt(timeSeries);
            
            //Creates an instance of StockDataReader and uses its fetchData() method to get stock data stored in a TreeMap 
            StockDataReader stock = new StockDataReader(ticker, timeSeriesInt);
            
            try {
                HashMap<Integer, StockData> stockData = stock.fetchData();
                
                //Creates an instance of DataConverter and uses its methods to convert TreeMap data to stock time and stock value arrays
                DataConverter dataConvert = new DataConverter(stockData);
                String[] stockTimes = dataConvert.getStockTimes();
                double[] stockValues = dataConvert.getStockValues();
                
                //If the trendline checkbox is not selected, create an array of zeros. 
                //Otherwise, create another instance of DataConverter to fetch an array of trendline data.
                double[] trendlineValues = new double[stockValues.length];
                if (trendLineFlag) {
                    HashMap<Integer, StockData> trendlineData = stock.fetchTrendlineData();
                    DataConverter trendlineConvert = new DataConverter(trendlineData);
                    trendlineValues = trendlineConvert.getStockValues();
                } 
 
                if (graphPanel.getComponentCount() > 0) graphPanel.remove(graphPanel.getComponent(0)); //remove previous graph JPanel if any 
                GraphConstructor.main(stockTimes, stockValues, timeSeriesInt, trendLineFlag, trendlineValues, graphPanel);
                graphPanel.validate();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(frame, "Unable to get stock data. Please check Internet connection.", "Message", JOptionPane.WARNING_MESSAGE);
            } catch (NullPointerException e1) {
                //create a timer that goes off every 1 second to update the timer label
                //timer is stopped when secondsLeft variable is zero
                timer = new Timer(1000, new ActionListener() {
                    int currentSecond = LocalTime.now().getSecond();
                    int secondsLeft = 60 - currentSecond; //calculates the number of seconds left until next minute

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timerLabel.setText("Wait Time: " + secondsLeft + " sec");
                        timerFlag = true;
                        okButton.setEnabled(false); //disable okButton until timer is done
                        if (secondsLeft <= 0) {
                            timerFlag = false;
                            okButton.setEnabled(true); //enable okButton when timer is off
                            timer.stop();
                        }
                        secondsLeft -= 1;
                        
                    }
                });
                timer.start();
                JOptionPane.showMessageDialog(frame, "Unable to get stock data. Please try again later. "
                        + "\nAlpha Vantage limits API calls to 5 per minute.", "Message", JOptionPane.WARNING_MESSAGE);
            } 
 
        }
        
        
        
        /**
         * This method converts the timeSeries variable from String to integer format. 
         * @param timeSeries
         * @return integer of time series value
         */
        private int convertTimeSeriesToInt(String timeSeries) {
            switch(timeSeries) {
                case "Day": 
                    return 1;
                case "Week":
                    return 5;
                case "Month":
                    return 20   ;
                case "Year":
                    return 260;     
            }
            return 0;
        }
    }
    
    
    
    /**
     * This method creates and displays the application window. 
     * It places an instance of the UserInterface JPanel inside the window.  
     */
    private static void createAndShowGUI() {
        //Create and set up the window
        JFrame frame = new JFrame("Stock Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane
        UserInterface newPanel = new UserInterface(frame);
        newPanel.setOpaque(true);
        frame.setContentPane(newPanel);
        
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

