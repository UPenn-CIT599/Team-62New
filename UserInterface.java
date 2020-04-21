import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



/**
 * This class creates the user interface container (JPanel) that is placed inside the application window, and runs the application window.
 * @author gracepark
 *
 */
@SuppressWarnings("serial")
public class UserInterface extends JPanel {
    JFrame frame;
    JPanel graphPanel;
    TickerMap tickerMap;
    JList<String> tickerList;
    boolean tickerSelectedFlag;
    JList<String> timeSeriesList;
    boolean timeSelectedFlag;
    JButton okButton;
   
    

    public UserInterface(JFrame frame) {
        this.frame = frame;
        
        //Create JPanel container for chart to be placed in
        graphPanel = new JPanel();
        graphPanel.setPreferredSize(new Dimension(800, 500));
        graphPanel.setLayout(new GridLayout(1,1));
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        graphPanel.setBorder(blackLine);
        
        
        //Create ticker list and put it in a scroll pane 
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
        tickerList.addMouseListener(new TickerMouseListener());
        tickerSelectedFlag = false;
        
        
        //Create time series list and put it in a scroll pane
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
        

        //Create OK Button and set up handler
        okButton = new JButton("OK");
        okButton.addActionListener(new OkButtonHandler());
        okButton.setEnabled(false);

        
        //Customize layout of UserInterface JPanel 
        setLayout(new BorderLayout(10, 10));
        add(graphPanel, BorderLayout.PAGE_START);
        add(tickerListScroller, BorderLayout.LINE_START);
        add(timeListScroller, BorderLayout.LINE_END);
        add(okButton, BorderLayout.PAGE_END);
    }
    
    
    /**
     * This inner class sets the "tickerSelectedFlag" to indicate that the user has selected a stock ticker, and then calls setButtonState() method. 
     * @author gracepark
     *
     */
    private class TickerListHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            tickerSelectedFlag = true;
            setButtonState();
        } 
    }
    
    
    /**
     * This event handling class sets the tooltip text of the ticker selection list displayed inside the UI window.
     * The tooltip text displays the company name of a specific ticker symbol when the user's mouse hovers over the ticker symbol. 
     * This class extends the abstract MouseAdapter class, which implements MouseListener interface to override the mouseEntered method. 
     * @author gracepark
     *
     */
    private class TickerMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
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
     * This inner class sets the "timeSelectedFlag" to indicate that the user has selected a time series, and then calls setButtonState() method. 
     * @author gracepark
     *
     */
    private class TimeListHandler implements ListSelectionListener {
        @Override
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
     * This event handling class sets the events that occur after a user presses the "OK" button. 
     * It creates objects of the StockDataReader and GraphConstructor classes. 
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
                    
                if (graphPanel.getComponentCount() > 0) graphPanel.remove(graphPanel.getComponent(0));
                GraphConstructor.main(stockTimes, stockValues, graphPanel);
                //GraphConstructor.main(stockTimes, stockValues, graphPanel, timeSeriesInt);
                graphPanel.validate();
            } catch (IOException e1) {
                //e1.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Unable to get stock data. Please check Internet connection.", "Message", JOptionPane.WARNING_MESSAGE);
            } catch (NullPointerException e1) {
                System.out.println(e1.getMessage());
                JOptionPane.showMessageDialog(frame, "Unable to get stock data. Please try again.", "Message", JOptionPane.WARNING_MESSAGE);
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
                    return 30;
                case "Year":
                    return 365;     
            }
            return 0;
        }
    }
    
    
    
    /**
     * This method creates and displays the application window. 
     * It also places an instance of the UserInterface JPanel inside the window.  
     */
    private static void createAndShowGUI() {
        //Create and set up the window
        //JFrame frame = new JFrame("Stock Visualizer");
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

