
/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 7.0
 */
public class LogAnalyzer
{
    public static final int HOURS_PER_DAY = 24;
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    private int successfulCount;
    private int forbiddenCount;  
    private int notFoundCount;   
    private int otherErrorCount;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[HOURS_PER_DAY];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
        
        successfulCount = 0;
        forbiddenCount = 0;
        notFoundCount = 0;
        otherErrorCount = 0;    
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {   
        successfulCount = 0;
        forbiddenCount = 0;
        notFoundCount = 0;
        otherErrorCount = 0;   
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
            
            int status = entry.getStatus(); 
            switch (status) {
                case 200: 
                    successfulCount++;      
                    break;                 
                case 403:                
                    forbiddenCount++;       
                    break;                   
                case 404:             
                    notFoundCount++;      
                    break;                  
                default:                  
                    otherErrorCount++;   
                    break;
                }
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    public void printStatusSummary() // new
    { // new
        int total = successfulCount + forbiddenCount + notFoundCount + otherErrorCount; 
        System.out.println("\nAccess Status Summary:");
        System.out.println("----------------------");
        System.out.println("Total accesses: " + total); 
        System.out.println("Successful (200): " + successfulCount);
        System.out.println("Forbidden (403): " + forbiddenCount); 
        System.out.println("Not Found (404): " + notFoundCount); 
        System.out.println("Other Errors: " + otherErrorCount); 
    }
}
