package org.celllife.idart.print.label;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocFlavor.INPUT_STREAM;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import org.celllife.idart.commonobjects.iDartProperties;
import org.celllife.idart.commonobjects.iDartProperties.LabelType;

public class PrintLabel
{
  public static int NEW_LABEL_WIDTH = 216;
  public static int EPL2_LABEL_WIDTH = 600;
  
  public static void printLinuxZebraLabels(List<DefaultLabel> labelsToPrint, PrintService service)
  {
    try
    {
      for (int i = 0; i < labelsToPrint.size(); i++)
      {
        File tmpFile = new File("tmp.raw");
        FileWriter fWriter = new FileWriter(tmpFile);
        
        Vector<String> commands = ((DefaultLabel)labelsToPrint.get(i)).getEPL2Commands();
        
        System.out.println(commands);
        for (int a = 0; a < commands.size(); a++) {
          fWriter.write((String)commands.elementAt(a));
        }
        fWriter.close();
        
        FileInputStream fs = new FileInputStream("tmp.raw");
        
        DocAttributeSet das = new HashDocAttributeSet();
        DocPrintJob job = service.createPrintJob();
        Doc ptDoc = new SimpleDoc(fs, DocFlavor.INPUT_STREAM.AUTOSENSE, das);
        try
        {
          job.print(ptDoc, null);
        }
        catch (PrintException e)
        {
          e.printStackTrace();
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void printWindowsLabels(List<Printable> labelsToPrint)
  {
    PageFormat pf = new PageFormat();
    Paper paper = new Paper();
    if (iDartProperties.labelType.equals(iDartProperties.LabelType.EKAPA)) {
      paper.setSize(285.0D, 135.0D);
    } else {
      paper.setSize(216.0D, 135.0D);
    }
    paper.setImageableArea(0.0D, 0.0D, paper.getWidth(), paper.getHeight());
    pf.setPaper(paper);
    pf.setOrientation(1);
    
    Book book = new Book();
    for (int i = 0; i < labelsToPrint.size(); i++)
    {
      Printable p = (Printable)labelsToPrint.get(i);
      book.append(p, pf);
    }
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPageable(book);
    
    boolean doPrint = job.printDialog();
    if (doPrint) {
      try
      {
        job.print();
      }
      catch (PrinterException e)
      {
        e.printStackTrace();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
  }
  
  public static void printiDARTLabels(List<Object> labelsToPrint)
    throws Exception
  {
    if (System.getProperty("os.name").toUpperCase().startsWith("WINDOWS"))
    {
      List<Printable> printableLabelList = new ArrayList();
      for (Object o : labelsToPrint) {
        printableLabelList.add((Printable)o);
      }
      printWindowsLabels(printableLabelList);
    }
    else if (!System.getProperty("os.name").toUpperCase().startsWith("LINUX"))
    {
      PrintService[] allServices = PrintServiceLookup.lookupPrintServices(null, null);
      
      PrintService serv = null;
      for (PrintService s : allServices) {
        if (s.getName().contains("Zebra")) {
          serv = s;
        }
      }
      if (serv != null)
      {
        List<DefaultLabel> defaultLabelList = new ArrayList();
        for (Object o : labelsToPrint) {
          defaultLabelList.add((DefaultLabel)o);
        }
        printLinuxZebraLabels(defaultLabelList, serv);
        return;
      }
      boolean foundZebra = false;
      for (int j = 0; j < allServices.length; j++)
      {
        PrintService service = allServices[j];
        if (service != null) {
          try
          {
            Runtime rt = Runtime.getRuntime();
            
            Process p = rt.exec(new String[] { "/bin/sh", "-c", "lpstat -v| grep '" + service.getName() + "' | grep Zebra" });
            
            InputStream inputstream = p.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            
            BufferedReader bufferedReader = new BufferedReader(inputstreamreader);
            
            String line = bufferedReader.readLine();
            bufferedReader.close();
            if (line != null)
            {
              foundZebra = true;
              List<DefaultLabel> defaultLabelList = new ArrayList();
              for (Object o : labelsToPrint) {
                defaultLabelList.add((DefaultLabel)o);
              }
              printLinuxZebraLabels(defaultLabelList, service);
              return;
            }
          }
          catch (IOException e)
          {
            e.printStackTrace();
          }
        }
      }
      if (!foundZebra)
      {
        List<Printable> printableLabelList = new ArrayList();
        for (Object o : labelsToPrint) {
          printableLabelList.add((Printable)o);
        }
        printWindowsLabels(printableLabelList);
      }
    }
  }
}
