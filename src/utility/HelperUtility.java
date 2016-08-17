package utility;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;

import org.apache.commons.collections.map.StaticBucketMap;
import org.apache.log4j.Logger;

import sun.util.logging.resources.logging;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.org.glassfish.external.statistics.Statistic;

public class HelperUtility {
	private static Timer screenSaverDisabler;
	public static String driverType = "";
	public static String testType = ""; // create, maintain, ccg.
	public static String defaultAuthCode = "";//
	public static String responseString = "";
	private int delayTime;
	public Logger log = Logger.getRootLogger();
	
	
	
	

	/**
	 * disable screen saver
	 */
	public static void disableScreenSaver() {
		screenSaverDisabler = new Timer();
		screenSaverDisabler.scheduleAtFixedRate(new TimerTask() {
			Robot r = null;
			// initialization block
			{
				try {
					r = new Robot();
				} catch (AWTException headlessEnvironmentException) {
					screenSaverDisabler.cancel();
				}
			}

			@Override
			public void run() {
				java.awt.Point loc = MouseInfo.getPointerInfo().getLocation();
				r.mouseMove(loc.x + 1, loc.y);
				r.mouseMove(loc.x, loc.y);
			}
		}, 0, 59 * 1000);
	}
	/**
	 * put thread on sleep 
	 * @param time
	 */
	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.println("In HelperUtility.sleep()");
		}
	}
	
		
	
		
	
	
}
