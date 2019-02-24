package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class Task2 {
	
	public <K> void saveResult(List<Entry<K, BigDecimal>> list, String fname){
		SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
		try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fname)))){
			for(Entry<K, BigDecimal> el: list){
				K key = el.getKey();
				String str;
				if(key.getClass()==Date.class){
					str = dfm.format(key);
				}
				else{
					str = key.toString();
				}
				writer.write(str + " " + el.getValue().toString());
				writer.newLine();
			}
		}
		catch(IOException ex){
			System.out.println("Error writing in file " + fname);
			ex.printStackTrace();
		}
	}
	
	public int process(String dateFile, String officeFile, List<String> inputFiles){
		int ret = 0;
		Map<Date,BigDecimal> statDate = new TreeMap<>();
		Map<Integer, BigDecimal> statOffice = new HashMap<>();
		for(String fname: inputFiles){
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fname)))){
	            String line;
	            while ((line = reader.readLine()) != null) {
	            	String[] parts = line.split(" ");
	            	try{
		            	Integer office = Integer.valueOf(parts[0]);
		            	long time = Long.parseLong(parts[1]);
		            	Date date = new Date((time/(24*60*60*1000))*(24*60*60*1000));
		            	BigDecimal amount = new BigDecimal(parts[2]).setScale(2, BigDecimal.ROUND_HALF_UP);
		            	BigDecimal sum = statDate.get(date);
		            	sum = sum==null ? amount:sum.add(amount);
		            	statDate.put(date, sum);
		            	sum = statOffice.get(office);
		            	sum = sum==null ? amount:sum.add(amount);
		            	statOffice.put(office, sum);
		            	ret++;
	            	}
	            	catch(NumberFormatException ex){
	            		System.out.println("Error parsing data: " + line);
	            		ex.printStackTrace();
	            	}
	            }
			}catch(IOException ex){
				System.out.println("Error reading from file " + fname);
				ex.printStackTrace();
			}
		}
		List<Map.Entry<Integer, BigDecimal>> list = new ArrayList<>(statOffice.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, BigDecimal>>() {
			@Override
			public int compare(Map.Entry<Integer, BigDecimal> a, Map.Entry<Integer, BigDecimal> b) {
				return b.getValue().compareTo(a.getValue());
			}
		});
		saveResult(list, officeFile);
		saveResult(new ArrayList<>(statDate.entrySet()), dateFile);
		return ret;
	}
	
	public static void main(String[] args){
		if(args.length<3){
			System.out.println("Argument absents!");
			return;
		}
		Task2 t = new Task2();
		long st = System.currentTimeMillis();
		int cn = t.process(args[0], args[1], Arrays.asList(Arrays.copyOfRange(args,2,args.length)));
		long et = System.currentTimeMillis();
		System.out.println("Processed records: " + cn + " duration: " + (et-st)/1000.0 + "c");
	}
}
