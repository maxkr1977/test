package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import test.model.Office;
import test.model.Opeartions;

public class Task1 {
	private List<Office> off = new ArrayList<>();
	private Integer countOperation;
	private final long minDate;
	private final long maxDate;
	
	private static final long MIN_AMOUNT = 1000012;
	private static final long MAX_AMOUNT = 10000051;
	
	
	public Task1(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		c.set(year, 0, 1);
		this.maxDate = c.getTimeInMillis()+1;
		c.set(year-1, 0, 1);
		this.minDate = c.getTimeInMillis();
	}
	
	private boolean addOffices(String fname){
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));){
            String line;
            while ((line = reader.readLine()) != null) {
    			Office o = new Office();
    			o.setCode(Integer.valueOf(line.split(" ")[0]));
    			o.setDescription(line.split(" ")[1]);
    			off.add(o);
            }
		} catch(IOException | NumberFormatException ex){
			System.out.println("Error reading Offices from file!");
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean setOpsCount(String count){
		try{
			countOperation = Integer.valueOf(count);
		}catch(NumberFormatException ex){
			System.out.println("Error parsing args[1] to Integer!");
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void process(List<String> outputFiles){
		List<BufferedWriter> wr = new ArrayList<>();
		for(String fn: outputFiles){
			try{
				
				wr.add(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fn))));
			}
			catch(IOException ex){
				System.err.println("Не удалось создать/открыть файл " + fn);
				ex.printStackTrace();
			}
		}
		int j=0;
		for(int i = 0; i<countOperation; i++){
			Opeartions o = new Opeartions();
			o.setDate(ThreadLocalRandom.current().nextLong(minDate, maxDate));
			o.setAmount(BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(MIN_AMOUNT, MAX_AMOUNT)/100.0).setScale(2, BigDecimal.ROUND_HALF_UP));
			o.setOffice(off.get(ThreadLocalRandom.current().nextInt(0, off.size())));
			try{
				wr.get(j).write(o.toString());
				wr.get(j).newLine();
				j++;
			}
			catch(IOException ex){
				System.out.println("Не удалось произвести запись в файл " + outputFiles.get(j));
				ex.printStackTrace();
			}
			if(j == wr.size()){
				j=0;
			}
		}
		j=0;
		try{
			for(BufferedWriter w: wr){
				w.close();
				j++;
			}
		}
		catch(IOException ex){
			System.out.println("Ошибка при закрытии файла " + outputFiles.get(j));
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		if(args.length<3){
			System.out.println("Не хватает аргументов!");
			return;
		}
		Task1 gd = new Task1();
		if(gd.addOffices(args[0]) && gd.setOpsCount(args[1])){
			long st = System.currentTimeMillis();
			gd.process(Arrays.asList(Arrays.copyOfRange(args,2,args.length)));
			long et = System.currentTimeMillis();
			System.out.println(String.format("Сгенерировано %1$s операций за %2$sс", args[1], (et-st)/1000.0));
		}
	}
}
