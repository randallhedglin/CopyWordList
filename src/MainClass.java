import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;

public class MainClass
{
	static Robot            robot;
	static String           string;
	static Clipboard        clipboard;
	static File             file;
	static FileOutputStream fileout;

	static final int delay=10;

	public static void main(String[] args)
	{
		try
		{
			robot     =new Robot();
			string    =new String();
			clipboard =Toolkit.getDefaultToolkit().getSystemClipboard();
			file      =new File("C:\\Users\\Randall Hedglin\\Desktop\\output.txt");

			file.createNewFile();

			fileout=new FileOutputStream(file);
		}
		catch(Throwable e)
		{
			System.out.println("Failed to initialize.");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Robot successfully created.\n");
		System.out.println("Click in the word list edit box.  You have 5 seconds.\n");
		
		robot.delay(5000);
		
		while(true)
		{
			PressKey(KeyEvent.VK_A,
					 KeyEvent.VK_CONTROL);
			
			PressKey(KeyEvent.VK_INSERT,
					 KeyEvent.VK_CONTROL);
			
			Transferable contents=clipboard.getContents(null);
			
		    boolean bTransferable=(contents!=null)&&
		    					   contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		    
		    if(bTransferable)
		    {
		    	try
		    	{
		    		string=(String)contents.getTransferData(DataFlavor.stringFlavor);
		    	}
				catch(Throwable e)
				{
					System.out.println("\nInvalid clipboard data.");
					e.printStackTrace();
					return;
				}
		    }			
		
	    	try
	    	{
			    fileout.write(string.getBytes());
			    fileout.write('\r');
			    fileout.write('\n');
	    	}
			catch(Throwable e)
			{
				System.out.println("\nFailed to write output.");
				e.printStackTrace();
				return;
			}
			
			System.out.println(string);
			
			if(string.equals("zz"))
				break;

			PressKey(KeyEvent.VK_TAB);
			
			PressKey(KeyEvent.VK_DOWN);
			
			PressKey(KeyEvent.VK_TAB,
					 KeyEvent.VK_SHIFT);
		}
		
    	try
    	{
    		fileout.close();
    	}
		catch(Throwable e)
		{
			System.out.println("\nFailed to close output file.");
			e.printStackTrace();
			return;
		}
    	
		System.out.println("\nFile output complete.  Have a nice day.");
	}
	
	public static boolean PressKey(int keycode1)
	{
		return(PressKey(keycode1,
						0,
						0));
	}
	
	public static boolean PressKey(int keycode1,
			   					   int keycode2)
	{
		return(PressKey(keycode1,
						keycode2,
						0));
	}
	
	public static boolean PressKey(int keycode1,
								   int keycode2,
								   int keycode3)
	{
		if(keycode3!=0)
		{
			robot.keyPress(keycode3);
			robot.delay(delay);
		}

		if(keycode2!=0)
		{
			robot.keyPress(keycode2);
			robot.delay(delay);
		}

		robot.keyPress(keycode1);
		robot.delay(delay);

		robot.keyRelease(keycode1);
		robot.delay(delay);

		if(keycode2!=0)
		{
			robot.keyRelease(keycode2);
			robot.delay(delay);
		}

		if(keycode3!=0)
		{
			robot.keyRelease(keycode3);
			robot.delay(delay);
		}

		robot.waitForIdle();
		
		return(true);
	}
}